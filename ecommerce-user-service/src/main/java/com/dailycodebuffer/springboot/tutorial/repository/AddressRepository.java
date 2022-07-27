package com.dailycodebuffer.springboot.tutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dailycodebuffer.springboot.tutorial.domain.Address;


public interface AddressRepository extends JpaRepository<Address, Integer> {
	
}
