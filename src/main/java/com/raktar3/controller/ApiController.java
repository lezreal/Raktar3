package com.raktar3.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raktar3.entities.Product;
import com.raktar3.entities.Stock;
import com.raktar3.service.ProductService;
import com.raktar3.service.StockService;

@RestController
public class ApiController {

	@Autowired
	StockService stockService;
	
	@Autowired
	ProductService productService;
	
	final Logger log = LoggerFactory.getLogger(this.getClass());
	
//	@RequestMapping("/tesztkeszlet")
//	public List<Product> keszletTermek() {
//		List<Integer> id_lista =stockService.findProducts();
//		log.info("ID_LISTA mérete: "+id_lista.size());
//		List<Product> lista = new ArrayList();
//		for (int i=0;i<id_lista.size();i++) {
//			Product p =productService.findById(id_lista.get(i)).get();
//			int osszes=stockService.getAmount(id_lista.get(i))-stockService.getAmountSale(id_lista.get(i));
//			p.setAmount(osszes);
//			lista.add(p);
//		}
//		
//		return null;
//	}
	
	
}	
		
//			log.info(""+id_lista.get(i));
//			log.info("Termék: "+p.getName());
//			log.info("Összesen: "+osszes);
//			
			
		
		
			//Long vmi = (long) 1;
//				Optional<Product> p =productService.findById(vmi);
//				log.info(p.get().getName());
				
	

