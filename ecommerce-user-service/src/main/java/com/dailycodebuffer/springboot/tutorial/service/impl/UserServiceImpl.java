package com.dailycodebuffer.springboot.tutorial.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.dailycodebuffer.springboot.tutorial.dto.UserDto;
import com.dailycodebuffer.springboot.tutorial.exception.wrapper.UserObjectNotFoundException;
import com.dailycodebuffer.springboot.tutorial.helper.UserMappingHelper;
import com.dailycodebuffer.springboot.tutorial.repository.UserRepository;
import com.dailycodebuffer.springboot.tutorial.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	
	
	@Override
	public List<UserDto> findAll() {
		
		log.info("*** UserDto List, service; fetch all users *");
		
		// TODO Auto-generated method stub
		return this.userRepository.findAll().stream().map(UserMappingHelper::map).distinct().collect(Collectors.toUnmodifiableList());
		
	}

	@Override
	public UserDto findById(Integer userId) {
		
		log.info("*** UserDto, service; fetch user by id *");
		
		
		// TODO Auto-generated method stub
		return this.userRepository.findById(userId).map(UserMappingHelper::map).orElseThrow(()->new UserObjectNotFoundException(String.format("User with id: %d not found", userId)));
	}

	
	@Override
	public UserDto findByUsername(String username) {
		// TODO Auto-generated method stub
		
		log.info("*** UserDto, service; fetch user with username *");
		return UserMappingHelper.map(this.userRepository.findByCredentialUsername(username)
				.orElseThrow(() -> new UserObjectNotFoundException(String.format("User with username: %s not found", username))));
	}

	
	
	@Override
	public UserDto save(UserDto userDto) {
		
		log.info("*** UserDto, service; save user *");
		
		return UserMappingHelper.map(this.userRepository.save(UserMappingHelper.map(userDto)));
		

	}

	@Override
	public UserDto update(UserDto userDto) {
		
		log.info("*** UserDto, service; update user *");
		
		
		// TODO Auto-generated method stub
		return UserMappingHelper.map(this.userRepository.save(UserMappingHelper.map(userDto)));
		
	}

	@Override
	public UserDto update(Integer userId, UserDto userDto) {
		// TODO Auto-generated method stub

		log.info("*** UserDto, service; update user with userId *");

		
		return UserMappingHelper.map(this.userRepository.save(UserMappingHelper.map(this.findById(userId))));

	}

	@Override
	public void deleteById(Integer userId) {
		// TODO Auto-generated method stub

		log.info("*** UserDto, service; delete user*");

		
		userRepository.deleteById(userId);
		
	}

	
	
}
