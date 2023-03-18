package com.spring.project.respositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.project.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
}
