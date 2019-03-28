package com.raktar3.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.raktar3.entities.Company;
import com.raktar3.entities.MachHistory;
import com.raktar3.entities.Machine;

public interface MachHistoryRepository extends CrudRepository<MachHistory, Integer> {

	List<MachHistory> findAll();
	
//	@Query(value="select * from mach_history where machine_id=?1",nativeQuery=true)
	List<MachHistory> findAllByMachine(Machine m);

	List<MachHistory> findAllByCompany(Company c);

	@Transactional
	@Modifying
	@Query(value="delete from mach_history where id=?1", nativeQuery=true)
	void deleteSelected(int hid);


}
