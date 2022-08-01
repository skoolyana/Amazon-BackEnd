package com.dailycodebuffer.springboot.tutorial.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.dailycodebuffer.springboot.tutorial.domain.VerificationToken;
import com.dailycodebuffer.springboot.tutorial.dto.UserDto;
import com.dailycodebuffer.springboot.tutorial.dto.VerificationTokenDto;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.UserObjectNotFoundException;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.VerificationTokenNotFoundException;
import com.dailycodebuffer.springboot.tutorial.service.VerificationTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureJsonTesters
@WebMvcTest(VerificationTokenController.class)
public class VerificationTokenControllerTest {

	@MockBean
	private VerificationTokenService tokenService;

	@Autowired
	private MockMvc mvc;

	// This object will be magically initialized by the initFields method below.
	private JacksonTester<VerificationTokenDto> json;

	private VerificationTokenDto verificationTokenDto;

	@BeforeEach
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
		verificationTokenDto = VerificationTokenDto.builder().token("skulyana").verificationTokenId(1)
				.build();

	}

	@Test
	void shouldFetchAllTokens() throws Exception {

		given(tokenService.findAll()).willReturn(List.of(verificationTokenDto));

		// when
		MockHttpServletResponse response = mvc
				.perform(get("/api/verificationTokens").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

	}

	@Test
	void shouldFetchTokenById() throws Exception {

		given(tokenService.findById(1)).willReturn(Optional.of(verificationTokenDto).get());

		Integer verificationTokenId = 1;

		// when
		MockHttpServletResponse response = mvc
				.perform(get("/api/verificationTokens/{verificationTokenId}", verificationTokenId)
						.accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(response.getContentAsString()).isEqualTo(json.write(verificationTokenDto).getJson());

	}

	
	@Test
	void shouldReturn404WhenFetchTokenById_Does_Not_Exist() throws Exception {

		given(tokenService.findById(1)).willThrow(VerificationTokenNotFoundException.class);

		Integer userId = 1;

		// when
		MockHttpServletResponse response = mvc
				.perform(get("/api/verificationTokens/{verificationTokenId}", userId).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());

	}

	
	// JUnit test for createUser method in Controller
	@DisplayName("JUnit test for createToken method")
	@Test
	public void shouldCreateNewToken() throws Exception {

		given(tokenService.save(verificationTokenDto)).willReturn(verificationTokenDto);

		// when
		MockHttpServletResponse response = mvc.perform(
				post("/api/verificationTokens").contentType(MediaType.APPLICATION_JSON).content(json.write(verificationTokenDto).getJson()))
				.andReturn().getResponse();
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(response.getContentAsString()).isEqualTo(json.write(verificationTokenDto).getJson());

	}


	
	// JUnit test for updateUser method in Controller
	@DisplayName("JUnit test for updateToken method")
	@Test
	public void shouldUpdateToken_When_TokenObject_Given() throws Exception {

		given(tokenService.update(verificationTokenDto)).willReturn(verificationTokenDto);

		// when
		MockHttpServletResponse response = mvc.perform(
				put("/api/verificationTokens").contentType(MediaType.APPLICATION_JSON).content(json.write(verificationTokenDto).getJson()))
				.andReturn().getResponse();
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(response.getContentAsString()).isEqualTo(json.write(verificationTokenDto).getJson());

	}
	

	// JUnit test for updateUser method in Controller
	@DisplayName("JUnit test for updateToken method when tokenId is given")
	@Test
	public void shouldUpdateToken_When_TokenId_Given() throws Exception {

		Integer verificationTokenId = 1;
		
		given(tokenService.update(verificationTokenId,verificationTokenDto)).willReturn(verificationTokenDto);

		// when
		MockHttpServletResponse response = mvc.perform(
				put("/api/verificationTokens/{verificationTokenId}", verificationTokenId).contentType(MediaType.APPLICATION_JSON).content(json.write(verificationTokenDto).getJson()))
				.andReturn().getResponse();
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(response.getContentAsString()).isEqualTo(json.write(verificationTokenDto).getJson());

	}


	@Test
	void shouldReturn404When_Update_Token_By_Id_Does_Not_Exist() throws Exception {

		Integer verificationTokenId=1;
		given(tokenService.update(verificationTokenId,verificationTokenDto)).willThrow(VerificationTokenNotFoundException.class);

	
		// when
		MockHttpServletResponse response = mvc.perform(
				put("/api/verificationTokens/{verificationTokenId}", verificationTokenId).contentType(MediaType.APPLICATION_JSON).content(json.write(verificationTokenDto).getJson()))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
 
	}



	// JUnit test for createUser method in Controller
	@DisplayName("JUnit test for deleteToken method")
	@Test
	public void shouldDeleteNewToken() throws Exception {

		Integer userId = 1;

		// when
		MockHttpServletResponse response = mvc
				.perform(delete("/api/verificationTokens/{verificationTokenId}", userId).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(new Boolean(response.getContentAsString())).isEqualTo(true);

		// then - verify the output

		verify(tokenService, times(1)).deleteById(userId);

	}
	
	
	
	
	
	
	
}
