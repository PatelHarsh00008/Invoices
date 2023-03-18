package com.harsh.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harsh.project.entities.Project;
import com.harsh.project.exceptions.ResourceNotFoundException;
import com.harsh.project.respositories.ProjectRepository;
import com.harsh.project.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService{

	@Autowired
	private ProjectRepository projectRepository;
	
	@Override
	public List<Project> getAllProjects() {
		
		return this.projectRepository.findAll();
	}

	@Override
	public Project getProjectById(long id) {
		return this.projectRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Project", "Id", id));
	}

	@Override
	public Project updateProject(Project project, long id) {
		Project p = this.projectRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Project", "Id", id));
		p.setName(project.getName());
		p.setRatePerHour(project.getRatePerHour());
		p.setRatePerKm(project.getRatePerKm());
		p.setCompany(project.getCompany());
		return this.projectRepository.save(p);
	}

	@Override
	public Project createProject(Project project) {
		Project createdProject = this.projectRepository.save(project);
		return createdProject;
	}

	@Override
	public void deleteProject(long id) {
		Project p = this.projectRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Project", "Id", id));
		this.projectRepository.delete(p);
	}

	@Override
	public List<Project> getAllProjectsByCompanyId(long id) {
		List<Project> projects = this.projectRepository.getAllProjectsByCompanyId(id);
		if (projects.size() == 0) throw new ResourceNotFoundException("Projects", "company id", id);
		return projects;
	}

}
