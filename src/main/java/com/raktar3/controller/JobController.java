package com.raktar3.controller;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.text.TableView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.raktar3.entities.Company;
import com.raktar3.entities.Daycompany;
import com.raktar3.entities.Daylist;
import com.raktar3.entities.Employe;
import com.raktar3.entities.MachHistory;
import com.raktar3.entities.Machine;
import com.raktar3.entities.Product;
import com.raktar3.entities.Reminder;
import com.raktar3.entities.Repair;
import com.raktar3.entities.Stock;
import com.raktar3.entities.Traffic;
import com.raktar3.service.CompanyService;
import com.raktar3.service.DaycompanyService;
import com.raktar3.service.DaylistService;
import com.raktar3.service.EmployeService;
import com.raktar3.service.MachHistoryService;
import com.raktar3.service.MachineService;
import com.raktar3.service.ProductService;
import com.raktar3.service.ReminderService;
import com.raktar3.service.RepairService;
import com.raktar3.service.StockService;
import com.raktar3.service.TrafficService;
import com.raktar3.temp.Companyfixdays;

@Controller
public class JobController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TrafficService trafficService;
	
	@Autowired
	ReminderService reminderService;

	@Autowired
	RepairService repairService;
	
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
	DaycompanyService daycompanyService;
	
	@RequestMapping("/regProductToDb")
	public String regProductToDb(@ModelAttribute("product") Product p, Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		if (productService.findProductByName(p.getName())) { 
			model.addAttribute("siker","");
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
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		if (employeService.addUserToDb(emp)==true) model.addAttribute("siker", ""); else model.addAttribute("vanmar", ""); 
		model.addAttribute("emplist", productService.findAllEmploye());
		
		return "newEmp";
	}
	
	@RequestMapping("/newCompanyToDb")
	public String newCompany(@ModelAttribute("company") Company company, Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		productService.addCompany(company);
		model.addAttribute("company", new Company());
		model.addAttribute("siker","");
		return "newCompany";
		
		
	}

	@RequestMapping("/newCompanyToDbAndBackToList")
	public String newCompanyBackList (@ModelAttribute("company") Company company, Model model, @RequestParam("miutan") int miutan, @RequestParam("maxelem") int maxelem, @RequestParam("listanev") String listanev) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		
		productService.addCompany(company);
		
		Daylist dl = new Daylist();
		dl.setCompany(company);
		dl.setSorszam(miutan+1);
		
//		daycompanyService.sorszamNovel(listanev, miutan);
//		daycompanyService.addNewDaycompany(dc);
//		////////////
		
		daylistService.sorszamNovel(miutan);
		daylistService.addToDb(dl);
	
		model.addAttribute("listanev", listanev);
		model.addAttribute("daylist", daylistService.findAll());
		model.addAttribute("allcompany", companyService.findAll());
		model.addAttribute("maxsorszam", daylistService.findAll().size());
		model.addAttribute("csakfix", daycompanyService.findDistinctName());
		return "daylistprev";  // EZ MÉG AZ ELŐKÉSZÜLET
		
		
	}
	
	
	
	
	@RequestMapping("/keszletrolLevetel")
	public String keszletrolLevetel(@ModelAttribute("stock") Stock stock, Model model) {
		
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		stock.setEladas(true);;
		
		stockService.saleStock(stock);
		model.addAttribute("utolsoemploye", stock.getEmploye());   // utoljára rögzített EMP
		
		model.addAttribute("stock", new Stock());
		model.addAttribute("emps", employeService.findAllHumanEmploye());
		model.addAttribute("products", productService.findAll());
		model.addAttribute("siker", "");
		return "eladas";
	}

	@RequestMapping("/keszletlista")
	public String keszletlista(Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		List<Integer> id_lista =stockService.findProducts();
		
		List<Product> lista = new ArrayList();
				
		for (int i=0;i<id_lista.size();i++) {
			Product p =productService.findById(id_lista.get(i));  // KIKERESI AZ ADOTT IP-jü TERMÉKET
			
			p.setAmount(stockService.getAmount(id_lista.get(i))-stockService.getAmountSale(id_lista.get(i)));
			//stockService.getAmount(id_lista.get(i))-stockService.getAmountSale(id_lista.get(i))
			lista.add(p);
		}
		model.addAttribute("productlist", lista);
		
		return "productList";
	}
	
	
	
	
	
	
	
	@RequestMapping("/newMachToDb")
	public String newMachine(@ModelAttribute("newmach") Machine mach, Model model, @RequestParam("sorszam") int sorszam) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		if (machineService.findSorszam(sorszam)){
			mach.setSorszam(sorszam);
			mach.setCompany(companyService.findById(1));
			model.addAttribute("siker", "");
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
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		model.addAttribute("selected", machineService.findById(id));
		model.addAttribute("comps", companyService.findAll());
		model.addAttribute("history", machHistoryService.findMachine(machineService.findById(id)));
		
		return "selectedMachine";
	}
	
	@RequestMapping("/machHistoryToDb")
	public String historyToDb(Model model,@ModelAttribute("selected") Machine m, @RequestParam("compid") int compid,
			@RequestParam("ujsorszam") String ujsorszam, @RequestParam("regisorszam") String regisorszam, 
			@RequestParam("oldcompid") int oldcompid, @RequestParam("alertradio") String alertradio,@RequestParam("alertdate") String alertdate,
			@RequestParam("alertcomment") String alertcomment, @RequestParam("alerttime") Integer alerttime, @RequestParam("newtype") byte newtype) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
				////// ide jön az ALERTTIME
			if (alertradio.equals("yes")) {
				if (alertdate.isEmpty() || alertdate==null || alertcomment.isEmpty() || alertcomment==null) {  // ha hiányoznak adatok
					model.addAttribute("reminderhiba", "");
					model.addAttribute("selected", machineService.findById(m.getId()));
					model.addAttribute("comps", companyService.findAll());
					model.addAttribute("history", machHistoryService.findMachine(m));
				return "selectedMachine";
				}
				Reminder rem = new Reminder();
				rem.setAlerttime(alerttime);
				rem.setComment(alertcomment);
				rem.setDate(alertdate);
				rem.setMachine(m);
				reminderService.addReminder(rem);
				
			}
		
				////// ide jön az ALERTTIME
			
			if (oldcompid!=compid) {
				MachHistory mh = new MachHistory();
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				Date date = new Date();
				mh.setDate(dateFormat.format(date));
				mh.setCompany(companyService.findById(compid));
				mh.setMachine(m);
				mh.setOldcompany(companyService.findById(oldcompid));
				machHistoryService.addNewHistory(mh);
			}
		
		
		if (Integer.parseInt(ujsorszam)>0) {
		
		if (Integer.parseInt(ujsorszam)==Integer.parseInt(regisorszam)) {
			
			
			if (compid==0) m.setCompany(companyService.findById(m.getId()));
			
			model.addAttribute("siker","");
			
			m.setCompany(companyService.findById(compid));
			m.setSorszam(Integer.parseInt(regisorszam));
			machineService.addMachineToDb(m);
			m.setType(newtype);
		} else if (machineService.findSorszam(Integer.parseInt(ujsorszam))){   // HA MÁS SORSZÁMOT ADOTT MEG ÉS SZABAD
			
			
			
			model.addAttribute("siker","");
			m.setCompany(companyService.findById(compid));
			m.setSorszam(Integer.parseInt(ujsorszam));
			m.setType(newtype);
			machineService.addMachineToDb(m);
			
		} else {
		
			model.addAttribute("foglalt",""); 
			model.addAttribute("selected", machineService.findById(m.getId()));
			model.addAttribute("comps", companyService.findAll());
			model.addAttribute("history", machHistoryService.findMachine(m));
		return "selectedMachine";
		}
		} else { // HA ROSSZ SORSZÁMOT ADOTT MEG (<0)
			model.addAttribute("foglalt","");
			model.addAttribute("selected", machineService.findById(m.getId()));
			model.addAttribute("comps", companyService.findAll());
			model.addAttribute("history", machHistoryService.findMachine(m));
		return "selectedMachine";
		}
			model.addAttribute("selected", m);
			model.addAttribute("comps", companyService.findAll());
			model.addAttribute("history", machHistoryService.findMachine(m));
		return "selectedMachine";
	}


	
	
	@RequestMapping("/probafeldolgoz")
	public String probafeldolgoz( ) {
		
		return "index";
	}
	
	@RequestMapping("/company/{id}")
	public String editCompany(@PathVariable("id") int id, Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		model.addAttribute("company", companyService.findById(id));
		List<Employe> emplist = employeService.findAllHumanEmploye();
		
		
		
		
		model.addAttribute("employes", emplist);
		return "editCompany";
	}
	
	
	@RequestMapping("/product/{id}")
	public String editProduct(@PathVariable("id") int id, Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		model.addAttribute("product", productService.findById(id));
		List<Employe> emplist = employeService.findAllHumanEmploye();
		
		
		return "editProduct";
	}
	
	
	@RequestMapping("/editCompanyToDb")
	public String editCompany(@ModelAttribute("company") Company c, Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
	
		companyService.addNewCompany(c);
		
		model.addAttribute("compok", "");
		model.addAttribute("company", c);
		
		
		model.addAttribute("editsuccess", "");
		
		return "editCompany";
	}
	
	@RequestMapping("/editProductToDb")
	public String editProduct(@RequestParam("productid") int id,@RequestParam("productname") String name,@RequestParam("productcomment") String comment, Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		Product p = productService.findById(id);
		p.setName(name);
		p.setDescription(comment);
		productService.regNewProduct(p);
		model.addAttribute("product", p);
		model.addAttribute("editsuccess", "");
		return "editProduct";
	}
	
	@RequestMapping("/delstock")
	public String delstock(Model model, @RequestParam("torlendok") int[] torlendok) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		
		
		for (int i=0;i<torlendok.length;i++) {
			if (torlendok[i]!=0) stockService.deleteById(torlendok[i]);
		}
		model.addAttribute("stocklist", stockService.stockList());
		return "stockList";
	}
	
	@RequestMapping("/employe/{id}")
	public String editEmploye(@PathVariable("id") int id, Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
			model.addAttribute("employe",employeService.findById(id) );
			model.addAttribute("employeok","" );
			
		
		return "editEmploye";
	}
	
	@RequestMapping("editEmployeToDb")
	public String editemployetodb(@ModelAttribute("employe") Employe emp, Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		log.info(emp.getName());
		log.info(emp.getDescription());
		employeService.addUserToDb(emp);
		model.addAttribute("editsuccess", "");
		return "editEmploye";
	}
	
	@RequestMapping("delEmploye")
	public String delemploye(@RequestParam("delempid") int id, Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		if (stockService.findEmploye(employeService.findById(id))) {
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
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		if (productService.deleteProduct(productService.findById(id))) {
			model.addAttribute("delok","");
		} else model.addAttribute("delfail", "");
		model.addAttribute("productlist", productService.findAll());
		return "productListSimple";
	}
	
	@RequestMapping("/lekerdezes2")
	public String lekerdez2(@RequestParam("bday") String date,@RequestParam("empid") int empid,@RequestParam("pid") int pid, Model model,@RequestParam("gomb") String gomb) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		
		int eladva=0, bejott=0;
		
		if (gomb.equals("napi")) {
		if (trafficService.lekerdezes(date, empid, pid)==null) return "index";
		List<Traffic> trafficlista=trafficService.lekerdezes(date, empid, pid);
		for (int i=0;i<trafficlista.size();i++) {
			if (trafficlista.get(i).isBeerkezes()==true) bejott=bejott+trafficlista.get(i).getAmount(); else {
				if (trafficlista.get(i).isSelejt()==false)
				eladva=eladva+trafficlista.get(i).getAmount(); 
			}
		}
		
		model.addAttribute("lekerdezes", trafficlista);
		model.addAttribute("eladott", eladva);
		model.addAttribute("bejott", bejott);
		 model.addAttribute("datum", date);
		 if (empid!=0) model.addAttribute("employe", employeService.findById(empid).getName());
		 
		return "lekerdezes2";
		} else {
			List<Traffic> trafficlista=trafficService.havilekerdezes(date, empid, pid);
			for (int i=0;i<trafficlista.size();i++) {
				if (trafficlista.get(i).isBeerkezes()==true) bejott=bejott+trafficlista.get(i).getAmount(); else {
					if (trafficlista.get(i).isSelejt()==false)
					eladva=eladva+trafficlista.get(i).getAmount(); 
				}
			}
			model.addAttribute("lekerdezes", trafficlista);
			model.addAttribute("eladott", eladva);
			model.addAttribute("bejott", bejott);
			model.addAttribute("datum", date);
			return "lekerdezes2";
			
		}
	}

	@RequestMapping("/companymachine/{id}")
	public String companymachine(@PathVariable("id") int compid, Model model) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		model.addAttribute("machines", machineService.findCompanyMachine(compid));
		return "companymachinelist";
	}

	@RequestMapping("/historydelete")
	public String historydelete(@RequestParam("hid") int hid, Model model) {
		
		machHistoryService.deleteSelected(hid);
		model.addAttribute("historylist", machHistoryService.findAll());
		model.addAttribute("siker", "");
		return "machhistory";
	}
	
	@RequestMapping("/addreminder")
	public String addreminder(@RequestParam("comment") String comment, @RequestParam("mid") int mid, @RequestParam("bday") String date, Model model, @RequestParam("alert") int alerttime) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		Reminder rem = new Reminder();
		rem.setComment(comment);
		rem.setDate(date);
		rem.setAlerttime(alerttime);
		rem.setMachine(machineService.findById(mid));
		reminderService.addReminder(rem);
		model.addAttribute("machines", machineService.findAll());
		model.addAttribute("siker", "");
		return "reminder";
	}
	
	@RequestMapping("/deaktival")
	public String deaktival(Model model, @ RequestParam("rid") int rid) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		reminderService.deactival(rid);
		model.addAttribute("reminders", reminderService.findAll());
		return "reminderlist";

	}
	
	@RequestMapping("/probacompany")
	public String probacompany(@RequestParam("keres") String keres, Model model) {
		Company comp = new Company();
		comp.setName(keres);
		
		
		model.addAttribute("futar", employeService.findAllHumanEmploye());
		model.addAttribute("company",comp);
		return "newCompany";
	}
	@RequestMapping("newservice")
	public String newservice(Model model) {
		model.addAttribute("service", new Repair());
		model.addAttribute("machines", machineService.findAll());
		return "newservice";
	}
	
	@RequestMapping("newServiceToDb")
	public String newservicetodb(Model model, @ModelAttribute("service") Repair s,@RequestParam("mid") int mid) {
		
		s.setMachine(machineService.findById(mid));
		//s.setDate(date);
		
		repairService.addNewService(s);
		model.addAttribute("service", new Repair());
		model.addAttribute("machines", machineService.findAll());
		model.addAttribute("siker", "");
		return "newservice";
	}
	
	@RequestMapping("repairlist")
	public String repairlist(Model model) {
		
		model.addAttribute("repairlist", repairService.findAll());
		return "repairList";
	}
	
	@RequestMapping("/beszurcompany/{id}/{lista}/{sorszam}")
	public String beszurCompany(@PathVariable("id") int ujcompid, Model model,@PathVariable("lista") String listanev,@PathVariable("sorszam") int sorszam) {
		if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
		
		log.info("ID: "+ujcompid);
		log.info("Lista: "+listanev);
		log.info("Sorszám: "+sorszam);
		
//		model.addAttribute("company", companyService.findById(ujcompid));
//		List<Employe> emplist = employeService.findAllHumanEmploye();
//		List<Days> daylist = daysService.findAll();
//		
		
//		Collections.swap(emplist, 0, emplist.indexOf(companyService.findById(id).getEmploye()));
//		Collections.swap(daylist, 0, daylist.indexOf(companyService.findById(id).getDays()));
//		
		model.addAttribute("companies", companyService.findAll());
		model.addAttribute("lista", listanev);
		model.addAttribute("sorszam", sorszam);
		return "selectcompany";
	}
	
	
		@RequestMapping("/quickMachine")
		public String quickmachine(Model model,@RequestParam("machsorszam") int sorszam) {
			
			
			if (machineService.findBySorszam(sorszam)==null) {
				model.addAttribute("nomachine", "");
				return "productList";
			}
			
			
			
			model.addAttribute("selected", machineService.findBySorszam(sorszam));
			model.addAttribute("comps", companyService.findAll());
			model.addAttribute("history", machHistoryService.findMachine( machineService.findBySorszam(sorszam)));
			
			return "selectedMachine";
		}
		
		@RequestMapping("/reminder/{id}")
		public String editreminder(Model model, @PathVariable("id") int rid) {
			model.addAttribute("reminder", reminderService.findById(rid));
			model.addAttribute("selectedmachine", reminderService.findById(rid).getMachine());
			model.addAttribute("machine", machineService.findAll());
			return "editreminder";
		}
	
		@RequestMapping("/editReminderToDb")
		public String editReminderToDb(@ModelAttribute("reminder") Reminder reminder, @RequestParam("mid") int mid) {
			log.info(reminder.getComment());
			log.info(""+reminder.getMachine());
			log.info(""+mid);
			if (reminder.getMachine()==null && mid!=0) {
				reminder.setMachine(machineService.findById(mid));
			} else 
				
				if (reminder.getMachine()!=null && mid!=reminder.getMachine().getId()) {
					reminder.setMachine(machineService.findById(mid));	
				}
			reminderService.addReminder(reminder);
			return "index";
		}
		
		@RequestMapping("/quickCompany")
		public String quickcompany(Model model,@RequestParam("cegkereso") String ceg) {
			if (reminderService.vizsgal().size()>0) model.addAttribute("reminder", "");
			
			Set<Companyfixdays> cfdlist = new HashSet<Companyfixdays>();  // 
			Set<Company> complist = companyService.findByName(ceg);  // tömbben a cégek
			List<Daycompany> daycomplist = daycompanyService.findAll();  // tömbben a daycompany-k
			
			
			for (Company x : complist) {
				Companyfixdays cfix = new Companyfixdays();
				cfix.setCompany(x);
				
				
				List<String> fixdayslist = new ArrayList();
				for (int k=0;k<daycomplist.size();k++) {
					if (x==daycomplist.get(k).getCompany()) {
						fixdayslist.add(daycomplist.get(k).getName());
					}
				}
				
				cfix.setFixdays(fixdayslist);
					
				if (machineService.vanegepe(x.getId())>0){
						cfix.setVangepe(true);
				}
				
					cfdlist.add(cfix);
			}
			
			
//			for (int i=0;i<complist.size();i++) {
//				Companyfixdays cfix = new Companyfixdays();
//				cfix.setCompany(complist.get(i));
//				List<String> fixdayslist = new ArrayList();
//				for (int k=0;k<daycomplist.size();k++) {
//					if (complist.get(i)==daycomplist.get(k).getCompany()) {
//						fixdayslist.add(daycomplist.get(k).getName());
//					}
//				}
//				
//				cfix.setFixdays(fixdayslist);
//				
//						
//						if (machineService.vanegepe(complist.get(i).getId())>0){
//							cfix.setVangepe(true);
//						}
//				cfdlist.add(cfix);
//			}
			
			model.addAttribute("companies", cfdlist);
			model.addAttribute("daycompanies", daycompanyService.findAll());
			
			
			
			return "torolheto";
		}
		
		@RequestMapping("/elszamolasbe2")
		public String elszamolasbe(Model model, @RequestParam("darab") ArrayList<Integer> darab, @RequestParam("pid") ArrayList<Integer> pid, @RequestParam("comment") ArrayList<String> comment, @RequestParam("empid") int empid, @RequestParam("bday") String date) {
			
			for (int i=0;i<pid.size();i++) {
				if (darab.get(i)>0 && darab.get(i)!=null) {
					Stock stocktemp = new Stock();
					stocktemp.setProduct(productService.findById(pid.get(i)));
					stocktemp.setAmount(darab.get(i));
					stocktemp.setDate(date);
					stocktemp.setEmploye(employeService.findById(empid));
					stocktemp.setBeerkezes(true);
					stocktemp.setComment(comment.get(i));
					stockService.addIncoming(stocktemp);
					trafficService.addIncomingTraffic(stocktemp);
					stocktemp=null;
				}
			}
			
			model.addAttribute("rogzitve",pid);
			model.addAttribute("beerkezesok","");
			model.addAttribute("termekek",productService.findAll());
			model.addAttribute("emps", employeService.findAllHumanEmploye());
			return "beerkezes";
		}
		
		
		@RequestMapping("/elszamolaski2")
		public String elszamolaski(Model model, @RequestParam("darab") ArrayList<Integer> darab, @RequestParam("pid") ArrayList<Integer> pid, @RequestParam("comment") ArrayList<String> comment, 
				@RequestParam("empid") int empid, @RequestParam("bday") String date, @RequestParam("selejt") ArrayList<Integer> selejt) {
		
			for (int i=0;i<pid.size();i++) {
				if (darab.get(i)>0 && darab.get(i)!=null) {
					Stock stocktemp = new Stock();
					stocktemp.setProduct(productService.findById(pid.get(i)));
					stocktemp.setAmount(darab.get(i));
					stocktemp.setDate(date);
					stocktemp.setEmploye(employeService.findById(empid));
					stocktemp.setEladas(true);
					stocktemp.setComment(comment.get(i));
					if (selejt!=null) {
					if (selejt.contains(pid.get(i))) {
						stocktemp.setSelejt(true);
						stocktemp.setEladas(false);
					}
					}
					stockService.addIncoming(stocktemp);
					trafficService.addIncomingTraffic(stocktemp);
					stocktemp=null;
				}
			}
		
			
			model.addAttribute("eladasok","");
			model.addAttribute("termekek",productService.findAll());
			model.addAttribute("emps", employeService.findAllHumanEmploye());
			
			return "eladas";
		}
		
		@RequestMapping("/alapkeszletToDb")
		public String alapkeszletbeallitas(@RequestParam("pid") int pid,@RequestParam("darab") int uj_darabszam,@RequestParam("bday") String date,@RequestParam("comment") String comment, Model model) {
		
			stockService.deleteByProduct(pid);
			Stock s = new Stock();
			s.setAmount(uj_darabszam);
			s.setBeerkezes(true);
			s.setComment(comment);
			s.setDate(date);
			s.setEmploye(employeService.findById(1));
			s.setProduct(productService.findById(pid));
			s.setEladas(false);
			s.setSelejt(false);
			stockService.addIncoming(s);
			trafficService.addIncomingTraffic(s);
			model.addAttribute("success", "");
			model.addAttribute("products", productService.findAll());
			return "alapkeszlet";
		}
}


