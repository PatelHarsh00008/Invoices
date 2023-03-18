package com.harsh.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harsh.project.entities.Company;
import com.harsh.project.exceptions.ResourceNotFoundException;
import com.harsh.project.respositories.CompanyRespository;
import com.harsh.project.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService{

	@Autowired
	private CompanyRespository companyRespository;
	
	@Override
	public List<Company> getAllCompanies() {
		return this.companyRespository.findAll();
	}

	@Override
	public Company getCompany(long id) {
		return this.companyRespository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));
	}

	@Override
	public Company updateCompany(Company company, long id) {
		Company c = this.companyRespository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));
		c.setName(company.getName());
		return this.companyRespository.save(c);
		
	}

	@Override
	public Company createCompany(Company company) {
		Company createdCompany = this.companyRespository.save(company);
		return createdCompany;
	}

	@Override
	public void deleteCompany(long id) {
		Company c = this.companyRespository.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));
		this.companyRespository.delete(c);
	}


}
