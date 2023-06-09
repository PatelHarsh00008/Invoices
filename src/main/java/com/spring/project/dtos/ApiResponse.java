package com.spring.project.dtos;

public class ApiResponse {
	public ApiResponse(String message, boolean success) {
		this.message = message;
		this.success = success;
	}
	public ApiResponse() {}
	private String message;
	private boolean success;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
