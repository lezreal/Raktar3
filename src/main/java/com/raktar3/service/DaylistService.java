package com.raktar3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raktar3.entities.Daylist;
import com.raktar3.repository.DaylistRepository;

@Service
public class DaylistService {

	
	@Autowired
	DaylistRepository daylistRepo;
	
	
	public void addToDb(Daylist d) {
		daylistRepo.save(d);
	}
	
	public void deleteAll() {
		daylistRepo.deleteAll();
	}
	
	
	public List<Daylist> findAll(){
		return daylistRepo.findAllBySorszam();
	}
	
	public void deleteSelected(Daylist d) {
		daylistRepo.delete(d);
	}
	
	
	public Daylist findById(int id) {
		return daylistRepo.findById(id);
	}
	
	public void sorszamNovel(int sorszam) {
		daylistRepo.sorszamNovel(sorszam);
	}
	
	public void sorszamCsokkent(int sorszam) {
		daylistRepo.sorszamCsokkent(sorszam);
	}

	public Daylist findFirstBySorszam(int sorszam) {
		return daylistRepo.findFirstBySorszam(sorszam);
		
	}
}
