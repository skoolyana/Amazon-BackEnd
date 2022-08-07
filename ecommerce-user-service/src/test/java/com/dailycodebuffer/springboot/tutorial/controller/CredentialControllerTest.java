package com.dailycodebuffer.springboot.tutorial.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dailycodebuffer.springboot.tutorial.dto.AddressDto;
import com.dailycodebuffer.springboot.tutorial.dto.CredentialDto;
import com.dailycodebuffer.springboot.tutorial.dto.UserDto;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.AddressNotFoundException;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.CredentialNotFoundException;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.UserObjectNotFoundException;
import com.dailycodebuffer.springboot.tutorial.service.CredentialService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureJsonTesters
@WebMvcTest(CredentialController.class)
public class CredentialControllerTest {

	@MockBean
	private CredentialService credentialService;

	@Autowired
	private MockMvc mvc;

	// This object will be magically initialized by the initFields method below.
	private JacksonTester<CredentialDto> json;

	private CredentialDto credentialDto;

	
	@BeforeEach
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
		credentialDto = CredentialDto.builder().credentialId(1).isAccountNonExpired(true).isEnabled(true).username("Sunil").build();

	}

	
	@Test
	void shouldFetchAllCredentials() throws Exception {

		given(credentialService.findAll()).willReturn(List.of(credentialDto));

		// when
		MockHttpServletResponse response = mvc.perform(get("/api/credentials").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

	}


	
	@Test
	void shouldFetchCredentialById() throws Exception {

		given(credentialService.findById(1)).willReturn(Optional.of(credentialDto).get());

		Integer credentialId = 1;

		// when
		MockHttpServletResponse response = mvc
				.perform(get("/api/credentials/{credentialId}", credentialId).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(response.getContentAsString()).isEqualTo(json.write(credentialDto).getJson());

	}

	@Test
	void shouldReturn404WhenFetchCredentialById_Does_Not_Exist() throws Exception {

		given(credentialService.findById(1)).willThrow(CredentialNotFoundException.class);

		Integer credentialId = 1;

		// when
		MockHttpServletResponse response = mvc
				.perform(get("/api/credentials/{credentialId}", credentialId).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());

	}

	
	
	
	
	
	
	
	// JUnit test for createUser method in Controller

		@DisplayName("JUnit test for createCredentials method")

		@Test

		public void shouldCreateNewCredentials() throws Exception {

			given(credentialService.save(credentialDto)).willReturn(credentialDto);

			// when

			MockHttpServletResponse response = mvc.perform(
					post("/api/credentials").contentType(MediaType.APPLICATION_JSON).content(json.write(credentialDto).getJson()))
					.andReturn().getResponse();

			// then
			assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

			assertThat(response.getContentAsString()).isEqualTo(json.write(credentialDto).getJson());

		}
		
		// JUnit test for updateCredential method in Controller

		@DisplayName("JUnit test for updateCredential method")
		@Test
		public void shouldUpdateCredential_When_CredentialObject_Given() throws Exception {

			given(credentialService.update(credentialDto)).willReturn(credentialDto);

			// when
			MockHttpServletResponse response = mvc.perform(
					put("/api/credentials").contentType(MediaType.APPLICATION_JSON).content(json.write(credentialDto).getJson()))
					.andReturn().getResponse();

			// then
			assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

			assertThat(response.getContentAsString()).isEqualTo(json.write(credentialDto).getJson());

		}

		// JUnit test for updateCredential method in Controller

		@DisplayName("JUnit test for updateCredential method when CredentialId is given")

		@Test
		public void shouldUpdateCredential_When_CredentialId_Given() throws Exception {

			Integer credentialId = 1;

			given(credentialService.update(credentialId, credentialDto)).willReturn(credentialDto);

			// when
			MockHttpServletResponse response = mvc.perform(put("/api/credentials/{credentialId}", credentialId)
					.contentType(MediaType.APPLICATION_JSON).content(json.write(credentialDto).getJson())).andReturn()
					.getResponse();

			// then
			assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

			assertThat(response.getContentAsString()).isEqualTo(json.write(credentialDto).getJson());

		}

		@Test
		void shouldReturn404When_Update_Credentials_By_Id_Does_Not_Exist() throws Exception {

			Integer credentialId = 1;

			given(credentialService.update(credentialId, credentialDto)).willThrow(CredentialNotFoundException.class);

			// when
			MockHttpServletResponse response = mvc.perform(put("/api/credentials/{credentialId}", credentialId)
					.contentType(MediaType.APPLICATION_JSON).content(json.write(credentialDto).getJson())).andReturn()
					.getResponse();

			// then
			assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());

		}

		// JUnit test for delete method in Controller

		@DisplayName("JUnit test for deleteCredentials method")

		@Test
		public void shouldDeleteNewCredentials() throws Exception {

			Integer credentialId = 1;

			// when
			MockHttpServletResponse response = mvc
					.perform(delete("/api/credentials/{credentialId}", credentialId).accept(MediaType.APPLICATION_JSON)).andReturn()
					.getResponse();

			// then

			assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

			assertThat(new Boolean(response.getContentAsString())).isEqualTo(true);

			// then - verify the output

			verify(credentialService, times(1)).deleteById(credentialId);

		}

		

		
		
		@Test
		void shouldFetchCredentialsByName() throws Exception {

			given(credentialService.findByUsername("skulyana")).willReturn(Optional.of(credentialDto).get());


			
			String username = "skulyana";

			// when
			MockHttpServletResponse response = mvc
					.perform(get("/api/credentials/username/{username}", username).accept(MediaType.APPLICATION_JSON)).andReturn()
					.getResponse();

			// then
			assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

			assertThat(response.getContentAsString()).isEqualTo(json.write(credentialDto).getJson());

		}

		
		@Test
		void shouldReturn404WhenFetchCredentialByName_Does_Not_Exist() throws Exception {

			given(credentialService.findByUsername("skulyana")).willThrow(CredentialNotFoundException.class);

			String username = "skulyana";

			// when
			MockHttpServletResponse response = mvc
					.perform(get("/api/credentials/username/{username}", username).accept(MediaType.APPLICATION_JSON)).andReturn()
					.getResponse();

			// then
			assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());

		}
		
		
	
	
}
