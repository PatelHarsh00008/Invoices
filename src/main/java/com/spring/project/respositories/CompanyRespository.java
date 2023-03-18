package com.spring.project.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.project.entities.Company;

public interface CompanyRespository extends JpaRepository<Company, Long>{
	

}
