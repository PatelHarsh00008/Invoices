package com.harsh.project.service;

import java.util.List;

import com.harsh.project.entities.Company;

public interface CompanyService {
	List<Company> getAllCompanies();
	Company getCompany(long id);
	Company updateCompany(Company company, long id);
	Company createCompany(Company company);
	void deleteCompany(long id);
}
