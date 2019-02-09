package com.raktar3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raktar3.entities.Stock;
import com.raktar3.repository.StockRepository;

@Service
public class StockService {

	
	
	@Autowired
	StockRepository stockRepo;
	
	
	public void addIncoming(Stock s) {
		stockRepo.save(s);
	}
	
	public List<Stock> stockList(){
		return stockRepo.findAll();
	}
	
	
	
}
