package com.dailycodebuffer.springboot.tutorial.app.business.user.domain.response;

import java.io.Serializable;
import java.util.Collection;

import com.dailycodebuffer.springboot.tutorial.app.business.user.domain.VerificationTokenDto;

public class VerificationUserTokenServiceCollectionDtoResponse implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	private Collection<VerificationTokenDto> collection;
	
	
	
}
