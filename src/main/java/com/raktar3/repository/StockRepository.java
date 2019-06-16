package com.raktar3.repository;

import java.util.List;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.raktar3.entities.Employe;
import com.raktar3.entities.Product;
import com.raktar3.entities.Stock;

public interface StockRepository extends CrudRepository<Stock, Integer> {
	
	
	List<Stock> findAll();
	
	@Query(value="SELECT * FROM stock ORDER BY id desc LIMIT 5",nativeQuery=true)
	List<Stock> findTop5();
	
	
	@Query(value="select sum(amount) from stock where incoming=0", nativeQuery=true)
	int findToSelejt();
	
	@Query(value = "select distinct product_id from stock", nativeQuery = true)
	List<Integer> findDistinctProduct();
	
	@Query(value="select sum(amount) from stock where product_id=?1 and incoming=0", nativeQuery=true)
	Integer getAmountByProduct(Integer id);    // BEJÖVŐ

	@Query(value="select sum(amount) from stock where product_id=?1 and (incoming=1 or incoming=2)", nativeQuery=true)
	Integer getAmountByProductSale(Integer id);  // KIMENŐ és a SELEJT

	@Query(value="select count(id) as pid from stock where product_id=?1", nativeQuery=true)
	Integer isProductinStock(int id);
	
	@Query(value="delete from stock where incoming>=0",nativeQuery=true)
	public void deleteAllByPid(int id);
	
	
	Stock findFirstByEmploye(Employe e);
	
	
	@Query(value="delete from stock where product_id=?1",nativeQuery=true)
	public void deleteByProduct(Integer id);
	
	List<Stock> findByProduct(Product p);
	
	@Query(value="select * from stock where date=?1 and employe_id=?2 and product_id=?3",nativeQuery=true)
	public List<Stock> findSelectedLekerdezes(String date, int empid, int pid);
	
	@Query(value="select * from stock where date=?1 and employe_id=?2",nativeQuery=true)
	public List<Stock> findAllLekerdezes(String date, int empid);
	
	public void delete(Stock s);

	@Query(value="select * from stock where date=?1 and product_id=?2",nativeQuery=true)
	List<Stock> findAllProductDate(String date, int pid);

	
	@Query(value="select distinct date from stock", nativeQuery=true)
	List<String> findDistinctDate();
	
	@Query(value="select * from stock where date like ?1%", nativeQuery=true)
	List<Stock> haviOsszes(String date);

	@Query(value="select * from stock where employe_id=?2 and date like ?1%", nativeQuery=true)
	List<Stock> haviOsszesEmploye(String date, int empid);
	
	@Query(value="select * from stock where product_id=?2 and date like ?1%", nativeQuery=true)
	List<Stock> haviOsszesProduct(String date, int pid);

	@Query(value="select * from stock where product_id=?2 and employe_id=?3 and date like ?1%", nativeQuery=true)
	List<Stock> haviOsszesProductEmploye(String date, int pid, int empid);
	
	
	
	
}
