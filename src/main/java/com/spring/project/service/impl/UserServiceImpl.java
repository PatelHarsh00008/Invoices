package com.spring.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.project.entities.User;
import com.spring.project.exceptions.ResourceNotFoundException;
import com.spring.project.respositories.UserRepository;
import com.spring.project.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}

	@Override
	public User getUserById(long id) {
		User user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		return user;
	}

	@Override
	public User updateUserById(User user, long id) {
		User updateUser = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		updateUser.setUsername(user.getUsername());
		updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
		return updateUser;
	}

	@Override
	public User registerNewUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return this.userRepository.save(user);
	}

	@Override
	public void deleteUserById(long id) {
		User user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		this.userRepository.delete(user);
	}

}
