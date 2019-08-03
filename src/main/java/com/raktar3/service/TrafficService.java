package com.raktar3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raktar3.entities.Stock;
import com.raktar3.entities.Traffic;
import com.raktar3.repository.TrafficRepository;

@Service
public class TrafficService {

	@Autowired
	TrafficRepository trafficRepo;
	
	public void addIncomingTraffic(Stock s) {
		trafficRepo.save(new Traffic(s));
	}
	
	
	public List<Traffic> lekerdezes(String date, int empid, int pid){
		
		if (empid==0 && pid==0) {  // ha csak a dátum van megadva
			return trafficRepo.findAllByDate(date);
		}
		
		if (empid==0) {
			
			return trafficRepo.findAllProductDate(date,pid);
		} else 
		if (pid==0) {
			return trafficRepo.findAllLekerdezes(date, empid);
		} else return trafficRepo.findSelectedLekerdezes(date, empid, pid);
	}
	
	public List<Traffic> havilekerdezes(String date, int empid, int pid){
		String datum=date.substring(0, 7);
		if (pid==0 && empid!=0) return trafficRepo.haviOsszesEmploye(datum, empid); else 
		if (pid!=0 && empid==0) return trafficRepo.haviOsszesProduct(datum, pid); else
		if (pid!=0 && empid!=0) return trafficRepo.haviOsszesProductEmploye(datum, pid, empid); else
		return trafficRepo.haviOsszes(datum);
	}
	
	
}
