package com.harsh.project.respositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harsh.project.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
}
