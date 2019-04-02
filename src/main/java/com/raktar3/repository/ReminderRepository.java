package com.raktar3.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.raktar3.entities.Reminder;

public interface ReminderRepository extends CrudRepository<Reminder, Integer> {

	@Query(value="select * from reminder where status = true",nativeQuery=true)
	List<Reminder> findAllActive();
	
	@Query(value="select * from reminder where status=true order by date",nativeQuery=true)
	List<Reminder> findAllByStatusAsc();

	@Transactional
	@Modifying
	@Query(value="update reminder set status=false where id=?1", nativeQuery=true)
	void deactival(int rid);
	
	List<Reminder> findAll();
	
}
