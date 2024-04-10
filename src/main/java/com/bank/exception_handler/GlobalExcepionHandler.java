package com.bank.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bank.custom_exceptions.InSuffBalanceException;
import com.bank.custom_exceptions.ResourceNotFoundException;
import com.bank.dto.CustomResponse;

@RestControllerAdvice
public class GlobalExcepionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
		System.out.println("in res not found " + e);
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(new CustomResponse<>(true, e.getMessage(), null));
	}
	
	@ExceptionHandler(InSuffBalanceException.class)
	public ResponseEntity<?> handleInSuffBalanceException(InSuffBalanceException e) {
		System.out.println("in InSuffBalanceexception " + e);
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new CustomResponse<>(true, e.getMessage(),null));
	}
}



