package com.dailycodebuffer.springboot.tutorial.app.exception.wrapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) 
public class VerificationTokenNotFoundException extends RuntimeException  {

	private static final long serialVersionUID = 1L;
	
	public VerificationTokenNotFoundException() {
		super();
	}
	
	public VerificationTokenNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public VerificationTokenNotFoundException(String message) {
		super(message);
	}
	
	public VerificationTokenNotFoundException(Throwable cause) {
		super(cause);
	}
	
	
}
