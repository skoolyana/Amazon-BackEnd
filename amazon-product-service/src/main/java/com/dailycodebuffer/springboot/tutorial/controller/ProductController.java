package com.dailycodebuffer.springboot.tutorial.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailycodebuffer.springboot.tutorial.dto.ProductDto;
import com.dailycodebuffer.springboot.tutorial.dto.response.collection.DtoCollectionResponse;
import com.dailycodebuffer.springboot.tutorial.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/products")
@Slf4j
@RequiredArgsConstructor

public class ProductController {

	
	private final ProductService productService;
	
	
	@GetMapping
	public ResponseEntity<DtoCollectionResponse<ProductDto>> findAll() {
		log.info("*** ProductDto List, controller; fetch all categories *");
		return ResponseEntity.ok(new DtoCollectionResponse<>(this.productService.findAll()));
	}
	
	
	
	
}
