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
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public boolean isIncoming() {
		return isIncoming;
	}

	public void setIncoming(boolean isIncoming) {
		this.isIncoming = isIncoming;
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
	Long id;
	
	
	
	@ManyToOne
	Product product;
	
	
	@ManyToOne
	Employe employe;
	
	@Column(columnDefinition="tinyint(1) default 0")
	boolean isIncoming;
	
	String date;
	
	
	@Column(nullable=true)
	String comment;
	
	@Column(nullable=false)
	int amount;
	
}
