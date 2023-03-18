package com.spring.project.service;

import java.util.List;

import com.spring.project.entities.Project;

public interface ProjectService {
	List<Project> getAllProjects();
	Project getProjectById(long id);
	Project updateProject(Project project, long id);
	Project createProject(Project project);
	void deleteProject(long id);
	List<Project> getAllProjectsByCompanyId(long id);
}
