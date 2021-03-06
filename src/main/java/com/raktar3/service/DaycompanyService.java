package com.raktar3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raktar3.entities.Daycompany;
import com.raktar3.repository.DaycompanyRepository;

@Service
public class DaycompanyService {

	
	@Autowired
	DaycompanyRepository daycompanyRepo;
	
	
	public void addNewDaycompany(Daycompany d) {
		daycompanyRepo.save(d);
	}
	
	public List<Daycompany> findAll() {
		return daycompanyRepo.findAll();
	}
	
	
	public List<String> findDistinctName(){
		return daycompanyRepo.findDistinctName();
		
	}
	
	public List<Daycompany> findSelectedNAme(String name){
		return daycompanyRepo.findSelectedName(name);
	}
	
	public void deleteSelectedName(String name) {
		daycompanyRepo.deleteSelectedName(name);
		
	}
	
	public void sorszamNovel(String name, int sorszam) {
		daycompanyRepo.sorszamNovel(name, sorszam);
	}

	public boolean vaneilyennev(String name) {
		
		if (daycompanyRepo.vanemarnilyennev(name)>0)  return false;
		
		return true;
	}

	
	
}
