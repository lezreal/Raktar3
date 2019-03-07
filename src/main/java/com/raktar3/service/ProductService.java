package com.raktar3.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raktar3.entities.Company;
import com.raktar3.entities.Employe;
import com.raktar3.entities.Product;
import com.raktar3.repository.CompanyRepository;
import com.raktar3.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	StockService stockService;
	
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	CompanyRepository companyRepo;
	
	@Autowired
	EmployeService employeService;
	
	
	public Product findById(int id) {
		return productRepo.findById(id);
	}
	
	public List<Product> findUres(String name){
		return productRepo.findProductsWithPartOfName(name);
	}
	
	public boolean findProductByName(String name) {
		if (productRepo.findByNameIgnoreCase(name)==null) return true; else return false;
	}
	
	public void regNewProduct(Product p) {
		productRepo.save(p);
	}
	
	public boolean deleteProduct(Product p) {
		if (!stockService.vaneProduct(p.getId())) {
			productRepo.delete(p);
			return true;
		} else return false;
	}
	
	public List<Product> findAll(){
	return productRepo.findAll();	
	}
	
	public Employe empNameSearch(String name) {
		return employeService.findByNameIgnoreCase(name);
	}
	
	
	public List<Employe> findAllEmploye(){
		return employeService.findAllEmploye();
	}
	
	public boolean addUserToDb(Employe emp) {
		if (empNameSearch(emp.getName())==null) {
			employeService.addUserToDb(emp);
			return true;
		} else return false;
	}
	
	
	public void addCompany(Company c) {
		companyRepo.save(c);
	}
	
	public List<Company> findAllCompany(){
		return companyRepo.findAll();
	}

	public void deleteAll() {
		productRepo.deleteAll();
		
	}
	
	
	
//	@PostConstruct
//	public void init() {
//
//		Employe e = new Employe("ALAPKÉSZLET");
//		Employe e1 = new Employe("KAMION");
//		employeService.addUserToDb(e);
//		employeService.addUserToDb(e1);
//		
//		Company c= new Company();
//		c.setAddress("");
//		c.setCity("");
//		c.setName("RAKTÁR");
//		companyRepo.save(c);
//
//	}
				


	


	
	
}
