package com.dailycodebuffer.springboot.tutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dailycodebuffer.springboot.tutorial.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
