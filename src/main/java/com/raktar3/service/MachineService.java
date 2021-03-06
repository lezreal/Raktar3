package com.raktar3.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raktar3.entities.Machine;
import com.raktar3.repository.CompanyRepository;
import com.raktar3.repository.MachineRepository;

@Service
public class MachineService {

	@Autowired
	CompanyRepository companyRepo;
	
	@Autowired
	MachineRepository machineRepo;
	
	
	
	public int addMachineToDb(Machine m) {
		
	
		
		machineRepo.save(m);
		return m.getSorszam();  // visszatér az utoljára rögzített gép SORSZÁMÁVAL
	}
	
	public void editMachineSave(Machine m) {
		machineRepo.save(m);
	}
	
	public List<Machine> findAll(){
		return machineRepo.findAll();  // össze machine kilistázása
	}
	
	public Machine findById(int id) {
		return machineRepo.findById(id);
	}
	
	public boolean findSorszam(Integer sorszam) {
		if (machineRepo.findSorszam(sorszam)==null) return true; else return false;
	}

	public int getNewSorszam() {
		if (machineRepo.getNewSorszam()==null) return 1; else 
		return machineRepo.getNewSorszam()+1;
	}
	
	public List<Machine> findCompanyMachine(int id){
		return machineRepo.findCompanyId(id);
	}
	
	public int vanegepe(int id) {
		if (machineRepo.vanegepe(id)>0) return 1; else return 0;
	}

	public Machine findBySorszam(int sorszam) {
		
		return machineRepo.findBySorszam(sorszam);
	}
	
//	@PostConstruct
//	public void init() {
//		byte vmi=1;
//		Machine m1 = new Machine();
//		m1.setName("HC-66L");
//		m1.setComment("repedt az oldala");
//		m1.setCompany(companyRepo.findById(1));
//		m1.setType(vmi);
//		machineRepo.save(m1);
//		
//		Machine m2 = new Machine();
//		m2.setName("Fairline");
//		m2.setComment("régi fos");
//		m2.setType(vmi);
//		machineRepo.save(m2);
//		vmi=2;
//		
//		Machine m3 = new Machine();
//		m3.setName("WaterPia");
//		m3.setComment("szódás");
//		m3.setType(vmi);
//		m3.setCompany(companyRepo.findById(2));
//		machineRepo.save(m3);
	//}
	
}
