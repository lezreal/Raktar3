package com.raktar3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raktar3.entities.Employe;
import com.raktar3.repository.EmployeRepository;

@Service
public class EmployeService {

	@Autowired
	EmployeRepository employeRepo;
	
	
	public List<Employe> findAllEmploye(){
		return employeRepo.findAll();
	}
	
	
	
}
