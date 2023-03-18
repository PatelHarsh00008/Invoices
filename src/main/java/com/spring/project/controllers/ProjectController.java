package com.spring.project.controllers;

import java.util.List;

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

import com.spring.project.entities.Company;
import com.spring.project.entities.Project;
import com.spring.project.service.ProjectService;

@RestController
@RequestMapping("/project")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@GetMapping("/")
	public ResponseEntity<List<Project>> getAllProjects(){
		return ResponseEntity.ok(this.projectService.getAllProjects());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Project> getProject(@PathVariable long id){
		return ResponseEntity.ok(this.projectService.getProjectById(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Project> updateProject(@RequestBody Project project, @PathVariable long id){
		Project updatedProject = this.projectService.updateProject(project, id);
		return ResponseEntity.ok(updatedProject);
	}
	
	@PostMapping("/")
	public ResponseEntity<Project> createProject(@RequestBody Project project){
		Project createdProject = this.projectService.createProject(project);
		return ResponseEntity.ok(createdProject);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProject(@PathVariable long id){
		this.projectService.deleteProject(id);
		return ResponseEntity.ok("Project with id : " + id + " successfully deleted");
	}
	
	@GetMapping("/companyId/{id}")
	public ResponseEntity<List<Project>> getProjectsWithCompanyId(@PathVariable long id){
		return ResponseEntity.ok(this.projectService.getAllProjectsByCompanyId(id));
	}
	

}
