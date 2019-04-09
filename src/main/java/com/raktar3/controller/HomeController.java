package com.raktar3.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.raktar3.entities.Reminder;
import com.raktar3.entities.Stock;
import com.raktar3.service.CompanyService;
import com.raktar3.service.DaysService;
import com.raktar3.service.EmployeService;
import com.raktar3.service.MachHistoryService;
import com.raktar3.service.MachineService;
import com.raktar3.service.ProductService;
import com.raktar3.service.ReminderService;
import com.raktar3.service.StockService;



@Controller
public class HomeController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ReminderService reminderService;
		
	@Autowired
	MachHistoryService machHistoryService;
	
	@Autowired
	MachineService machineService;
	
	@Autowired
	EmployeService employeService;
	
	@Autowired
	StockService stockService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CompanyService companyService;

	@Autowired
	DaysService daysService;
	
	@RequestMapping("/")
	public String home(Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		
		List<Integer> id_lista =stockService.findProducts();
		List<Product> lista = new ArrayList<Product>();
		for (int i=0;i<id_lista.size();i++) {
			Product p =productService.findById(id_lista.get(i));
			int osszes=stockService.getAmount(id_lista.get(i))-stockService.getAmountSale(id_lista.get(i));
			p.setAmount(osszes);
			lista.add(p);
		}
		model.addAttribute("productlist", lista);
		model.addAttribute("proba", "picsa");
		return "productList";
	}
	
	@RequestMapping("/csengos")
	public String homeWithCsengo(Model model) {
		if (reminderService.vizsgal().size()>0) 
		{
			model.addAttribute("csengo", reminderService.vizsgal());
			model.addAttribute("reminder", "");
			
		}
		List<Integer> id_lista =stockService.findProducts();
		List<Product> lista = new ArrayList<Product>();
		for (int i=0;i<id_lista.size();i++) {
			Product p =productService.findById(id_lista.get(i));
			int osszes=stockService.getAmount(id_lista.get(i))-stockService.getAmountSale(id_lista.get(i));
			p.setAmount(osszes);
			lista.add(p);
		}
		model.addAttribute("productlist", lista);
		model.addAttribute("proba", "picsa");
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
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
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
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		model.addAttribute("newemp", new Employe());
		model.addAttribute("emplist", employeService.findAllHumanEmploye());
		return "newEmp";
	}
	
	@RequestMapping("/beerkezes")
	public String beerkezes(Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
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
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
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
		
		return "eladas";
	}
	
	@RequestMapping("/newcompany")
	public String newcompany(Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		if (employeService.findAllHumanEmploye().isEmpty()) {
			model.addAttribute("noemploye","");
			return "index";
		}
		if (daysService.findAll().isEmpty()) {
			model.addAttribute("nodays","");
			return "index";
		}
		
		model.addAttribute("daylist", daysService.findAll());
		model.addAttribute("futar", employeService.findAllHumanEmploye());
		model.addAttribute("company", new Company());
		return "newCompany";
	}
	
	@RequestMapping("/companylist")
	public String companylist(Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		model.addAttribute("companies", companyService.findAllReal());
		model.addAttribute("vanegepe", machineService.findAll());
		return "companyList";
	}
	
	@RequestMapping("/employelist")
	public String employelist(Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		model.addAttribute("employes", employeService.findAllHumanEmploye());
		return "employeList";
	}
	
	@RequestMapping("/keszletmozgaslista")
	public String keszletmozgaslista(Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		model.addAttribute("stocklist", stockService.stockList());
		return "stockList";
	}
	
	@RequestMapping("/kamion")
	public String kamion(Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
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
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
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
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		model.addAttribute("newmach", new Machine());
		model.addAttribute("ujsorszam", machineService.getNewSorszam());
		return "newmachine";
	}
	
	@RequestMapping("/machinelist")
	public String machineList(Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		model.addAttribute("machinelist", machineService.findAll());

		return "machineList";
	}
	
	@RequestMapping("/alapkeszlet")
	public String alapkeszlet(Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		if (productService.findAll().isEmpty()) {
			model.addAttribute("noproduct","");
			return "index";
		}
		model.addAttribute("products", productService.findAll());
		return "alapkeszlet";
	}
	
	
	@RequestMapping("/lekerdezes")
	public String lekerdezes(Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		model.addAttribute("employes", employeService.findAllEmploye());
		model.addAttribute("products", productService.findAll());
		return "lekerdezes1";
	}
	
	@RequestMapping("/history")
	public String history(Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		model.addAttribute("historylist", machHistoryService.findAll());
		return "machhistory";
	}
	
	@RequestMapping("/reminder")
	public String reminder(Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		model.addAttribute("machines", machineService.findAll());
		return "reminder";
	}

	@RequestMapping("/reminderlist")
	public String reminderlist(Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		List<Reminder> lista = reminderService.findAll(); // az összes TRUE reminder-t megkapja
		List<Reminder> listatemp = new ArrayList<Reminder>();
		for (int i=0;i<lista.size();i++) {
			log.info("Hátranapok: "+kelle(lista.get(i).getDate())+" - "+lista.get(i).getDate());
			if (kelle(lista.get(i).getDate())<=lista.get(i).getAlerttime()) {
				listatemp.add(lista.get(i));
			}
		}
		
		model.addAttribute("reminders", listatemp);   
		
		return "reminderlist";
	}
	
	public int kelle(String datum) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String mai =dateFormat.format(date);  // mai dátum kinyerve  2019/02/22
		int ma=0, vizsgalando=0;
		ma= 365*Integer.parseInt(mai.substring(0, 4))+30*Integer.parseInt(mai.substring(5,7))+Integer.parseInt(mai.substring(8));
		vizsgalando= 365*Integer.parseInt(datum.substring(0, 4))+30*Integer.parseInt(datum.substring(5,7))+Integer.parseInt(datum.substring(8));
		return vizsgalando-ma;
	}
	
	@RequestMapping("/reminderAllList")
	public String reminderAllList(Model model) {
		model.addAttribute("reminders", reminderService.findVeryAll());
		return "reminderlist";
	}

	
}
