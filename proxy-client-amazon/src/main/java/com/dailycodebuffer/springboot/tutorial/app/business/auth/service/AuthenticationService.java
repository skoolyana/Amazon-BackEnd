package com.dailycodebuffer.springboot.tutorial.app.business.auth.service;

import com.dailycodebuffer.springboot.tutorial.app.business.auth.model.request.AuthenticationRequest;
import com.dailycodebuffer.springboot.tutorial.app.business.auth.model.response.AuthenticationResponse;

public interface AuthenticationService {

	AuthenticationResponse authenticate(final AuthenticationRequest authenticationRequest);
	Boolean authenticate(final String jwt);
	
}
