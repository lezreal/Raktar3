package com.raktar3.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raktar3.entities.Reminder;
import com.raktar3.repository.ReminderRepository;

@Service
public class ReminderService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	
	@Autowired
	ReminderRepository reminderRepo;
	
	public List<Reminder> findVeryAll(){
		return reminderRepo.findAll();
	}
	
	public List<Reminder> findAll(){
		return reminderRepo.findAllByStatusAsc();
	}
	
	public void addReminder(Reminder r) {
		reminderRepo.save(r);
	}
	
	public List<Reminder> vizsgal() {
		List<Reminder> lista = reminderRepo.findAllActive();
		List<Reminder> listatemp = new ArrayList<Reminder>();	
		if (lista.size()>0) {
			
			for (int i=0;i<lista.size();i++) {
				if (kelle(lista.get(i).getDate())<=lista.get(i).getAlerttime()) {
					listatemp.add(lista.get(i));
				}
			}
			
		}
		
		
		return listatemp;
	}
	
	public int kelle(String datum) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String mai =dateFormat.format(date);  // mai dátum kinyerve  2019/02/22
		int ma=0, vizsgalando=0;
		
		ma= 365*Integer.parseInt(mai.substring(0, 4))+30*Integer.parseInt(mai.substring(5,7))+Integer.parseInt(mai.substring(8));
		vizsgalando= 365*Integer.parseInt(datum.substring(0, 4))+30*Integer.parseInt(datum.substring(5,7))+Integer.parseInt(datum.substring(8));
		
		
		
		return vizsgalando-ma;
	}

	public void deactival(int rid) {
		reminderRepo.deactival(rid);
		
	}

	public Reminder findById(int rid) {

		return reminderRepo.findById(rid);
	}
	
}
