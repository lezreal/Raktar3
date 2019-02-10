package com.raktar3.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;



@Entity
public class Product {

	
	public Product() {}
	
	public Product(String name) {
		this.name=name;
	}
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	int id;

	@Column(columnDefinition="tinyint(1) default 0")
	boolean isEmpty=false;
	
	
	@Column(length=50,nullable=true)
	String description;
	
	@Column(length=50,nullable=true)
	int amount;
	
	
	@Column(length=30,nullable=false)
	String name;
	
	@OneToMany(mappedBy="product")
	List<Stock> stockes;
	
	
	@ManyToMany(mappedBy="products")
	Set<Company> companies=new HashSet<Company>();
	
	public Set<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(Set<Company> companies) {
		this.companies = companies;
	}

	public List<Stock> getStockes() {
		return stockes;
	}

	public void setStockes(List<Stock> stockes) {
		this.stockes = stockes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isIsEmpty() {
		return isEmpty;
	}

	public void setIsEmpty(boolean hasEmpty) {
		this.isEmpty = hasEmpty;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}


	
}
