package com.spring.project.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.project.dtos.ApiResponse;
import com.spring.project.dtos.JwtAuthRequest;
import com.spring.project.dtos.JwtAuthResponse;
import com.spring.project.entities.User;
import com.spring.project.security.JwtTokenHelper;
import com.spring.project.service.AuthenticationService;
import com.spring.project.service.UserService;

import io.jsonwebtoken.impl.DefaultClaims;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private AuthenticationService authService;
	
	@GetMapping("/")
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getSingleUser(@PathVariable long id){
		return ResponseEntity.ok(this.userService.getUserById(id));
	}
	
	@PostMapping("/register")
	public ResponseEntity<JwtAuthResponse> register(@RequestBody JwtAuthRequest request){
		return ResponseEntity.ok(this.authService.register(request));
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<JwtAuthResponse> authenticate(@RequestBody JwtAuthRequest request){
		return ResponseEntity.ok(this.authService.authenticate(request));
	}
	
	@GetMapping("/refreshtoken")
	public ResponseEntity<?> refreshToken(HttpServletRequest request){
		DefaultClaims claims = (DefaultClaims) request.getAttribute("claims");
		Map<String, Object> expectedMap = this.authService.getMapFromJwtClaims(claims);
		String token = this.jwtTokenHelper.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
		return ResponseEntity.ok(new JwtAuthResponse(token));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable long id){
		User updatedUser = this.userService.updateUserById(user, id);
		return ResponseEntity.ok(updatedUser);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable long id){
		this.userService.deleteUserById(id);
		return ResponseEntity.ok(new ApiResponse("User deleted successfullly", true));
	}
}
