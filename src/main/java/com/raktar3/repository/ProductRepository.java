package com.raktar3.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.raktar3.entities.Product;


public interface ProductRepository extends CrudRepository<Product, Integer> {
	
	Product findByNameIgnoreCase(String name);
	
	List<Product> findAll();
	
	public void delete(Product p);
	
	@Query(value="select * from product where id=?1",nativeQuery=true)
	Product findById(int id);
	
	@Query(value="SELECT * FROM product WHERE name LIKE CONCAT('%',?1,'%')",nativeQuery=true)
	List<Product> findProductsWithPartOfName(String username);

}
