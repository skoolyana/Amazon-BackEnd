package com.dailycodebuffer.springboot.tutorial.app.business.auth.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dailycodebuffer.springboot.tutorial.app.business.auth.model.request.AuthenticationRequest;
import com.dailycodebuffer.springboot.tutorial.app.business.auth.model.response.AuthenticationResponse;
import com.dailycodebuffer.springboot.tutorial.app.business.auth.service.AuthenticationService;
import com.dailycodebuffer.springboot.tutorial.app.exception.wrapper.IllegalAuthenticationCredentialsException;
import com.dailycodebuffer.springboot.tutorial.app.jwt.service.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {


	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final JwtService jwtService;
	
	
	
	@Override
	public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
	
		log.info("** AuthenticationResponse, authenticate user service*\n");
		
		
		UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUsername(), new BCryptPasswordEncoder().encode(authenticationRequest.getPassword()));
		
		
		try {
			this.authenticationManager.authenticate(authtoken);
		}
		catch (BadCredentialsException e) {
			throw new IllegalAuthenticationCredentialsException("#### Bad credentials! ####");
		}
		
		final UserDetails userDetails = this.userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		
		final String token = this.jwtService.generateToken(userDetails);
		
		return new AuthenticationResponse(token);
		
		  
	}

	@Override
	public Boolean authenticate(String jwt) {
		// TODO Auto-generated method stub
		return null;
	}

}
