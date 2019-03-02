package com.raktar3.service;

import java.util.List;
import java.util.Optional;

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
	
	public Employe findById(int id) {
		return employeRepo.findById(id);
	}

	public boolean addUserToDb(Employe emp) {
		try {
		employeRepo.save(emp);
		return true;
		} catch(Exception ex) {
			return false;
		}
		
	}

	public Employe findByNameIgnoreCase(String name) {
		return employeRepo.findByNameIgnoreCase(name);
	}

	public List<Employe> findAllHumanEmploye() {
		return employeRepo.findAllHumanEmploye();
	}
	
	public void delEmploye(Employe e) {
		employeRepo.delete(e);
	}

	
	
}
