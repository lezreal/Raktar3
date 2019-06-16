package com.raktar3.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raktar3.entities.Company;

import com.raktar3.repository.CompanyRepository;

@Service
public class CompanyService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CompanyRepository companyRepo;
	
	public Set<Company> findByName(String s){
		
		Set<Company> templist = new HashSet<Company>();
		
		for (Company x:companyRepo.findByAddressContainingIgnoreCase(s)) {
			templist.add(x);
		}
		
		for (Company x:companyRepo.findByNameContainingIgnoreCase(s)) {
			templist.add(x);
		}
		
		
		return templist;
	}
	
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

	
	
	
	
	public List<Company> finddelday(int id){
		
		List<Company> sorszamNelkuli = companyRepo.findDelDay(id);
		
		for (int i=0;i<sorszamNelkuli.size();i++) {
			
			if (sorszamNelkuli.get(i).getTablasorszam()==0) {
//				companyRepo.addTableSorszam(sorszamNelkuli.get(i).getId(), i+1);
				sorszamNelkuli.get(i).setTablasorszam(i+1);
				
				
			}
			
		}
		
		//return companyRepo.findDelDay(mit, id);
		return sorszamNelkuli;
	}

	public void updateAddress(int compid, String address) {
		companyRepo.updateAddress(compid,address);
		
	}

	public void updateComment(int compid, String comment) {
		companyRepo.updateComment(compid,comment);
		
	}

	public void updateName(int compid, String name) {
		companyRepo.updateName(compid, name);
		
	}

	public void updateTartozas(int compid, String tartozas) {
		companyRepo.updateTartozas(compid, tartozas);
		
	}

	public List<Company> tartozok() {
		return companyRepo.tartozok();
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
