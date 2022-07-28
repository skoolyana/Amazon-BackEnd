package com.dailycodebuffer.springboot.tutorial.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailycodebuffer.springboot.tutorial.dto.UserDto;
import com.dailycodebuffer.springboot.tutorial.dto.response.collection.DtoCollectionResponse;
import com.dailycodebuffer.springboot.tutorial.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = {"/api/users"})
@Slf4j
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	
	@GetMapping
	public ResponseEntity<DtoCollectionResponse<UserDto>> findAll() {
	
		log.info("*** UserDto List, controller; fetch all users *");
	
		return ResponseEntity.ok(new DtoCollectionResponse<>(this.userService.findAll()));
			
	}
	
	
	
	
}
