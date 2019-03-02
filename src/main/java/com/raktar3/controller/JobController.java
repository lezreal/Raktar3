package com.raktar3.controller;


import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
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
			model.addAttribute("product_list", productService.findAll());
		} else {
			model.addAttribute("product_list", productService.findAll());
			model.addAttribute("vanmar",""); 
		}
		return "newProduct";
	}
	
	
	
	@RequestMapping("/newEmpToDb")
	public String newEmploye(@ModelAttribute("newemp") Employe emp, Model model) {
		if (employeService.addUserToDb(emp)==true) model.addAttribute("empregok", ""); else model.addAttribute("vanmar", ""); 
		model.addAttribute("emplist", productService.findAllEmploye());
		return "newEmp";
	}
	
	@RequestMapping("/newCompany")
	public String newCompany(@ModelAttribute("company") Company company, Model model) {
		productService.addCompany(company);
		model.addAttribute("futar", employeService.findAllHumanEmploye());
		model.addAttribute("company", new Company());
		model.addAttribute("clientregok","");
		return "newCompany";
		
	}
	
	@RequestMapping("/beerkezesToDb")
	public String beerkToDb(@ModelAttribute("stock") Stock stock) {
		stock.setIncoming(0);
		stockService.addIncoming(stock);
		
		return "index";
	}
	
	
	@RequestMapping("/keszletrolLevetel")
	public String keszletrolLevetel(@ModelAttribute("stock") Stock stock, @RequestParam("amount") int mennyiseg) {
		
		
		
		
		
		stock.setIncoming(1 );
		
		stockService.saleStock(stock);

		return "index";
	}

	@RequestMapping("/keszletlista")
	public String keszletlista(Model model) {
		List<Integer> id_lista =stockService.findProducts();
		List<Product> lista = new ArrayList();
		for (int i=0;i<id_lista.size();i++) {
			Product p =productService.findById(id_lista.get(i));
			
			p.setAmount(stockService.getAmount(id_lista.get(i))-stockService.getAmountSale(id_lista.get(i)));
			lista.add(p);
		}
		model.addAttribute("productlist", lista);
		
		return "productList";
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
	public String selejtToDb(@ModelAttribute("stock") Stock stock, Model model) {
		stock.setIncoming(2);
		stockService.addIncoming(stock);
		model.addAttribute("selejt", "");
		if (employeService.findAllEmploye().isEmpty()) {
			model.addAttribute("noemploye","");
			return "index";
		}
		
		if (productService.findAll().isEmpty()) {
			model.addAttribute("noproduct","");
			return "index";
		}
		
		if (!stockService.vaneKeszlet()) {
			model.addAttribute("nostock","");
			return "index";
		}
		
		model.addAttribute("stock", new Stock());
		model.addAttribute("emps", employeService.findAllEmploye());
		model.addAttribute("products", productService.findAll());
		return "selejt";
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


	@RequestMapping("/alapkeszletToDb")
	public String alapkeszlet(@RequestParam("pid") int pid, @RequestParam("darab") String darab, Model model, @RequestParam("bday") String datum, @RequestParam("comment") String comment) {
		if (darab==null || darab.isEmpty()) {
			model.addAttribute("fos","");
			model.addAttribute("products", productService.findAll());
			return "alapkeszlet";
		}
		log.info("ID: "+pid+" - DB: "+darab);
		stockService.deleteAllStock();   // összes STOCK bejegyzés törlése adott product_id-ra
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
	
	@RequestMapping("/probafeldolgoz")
	public String probafeldolgoz( ) {
		return "index";
	}
	
	@RequestMapping("/company/{id}")
	public String editCompany(@PathVariable("id") int id, Model model) {
		model.addAttribute("company", companyService.findById(id));
		List<Employe> emplist = employeService.findAllHumanEmploye();
		
		log.info("Index: "+emplist.indexOf(companyService.findById(id).getEmploye()));
		Collections.swap(emplist, 0, emplist.indexOf(companyService.findById(id).getEmploye()));
		
		model.addAttribute("employes", emplist);
		return "editCompany";
	}
	
	
	@RequestMapping("/product/{id}")
	public String editProduct(@PathVariable("id") int id, Model model) {
		model.addAttribute("product", productService.findById(id));
		List<Employe> emplist = employeService.findAllHumanEmploye();
		
		
		return "editProduct";
	}
	
	
	@RequestMapping("/editCompanyToDb")
	public String editCompany(@ModelAttribute("company") Company c,@RequestParam("empid") int empid, @RequestParam("deldays") String deldays, @RequestParam("compid") int compid, Model model) {
		Company oldcomp = companyService.findById(compid);
		oldcomp.setName(c.getName());
		oldcomp.setCity(c.getCity());
		oldcomp.setAddress(c.getAddress());
		oldcomp.setComment(c.getComment());
		oldcomp.setEmploye(employeService.findById(empid));
		if (deldays!=null && !deldays.isEmpty()) oldcomp.setDeliverydays(deldays);
		companyService.addNewCompany(oldcomp);
		model.addAttribute("compok", "");
		model.addAttribute("company", oldcomp);
		List<Employe> emplist = employeService.findAllHumanEmploye();
		Collections.swap(emplist, 0, emplist.indexOf(oldcomp.getEmploye()));
		model.addAttribute("editsuccess", "");
		model.addAttribute("employes", emplist);
		return "editCompany";
	}
	
	@RequestMapping("/editProductToDb")
	public String editProduct(@RequestParam("productid") int id,@RequestParam("productname") String name,@RequestParam("productcomment") String comment, Model model) {
		Product p = productService.findById(id);
		p.setName(name);
		p.setDescription(comment);
		productService.regNewProduct(p);
		model.addAttribute("product", p);
		model.addAttribute("editsuccess", "");
		return "editProduct";
	}
	
	@RequestMapping("/delstock")
	public String delstock(Model model, @RequestParam("gomb") int gomb) {
		log.info(""+gomb);
		stockService.deleteById(gomb);
		model.addAttribute("stocklist", stockService.stockList());
		return "stockList";
	}
	
	@RequestMapping("/employe/{id}")
	public String editEmploye(@PathVariable("id") int id, Model model) {
		
			model.addAttribute("employe",employeService.findById(id) );
			model.addAttribute("employeok","" );
			
		
		return "editEmploye";
	}
	
	@RequestMapping("editEmployeToDb")
	public String editemployetodb(@ModelAttribute("employe") Employe emp, Model model) {
		log.info(emp.getName());
		log.info(emp.getDescription());
		employeService.addUserToDb(emp);
		model.addAttribute("editsuccess", "");
		return "editEmploye";
	}
	
	@RequestMapping("delEmploye")
	public String delemploye(@RequestParam("delempid") int id, Model model) {
		
		if (companyService.findByEmploye(employeService.findById(id))) {
			employeService.delEmploye(employeService.findById(id));
			model.addAttribute("employes", employeService.findAllHumanEmploye());
			model.addAttribute("delsuccess", "");
			return "employeList";
		}
			else {
				model.addAttribute("employes", employeService.findAllHumanEmploye());
				model.addAttribute("delfail", "");
				return "employeList";
			}
	}
	
	@RequestMapping("/delproduct")
	public String delProduct(Model model, @RequestParam("gomb") int id) {
		if (productService.deleteProduct(productService.findById(id))) {
			model.addAttribute("delok","");
		} else model.addAttribute("delfail", "");
		model.addAttribute("productlist", productService.findAll());
		return "productListSimple";
	}
}


