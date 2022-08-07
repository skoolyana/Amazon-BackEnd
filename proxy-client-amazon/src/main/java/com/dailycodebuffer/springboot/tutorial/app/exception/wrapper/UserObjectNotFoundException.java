package com.dailycodebuffer.springboot.tutorial.app.exception.wrapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserObjectNotFoundException extends RuntimeException  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public UserObjectNotFoundException() {
		super();
	}
	
	public UserObjectNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	public UserObjectNotFoundException(String message) {
		super(message);
	}
	
	public UserObjectNotFoundException(Throwable cause) {
		super(cause);
	}
	
	
	
	
	
	
}
