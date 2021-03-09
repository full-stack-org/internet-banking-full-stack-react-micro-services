package com.internet.banking.account.open.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AccountOpeningExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(AccountOpenException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(AccountOpenException foundException,
			WebRequest webRequest) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setLocalDateTime(LocalDateTime.now());
		exceptionDetails.setMessage(foundException.getMessage());
		exceptionDetails.setDetails(webRequest.getDescription(false));
		return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGlobalException(Exception exception, WebRequest webRequest) {
		ExceptionDetails exceptionDetails = new ExceptionDetails();
		exceptionDetails.setLocalDateTime(LocalDateTime.now());
		exceptionDetails.setMessage(exception.getMessage());
		exceptionDetails.setDetails(webRequest.getDescription(false));
		return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", status.value());

		// Get all errors
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		body.put("errors", errors);

		return new ResponseEntity<>(body, headers, status);

	}

}
