package com.harsh.project.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.harsh.project.dtos.JwtAuthRequest;
import com.harsh.project.dtos.JwtAuthResponse;
import com.harsh.project.entities.User;
import com.harsh.project.exceptions.ResourceNotFoundException;
import com.harsh.project.respositories.UserRepository;
import com.harsh.project.security.JwtTokenHelper;

import io.jsonwebtoken.impl.DefaultClaims;

@Service
public class AuthenticationService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	public JwtAuthResponse register(JwtAuthRequest request) {
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(this.passwordEncoder.encode(request.getPassword()));
		this.userRepository.save(user);
		String token = this.jwtTokenHelper.generateToken(user);
		return new JwtAuthResponse(token);
	}

	public JwtAuthResponse authenticate(JwtAuthRequest request) {
		this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		User user = this.userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new ResourceNotFoundException("User", "username" , request.getUsername()));
		String token = this.jwtTokenHelper.generateToken(user);
		return new JwtAuthResponse(token);
	}

	public Map<String, Object> getMapFromJwtClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<>();
		for(Entry<String, Object> entry : claims.entrySet())
			expectedMap.put(entry.getKey(), entry.getValue());
		return expectedMap;
	}
	
}
