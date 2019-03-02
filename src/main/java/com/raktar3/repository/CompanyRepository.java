package com.raktar3.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.raktar3.entities.Company;
import com.raktar3.entities.Employe;

public interface CompanyRepository extends CrudRepository<Company, Integer> {

	
	List<Company> findAll();
	
	Company findById(int id);
	
	Company findFirstByEmploye(Employe e); 
}
