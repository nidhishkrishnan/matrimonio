package com.matrimonio.domain;

import java.util.List;

public class ErrorResponse {

	private int status;
	private String message;
	private List<?> errors;

	public ErrorResponse(int status, String message, List<?> errors) {
		super();
		this.status = status;
		this.message = message;
		this.errors = errors;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<?> getErrors() {
		return errors;
	}

	public void setErrors(List<?> errors) {
		this.errors = errors;
	}
}