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
import com.raktar3.entities.Daycompany;
import com.raktar3.entities.Employe;
import com.raktar3.entities.Machine;
import com.raktar3.entities.Product;
import com.raktar3.entities.Reminder;
import com.raktar3.entities.Stock;

import com.raktar3.service.CompanyService;
import com.raktar3.service.DaycompanyService;
import com.raktar3.service.EmployeService;
import com.raktar3.service.MachHistoryService;
import com.raktar3.service.MachineService;
import com.raktar3.service.ProductService;
import com.raktar3.service.ReminderService;
import com.raktar3.service.StockService;
import com.raktar3.temp.Companyfixdays;



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
	DaycompanyService daycompanyService;
	
	
	
	@RequestMapping("/")
	public String home(Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		
		model.addAttribute("topstock5", stockService.findTop5());
		model.addAttribute("fixdays", daycompanyService.findDistinctName());
		return "index";
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
	
	

	
	@RequestMapping("/newemp")
	public String newEmp(Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		model.addAttribute("newemp", new Employe());
		model.addAttribute("emplist", employeService.findAllHumanEmploye());
		return "newEmp";
	}
	
	
		
	@RequestMapping("/newcompany")
	public String newcompany(Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		if (employeService.findAllHumanEmploye().isEmpty()) {
			model.addAttribute("noemploye","");
			return "index";
		}
		
		model.addAttribute("futar", employeService.findAllHumanEmploye());
		model.addAttribute("company", new Company());
		return "newCompany";
	}
	
	@RequestMapping("/companylist")
	public String companylist(Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		
		List<Companyfixdays> cfdlist = new ArrayList<Companyfixdays>();  // 
		List<Company> complist = companyService.findAllReal();  // tömbben a cégek
		List<Daycompany> daycomplist = daycompanyService.findAll();  // tömbben a daycompany-k
		
		for (int i=0;i<complist.size();i++) {
			Companyfixdays cfix = new Companyfixdays();
			cfix.setCompany(complist.get(i));
			List<String> fixdayslist = new ArrayList();
			for (int k=0;k<daycomplist.size();k++) {
				if (complist.get(i)==daycomplist.get(k).getCompany()) {
					fixdayslist.add(daycomplist.get(k).getName());
				}
			}
			
			cfix.setFixdays(fixdayslist);
			
					
					if (machineService.vanegepe(complist.get(i).getId())>0){
						cfix.setVangepe(true);
					}
			cfdlist.add(cfix);
		}
		
		model.addAttribute("companies", cfdlist);
		model.addAttribute("daycompanies", daycompanyService.findAll());
		return "companyList";
	}
	
	@RequestMapping("/tartozok")
	public String tartozok(Model model) {
		
		model.addAttribute("companies", companyService.tartozok());
		model.addAttribute("daycompanies", daycompanyService.findAll());
		return "tartozok";
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
	
	
	
	
	@RequestMapping("/lekerdezes")
	public String lekerdezes(Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		model.addAttribute("employes", employeService.findAllEmploye());
		model.addAttribute("products", productService.findAll());
//		List<String> lista=new ArrayList<String>();
//		for (int i=0; i<stockService.distinctDate().size();i++) {
//			if (i==0) lista.add(stockService.distinctDate().get(i).substring(0,7)); else
//				
//				if (!stockService.distinctDate().get(i).substring(0,7).equals(stockService.distinctDate().get(i-1).substring(0,7))) lista.add(stockService.distinctDate().get(i).substring(0,7));
//			
//		}
		
			
//			for (String x:stockService.distinctDate()) {
//			lista.add(x.substring(0,7));
//		}
//		model.addAttribute("dates", lista);
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


	@RequestMapping("/elszamolasBE")
	public String elszamolasbe(Model model) {
		model.addAttribute("emps", employeService.findAllHumanEmploye());
		model.addAttribute("termekek", productService.findAll());
		return "beerkezes";
	}
	
	@RequestMapping("/eladas")
	public String elszamolaski(Model model) {
		model.addAttribute("emps", employeService.findAllHumanEmploye());
		model.addAttribute("termekek", productService.findAll());
		return "eladas";
	}
	
	@RequestMapping("/alapkeszlet")
	public String alapbeallitas(Model model) {
		model.addAttribute("products", productService.findAll());
		return "alapkeszlet";
	}
	
}
