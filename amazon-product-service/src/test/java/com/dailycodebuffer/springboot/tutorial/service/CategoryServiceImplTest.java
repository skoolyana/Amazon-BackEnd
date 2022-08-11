package com.dailycodebuffer.springboot.tutorial.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.dailycodebuffer.springboot.tutorial.domain.Category;
import com.dailycodebuffer.springboot.tutorial.domain.Product;

import com.dailycodebuffer.springboot.tutorial.dto.CategoryDto;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.CategoryNotFoundException;
import com.dailycodebuffer.springboot.tutorial.helper.CategoryMappingHelper;
import com.dailycodebuffer.springboot.tutorial.repository.CategoryRepository;
import com.dailycodebuffer.springboot.tutorial.service.impl.CategoryServiceImpl;




public class CategoryServiceImplTest {

	
	private CategoryServiceImpl categoryService;

	@Mock
	private CategoryRepository categoryRepository;
	
	private Category category1;
	
	@BeforeEach
	public void setUp() {

		
		
		// With this call to initMocks we tell Mockito to process the annotations
		MockitoAnnotations.initMocks(this);
	
		categoryService = new CategoryServiceImpl(categoryRepository);
		
		Set<Product> productSet = new HashSet<>();

		productSet.add(
				Product.builder().productId(1).productTitle("Lenovo").priceUnit(250d).quantity(4).sku("sku").build());

		productSet.add(
				Product.builder().productId(2).productTitle("HP").priceUnit(150d).quantity(3).sku("sku").build());

		
		Set<Category> subcategory_Set = new HashSet<>();
		

		subcategory_Set.add(
				
				Category.builder().categoryTitle("Laptops-Mini").imageUrl("google.com").build()
				
				);

		productSet.add(
				Product.builder().productId(2).productTitle("HP").priceUnit(150d).quantity(3).sku("sku").build());

		
	
		category1 = Category.builder().categoryId(1).categoryTitle("Laptops").imageUrl("google.com").products(productSet).subCategories(subcategory_Set).build();

		
		
	}
	
	
	// JUnit test for findAll categories method

	@DisplayName("JUnit test for findAll Categories method")
	@Test
	public void givenCategoriesList_whenfindAllCategoriess_thenReturnCategoriesList() {

		given(categoryRepository.findAll()).willReturn(List.of(category1));

		List<CategoryDto> categoryList = categoryService.findAll();

		// then - verify the output
		assertThat(categoryList).isNotNull();

		assertThat(categoryList.size()).isEqualTo(1);

		verify(categoryRepository).findAll();

	}


	// JUnit test for findAll categories method: negative Scenario

		@DisplayName("JUnit test for findAll categories method (negative scenario)")
		@Test
		public void givenEmptyCategoriesList_whenfindAllCategories_thenReturnEmptyCategoriesList() {

			given(categoryRepository.findAll()).willReturn(Collections.EMPTY_LIST);

			List<CategoryDto> categoryList = categoryService.findAll();

			// then - verify the output
			assertThat(categoryList).isEmpty();
			assertThat(categoryList.size()).isEqualTo(0);
			verify(categoryRepository).findAll();
		}


		
		
		// JUnit test for getCategoryById method
		@DisplayName("JUnit test for getCategoryById method")
		@Test
		public void givenCategoryId_whenGetCategoryById_thenRetuernCategorybject() {

			given(categoryRepository.findById(1)).willReturn(Optional.of(category1));

			// when
			CategoryDto savedCategory = categoryService.findById(category1.getCategoryId());

			// then
			assertThat(savedCategory).isNotNull();

		}

		
		@Test()
		public void should_throw_exception_when_category_doesnt_exist() {

			given(categoryRepository.findById(1)).willReturn(Optional.ofNullable(null));

			Assertions.assertThrows(CategoryNotFoundException.class, () -> categoryService.findById(category1.getCategoryId()));

		}
		

		
		
		// JUnit test for saveCategory method
		@DisplayName("JUnit test for saveCategory method")
		@Test
		public void givenCategoryObject_whenSaveCategory_thenReturnCategoryObject() {

			// given - precondition or setup

			given(categoryRepository.save(category1)).willReturn(category1);

			// when
			CategoryDto categoryDto = categoryService.save(CategoryMappingHelper.map(category1));

			// then - verify the output

			Category category = CategoryMappingHelper.map(categoryDto);

			assertThat(category).isNotNull();

			assertThat(category.getCategoryId()).isEqualTo(category1.getCategoryId());

		}


		
		
		// JUnit test for updateCategory method
		@DisplayName("JUnit test for updateCategory method")
		@Test
		public void givenCategoryObject_whenUpdateCategory_thenReturnUpdatedCategory() {

			// given - precondition or setup

			given(categoryRepository.save(category1)).willReturn(category1);

			// when

			CategoryDto categoryDto = categoryService.update(CategoryMappingHelper.map(category1));

			// then - verify the output

			Category category = CategoryMappingHelper.map(categoryDto);

			assertThat(category).isNotNull();

			assertThat(category.getCategoryId()).isEqualTo(category1.getCategoryId());

		}

		
		

		// JUnit test for updateCategory method
		@DisplayName("JUnit test for updateCategory method using categoryId")
		@Test
		public void givenCategoryId_whenUpdateCategory_thenReturnUpdatedCategory() {

			// given - precondition or setup

			given(categoryRepository.save(category1)).willReturn(category1);
			given(categoryRepository.findById(category1.getCategoryId())).willReturn(Optional.of(category1));
			
			// when

			CategoryDto categoryDto = categoryService.update(category1.getCategoryId(),CategoryMappingHelper.map(category1));

			// then - verify the output

			Category category = CategoryMappingHelper.map(categoryDto);

			assertThat(category).isNotNull();

			assertThat(category.getCategoryId()).isEqualTo(category1.getCategoryId());

		}

		
		
		@Test()
		public void update_should_throw_exception_when_category_doesnt_exist() {

			given(categoryRepository.findById(1)).willReturn(Optional.ofNullable(null));

			Assertions.assertThrows(CategoryNotFoundException.class, () -> categoryService.update(category1.getCategoryId(),CategoryMappingHelper.map(category1)));

		}
		
		
		// JUnit test for deleteCategory method
	    @DisplayName("JUnit test for deleteCategory method")
	    @Test
	    public void givenUserId_whenDeleteCategory_thenNothing(){
	    	
	    	 // given - precondition or setup
	        Integer categoryId = 1;
	        
	        
	        willDoNothing().given(categoryRepository).deleteById(categoryId);

	        // when -  action or the behaviour that we are going test
	        
	        categoryService.deleteById(categoryId);
	        
	        
	        // then - verify the output
	        
	        verify(categoryRepository, times(1)).deleteById(categoryId);
	        
	    	
	    	
	    }

		
	
}
