package com.raktar3.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raktar3.entities.Company;
import com.raktar3.entities.Employe;
import com.raktar3.repository.CompanyRepository;

@Service
public class CompanyService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
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
