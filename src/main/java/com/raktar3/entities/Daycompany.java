package com.raktar3.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Daycompany {

	public Daycompany() {}
	
	public Daycompany(Company c, int sorszam, String name) {
		this.company=c;
		this.sorszam=sorszam;
		this.name=name;
		
	}
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	Integer id;
	
	String name;

	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@OneToOne
	Company company;
	
	
	
	
	int sorszam;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


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
}
