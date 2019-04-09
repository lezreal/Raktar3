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

	@Transactional
	@Modifying
	@Query(value="update daycompany set sorszam=sorszam+1 where sorszam>?2 and name=?1",nativeQuery=true)
	public void sorszamNovel(String name, int sorszam);
	
	@Query(value="select count(id) from daycompany where name=?1",nativeQuery=true)
	int vanemarnilyennev(String name);

		
}
