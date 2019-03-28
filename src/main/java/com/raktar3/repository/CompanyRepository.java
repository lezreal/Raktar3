package com.raktar3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.raktar3.entities.Company;
import com.raktar3.entities.Employe;

public interface CompanyRepository extends CrudRepository<Company, Integer> {

	
	List<Company> findAll();
	
	
	@Query(value="select * from company where id>1", nativeQuery=true)
	List<Company> findAllReal();
	
	
	
	Company findById(int id);
	
	Company findFirstByEmploye(Employe e); 
	
	
//	@Query(value="select * from company c where c.deliverydays like %:minta% and c.employe_id=:id",nativeQuery=true)
	@Query(value="select * from company where days_id=?1",nativeQuery=true)
	List<Company> findDelDay(@Param("id") int id);
	
	
	
	
	@Query(value="update company set tablasorszam='?2' where id=?1",nativeQuery=true)
	public void addTableSorszam(Integer id, int sorszam);



}
