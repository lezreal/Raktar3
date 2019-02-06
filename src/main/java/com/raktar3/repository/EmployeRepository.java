package com.raktar3.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.raktar3.entities.Employe;

public interface EmployeRepository extends CrudRepository<Employe, Long> {
	
	Employe findByNameIgnoreCase(String name);
	
	List<Employe> findAll();

}
