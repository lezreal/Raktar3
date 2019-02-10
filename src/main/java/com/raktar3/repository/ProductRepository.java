package com.raktar3.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.raktar3.entities.Product;


public interface ProductRepository extends CrudRepository<Product, Integer> {
	
	Product findByNameIgnoreCase(String name);
	
	List<Product> findAll();
	
	public void delete(Product p);
	
	Optional<Product> findById(Integer id);
	


}
