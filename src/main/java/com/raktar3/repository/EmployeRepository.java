package com.raktar3.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.raktar3.entities.Employe;

public interface EmployeRepository extends CrudRepository<Employe, Integer> {
	
	Employe findByNameIgnoreCase(String name);
	
	List<Employe> findAll();
	
	Employe findById(int id);

	
	@Query(value="select * from employe where id>1",nativeQuery=true)
	List<Employe> findAllHumanEmploye();
}
