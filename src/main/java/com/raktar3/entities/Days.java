package com.raktar3.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Days {

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	Integer id;
	
	public Days() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Days(String name) {
		
		this.name=name;
	}

	@Column(nullable=false)
	String name;
	
}
