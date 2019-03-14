package com.raktar3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raktar3.entities.Days;
import com.raktar3.repository.DaysRepository;

@Service
public class DaysService {

	@Autowired
	DaysRepository daysRepo;
	
	public void addNewDay(Days d) {
		daysRepo.save(d);
	}

	public Days findById(int id) {
		return daysRepo.findById(id);
	}
	
	public List<Days> findAll(){
		return daysRepo.findAll();
	}
	
}
