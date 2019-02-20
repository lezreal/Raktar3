package com.raktar3.controller;


import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.raktar3.entities.Company;
import com.raktar3.entities.Employe;
import com.raktar3.entities.MachHistory;
import com.raktar3.entities.Machine;
import com.raktar3.entities.Product;
import com.raktar3.entities.Stock;
import com.raktar3.service.CompanyService;
import com.raktar3.service.EmployeService;
import com.raktar3.service.MachHistoryService;
import com.raktar3.service.MachineService;
import com.raktar3.service.ProductService;
import com.raktar3.service.StockService;

@Controller
public class JobController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	FileOutputStream fos=null;

	@Autowired
	MachHistoryService machHistoryService;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	MachineService machineService;
	
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
			
			
			if (!productService.deleteProduct(x))
			{
				
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
		stock.setIncoming(1);
		stockService.addIncoming(stock);
		
		return "index";
	}
	
	
	@RequestMapping("/keszletrolLevetel")
	public String keszletrolLevetel(@ModelAttribute("stock") Stock stock, @RequestParam("amount") int mennyiseg,@RequestParam("ures") int ures,@RequestParam("amountures") int uresamount,@RequestParam("ureskomment") String ureskomment, @RequestParam("uresbox") int uresbox) {
		
		if (uresbox==1) {
			log.info("BEJÖTT");
			Product p = productService.findById(ures);
			Stock ns = new Stock();
			ns.setProduct(p);
			ns.setIncoming(1);
			ns.setDate(stock.getDate());
			ns.setEmploye(stock.getEmploye());
			ns.setAmount(uresamount);
			ns.setComment(ureskomment);
			stockService.addIncoming(ns);
		}
		
		
		stock.setIncoming(0);
		stock.setAmount(mennyiseg);
		stockService.saleStock(stock);

		return "index";
	}

	@RequestMapping("/keszletlista")
	public String keszletlista(Model model) {
		List<Integer> id_lista =stockService.findProducts();
		List<Product> lista = new ArrayList();
		for (int i=0;i<id_lista.size();i++) {
			Product p =productService.findById(id_lista.get(i));
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
		
		
		s.setEmploye( employeService.findById(emp));
		
		if (hozott==1) {
			s.setIncoming(1);
			stockService.addIncoming(s); 
		} else if (hozott==0) {
			s.setIncoming(0);
			stockService.saleStock(s);
		} else {
			s.setIncoming(2);
			stockService.saleStock(s);	
		}
		
		
		return "index";
	}
	
	@RequestMapping("/selejtToDb")
	public String selejtToDb(@ModelAttribute("stock") Stock stock) {
		stock.setIncoming(2);
		stockService.addIncoming(stock);
		
		return "index";
	}
	
	@RequestMapping("/newMachToDb")
	public String newMachine(@ModelAttribute("newmach") Machine mach, Model model) {
		
		model.addAttribute("machregok", "");
		model.addAttribute("machregid", machineService.addMachineToDb(mach));  
		
		return "newmachine";
	}
	
	@RequestMapping("/machine/{search}")
	public String machine(@PathVariable("search") String id, Model model) {
		log.info(machineService.findById(Integer.parseInt(id)).getName());
		model.addAttribute("selected", machineService.findById(Integer.parseInt(id)));
		model.addAttribute("comps", companyService.findAll());
		return "selectedMachine";
	}
	
	@RequestMapping("/machHistoryToDb")
	public String historyToDb(@RequestParam("ujcim") int kelleujcim, @RequestParam("gepid") int gepid,   @RequestParam("compid") int compid,  @RequestParam("bday") String date,  @RequestParam("regicomment") String regicomment,  @RequestParam("reginev") String reginev,  @RequestParam("historycomment") String historycomment) {
		log.info("Kell új cim: "+kelleujcim);
		log.info("Gép ID: "+gepid);
		log.info("Új cég ID: "+compid);
		log.info("Dátum: "+date);
		MachHistory mh = new MachHistory();
		Machine m = machineService.findById(gepid);
		if (kelleujcim==0) {
			
			m.setComment(regicomment);
			m.setName(reginev);
			machineService.addMachineToDb(m);
		} else {
			mh.setMachine(m);
			mh.setComment(historycomment);
			mh.setDate(date);
			mh.setCompany(companyService.findById(compid));
			m.setComment(regicomment);
			m.setName(reginev);
			machineService.addMachineToDb(m);
			m.setCompany(companyService.findById(compid));
			machineService.addMachineToDb(m);
			machHistoryService.addNewHistory(mh);
			
		}
		
		return "index";
	}

// ITT JŰÁROK
	@RequestMapping("/alapkeszletToDb")
	public String alapkeszlet(@RequestParam("pid") int pid, @RequestParam("darab") String darab, Model model, @RequestParam("bday") String datum, @RequestParam("comment") String comment) {
		if (darab==null || darab.isEmpty()) {
			model.addAttribute("fos","");
			model.addAttribute("products", productService.findAll());
			return "alapkeszlet";
		}
		log.info("ID: "+pid+" - DB: "+darab);
		stockService.deleteById(pid);
		Stock stock = new Stock();
		stock.setProduct(productService.findById(pid));
		stock.setEmploye(employeService.findById(1));
		stock.setIncoming(0);
		stock.setAmount(Integer.parseInt(darab));
		stock.setDate(datum);
		stock.setComment(comment);
		stockService.addIncoming(stock);
		
		model.addAttribute("success","");
		model.addAttribute("products", productService.findAll());
		return "alapkeszlet";
	}
	
}


