package com.raktar3.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.raktar3.entities.Product;


public interface ProductRepository extends CrudRepository<Product, Long> {
	
	Product findByNameIgnoreCase(String name);
	
	List<Product> findAll();
	
	public void delete(Product p);

}
