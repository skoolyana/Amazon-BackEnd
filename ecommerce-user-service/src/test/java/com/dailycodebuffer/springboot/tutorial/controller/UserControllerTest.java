package com.dailycodebuffer.springboot.tutorial.controller;

import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.http.MediaType;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.core.Response;
import com.dailycodebuffer.springboot.tutorial.dto.UserDto;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.UserObjectNotFoundException;
import com.dailycodebuffer.springboot.tutorial.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@AutoConfigureJsonTesters
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@MockBean
	private UserService userService;

	@Autowired
	private MockMvc mvc;

	// This object will be magically initialized by the initFields method below.
	private JacksonTester<UserDto> json;

	// This object will be magically initialized by the initFields method below.
	private JacksonTester<Collection<UserDto>> jsonCollective;

	private UserDto userDto;

	@BeforeEach
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
		userDto = UserDto.builder().email("skulyana@gmail.com").firstName("Sunil").lastName("Kulyana")
				.imageUrl("google.com").phone("111").build();

	}

	@Test
	void shouldFetchAllUsers() throws Exception {

		given(userService.findAll()).willReturn(List.of(userDto));

		// when
		MockHttpServletResponse response = mvc.perform(get("/api/users").accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

	}

	@Test
	void shouldFetchUserById() throws Exception {

		given(userService.findById(1)).willReturn(Optional.of(userDto).get());

		Integer userId = 1;

		// when
		MockHttpServletResponse response = mvc
				.perform(get("/api/users/{userId}", userId).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(response.getContentAsString()).isEqualTo(json.write(userDto).getJson());

	}

	@Test
	void shouldReturn404WhenFetchUserById_Does_Not_Exist() throws Exception {

		given(userService.findById(1)).willThrow(UserObjectNotFoundException.class);

		Integer userId = 1;

		// when
		MockHttpServletResponse response = mvc
				.perform(get("/api/users/{userId}", userId).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());

	}
	
	@Test
	void shouldFetchUserByName() throws Exception {

		given(userService.findByUsername("skulyana")).willReturn(Optional.of(userDto).get());

		Integer userId = 1;
		
		String username = "skulyana";

		// when
		MockHttpServletResponse response = mvc
				.perform(get("/api/users/username/{username}", username).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(response.getContentAsString()).isEqualTo(json.write(userDto).getJson());

	}

	
	@Test
	void shouldReturn404WhenFetchUserByName_Does_Not_Exist() throws Exception {

		given(userService.findByUsername("skulyana")).willThrow(UserObjectNotFoundException.class);

		String username = "skulyana";

		// when
		MockHttpServletResponse response = mvc
				.perform(get("/api/users/username/{username}", username).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());

	}

	

	// JUnit test for createUser method in Controller
	@DisplayName("JUnit test for createUser method")
	@Test
	public void shouldCreateNewUser() throws Exception {

		given(userService.save(userDto)).willReturn(userDto);

		// when
		MockHttpServletResponse response = mvc.perform(
				post("/api/users").contentType(MediaType.APPLICATION_JSON).content(json.write(userDto).getJson()))
				.andReturn().getResponse();
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(response.getContentAsString()).isEqualTo(json.write(userDto).getJson());

	}

	
	// JUnit test for updateUser method in Controller
	@DisplayName("JUnit test for updateUser method")
	@Test
	public void shouldUpdateUser_When_UserObject_Given() throws Exception {

		given(userService.update(userDto)).willReturn(userDto);

		// when
		MockHttpServletResponse response = mvc.perform(
				put("/api/users").contentType(MediaType.APPLICATION_JSON).content(json.write(userDto).getJson()))
				.andReturn().getResponse();
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(response.getContentAsString()).isEqualTo(json.write(userDto).getJson());

	}
	
	
	// JUnit test for updateUser method in Controller
		@DisplayName("JUnit test for updateUser method when userId is given")
		@Test
		public void shouldUpdateUser_When_UserId_Given() throws Exception {

			Integer userId = 1;
			
			given(userService.update(userId,userDto)).willReturn(userDto);

			// when
			MockHttpServletResponse response = mvc.perform(
					put("/api/users/{userId}", userId).contentType(MediaType.APPLICATION_JSON).content(json.write(userDto).getJson()))
					.andReturn().getResponse();
			// then
			assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

			assertThat(response.getContentAsString()).isEqualTo(json.write(userDto).getJson());

		}
		

		@Test
		void shouldReturn404When_Update_User_By_Id_Does_Not_Exist() throws Exception {

			Integer userId=1;
			given(userService.update(userId,userDto)).willThrow(UserObjectNotFoundException.class);

			String username = "skulyana";

			// when
			MockHttpServletResponse response = mvc.perform(
					put("/api/users/{userId}", userId).contentType(MediaType.APPLICATION_JSON).content(json.write(userDto).getJson()))
					.andReturn().getResponse();

			// then
			assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());

		}


	
	
	
	
	// JUnit test for createUser method in Controller
	@DisplayName("JUnit test for deleteUser method")
	@Test
	public void shouldDeleteNewUser() throws Exception {

		Integer userId = 1;

		// when
		MockHttpServletResponse response = mvc
				.perform(delete("/api/users/{userId}", userId).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(new Boolean(response.getContentAsString())).isEqualTo(true);

		// then - verify the output

		verify(userService, times(1)).deleteById(userId);

	}

}
