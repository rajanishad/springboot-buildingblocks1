package com.stacksimplify.restservices.exceptions;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	// method arguentnotvalid exception
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(),
				"From methodargnotvalid exception in geh", ex.getMessage());

		return new ResponseEntity<Object>(customErrorDetails, HttpStatus.BAD_REQUEST);
	}

	// httprequestmethodnot supported exception
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(),
				"From httpRequestMethodNotSuportedEcxeption in geh - method not allowed", ex.getMessage());

		return new ResponseEntity<Object>(customErrorDetails, HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	//username notfound exception
	@ExceptionHandler(UserNameNotFoundEcxeption.class)
	public final ResponseEntity<Object> handleUserNameNotFoundException(UserNameNotFoundEcxeption ex,
			WebRequest request) {
		
		CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(),
				ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<Object>(customErrorDetails, HttpStatus.NOT_FOUND);
		
		
	}
	
	//constraintvoilationException
	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<Object> handleConstraintVoilationException(ConstraintViolationException ex,
			WebRequest request) {
		
		CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(),
				ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<Object>(customErrorDetails, HttpStatus.BAD_REQUEST);
		
	}
	
}
