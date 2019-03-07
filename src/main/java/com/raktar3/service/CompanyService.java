package com.raktar3.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raktar3.entities.Company;
import com.raktar3.entities.Employe;
import com.raktar3.repository.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	CompanyRepository companyRepo;
	
	
	public List<Company> findAll(){
		return companyRepo.findAll();
	}
	
	public List<Company> findAllReal(){
		return companyRepo.findAllReal();
	}
	
	public Company findById(int id) {
		return companyRepo.findById(id);
	}
	
	public void addNewCompany(Company c) {
		companyRepo.save(c);
	}

	public boolean findByEmploye(Employe e) {
		Company tmp =companyRepo.findFirstByEmploye(e);
		
		if (tmp!=null) return false; else return true;
		
	}
	
	
	public List<Company> finddelday(String mit, int id){
		return companyRepo.findDelDay(mit, id);
	}

	
	
	
//	@PostConstruct
//	public void init() {
//		Company c= new Company();
//		c.setAddress("");
//		c.setCity("");
//		c.setName("RAKTÁR");
//		companyRepo.save(c);
//		
//	}
}
