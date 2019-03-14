package com.raktar3.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.raktar3.entities.Days;

public interface DaysRepository extends CrudRepository<Days, Integer> {

	
	Days findById(int id);
	
	List<Days> findAll();
}
