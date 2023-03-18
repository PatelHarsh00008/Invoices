package com.spring.project.dtos;

public class JwtAuthResponse {
	private String token;
	public JwtAuthResponse(String token) {
		this.token = token;
	}
	public String getToken() {return this.token;}
	public void setToken(String token) {this.token = token;}
}
