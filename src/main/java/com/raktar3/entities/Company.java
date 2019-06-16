package com.raktar3.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Company {
	
	public Company(String name, String city, String address) {
		this.name=name;
		this.address=address;
		this.city=city;
		
		
	}
	

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	int id;
	
	


	@Column(nullable=false,length=50)
	String name;
	
	@Column(nullable=false,length=50)
	String city;
	
	@Column(nullable=true)
	String comment;
	
	@Column(nullable=true)
	String tartozik;
	
	
	public String getTartozik() {
		return tartozik;
	}


	public void setTartozik(String tartozik) {
		this.tartozik = tartozik;
	}


	int tablasorszam;
	
	public int getTablasorszam() {
		return tablasorszam;
	}


	public void setTablasorszam(int tablasorszam) {
		this.tablasorszam = tablasorszam;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	
	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}






	public Company() {}
	
	
	public Company(String name) {
		this.name=name;
		
	}
	
	@OneToMany
	Set<Product> products = new HashSet<Product>();

	@Column(length=200,nullable=false)
	String address;


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
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



	public Set<Product> getProducts() {
		return products;
	}



	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	public void addProduct(Product product) {
		this.products.add(product);
	}
	
	
}
