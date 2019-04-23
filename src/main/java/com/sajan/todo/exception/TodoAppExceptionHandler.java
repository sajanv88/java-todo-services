package com.sajan.todo.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class TodoAppExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest req) {
		ExceptionResponse exceptionResponse = 
				new ExceptionResponse(new Date(),ex.getMessage(),
				req.getDescription(false));
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserException.class)
	public final ResponseEntity<Object> UserExceptionHandler(Exception ex, WebRequest req) {
		ExceptionResponse exceptionResponse = 
				new ExceptionResponse(new Date(),ex.getMessage(),
				req.getDescription(false));
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		System.out.println(ex.getBindingResult().getAllErrors());
		ExceptionResponse exceptionResponse = 
				new ExceptionResponse(new Date(), "Validation Failed",
				ex.getBindingResult().toString());
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserForBiddenException.class)
	public final ResponseEntity<Object> accessDeniedHandler(Exception ex, WebRequest req) {
		ExceptionResponse exceptionResponse = 
				new ExceptionResponse(new Date(),ex.getMessage(),
				req.getDescription(false));
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(TodoAccessNotValidException.class)
	public final ResponseEntity<Object> appKeyNotValidHandler(Exception ex, WebRequest req) {
		ExceptionResponse exceptionResponse = 
				new ExceptionResponse(new Date(),ex.getMessage(),
				req.getDescription(false));
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
}
