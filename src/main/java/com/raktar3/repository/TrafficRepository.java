package com.raktar3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.raktar3.entities.Traffic;

public interface TrafficRepository extends CrudRepository<Traffic,Integer> {

	
	@Query(value="select * from traffic where date=?1 order by employe_id",nativeQuery=true)
	List<Traffic> findAllByDate(String date);
	
	@Query(value="select * from traffic where date=?1 and product_id=?2",nativeQuery=true)
	List<Traffic> findAllProductDate(String date, int pid);
	
	@Query(value="select * from traffic where date=?1 and employe_id=?2",nativeQuery=true)
	public List<Traffic> findAllLekerdezes(String date, int empid);
	
	@Query(value="select * from traffic where date=?1 and employe_id=?2 and product_id=?3",nativeQuery=true)
	public List<Traffic> findSelectedLekerdezes(String date, int empid, int pid);
	
	@Query(value="select * from traffic where employe_id=?2 and date like ?1%", nativeQuery=true)
	List<Traffic> haviOsszesEmploye(String date, int empid);
	
	@Query(value="select * from traffic where product_id=?2 and date like ?1%", nativeQuery=true)
	List<Traffic> haviOsszesProduct(String date, int pid);

	@Query(value="select * from traffic where product_id=?2 and employe_id=?3 and date like ?1%", nativeQuery=true)
	List<Traffic> haviOsszesProductEmploye(String date, int pid, int empid);
	
	@Query(value="select * from Traffic where date like ?1%", nativeQuery=true)
	List<Traffic> haviOsszes(String date);
}
