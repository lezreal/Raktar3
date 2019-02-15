package com.raktar3.controller;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.raktar3.entities.Company;
import com.raktar3.entities.Employe;
import com.raktar3.entities.Product;
import com.raktar3.entities.Stock;
import com.raktar3.repository.EmployeRepository;
import com.raktar3.service.EmployeService;
import com.raktar3.service.ProductService;
import com.raktar3.service.StockService;

@Controller
public class JobController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	FileOutputStream fos=null;
	
	
	
	@Autowired
	ProductService productService;
	
	@Autowired
	StockService stockService;
	
	@Autowired
	EmployeService employeService;
	
	@RequestMapping("/regProductToDb")
	public String regProductToDb(@ModelAttribute("product") Product p, Model model) {
		if (productService.findProductByName(p.getName())) { 
			model.addAttribute("regok","");
			productService.regNewProduct(p);
		} else model.addAttribute("vanmar",""); 
		return "newProduct";
	}
	
	@RequestMapping("/delproduct")
	public String Product(@RequestParam("boxok") ArrayList<Product> vmi, Model model) {
		for (Product x:vmi) {
			
			try {
				productService.deleteProduct(x);
			} catch (Exception e) {
				
				//e.printStackTrace();
				model.addAttribute("nemtorolheto","");
				model.addAttribute("keszletrol","");
				model.addAttribute("productlist", productService.findAll());
				return "productList";
			}
		}
		model.addAttribute("productlist", productService.findAll());
		return "productList";
	}
	
	@RequestMapping("/newEmpToDb")
	public String newEmploye(@ModelAttribute("newemp") Employe emp, Model model) {
		if (employeService.addUserToDb(emp)==true) model.addAttribute("empregok", ""); else model.addAttribute("vanmar", ""); 
		model.addAttribute("emplist", productService.findAllEmploye());
		return "newEmp";
	}
	
	@RequestMapping("/newCompany")
	public String newCompany(@ModelAttribute("company") Company company) {
		productService.addCompany(company);
		return "index";
	}
	
	@RequestMapping("/beerkezesToDb")
	public String beerkToDb(@ModelAttribute("stock") Stock stock) {
		stock.setIncoming(true);
		stockService.addIncoming(stock);
		
		return "index";
	}
	
	
	@RequestMapping("/keszletrolLevetel")
	public String keszletrolLevetel(@ModelAttribute("stock") Stock stock, @RequestParam("amount") int mennyiseg,@RequestParam("ures") int ures,@RequestParam("amountures") int uresamount,@RequestParam("ureskomment") String ureskomment) {
		
		if (stock.isIncoming()) {
			log.info("BEJÖTT");
			Product p = productService.findById(ures).get();
			Stock ns = new Stock();
			ns.setProduct(p);
			ns.setIncoming(true);
			ns.setDate(stock.getDate());
			ns.setEmploye(stock.getEmploye());
			ns.setAmount(uresamount);
			ns.setComment(ureskomment);
			stockService.addIncoming(ns);
		}
		
		
		stock.setIncoming(false);
		stock.setAmount(mennyiseg);
		stockService.saleStock(stock);

		return "index";
	}

	@RequestMapping("/keszletlista")
	public String keszletlista(Model model) {
		List<Integer> id_lista =stockService.findProducts();
		List<Product> lista = new ArrayList();
		for (int i=0;i<id_lista.size();i++) {
			Product p =productService.findById(id_lista.get(i)).get();
			int osszes=stockService.getAmount(id_lista.get(i))-stockService.getAmountSale(id_lista.get(i));
			p.setAmount(osszes);
			lista.add(p);
		}
		model.addAttribute("productlist", lista);
		
		return "productList";
	}
	
	@RequestMapping("/keszletmozgaslista")
	public String keszletmozgaslista(Model model) {
		model.addAttribute("stocklist", stockService.stockList());
		
		return "stockList";
	}
	
	@RequestMapping("/kamionToDb")
	public String kamiontodb(@ModelAttribute("stock") Stock s,@RequestParam("hozott") int hozott,@RequestParam("employe") int emp) {
		
		
		s.setEmploye( employeService.findById(emp).get());
		
		if (hozott==1) {
			s.setIncoming(true);
			stockService.addIncoming(s); 
		} else {
			s.setIncoming(false);
			stockService.saleStock(s);
		}
		
		
		return "index";
	}
	
	
}
