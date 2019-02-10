package com.raktar3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.raktar3.entities.Product;
import com.raktar3.entities.Stock;

public interface StockRepository extends CrudRepository<Stock, Integer> {
	
	List<Stock> findAll();
	
	
	@Query(value = "select distinct product_id from stock", nativeQuery = true)
	List<Integer> findDistinctProduct();
	
	@Query(value="select sum(amount) from stock where product_id=?1 and is_incoming=true", nativeQuery=true)
	Integer getAmountByProduct(Integer id);

	@Query(value="select sum(amount) from stock where product_id=?1 and is_incoming=false", nativeQuery=true)
	Integer getAmountByProductSale(Integer id);

}
