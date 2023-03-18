package com.harsh.project.respositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.harsh.project.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{
	@Query(value = "select * from projects where company_id = ?1", nativeQuery = true)
	List<Project> getAllProjectsByCompanyId(long id);
}
 