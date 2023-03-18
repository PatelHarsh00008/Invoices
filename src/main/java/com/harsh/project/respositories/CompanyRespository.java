package com.harsh.project.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harsh.project.entities.Company;

public interface CompanyRespository extends JpaRepository<Company, Long>{
	

}
