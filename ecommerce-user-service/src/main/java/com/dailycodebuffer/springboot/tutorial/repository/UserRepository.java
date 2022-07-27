package com.dailycodebuffer.springboot.tutorial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dailycodebuffer.springboot.tutorial.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByCredentialUsername(final String username);
	
}
