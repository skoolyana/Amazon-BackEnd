package com.dailycodebuffer.springboot.tutorial.exception.wrapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CredentialNotFoundException extends RuntimeException  {

	private static final long serialVersionUID = 1L;
	
	public CredentialNotFoundException() {
		super();
	}
	
	public CredentialNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CredentialNotFoundException(String message) {
		super(message);
	}
	
	public CredentialNotFoundException(Throwable cause) {
		super(cause);
	}
	
	
}
