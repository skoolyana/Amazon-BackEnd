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

import com.dailycodebuffer.springboot.tutorial.dto.AddressDto;
import com.dailycodebuffer.springboot.tutorial.dto.UserDto;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.AddressNotFoundException;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.UserObjectNotFoundException;
import com.dailycodebuffer.springboot.tutorial.service.AddressService;
import com.dailycodebuffer.springboot.tutorial.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureJsonTesters
@WebMvcTest(AddressController.class)

public class AddressControllerTest {

	@MockBean
	private AddressService addressService;

	@Autowired
	private MockMvc mvc;

	// This object will be magically initialized by the initFields method below.
	private JacksonTester<AddressDto> json;

	private AddressDto addressDto;

	@BeforeEach
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
		addressDto = AddressDto.builder().addressId(1).city("Delhi").fullAddress("296 Jood Bagh").postalCode("110003")
				.userDto(UserDto.builder().firstName("Sunil").lastName("Kulyana").userId(2).build()).build();

	}

	@Test
	void shouldFetchAllAddresses() throws Exception {

		given(addressService.findAll()).willReturn(List.of(addressDto));

		// when
		MockHttpServletResponse response = mvc.perform(get("/api/address").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

	}

	@Test
	void shouldFetchAddressById() throws Exception {

		given(addressService.findById(1)).willReturn(Optional.of(addressDto).get());

		Integer userId = 1;

		// when
		MockHttpServletResponse response = mvc
				.perform(get("/api/address/{addressId}", userId).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(response.getContentAsString()).isEqualTo(json.write(addressDto).getJson());

	}

	@Test
	void shouldReturn404WhenFetchAddressById_Does_Not_Exist() throws Exception {

		given(addressService.findById(1)).willThrow(AddressNotFoundException.class);

		Integer addressId = 1;

		// when
		MockHttpServletResponse response = mvc
				.perform(get("/api/address/{addressId}", addressId).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());

	}

	// JUnit test for createUser method in Controller

	@DisplayName("JUnit test for createAddress method")

	@Test

	public void shouldCreateNewAddress() throws Exception {

		given(addressService.save(addressDto)).willReturn(addressDto);

		// when

		MockHttpServletResponse response = mvc.perform(
				post("/api/address").contentType(MediaType.APPLICATION_JSON).content(json.write(addressDto).getJson()))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(response.getContentAsString()).isEqualTo(json.write(addressDto).getJson());

	}

	// JUnit test for updateAddress method in Controller

	@DisplayName("JUnit test for updateAddress method")

	@Test

	public void shouldUpdateAddress_When_AddressObject_Given() throws Exception {

		given(addressService.update(addressDto)).willReturn(addressDto);

		// when
		MockHttpServletResponse response = mvc.perform(
				put("/api/address").contentType(MediaType.APPLICATION_JSON).content(json.write(addressDto).getJson()))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(response.getContentAsString()).isEqualTo(json.write(addressDto).getJson());

	}

	// JUnit test for updateAddress method in Controller

	@DisplayName("JUnit test for updateAddress method when AddressId is given")

	@Test
	public void shouldUpdateAddress_When_AddressId_Given() throws Exception {

		Integer addressId = 1;

		given(addressService.update(addressId, addressDto)).willReturn(addressDto);

		// when
		MockHttpServletResponse response = mvc.perform(put("/api/address/{addressId}", addressId)
				.contentType(MediaType.APPLICATION_JSON).content(json.write(addressDto).getJson())).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(response.getContentAsString()).isEqualTo(json.write(addressDto).getJson());

	}

	@Test
	void shouldReturn404When_Update_Address_By_Id_Does_Not_Exist() throws Exception {

		Integer addressId = 1;

		given(addressService.update(addressId, addressDto)).willThrow(AddressNotFoundException.class);

		// when
		MockHttpServletResponse response = mvc.perform(put("/api/address/{addressId}", addressId)
				.contentType(MediaType.APPLICATION_JSON).content(json.write(addressDto).getJson())).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());

	}

	// JUnit test for delete method in Controller

	@DisplayName("JUnit test for deleteAddress method")

	@Test
	public void shouldDeleteNewAddress() throws Exception {

		Integer addressId = 1;

		// when
		MockHttpServletResponse response = mvc
				.perform(delete("/api/address/{addressId}", addressId).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(new Boolean(response.getContentAsString())).isEqualTo(true);

		// then - verify the output

		verify(addressService, times(1)).deleteById(addressId);

	}

}
