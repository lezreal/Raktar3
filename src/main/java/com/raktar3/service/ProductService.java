package com.raktar3.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raktar3.entities.Company;
import com.raktar3.entities.Employe;
import com.raktar3.entities.Product;
import com.raktar3.repository.CompanyRepository;
import com.raktar3.repository.EmployeRepository;
import com.raktar3.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	CompanyRepository companyRepo;
	
	@Autowired
	EmployeRepository employeRepo;
	
	public boolean findProductByName(String name) {
		if (productRepo.findByNameIgnoreCase(name)==null) return true; else return false;
	}
	
	public void regNewProduct(Product p) {
		productRepo.save(p);
	}
	
	public void deleteProduct(Product p) {
		productRepo.delete(p);
	}
	
	public List<Product> findAll(){
	return productRepo.findAll();	
	}
	
	public Employe empNameSearch(String name) {
		return employeRepo.findByNameIgnoreCase(name);
	}
	
	
	public List<Employe> findAllEmploye(){
		return employeRepo.findAll();
	}
	
	public boolean addUserToDb(Employe emp) {
		if (empNameSearch(emp.getName())==null) {
			employeRepo.save(emp);
			return true;
		} else return false;
	}
	
	
	public void addCompany(Company c) {
		companyRepo.save(c);
	}
	
	public List<Company> findAllCompany(){
		return companyRepo.findAll();
	}
	
	
	
	@PostConstruct
	public void init() {
		
		Product p = new Product("Aqua Via");
		productRepo.save(p);
		Product p1 = new Product("Brill");
		productRepo.save(p1);
		Product p2 = new Product("Milotai");
		productRepo.save(p2);
		
		

//		comp.addProduct(p);
//		comp.addProduct(p1);
		
		
		Employe e = new Employe("Vencel");
		Employe e2 = new Employe("Sanyi");
		Company comp = new Company("Bumet","Debrecen","Határ út","H,S,P",e);
		Company comp1 = new Company("Manz","Debrecen","Határ út","H",e2);
		employeRepo.save(e);
		employeRepo.save(e2);
		
		companyRepo.save(comp);
		companyRepo.save(comp1);
		
		
		
	}


	
	
}
