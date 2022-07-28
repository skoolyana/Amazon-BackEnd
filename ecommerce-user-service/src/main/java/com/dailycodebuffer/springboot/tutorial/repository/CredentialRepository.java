package com.dailycodebuffer.springboot.tutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dailycodebuffer.springboot.tutorial.domain.Address;
import com.dailycodebuffer.springboot.tutorial.domain.Credential;


public interface CredentialRepository extends JpaRepository<Credential, Integer> {
	
}
