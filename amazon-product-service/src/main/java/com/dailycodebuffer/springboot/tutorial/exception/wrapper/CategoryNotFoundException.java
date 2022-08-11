package com.dailycodebuffer.springboot.tutorial.exception.wrapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public CategoryNotFoundException() {
		super();
	}
	
	public CategoryNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CategoryNotFoundException(String message) {
		super(message);
	}
	
	public CategoryNotFoundException(Throwable cause) {
		super(cause);
	}
	
	
}
