package com.raktar3.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.raktar3.entities.Stock;

public interface StockRepository extends CrudRepository<Stock, Long> {
	
	List<Stock> findAll();

}
