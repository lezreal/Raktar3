package com.raktar3.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Company {

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	Long id;
	
	@Column(nullable=false,length=50)
	String name;
	
	@Column(nullable=true,length=50)
	String city;
	
	@Column(nullable=true,length=6)
	String deliverydays;
	
	@ManyToOne
	Employe employe;
	
	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getDeliverydays() {
		return deliverydays;
	}


	public void setDeliverydays(String deliverydays) {
		this.deliverydays = deliverydays;
	}


	public Employe getEmploye() {
		return employe;
	}


	public void setEmploye(Employe employe) {
		this.employe = employe;
	}


	public Company() {}
	
	
	public Company(String name) {
		this.name=name;
		
	}
	
	@ManyToMany
	@JoinTable(
			name="companies_products",
			joinColumns= {@JoinColumn(name="company_id")},
			inverseJoinColumns= {@JoinColumn(name="product_id")}
			)
	
	Set<Product> products = new HashSet<Product>();

	@Column(length=200,nullable=true)
	String address;


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Long getId() {
		return id;
	}



	public void setId(Long id) {
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
