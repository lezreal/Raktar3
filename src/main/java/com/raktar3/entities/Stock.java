package com.raktar3.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Stock {

	public Stock() {}
	
	
	
	public boolean isSelejt() {
		return selejt;
	}

	public void setSelejt(boolean selejt) {
		this.selejt = selejt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	int id;
	
	
	
	@ManyToOne()
	Product product;
	
	
	@ManyToOne
	Employe employe;
	
	public Employe getEmploye() {
		return employe;
	}

	public void setEmploye(Employe employe) {
		this.employe = employe;
	}

		String date;
	
	
	@Column(nullable=true)
	String comment;
	
	@Column(nullable=false)
	int amount;
	
	@Column(nullable=false,columnDefinition="tinyint(1) default 0")
	boolean beerkezes;
	
	public boolean isBeerkezes() {
		return beerkezes;
	}

	public void setBeerkezes(boolean beerkezes) {
		this.beerkezes = beerkezes;
	}

	public boolean isEladas() {
		return eladas;
	}

	public void setEladas(boolean eladas) {
		this.eladas = eladas;
	}

	@Column(nullable=false,columnDefinition="tinyint(1) default 0")
	boolean eladas;

	@Column(nullable=false,columnDefinition="tinyint(1) default 0")
	boolean selejt;
}
