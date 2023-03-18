package com.harsh.project.controllers;

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

import com.harsh.project.entities.Company;
import com.harsh.project.service.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {
	@Autowired
	private CompanyService companyService;
	
	@PostMapping("/")
	public ResponseEntity<Company> createCompany(@RequestBody Company company){
		Company createdCompany = this.companyService.createCompany(company);
		return ResponseEntity.ok(createdCompany);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Company> updateCompany(@RequestBody Company company, @PathVariable long id){
		Company updatedCompany = this.companyService.updateCompany(company, id);
		return ResponseEntity.ok(updatedCompany);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Company>> getAllCompanies(){
		return ResponseEntity.ok(this.companyService.getAllCompanies());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Company> getCompany(@PathVariable long id){
		return ResponseEntity.ok(this.companyService.getCompany(id));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCompany(@PathVariable long id){
		this.companyService.deleteCompany(id);
		return ResponseEntity.ok("Company with id : " + id + " successfully deleted");
	}
}
