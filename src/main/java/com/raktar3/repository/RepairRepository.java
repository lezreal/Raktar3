package com.raktar3.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.raktar3.entities.Repair;


public interface RepairRepository extends CrudRepository<Repair, Integer> {
	
	List<Repair> findAll();

}
