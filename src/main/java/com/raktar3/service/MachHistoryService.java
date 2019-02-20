package com.raktar3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raktar3.entities.MachHistory;
import com.raktar3.repository.MachHistoryRepository;

@Service
public class MachHistoryService {

	@Autowired
	MachHistoryRepository machHistoryRepo;
	
	public void addNewHistory(MachHistory mh) {
		machHistoryRepo.save(mh);
	}
}
