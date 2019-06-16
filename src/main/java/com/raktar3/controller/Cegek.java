package com.raktar3.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;

import com.raktar3.entities.Company;
import com.raktar3.service.CompanyService;

@Configuration("allcompany")
public class Cegek {

	@Autowired
	CompanyService companyService;
	
	
	
	public List<Company> companyall(){
		
		return companyService.findAll();
	}
	
}
