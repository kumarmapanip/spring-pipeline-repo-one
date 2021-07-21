package com.sapient.ofd.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
/**
 * 
 * @author Admin
 * Purpose: To centralize exception-handling
 */
//combination of  @ControllerAdviceand @ResponseBody. 
@RestControllerAdvice
public class ExceptionAdvice {

	/*
	 * Any method within RestController class throws FoodDelivaryException, control comes
	 * to this method
	 */
//	@ExceptionHandler(value =  FoodDelivaryException.class)
//	public ResponseEntity<ErrorResponse> handleGenericNotFoundException(FoodDelivaryException e) {
//		ErrorResponse error = new ErrorResponse("FOOD_DELIVARY_ERROR", e.getMessage());
//		error.setTimestamp(LocalDateTime.now());
//		error.setStatus((HttpStatus.NOT_FOUND.value()));
//		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//	}	
	
	
	@ExceptionHandler(value =  FoodDelivaryException.class)
	public ResponseStatusException handleFoodDelivaryException(FoodDelivaryException e) {
		ErrorResponse error = new ErrorResponse("FOOD_DELIVARY_ERROR", e.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		return new ResponseStatusException(HttpStatus.NOT_FOUND,error.toString());
	}
	
	/*
	 * Any method within RestController class throws IllegalArgumentException, control comes
	 * to this method
	 */
	@ExceptionHandler(value = { IllegalArgumentException.class})
	public ResponseEntity<ErrorResponse> handleGenericIllegalArgumentException(IllegalArgumentException e) {
		ErrorResponse error = new ErrorResponse("ILLEGAL_ARGUMENT_ERROR", e.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus((HttpStatus.NOT_ACCEPTABLE.value()));
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}	
	
	/*
	 * Any method within RestController class throws IllegalArgumentException, control comes
	 * to this method
	 */
	@ExceptionHandler(value = { NumberFormatException.class})
	public ResponseEntity<ErrorResponse> handleGenericNumberFormatException(NumberFormatException e) {
		ErrorResponse error = new ErrorResponse("NUMBER_FORMAT_ERROR", e.getMessage());
		error.setTimestamp(LocalDateTime.now());
		error.setStatus((HttpStatus.METHOD_NOT_ALLOWED.value()));
		return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
	}	


}
