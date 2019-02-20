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
	
	
	
	@PostConstruct
	public void init() {
//		
//		Product p = new Product("Aqua Via");productRepo.save(p);
//		Product p1 = new Product("Brill");productRepo.save(p1);
//		Product p2 = new Product("Milotai");productRepo.save(p2);
//		Product p3 = new Product("via-ÜRES BALLON ");productRepo.save(p3);
//		Product p4 = new Product("brill-ÜRES BALLON");productRepo.save(p4);
//		Product p5 = new Product("milotai-ÜRES BALLON");productRepo.save(p5);
//		
//
//		Employe e = new Employe("ALAPKÉSZLET");
//		Employe e1 = new Employe("KAMION");
//		Employe e2 = new Employe("Vencel");
//		Employe e3 = new Employe("Sanyi");
//		
//		
		
//		employeService.addUserToDb(e);
//		employeService.addUserToDb(e1);
//
//		
// 		Company comp = new Company("Bumet","Debrecen","Határ út","H,S,P",e);
//		Company comp1 = new Company("Manz","Debrecen","Határ út","H",e2);		
//		companyRepo.save(comp);
//		companyRepo.save(comp1);
//		
//		Stock s = new Stock(50,"2019-02-08",true,e,p,"nincs komment");
//		Stock s2 = new Stock(120,"2019-02-07",true,e2,p2,"második");
//		Stock s3 = new Stock(15,"2019-02-08",false,e,p,"eladas");
//		Stock s4 = new Stock(70,"2019-02-07",false,e2,p2,"eladas");
//		
//		stockService.addIncoming(s);
//		stockService.addIncoming(s2);
//		stockService.addIncoming(s3);
//		stockService.addIncoming(s4);
//		
	}

	


	
	
}
