package com.dailycodebuffer.springboot.tutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dailycodebuffer.springboot.tutorial.domain.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {

}
