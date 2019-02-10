package com.raktar3.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
}
