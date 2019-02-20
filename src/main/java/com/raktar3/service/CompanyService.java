package com.raktar3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raktar3.entities.Company;
import com.raktar3.repository.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	CompanyRepository companyRepo;
	
	
	public List<Company> findAll(){
		return companyRepo.findAll();
	}
	
	public Company findById(int id) {
		return companyRepo.findById(id);
	}
	
}
