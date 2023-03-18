package com.harsh.project.respositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.harsh.project.entities.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long>{
	@Query(value = "select * from managers where project_id = ?1", nativeQuery = true)
	List<Manager> getAllManagersByProjectId(long id);
}
