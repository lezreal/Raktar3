package com.raktar3.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.raktar3.entities.Company;

public interface CompanyRepository extends CrudRepository<Company, Integer> {

	
	List<Company> findAll();
	
	Company findById(int id);
}
