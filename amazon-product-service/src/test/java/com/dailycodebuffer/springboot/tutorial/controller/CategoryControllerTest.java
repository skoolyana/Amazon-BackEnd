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
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.CategoryNotFoundException;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.ProductNotFoundException;
import com.dailycodebuffer.springboot.tutorial.service.CategoryService;
import com.dailycodebuffer.springboot.tutorial.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureJsonTesters
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

	@MockBean
	private CategoryService categoryService;

	@Autowired
	private MockMvc mvc;

	// This object will be magically initialized by the initFields method below.
	private JacksonTester<CategoryDto> json;

	private CategoryDto categoryDto;

	@BeforeEach
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());

		categoryDto = CategoryDto.builder().categoryTitle("Laptops-Mini").imageUrl("google.com").build();

	}

	@Test
	void shouldFetchAllCategories() throws Exception {

		given(categoryService.findAll()).willReturn(List.of(categoryDto));

		// when
		MockHttpServletResponse response = mvc.perform(get("/api/categories").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

	}

	@Test
	void shouldFetchCategoryById() throws Exception {

		given(categoryService.findById(1)).willReturn(Optional.of(categoryDto).get());

		Integer categoryId = 1;

		// when
		MockHttpServletResponse response = mvc
				.perform(get("/api/categories/{categoryId}", categoryId).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(response.getContentAsString()).isEqualTo(json.write(categoryDto).getJson());

	}

	@Test
	void shouldReturn404WhenFetchCategoryById_Does_Not_Exist() throws Exception {

		given(categoryService.findById(1)).willThrow(CategoryNotFoundException.class);

		Integer categoryId = 1;

		// when
		MockHttpServletResponse response = mvc
				.perform(get("/api/categories/{categoryId}", categoryId).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());

	}

	// JUnit test for createUser method in Controller
	@DisplayName("JUnit test for createCategory method")
	@Test
	public void shouldCreateNewCategory() throws Exception {

		given(categoryService.save(categoryDto)).willReturn(categoryDto);

		// when
		MockHttpServletResponse response = mvc.perform(
				post("/api/categories").contentType(MediaType.APPLICATION_JSON).content(json.write(categoryDto).getJson()))
				.andReturn().getResponse();
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(response.getContentAsString()).isEqualTo(json.write(categoryDto).getJson());

	}

	// JUnit test for updateUser method in Controller
	@DisplayName("JUnit test for updateCategory method")
	@Test
	public void shouldUpdateCategory_When_CategoryObject_Given() throws Exception {

		given(categoryService.update(categoryDto)).willReturn(categoryDto);

		// when
		MockHttpServletResponse response = mvc.perform(
				put("/api/categories").contentType(MediaType.APPLICATION_JSON).content(json.write(categoryDto).getJson()))
				.andReturn().getResponse();
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(response.getContentAsString()).isEqualTo(json.write(categoryDto).getJson());

	}

	// JUnit test for updateUser method in Controller
	@DisplayName("JUnit test for updateCategory method when categoryId is given")
	@Test
	public void shouldUpdateCategory_When_CategoryId_Given() throws Exception {

		Integer userId = 1;

		given(categoryService.update(userId, categoryDto)).willReturn(categoryDto);

		// when
		MockHttpServletResponse response = mvc.perform(put("/api/categories/{categoryDto}", userId)
				.contentType(MediaType.APPLICATION_JSON).content(json.write(categoryDto).getJson())).andReturn()
				.getResponse();
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(response.getContentAsString()).isEqualTo(json.write(categoryDto).getJson());

	}

	@Test
	void shouldReturn404When_Update_Category_By_Id_Does_Not_Exist() throws Exception {

		Integer categoryId = 1;
		given(categoryService.update(categoryId, categoryDto)).willThrow(CategoryNotFoundException.class);

		// when
		MockHttpServletResponse response = mvc.perform(put("/api/categories/{categoryId}", categoryId)
				.contentType(MediaType.APPLICATION_JSON).content(json.write(categoryDto).getJson())).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());

	}

	// JUnit test for createUser method in Controller
	@DisplayName("JUnit test for deleteCategory method")
	@Test
	public void shouldDeleteNewCategory() throws Exception {

		Integer categoryId = 1;

		// when
		MockHttpServletResponse response = mvc
				.perform(delete("/api/categories/{categoryId}", categoryId).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		assertThat(new Boolean(response.getContentAsString())).isEqualTo(true);

		// then - verify the output

		verify(categoryService, times(1)).deleteById(categoryId);

	}

}
