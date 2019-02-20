package com.raktar3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.raktar3.entities.Product;
import com.raktar3.entities.Stock;

public interface StockRepository extends CrudRepository<Stock, Integer> {
	
	
	List<Stock> findAll();
	
	
	@Query(value="select sum(amount) from stock where incoming=1", nativeQuery=true)
	int findToSelejt();
	
	@Query(value = "select distinct product_id from stock", nativeQuery = true)
	List<Integer> findDistinctProduct();
	
	@Query(value="select sum(amount) from stock where product_id=?1 and incoming=1", nativeQuery=true)
	Integer getAmountByProduct(Integer id);    // BEJÖVŐ

	@Query(value="select sum(amount) from stock where product_id=?1 and incoming=0 or incoming=2", nativeQuery=true)
	Integer getAmountByProductSale(Integer id);  // KIMENŐ és a SELEJT

	@Query(value="select count(id) as pid from stock where product_id=?1", nativeQuery=true)
	Integer isProductinStock(int id);
	
	@Query(value="delete from stock where product_id=?1",nativeQuery=true)
	public void deleteById(int id);
}
