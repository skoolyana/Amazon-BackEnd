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

import com.dailycodebuffer.springboot.tutorial.dto.CategoryDto;
import com.dailycodebuffer.springboot.tutorial.dto.ProductDto;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.ProductNotFoundException;
import com.dailycodebuffer.springboot.tutorial.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;


@AutoConfigureJsonTesters
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

	
	@MockBean
	private ProductService productService;

	@Autowired
	private MockMvc mvc;

	// This object will be magically initialized by the initFields method below.
	private JacksonTester<ProductDto> json;

	
	private ProductDto productDto;

	@BeforeEach
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());

		CategoryDto category = CategoryDto.builder().categoryTitle("Laptops-Mini").imageUrl("google.com").build();
		
		productDto = ProductDto.builder().productId(1).productTitle("Lenovo").priceUnit(250d).quantity(4).sku("sku")
		.categoryDto(category).build();

		
	}

	
	
	@Test
	void shouldFetchAllProducts() throws Exception {

		given(productService.findAll()).willReturn(List.of(productDto));

		// when
		MockHttpServletResponse response = mvc.perform(get("/api/products").accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

	}

	
	
	@Test
	void shouldFetchUserById() throws Exception {

		given(productService.findById(1)).willReturn(Optional.of(productDto).get());

		Integer productId = 1;

		// when
		MockHttpServletResponse response = mvc
				.perform(get("/api/products/{productId}", productId).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(response.getContentAsString()).isEqualTo(json.write(productDto).getJson());

	}

	
	@Test
	void shouldReturn404WhenFetchProductById_Does_Not_Exist() throws Exception {

		given(productService.findById(1)).willThrow(ProductNotFoundException.class);

		Integer productId = 1;

		// when
		MockHttpServletResponse response = mvc
				.perform(get("/api/products/{productId}", productId).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());

	}

	// JUnit test for createUser method in Controller
	@DisplayName("JUnit test for createProduct method")
	@Test
	public void shouldCreateNewProduct() throws Exception {

		given(productService.save(productDto)).willReturn(productDto);

				
		// when
		MockHttpServletResponse response = mvc.perform(
				post("/api/products").contentType(MediaType.APPLICATION_JSON).content(json.write(productDto).getJson()))
				.andReturn().getResponse();
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(response.getContentAsString()).isEqualTo(json.write(productDto).getJson());

	}

	
	
	// JUnit test for updateUser method in Controller
	@DisplayName("JUnit test for updateProduct method")
	@Test
	public void shouldUpdateProduct_When_ProductObject_Given() throws Exception {

		given(productService.update(productDto)).willReturn(productDto);

		// when
		MockHttpServletResponse response = mvc.perform(
				put("/api/products").contentType(MediaType.APPLICATION_JSON).content(json.write(productDto).getJson()))
				.andReturn().getResponse();
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(response.getContentAsString()).isEqualTo(json.write(productDto).getJson());

	}

	
	
	// JUnit test for updateUser method in Controller
			@DisplayName("JUnit test for updateProduct method when productId is given")
			@Test
			public void shouldUpdateProduct_When_ProductId_Given() throws Exception {

				Integer userId = 1;
				
				given(productService.update(userId,productDto)).willReturn(productDto);

				// when
				MockHttpServletResponse response = mvc.perform(
						put("/api/products/{productDto}", userId).contentType(MediaType.APPLICATION_JSON).content(json.write(productDto).getJson()))
						.andReturn().getResponse();
				// then
				assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

				assertThat(response.getContentAsString()).isEqualTo(json.write(productDto).getJson());

			}
			
			
			
			
			@Test
			void shouldReturn404When_Update_Product_By_Id_Does_Not_Exist() throws Exception {

				Integer userId=1;
				given(productService.update(userId,productDto)).willThrow(ProductNotFoundException.class);

				
				// when
				MockHttpServletResponse response = mvc.perform(
						put("/api/users/{userId}", userId).contentType(MediaType.APPLICATION_JSON).content(json.write(productDto).getJson()))
						.andReturn().getResponse();

				// then
				assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());

			}

			
			
			// JUnit test for createUser method in Controller
			@DisplayName("JUnit test for deleteProduct method")
			@Test
			public void shouldDeleteNewProduct() throws Exception {

				Integer productId = 1;

				// when
				MockHttpServletResponse response = mvc
						.perform(delete("/api/products/{productId}", productId).accept(MediaType.APPLICATION_JSON)).andReturn()
						.getResponse();

				// then
				assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

				assertThat(new Boolean(response.getContentAsString())).isEqualTo(true);

				// then - verify the output

				verify(productService, times(1)).deleteById(productId);

			}
			
			
			
			
			
			
	
	
}
