package com.raktar3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raktar3.entities.Company;
import com.raktar3.entities.MachHistory;
import com.raktar3.entities.Machine;
import com.raktar3.repository.MachHistoryRepository;

@Service
public class MachHistoryService {

	@Autowired
	MachHistoryRepository machHistoryRepo;
	
	public void addNewHistory(MachHistory mh) {
		machHistoryRepo.save(mh);
	}

	public List<MachHistory> findMachine(Machine m) {
		return machHistoryRepo.findAllByMachine(m);
		
	}
	
	public List<MachHistory> findCompany(Company c) {
		return machHistoryRepo.findAllByCompany(c);
	}
	
	public List<MachHistory> findAll(){
		return machHistoryRepo.findAll();
	}

	public void deleteSelected(int hid) {
		machHistoryRepo.deleteSelected(hid);
		
	}
}
