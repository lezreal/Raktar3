package com.raktar3.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
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
	
	 
	
	
//	@Query(value="select * from company c where c.deliverydays like %:minta% and c.employe_id=:id",nativeQuery=true)
	@Query(value="select * from company where days_id=?1",nativeQuery=true)
	List<Company> findDelDay(@Param("id") int id);
	
	
	
	
	@Query(value="update company set tablasorszam='?2' where id=?1",nativeQuery=true)
	public void addTableSorszam(Integer id, int sorszam);

	@Transactional
	@Modifying
	@Query(value="update company set address=?2 where id=?1",nativeQuery=true)
	void updateAddress(int compid, String address);

	@Transactional
	@Modifying
	@Query(value="update company set comment=?2 where id=?1",nativeQuery=true)
	void updateComment(int compid, String comment);

	@Transactional
	@Modifying
	@Query(value="update company set name=?2 where id=?1",nativeQuery=true)
	void updateName(int compid, String name);

	@Transactional
	@Modifying
	@Query(value="update company set tartozik=?2 where id=?1",nativeQuery=true)
	void updateTartozas(int compid, String tartozas);

	@Query(value="select * from company where tartozik!=null or tartozik!=''",nativeQuery=true)
	List<Company> tartozok();

	
	List<Company> findByNameContainingIgnoreCase(String s);
	List<Company> findByAddressContainingIgnoreCase(String s);


}
