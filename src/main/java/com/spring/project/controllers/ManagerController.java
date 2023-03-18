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

import com.spring.project.entities.Manager;
import com.spring.project.entities.Project;
import com.spring.project.service.ManagerService;

@RestController
@RequestMapping("/manager")
public class ManagerController {
	@Autowired
	private ManagerService managerService;
	
	@GetMapping("/")
	public ResponseEntity<List<Manager>> getAllManagers(){
		return ResponseEntity.ok(this.managerService.getAllManagers());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Manager> getManager(@PathVariable long id){
		return ResponseEntity.ok(this.managerService.getManagerById(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Manager> updateManager(@RequestBody Manager manager, @PathVariable long id){
		Manager updatedManager = this.managerService.updateManager(manager, id);
		return ResponseEntity.ok(updatedManager);
	}
	
	@PostMapping("/")
	public ResponseEntity<Manager> createManager(@RequestBody Manager manager){
		Manager createdManager = this.managerService.createManager(manager);
		return ResponseEntity.ok(createdManager);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteManager(@PathVariable long id){
		this.managerService.deleteManager(id);
		return ResponseEntity.ok("Manager with id : " + id + " successfully deleted");
	}
	
	@GetMapping("/projectId/{id}")
	public ResponseEntity<List<Manager>> getManagersWithProjectId(@PathVariable long id){
		return ResponseEntity.ok(this.managerService.getAllManagersByProjectId(id));
	}

}
