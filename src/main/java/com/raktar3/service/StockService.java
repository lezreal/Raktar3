package com.raktar3.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raktar3.entities.Employe;
import com.raktar3.entities.Product;
import com.raktar3.entities.Stock;
import com.raktar3.repository.StockRepository;

@Service
public class StockService {

	@Autowired
	ProductService productService;
	
	@Autowired
	EmployeService employeService;
	
	@Autowired
	StockRepository stockRepo;
	
	
	public void addIncoming(Stock s) {
		stockRepo.save(s);
	}
	
	public List<Stock> stockList(){
		return stockRepo.findAll();
	}
	
	public void saleStock(Stock s) {
		stockRepo.save(s);
	}
	
	public List<Integer> findProducts(){
		return stockRepo.findDistinctProduct();
	}
	
	public int getAmount(int i) {   // bejövő összeg egy termékből
		if (stockRepo.getAmountByProduct(i)==null) return 0; else
		return stockRepo.getAmountByProduct(i);
	}
	
	public int getAmountSale(int i) {  // kimenő összeg egy termékből
		if (stockRepo.getAmountByProductSale(i)==null) return 0; else
		return stockRepo.getAmountByProductSale(i);
	}
	public boolean vaneKeszlet() {
		try {
			if (stockRepo.findToSelejt()>0)
			return true; else return false;
		} catch(Exception ex) {
		return false;	
		}
	}
	
	public boolean vaneProduct(int id) {
		if (stockRepo.isProductinStock(id) ==0) return false; else return true;
		
	}
	
	public List<Stock> findByProduct(Product p){
		return stockRepo.findByProduct(p);
	}
	
	
	public List<Stock> lekerdezes(String date, int empid, int pid){
		if (pid==0) {
			return stockRepo.findAllLekerdezes(date, empid);
		} else return stockRepo.findSelectedLekerdezes(date, empid, pid);
	}
	
	public void deleteAll() {
	stockRepo.deleteAll();
	}
	
	public void deleteByProduct(Integer id) {
		stockRepo.deleteByProduct(id);
	}
	
	public void deleteStock(Stock s) {
		stockRepo.delete(s);
	}
	
	public boolean findEmploye(Employe e) {
		if (stockRepo.findFirstByEmploye(e)==null) return true; else return false;
	}
	
	
	public void deleteById(int id) {
		stockRepo.deleteById(id);
		}
}
