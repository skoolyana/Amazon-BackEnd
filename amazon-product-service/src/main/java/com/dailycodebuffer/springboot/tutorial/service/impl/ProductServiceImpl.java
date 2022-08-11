package com.dailycodebuffer.springboot.tutorial.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.dailycodebuffer.springboot.tutorial.dto.ProductDto;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.ProductNotFoundException;
import com.dailycodebuffer.springboot.tutorial.helper.ProductMappingHelper;
import com.dailycodebuffer.springboot.tutorial.repository.ProductRepository;
import com.dailycodebuffer.springboot.tutorial.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	@Override
	public List<ProductDto> findAll() {

		log.info("*** ProductDto List, service; fetch all products *");

		return this.productRepository.findAll().stream().map(ProductMappingHelper::map).distinct()
				.collect(Collectors.toUnmodifiableList());
	}

	@Override
	public ProductDto findById(Integer productId) {
		// TODO Auto-generated method stub

		log.info("*** ProductDto, service; fetch product by id *");

		return this.productRepository.findById(productId).map(ProductMappingHelper::map)
				.orElseThrow(() -> new ProductNotFoundException());

	}

	@Override
	public ProductDto save(ProductDto productDto) {
		// TODO Auto-generated method stub

		log.info("*** ProductDto, service; save product *");

		return ProductMappingHelper.map(this.productRepository.save(ProductMappingHelper.map(productDto)));
	}

	@Override
	public ProductDto update(ProductDto productDto) {

		log.info("*** ProductDto, service; update product *");

		// TODO Auto-generated method stub
		return ProductMappingHelper.map(this.productRepository.save(ProductMappingHelper.map(productDto)));
	}

	@Override
	public ProductDto update(Integer productId, ProductDto productDto) {
		log.info("*** ProductDto, service; update product with productId *");
		return ProductMappingHelper
				.map(this.productRepository.save(ProductMappingHelper.map(this.findById(productId))));

	}

	@Override
	public void deleteById(Integer productId) {
		// TODO Auto-generated method stub

		log.info("*** Void, service; delete product by id *");

		this.productRepository.deleteById(productId);
	}

}
