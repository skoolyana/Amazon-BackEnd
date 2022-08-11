package com.dailycodebuffer.springboot.tutorial.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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

	
	
	
	
}
