package com.raktar3.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employe {

	
	
	public Employe() {}
	
	
	public Employe(String name) {
		this.name=name;
		
	}
	
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	Long id;
	
	@Column(length=30, nullable=false, unique=true)
	String name;
	
	@Column(length=200, nullable=true)
	String description;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
