package com.raktar3.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.raktar3.entities.Daylist;

public interface DaylistRepository extends CrudRepository<Daylist, Integer> {

	
	@Query(value="select * from daylist order by sorszam", nativeQuery=true)
	List<Daylist> findAllBySorszam();
	
	Daylist findById(int id);
	
	@Transactional
	@Modifying
	@Query(value="update daylist set sorszam=sorszam+1 where sorszam>?1", nativeQuery=true)
	public void sorszamNovel(int sorszam);
	
	
	@Transactional
	@Modifying
	@Query(value="update daylist set sorszam=sorszam-1 where sorszam>?1", nativeQuery=true)
	public void sorszamCsokkent(int sorszam);
	
	
	public void delete(Daylist d);
	
	
	Daylist findFirstBySorszam(int sorszam);
}
