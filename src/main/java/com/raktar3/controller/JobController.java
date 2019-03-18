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
import com.raktar3.entities.Daylist;
import com.raktar3.entities.Days;
import com.raktar3.entities.Employe;
import com.raktar3.entities.MachHistory;
import com.raktar3.entities.Machine;
import com.raktar3.entities.Product;
import com.raktar3.entities.Stock;
import com.raktar3.service.CompanyService;
import com.raktar3.service.DaylistService;
import com.raktar3.service.DaysService;
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
	DaylistService daylistService;
	
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
	
	@Autowired
	DaysService daysService;
	
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
	
	@RequestMapping("/newCompanyToDb")
	public String newCompany(@ModelAttribute("company") Company company, Model model, @RequestParam("days") String days) {
		Days d = daysService.findById(Integer.parseInt(days));
		company.setDays(d);
		productService.addCompany(company);
		
		
		
		List<Employe> emplist = employeService.findAllHumanEmploye();
		List<Days> daylist = daysService.findAll();
		
 		Collections.swap(emplist, 0, emplist.indexOf(company.getEmploye()));
		Collections.swap(daylist, 0, daylist.indexOf(companyService.findById(Integer.parseInt(days)).getDays()));
		
		model.addAttribute("futar", emplist);
		model.addAttribute("company", new Company());
		model.addAttribute("clientregok","");
		model.addAttribute("daylist", daylist);
		return "newCompany";
		
	}
	
	@RequestMapping("/beerkezesToDb")
	public String beerkToDb(@ModelAttribute("stock") Stock stock, Model model) {
		stock.setIncoming(0);
		stockService.addIncoming(stock);
		
		
		model.addAttribute("utolsoemploye", stock.getEmploye());   // utoljára rögzített EMP
		model.addAttribute("products", productService.findAll());
		model.addAttribute("emps", employeService.findAllHumanEmploye());
		model.addAttribute("stock", new Stock());
		return "beerkezes";
	}
	
	
	@RequestMapping("/keszletrolLevetel")
	public String keszletrolLevetel(@ModelAttribute("stock") Stock stock, @RequestParam("amount") int mennyiseg, Model model) {
		
		
		stock.setIncoming(1);
		
		stockService.saleStock(stock);
		model.addAttribute("utolsoemploye", stock.getEmploye());   // utoljára rögzített EMP
		
		model.addAttribute("stock", new Stock());
		model.addAttribute("emps", employeService.findAllHumanEmploye());
		model.addAttribute("products", productService.findAll());
		model.addAttribute("eladasok", "");
		return "eladas";
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
	public String newMachine(@ModelAttribute("newmach") Machine mach, Model model, @RequestParam("sorszam") int sorszam) {
		
		if (machineService.findSorszam(sorszam)){
			mach.setSorszam(sorszam);
			mach.setCompany(companyService.findById(1));
			model.addAttribute("machregok", "");
			model.addAttribute("machregid", machineService.addMachineToDb(mach));	
		} else {
			model.addAttribute("machregfail", "");
		}
		model.addAttribute("newmach", new Machine());
		model.addAttribute("ujsorszam", machineService.getNewSorszam());
		return "newmachine";
	}
	
	@RequestMapping("/machine/{search}")
	public String machine(@PathVariable("search") Integer id, Model model) {
		model.addAttribute("selected", machineService.findById(id));
		model.addAttribute("comps", companyService.findAll());
		return "selectedMachine";
	}
	
	@RequestMapping("/machHistoryToDb")
	public String historyToDb(Model model,@ModelAttribute("selected") Machine m, @RequestParam("compid") String compid, @RequestParam("ujsorszam") String ujsorszam, @RequestParam("regisorszam") String regisorszam) {
		
		if (Integer.parseInt(ujsorszam)>0) {
		
		if (Integer.parseInt(ujsorszam)==Integer.parseInt(regisorszam)) {
			model.addAttribute("siker","");
			m.setCompany(companyService.findById(Integer.parseInt(compid)));
			m.setSorszam(Integer.parseInt(regisorszam));
			machineService.addMachineToDb(m);
		} else if (machineService.findSorszam(Integer.parseInt(ujsorszam)))
				{
			model.addAttribute("siker","");
			m.setCompany(companyService.findById(Integer.parseInt(compid)));
			m.setSorszam(Integer.parseInt(ujsorszam));
			machineService.addMachineToDb(m);
			
		} else {
			
			model.addAttribute("foglalt",""); 
		}
		} else {
			model.addAttribute("foglalt","");
		}
		
		
		
				//machineService.addMachineToDb(m);
//			 machineService.editMachineSave(m);
			model.addAttribute("selected", m);
			
			model.addAttribute("comps", companyService.findAll());
			
//			model.addAttribute("foglalt","");
//		
		return "selectedMachine";
	}


	@RequestMapping("/alapkeszletToDb")
	public String alapkeszlet(@RequestParam("pid") int pid, @RequestParam("darab") String darab, Model model, @RequestParam("bday") String datum, @RequestParam("comment") String comment) {
		if (darab==null || darab.isEmpty()) {
			model.addAttribute("fos","");
			model.addAttribute("products", productService.findAll());
			return "alapkeszlet";
		}
		log.info("ID: "+pid+" - DB: "+darab);
		
		List<Stock> lista = stockService.findByProduct(productService.findById(pid));
		for (Stock s : lista) {
			stockService.deleteStock(s);
			
		}
		
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
		List<Days> daylist = daysService.findAll();
		
		
		Collections.swap(emplist, 0, emplist.indexOf(companyService.findById(id).getEmploye()));
		Collections.swap(daylist, 0, daylist.indexOf(companyService.findById(id).getDays()));
		
		model.addAttribute("employes", emplist);
		model.addAttribute("days", daylist);
		return "editCompany";
	}
	
	
	@RequestMapping("/product/{id}")
	public String editProduct(@PathVariable("id") int id, Model model) {
		model.addAttribute("product", productService.findById(id));
		List<Employe> emplist = employeService.findAllHumanEmploye();
		
		
		return "editProduct";
	}
	
	
	@RequestMapping("/editCompanyToDb")
	public String editCompany(@ModelAttribute("company") Company c,@RequestParam("empid") int empid,@RequestParam("dayid") int daysid, Model model) {
		Days days = daysService.findById(daysid);

		c.setEmploye(employeService.findById(empid));
		c.setDays(daysService.findById(daysid));
		companyService.addNewCompany(c);
		
		
		model.addAttribute("compok", "");
		model.addAttribute("company", c);
		List<Employe> emplist = employeService.findAllHumanEmploye();
		List<Days> daylist = daysService.findAll();
		Collections.swap(emplist, 0, emplist.indexOf(c.getEmploye()));
		Collections.swap(daylist, 0, daylist.indexOf(companyService.findById(daysid).getDays()));
		model.addAttribute("editsuccess", "");
		model.addAttribute("days", daylist);
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
		
		if (companyService.findByEmploye(employeService.findById(id)) && stockService.findEmploye(employeService.findById(id))) {
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
	
	@RequestMapping("/lekerdezes2")
	public String lekerdez2(@RequestParam("bday") String date,@RequestParam("empid") int empid,@RequestParam("pid") int pid, Model model) {
		
		 model.addAttribute("lekerdezes", stockService.lekerdezes(date, empid, pid));
		
		return "lekerdezes2";
	}

	
}


