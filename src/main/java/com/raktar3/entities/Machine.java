package com.raktar3.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Machine {

	public Machine() {}
	
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	int id;
	
	@Column(nullable=false, length=30)
	String name;
	
	@OneToOne
	Company company;
	
	@Column(nullable=true, length=50)
	String comment;
	
	@Column(nullable=false)
	byte type;    // 1: sima ,  2: co2 poharas, 3: co2 szivattyús,  4: hálózati sima, 5: hálózati co2 


	int sorszam;

	
	


	public int getSorszam() {
		return sorszam;
	}

	public void setSorszam(int sorszam) {
		this.sorszam = sorszam;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}
	
	
}
