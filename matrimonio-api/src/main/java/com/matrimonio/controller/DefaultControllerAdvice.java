package com.matrimonio.controller;

import static com.google.common.collect.Lists.newArrayList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.matrimonio.domain.ErrorResponse;
import com.matrimonio.domain.FieldErrorResponse;

@RestControllerAdvice
public class DefaultControllerAdvice {

	@ExceptionHandler({HttpMessageNotReadableException.class})
	public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpServletRequest request, HttpServletResponse servletResponse,HttpMessageNotReadableException httpMessageNotReadableException)
	{
		Throwable mostSpecificCause = httpMessageNotReadableException.getMostSpecificCause();
		JsonMappingException jme = (JsonMappingException) httpMessageNotReadableException.getCause();
		List<Reference> fieldErrors = jme.getPath();
		List<FieldErrorResponse> errors = newArrayList();
		for (Reference fieldError : fieldErrors) {
			errors.add(new FieldErrorResponse(fieldError.getFieldName(), mostSpecificCause.getMessage()));
		}
		ErrorResponse apiError = new ErrorResponse(BAD_REQUEST.value(), "Field Invalid Format", errors);
		return new ResponseEntity<>(apiError, BAD_REQUEST);
	}

    
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAllOtherException(final Exception exception) {
		ErrorResponse apiError = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getLocalizedMessage(), newArrayList("Error Occurred"));
		return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class})
	public ResponseEntity<Object> handleMethodArgumentNotValidException(final MethodArgumentNotValidException methodArgumentNotValidException) {
		BindingResult result = methodArgumentNotValidException.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		return processFieldErrors(fieldErrors);
	}

	private ResponseEntity<Object> processFieldErrors(final List<FieldError> fieldErrors) {
		List<FieldErrorResponse> errors = newArrayList();
		for (FieldError fieldError : fieldErrors) {
			errors.add(new FieldErrorResponse(fieldError.getField(), fieldError.getDefaultMessage()));
		}
		ErrorResponse apiError = new ErrorResponse(BAD_REQUEST.value(), "Field Validation Failed!", errors);
		return new ResponseEntity<>(apiError, BAD_REQUEST);
	}
}