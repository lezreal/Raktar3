package com.raktar3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.raktar3.entities.Machine;

public interface MachineRepository extends CrudRepository<Machine, Integer> {

	
	@Query(value="select max(id) as maxid from machine",nativeQuery=true)
	int findLastId();
	
	List<Machine> findAll();
	
	Machine findById(int id);
}
