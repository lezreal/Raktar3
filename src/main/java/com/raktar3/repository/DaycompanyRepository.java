package com.raktar3.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.raktar3.entities.Daycompany;

public interface DaycompanyRepository extends CrudRepository<Daycompany, Integer> {

	
	
	@Query(value="select distinct(name) from daycompany",nativeQuery=true)
	List<String> findDistinctName();
	
	@Query(value="select * from daycompany where name=?1",nativeQuery=true)
	List<Daycompany> findSelectedName(String name);
	
	
	@Transactional
	@Modifying
	@Query(value="delete from daycompany where name=?1",nativeQuery=true)
	public void deleteSelectedName(String name);
	
}
