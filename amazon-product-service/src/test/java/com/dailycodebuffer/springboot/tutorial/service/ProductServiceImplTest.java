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

import com.dailycodebuffer.springboot.tutorial.domain.Category;
import com.dailycodebuffer.springboot.tutorial.domain.Product;
import com.dailycodebuffer.springboot.tutorial.dto.CategoryDto;
import com.dailycodebuffer.springboot.tutorial.dto.ProductDto;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.CategoryNotFoundException;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.ProductNotFoundException;
import com.dailycodebuffer.springboot.tutorial.helper.CategoryMappingHelper;
import com.dailycodebuffer.springboot.tutorial.helper.ProductMappingHelper;
import com.dailycodebuffer.springboot.tutorial.repository.ProductRepository;
import com.dailycodebuffer.springboot.tutorial.service.impl.ProductServiceImpl;

public class ProductServiceImplTest {

	private ProductServiceImpl productService;

	@Mock
	private ProductRepository productRepository;

	private Product product1;

	@BeforeEach
	public void setUp() {

		// With this call to initMocks we tell Mockito to process the annotations
		MockitoAnnotations.initMocks(this);

		productService = new ProductServiceImpl(productRepository);

		Category category = Category.builder().categoryTitle("Laptops-Mini").imageUrl("google.com").build();

		 product1 = Product.builder().productId(1).productTitle("Lenovo").priceUnit(250d).quantity(4).sku("sku")
				.category(category).build();

	}

	
	// JUnit test for findAll users method

	@DisplayName("JUnit test for findAll Products method")
	@Test
	public void givenProductList_whenfindAllProducts_thenReturnProductList() {

		given(productRepository.findAll()).willReturn(List.of(product1));

		List<ProductDto> productList = productService.findAll();

		// then - verify the output
		assertThat(productList).isNotNull();

		assertThat(productList.size()).isEqualTo(1);

		verify(productRepository).findAll();

	}

	
	
	// JUnit test for findAll products method: negative Scenario

	@DisplayName("JUnit test for findAll products method (negative scenario)")
	@Test
	public void givenEmptyProductsList_whenfindAllProducts_thenReturnEmptyProductsList() {

		given(productRepository.findAll()).willReturn(Collections.EMPTY_LIST);

		List<ProductDto> categoryList = productService.findAll();

		// then - verify the output
		assertThat(categoryList).isEmpty();
		assertThat(categoryList.size()).isEqualTo(0);
		verify(productRepository).findAll();
	}

	
	// JUnit test for getCategoryById method
	@DisplayName("JUnit test for getProductById method")
	@Test
	public void givenProductId_whenGetProductById_thenReturnProductObject() {

		given(productRepository.findById(1)).willReturn(Optional.of(product1));

		// when
		ProductDto savedProduct = productService.findById(product1.getProductId());

		// then
		assertThat(savedProduct).isNotNull();
	}

	
	@Test()
	public void should_throw_exception_when_product_doesnt_exist() {

		given(productRepository.findById(1)).willReturn(Optional.ofNullable(null));

		Assertions.assertThrows(ProductNotFoundException.class, () -> productService.findById(product1.getProductId()));

	}
	
	
	
	// JUnit test for saveCategory method
	@DisplayName("JUnit test for saveProduct method")
	@Test
	public void givenProductObject_whenSaveProduct_thenReturnProductObject() {

		// given - precondition or setup

		given(productRepository.save(product1)).willReturn(product1);

		// when
		ProductDto productDto = productService.save(ProductMappingHelper.map(product1));

		// then - verify the output

		Product product = ProductMappingHelper.map(productDto);

		assertThat(product).isNotNull();

		assertThat(product.getProductId()).isEqualTo(product.getProductId());

	}

	
	
	
	// JUnit test for updateCategory method
	@DisplayName("JUnit test for updateCategory method")
	@Test
	public void givenProductObject_whenUpdateProduct_thenReturnUpdatedProduct() {

		// given - precondition or setup

		given(productRepository.save(product1)).willReturn(product1);

		// when

		ProductDto productDto = productService.update(ProductMappingHelper.map(product1));

		// then - verify the output

		Product product = ProductMappingHelper.map(productDto);

		assertThat(product).isNotNull();

		assertThat(product.getProductId()).isEqualTo(product1.getProductId());

	}

	
	
	
	// JUnit test for updateCategory method
	@DisplayName("JUnit test for updateProduct method using productId")
	@Test
	public void givenProductId_whenUpdateProduct_thenReturnUpdatedProduct() {

		// given - precondition or setup

		given(productRepository.save(product1)).willReturn(product1);
		given(productRepository.findById(product1.getProductId())).willReturn(Optional.of(product1));
		
		// when

		ProductDto productDto = productService.update(product1.getProductId(),ProductMappingHelper.map(product1));

		// then - verify the output

		// then - verify the output

		Product product = ProductMappingHelper.map(productDto);

		assertThat(product).isNotNull();

		assertThat(product.getProductId()).isEqualTo(product1.getProductId());

	}

	
	@Test()
	public void update_should_throw_exception_when_product_doesnt_exist() {

		given(productRepository.findById(1)).willReturn(Optional.ofNullable(null));

		Assertions.assertThrows(ProductNotFoundException.class, () -> productService.update(product1.getProductId(),ProductMappingHelper.map(product1)));

	}


	
	// JUnit test for deleteCategory method
    @DisplayName("JUnit test for deleteProduct method")
    @Test
    public void givenProductId_whenDeleteProduct_thenNothing(){
    	
    	 // given - precondition or setup
        Integer productId = 1;
        
        
        willDoNothing().given(productRepository).deleteById(productId);

        // when -  action or the behaviour that we are going test
        
        productService.deleteById(productId);
        
        
        // then - verify the output
        
        verify(productRepository, times(1)).deleteById(productId);
        	
    	
    }

	
}
