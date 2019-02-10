package com.raktar3.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raktar3.entities.Company;
import com.raktar3.entities.Employe;
import com.raktar3.entities.Product;
import com.raktar3.entities.Stock;
import com.raktar3.repository.CompanyRepository;
import com.raktar3.repository.EmployeRepository;
import com.raktar3.repository.ProductRepository;
import com.raktar3.repository.StockRepository;

@Service
public class ProductService {

	@Autowired
	StockRepository stockRepo;
	
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	CompanyRepository companyRepo;
	
	@Autowired
	EmployeRepository employeRepo;
	
	
	public Optional<Product> findById(Integer id) {
		return productRepo.findById(id);
	}
	
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
	
	
	
//	@PostConstruct
//	public void init() {
//		
//		Product p = new Product("Aqua Via");
//		productRepo.save(p);
//		Product p1 = new Product("Brill");
//		productRepo.save(p1);
//		Product p2 = new Product("Milotai");
//		productRepo.save(p2);
//		
//
//		
//		
//		Employe e = new Employe("Vencel");
//		Employe e2 = new Employe("Sanyi");
//		Company comp = new Company("Bumet","Debrecen","Határ út","H,S,P",e);
//		Company comp1 = new Company("Manz","Debrecen","Határ út","H",e2);
//		employeRepo.save(e);
//		employeRepo.save(e2);
//		
//		companyRepo.save(comp);
//		companyRepo.save(comp1);
//		
//			
//		Stock s = new Stock(50,"2019-02-08",true,e,p,"nincs komment");
//		Stock s2 = new Stock(120,"2019-02-07",true,e2,p2,"második");
//		Stock s3 = new Stock(15,"2019-02-08",false,e,p,"eladas");
//		Stock s4 = new Stock(70,"2019-02-07",false,e2,p2,"eladas");
//		
//		stockRepo.save(s);
//		stockRepo.save(s2);
//		stockRepo.save(s3);
//		stockRepo.save(s4);
//		
//	}


	
	
}
