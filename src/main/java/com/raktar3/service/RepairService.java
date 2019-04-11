package com.raktar3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raktar3.entities.Repair;
import com.raktar3.repository.RepairRepository;

@Service
public class RepairService {

	
	@Autowired
	RepairRepository repairRepo;
	
	public void addNewService(Repair r) {
		repairRepo.save(r);
	}
	
	public List<Repair> findAll(){
		return repairRepo.findAll();
	}
	
}
