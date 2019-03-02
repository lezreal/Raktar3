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
import javax.persistence.OneToMany;

@Entity
public class Company {
	
	public Company(String name, String city, String address, String delday, Employe e) {
		this.name=name;
		this.address=address;
		this.city=city;
		this.deliverydays=delday;
		this.employe=e;
		
	}
	

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	int id;
	
	@Column(nullable=false,length=50)
	String name;
	
	@Column(nullable=false,length=50)
	String city;
	
	@Column(nullable=true,length=6)
	String deliverydays;
	@Column(nullable=true)
	String comment;
	
	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


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
