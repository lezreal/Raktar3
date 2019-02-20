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
	
	public Stock(int amount, String date,int is_incoming, Employe e,Product p, String comment) {
		
		this.amount=amount;
		this.date=date;
		this.incoming=is_incoming;
		this.employe=e;
		this.product=p;
		this.comment=comment;
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
	
	
	
	@ManyToOne
	Product product;
	
	
	@ManyToOne
	Employe employe;
	
	public Employe getEmploye() {
		return employe;
	}

	public void setEmploye(Employe employe) {
		this.employe = employe;
	}

	@Column(columnDefinition="tinyint(1) default 0") // ha 1 akkor bejövő, ha 0 akkor kimenő, ha 2 akkor selejt
	int incoming;
	
	

	

	public int getIncoming() {
		return incoming;
	}

	public void setIncoming(int incoming) {
		this.incoming = incoming;
	}

	String date;
	
	
	@Column(nullable=true)
	String comment;
	
	@Column(nullable=false)
	int amount;
	
}
