package com.joony.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice // 모든 예외처리
@RestController
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value=IllegalArgumentException.class)
	public String handleArgumentException(IllegalArgumentException e) {
		return "<h1>"+e.getMessage()+"</h1>";
	}
}//class
