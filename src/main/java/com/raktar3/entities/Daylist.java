package com.raktar3.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Daylist {

	
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	Integer id;
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	@ManyToOne
	Company company;
	
	int sorszam;

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public int getSorszam() {
		return sorszam;
	}

	public void setSorszam(int sorszam) {
		this.sorszam = sorszam;
	}
	
	public Daylist() {};
	
	public Daylist(Company c, int sorszam) {
		this.company=c;
		this.sorszam=sorszam;
	}
	
}
