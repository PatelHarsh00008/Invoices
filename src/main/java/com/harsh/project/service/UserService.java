package com.harsh.project.service;

import java.util.List;

import com.harsh.project.entities.User;

public interface UserService {

	List<User> getAllUsers();
	User getUserById(long id);
	User updateUserById(User user, long id);
	User registerNewUser(User user);
	void deleteUserById(long id);
}
