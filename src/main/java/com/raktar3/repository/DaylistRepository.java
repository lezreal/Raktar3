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
	
	
	Daylist findFirstBySorszam(int sorszam);
	
	@Query(value="select max(sorszam) as maxi from daylist", nativeQuery=true)
	int findMaxSorszam();
	
	
	@Transactional
	@Modifying
	@Query(value="update daylist set sorszam=sorszam+1 where sorszam>=?1 and sorszam<?2", nativeQuery=true)
	public void sorszamCsokkentAholKisebb(int mettol, int meddig);
	
	
	@Transactional
	@Modifying
	@Query(value="update daylist set sorszam=sorszam-1 where sorszam<=?1 and sorszam>?2", nativeQuery=true)
	void sorszamCsokkentAholNagyobb(int mettol, int meddig);
	
	
	public void delete(Daylist d);

	
	
	
}
