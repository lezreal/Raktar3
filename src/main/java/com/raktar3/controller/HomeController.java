package com.raktar3.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.raktar3.entities.Company;
import com.raktar3.entities.Employe;
import com.raktar3.entities.Machine;
import com.raktar3.entities.Product;
import com.raktar3.entities.Stock;
import com.raktar3.service.EmployeService;
import com.raktar3.service.MachineService;
import com.raktar3.service.ProductService;
import com.raktar3.service.StockService;



@Controller
public class HomeController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MachineService machineService;
	
	@Autowired
	EmployeService employeService;
	
	@Autowired
	StockService stockService;
	
	@Autowired
	ProductService productService;
	
	@RequestMapping("/")
	public String home(Model model) {
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
	
	@RequestMapping("/newproduct")
	public String newProduct(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("product_list", productService.findAll());
		return "newProduct";
	}
	
	@RequestMapping("/productlistsimple")
	public String productListsimple(Model model) {
		
		model.addAttribute("productlist", productService.findAll());
		
		return "productListSimple";
	}
	
	
	@RequestMapping("/productlist")
	public String productList(Model model) {
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
	
	@RequestMapping("/newemp")
	public String newEmp(Model model) {
		model.addAttribute("newemp", new Employe());
		model.addAttribute("emplist", employeService.findAllHumanEmploye());
		return "newEmp";
	}
	
	@RequestMapping("/beerkezes")
	public String beerkezes(Model model) {
		if (employeService.findAllHumanEmploye().isEmpty()) {
			model.addAttribute("noemploye","");
			return "index";
		}
		
		if (productService.findAll().isEmpty()) {
			model.addAttribute("noproduct","");
			return "index";
		}
		model.addAttribute("products", productService.findAll());
		model.addAttribute("emps", employeService.findAllHumanEmploye());
		
		model.addAttribute("stock", new Stock());
		return "beerkezes";
	}
	
	@RequestMapping("/eladas")
	public String eladas(Model model) {
		if (employeService.findAllHumanEmploye().isEmpty()) {
			model.addAttribute("noemploye","");
			return "index";
		}
		
		if (productService.findAll().isEmpty()) {
			model.addAttribute("noproduct","");
			return "index";
		}
		
		model.addAttribute("stock", new Stock());
		model.addAttribute("emps", employeService.findAllHumanEmploye());
		model.addAttribute("products", productService.findAll());
		if (productService.findUres("ÜRES")!=null) model.addAttribute("uresek", productService.findUres("ÜRES"));
		model.addAttribute("uresvane", new Boolean(false));
		return "eladas";
	}
	
	@RequestMapping("/newcompany")
	public String newcompany(Model model) {
		model.addAttribute("futar", employeService.findAllHumanEmploye());
		model.addAttribute("company", new Company());
		return "newCompany";
	}
	
	@RequestMapping("/companylist")
	public String companylist(Model model) {
		model.addAttribute("companies", productService.findAllCompany());
		return "companyList";
	}
	
	@RequestMapping("/employelist")
	public String employelist(Model model) {
		model.addAttribute("employes", employeService.findAllHumanEmploye());
		return "employeList";
	}
	
	@RequestMapping("/keszletmozgaslista")
	public String keszletmozgaslista(Model model) {
		model.addAttribute("stocklist", stockService.stockList());
		return "stockList";
	}
	
	@RequestMapping("/kamion")
	public String kamion(Model model) {
		if (productService.findAll().isEmpty()) {
			model.addAttribute("noproduct","");
			return "index";
		}
		model.addAttribute("stock",new Stock());
		model.addAttribute("products", productService.findAll());
		return "kamion";
	}
	
	@RequestMapping("/selejt")
	public String selejt(Model model) {
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

	@RequestMapping("/newmachine")
	public String newMachine(Model model) {
		model.addAttribute("newmach", new Machine());
		
		return "newmachine";
	}
	
	@RequestMapping("/machinelist")
	public String machineList(Model model) {
		model.addAttribute("machinelist", machineService.findAll());
		ArrayList<Long> boxi=new ArrayList<Long>();
		model.addAttribute("boxok", boxi);
		return "machineList";
	}
	
	@RequestMapping("/alapkeszlet")
	public String alapkeszlet(Model model) {
		if (productService.findAll().isEmpty()) {
			model.addAttribute("noproduct","");
			return "index";
		}
		model.addAttribute("products", productService.findAll());
		return "alapkeszlet";
	}
	
	@RequestMapping("/proba")
	public String proba() {
		return "proba";
	}
	
}
