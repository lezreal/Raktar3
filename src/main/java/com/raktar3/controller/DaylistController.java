package com.raktar3.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.raktar3.entities.Company;
import com.raktar3.entities.Daycompany;
import com.raktar3.entities.Daylist;
import com.raktar3.entities.Days;
import com.raktar3.service.CompanyService;
import com.raktar3.service.DaycompanyService;
import com.raktar3.service.DaylistService;
import com.raktar3.service.DaysService;
import com.raktar3.service.EmployeService;
import com.raktar3.service.MachHistoryService;
import com.raktar3.service.MachineService;
import com.raktar3.service.ProductService;
import com.raktar3.service.StockService;

@Controller
public class DaylistController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
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

	@Autowired
	DaycompanyService daycompanyService;
	
	@RequestMapping("/daylistCreate")
	public String daylistcreate(@RequestParam("dayid") int id, Model model) {
		List<Company> complist= companyService.finddelday(id);
		daylistService.deleteAll();
		
		for (int i=0; i<complist.size();i++) {
			Daylist dl = new Daylist();
			dl.setCompany(complist.get(i));
			dl.setSorszam(i+1);
			daylistService.addToDb(dl);
		}
		model.addAttribute("daylist", daylistService.findAll());
		model.addAttribute("allcompany", companyService.findAll());
		model.addAttribute("maxsorszam", daylistService.findAll().size());
		model.addAttribute("csakfix", daycompanyService.findDistinctName());
		return "daylistprev";  // EZ MÉG AZ ELŐKÉSZÜLET
	}
	
	@RequestMapping("/daylistbeszur")
	
	public String daylistbeszur(@RequestParam("ujcomp") String ujcomp,@RequestParam("elozo") String elozo, Model model) {
		Daylist beszurando = new Daylist();
		daylistService.sorszamNovel(Integer.parseInt(elozo));
		beszurando.setCompany(companyService.findById(Integer.parseInt(ujcomp)));
		beszurando.setSorszam(Integer.parseInt(elozo)+1);
		daylistService.addToDb(beszurando);
		
		model.addAttribute("daylist", daylistService.findAll());
		model.addAttribute("allcompany", companyService.findAll());
		model.addAttribute("maxsorszam", daylistService.findAll().size());
		model.addAttribute("csakfix", daycompanyService.findDistinctName());
		return "daylistprev";
		
		
		
	}
	
	@RequestMapping("/daylistkivesz")
	public String daylistkivesz(@RequestParam("kivesz") String kivesz, Model model, @RequestParam("sorszam") String sorszam) {
		
		daylistService.sorszamCsokkent(Integer.parseInt(sorszam));
		daylistService.deleteSelected(daylistService.findById(Integer.parseInt(kivesz)));
		
		model.addAttribute("daylist", daylistService.findAll());
		model.addAttribute("allcompany", companyService.findAll());
		model.addAttribute("maxsorszam", daylistService.findAll().size());
		model.addAttribute("csakfix", daycompanyService.findDistinctName());
		return "daylistprev";
	}

	
	@RequestMapping("/daylistfel")
	public String daylistfel(Model model, @RequestParam("sorszam") String sorszam) {
		
		Daylist regiday = daylistService.findFirstBySorszam(Integer.parseInt(sorszam)-1);
		Daylist ujday = daylistService.findFirstBySorszam(Integer.parseInt(sorszam));
		
		regiday.setSorszam(regiday.getSorszam()+1);
		ujday.setSorszam(ujday.getSorszam()-1);
		
		daylistService.addToDb(regiday);
		daylistService.addToDb(ujday);
		
		model.addAttribute("daylist", daylistService.findAll());
		model.addAttribute("allcompany", companyService.findAll());
		model.addAttribute("maxsorszam", daylistService.findAll().size());
		model.addAttribute("csakfix", daycompanyService.findDistinctName());
		return "daylistprev";
	}
	
	@RequestMapping("/daylistle")
	public String daylistle(Model model, @RequestParam("sorszam") String sorszam) {
		
		Daylist regiday = daylistService.findFirstBySorszam(Integer.parseInt(sorszam)+1);
		Daylist ujday = daylistService.findFirstBySorszam(Integer.parseInt(sorszam));
		
		regiday.setSorszam(regiday.getSorszam()-1);
		ujday.setSorszam(ujday.getSorszam()+1);
		
		daylistService.addToDb(regiday);
		daylistService.addToDb(ujday);
		
		model.addAttribute("daylist", daylistService.findAll());
		model.addAttribute("allcompany", companyService.findAll());
		model.addAttribute("maxsorszam", daylistService.findAll().size());
		model.addAttribute("csakfix", daycompanyService.findDistinctName());
		return "daylistprev";
	}
	
	@RequestMapping("/keszdaylist")
	public String keszdaylist(Model model) {
		model.addAttribute("companies", daylistService.findAll());
		return "daylist";
	}
	
	@RequestMapping("/daylistszerkesztomod")
	public String szerkesztomod(Model model) {
		model.addAttribute("daylist", daylistService.findAll());
		model.addAttribute("allcompany", companyService.findAll());
		model.addAttribute("maxsorszam", daylistService.findAll().size());
		model.addAttribute("csakfix", daycompanyService.findDistinctName());
		return "daylistprev";
	}
	
	@RequestMapping("/daylistsave")   // EZ AMIKOR ÚJ FIXLISTÁT CSINÁLOK
	public String daylistsave(@RequestParam("newlistname") String name) {
		List<Daylist> lista = daylistService.findAll();
		
		for (Daylist x:lista) {
			daycompanyService.addNewDaycompany(new Daycompany(x.getCompany(),x.getSorszam(),name));
		}
		return "index";
	}
	
	@RequestMapping("/daylistupdate")   // EZ AMIKOR ÚJ FIXLISTÁT CSINÁLOK
	public String daylistupdate(@RequestParam("name") String name) {
		daycompanyService.deleteSelectedName(name);
		List<Daylist> lista = daylistService.findAll();
		
		for (Daylist x:lista) {
			daycompanyService.addNewDaycompany(new Daycompany(x.getCompany(),x.getSorszam(),name));
		}
		
		return "index";
	}
	
	///////////////////// DAYS //////////////////////////////////////
	
	@RequestMapping("/newday")
	public String newday(Model model) {
		model.addAttribute("newday", new Days());
		model.addAttribute("daylist", daysService.findAll());
		return "newday";
	}
	
	@RequestMapping("/addNewDayToDb")
	public String addnewday(@ModelAttribute("newday") Days d, Model model) {
		daysService.addNewDay(d);
		model.addAttribute("regok", "");
		model.addAttribute("daylist", daysService.findAll());
		return "newday";
	}
	
	@RequestMapping("/createfixlist")
	public String createfixlist(Model model) {
		model.addAttribute("companies", companyService.findAll());
		return "createfixlist";
	}
	
	
	@RequestMapping("/selectedcomps")
	public String selectedcomps(@RequestParam("osszes") List<Integer> comps, Model model) {
		// TÖMBBE mentve kapom a COMPANY ID-kat
		daylistService.deleteAll();  // DAYLIST TÖRÖLVE
		
		for (int i=0;i<comps.size()-1;i++) {
			Company c = companyService.findById(comps.get(i));
			daylistService.addToDb(new Daylist(c,i+1));
		}
		
		
		model.addAttribute("daylist", daylistService.findAll());
		model.addAttribute("allcompany", companyService.findAll());
		model.addAttribute("maxsorszam", daylistService.findAll().size());
		model.addAttribute("csakfix", daycompanyService.findDistinctName());
		return "daylistprev";  // EZ MÉG AZ ELŐKÉSZÜLET
		
	}
	
	@RequestMapping("/loadfixlist")
	public String loadfixlist(Model model) {
		
		model.addAttribute("napok", daycompanyService.findDistinctName());
		
		return "loadfixlist";
	}
	
	@RequestMapping("/selectedfixlist")
	public String selectedfixlist(Model model, @RequestParam("name") String name, @RequestParam("action") String gomb) {
		
		if (gomb.equals("load")) {
		daylistService.deleteAll();
		List<Daycompany> selectedlist = daycompanyService.findSelectedNAme(name);
		
		for (Daycompany x:selectedlist) {
			Daylist dl = new Daylist(x.getCompany(),x.getSorszam());
			daylistService.addToDb(dl);
		}
		
		model.addAttribute("daylist", daylistService.findAll());
		model.addAttribute("allcompany", companyService.findAll());
		model.addAttribute("maxsorszam", daylistService.findAll().size());
		model.addAttribute("csakfix", daycompanyService.findDistinctName());
		return "daylistprev";  // EZ MÉG AZ ELŐKÉSZÜLET
		} else {
			daycompanyService.deleteSelectedName(name);
			return "index";
		}
	}
	
	@RequestMapping("/sorszamcsere")
	public String sorszamcsere(@RequestParam("ujsorszam") int ujsorszam,@RequestParam("keroid") int kid, Model model) {
		
		if (ujsorszam<=0) {   // ha HÜLYESÉGET üt be
			model.addAttribute("daylist", daylistService.findAll());
			model.addAttribute("allcompany", companyService.findAll());
			model.addAttribute("maxsorszam", daylistService.findAll().size());
			model.addAttribute("csakfix", daycompanyService.findDistinctName());
			return "daylistprev";  // EZ MÉG AZ ELŐKÉSZÜLET
		} 
		int maxi=daylistService.findMaxSorszam();
		if (ujsorszam>=maxi) {   // ha nagyobbat üt be, mint ahány sorszám van
			daylistService.sorszamCsokkent(daylistService.findById(kid).getSorszam());
			daylistService.findById(kid).setSorszam(maxi);
			daylistService.addToDb(daylistService.findById(kid));
			model.addAttribute("daylist", daylistService.findAll());
			model.addAttribute("allcompany", companyService.findAll());
			model.addAttribute("maxsorszam", daylistService.findAll().size());
			model.addAttribute("csakfix", daycompanyService.findDistinctName());
			return "daylistprev";  // EZ MÉG AZ ELŐKÉSZÜLET
		}
		
		if (ujsorszam<maxi) {
			
			if (ujsorszam<daylistService.findById(kid).getSorszam()) { // ha az új sorszam kisebb, mint a jelenlegi
				daylistService.sorszamCsereKisebbre(ujsorszam,daylistService.findById(kid).getSorszam());
				Daylist dl =daylistService.findById(kid);
				dl.setSorszam(ujsorszam);
				daylistService.addToDb(dl);
				model.addAttribute("daylist", daylistService.findAll());
				model.addAttribute("allcompany", companyService.findAll());
				model.addAttribute("maxsorszam", daylistService.findAll().size());
				model.addAttribute("csakfix", daycompanyService.findDistinctName());
				return "daylistprev";  // EZ MÉG AZ ELŐKÉSZÜLET
			} else {
				daylistService.sorszamCsereNagyobbra(ujsorszam,daylistService.findById(kid).getSorszam());
				Daylist dl =daylistService.findById(kid);
				dl.setSorszam(ujsorszam);
				daylistService.addToDb(dl);
				model.addAttribute("daylist", daylistService.findAll());
				model.addAttribute("allcompany", companyService.findAll());
				model.addAttribute("maxsorszam", daylistService.findAll().size());
				model.addAttribute("csakfix", daycompanyService.findDistinctName());
				return "daylistprev";  // EZ MÉG AZ ELŐKÉSZÜLET
			}
			
			
//			daylistService.sorszamCsereKisebbre(ujsorszam,daylistService.findById(kid).getSorszam());
//			daylistService.findById(kid).setSorszam(ujsorszam);
//			model.addAttribute("daylist", daylistService.findAll());
//			model.addAttribute("allcompany", companyService.findAll());
//			model.addAttribute("maxsorszam", daylistService.findAll().size());
//			return "daylistprev";  // EZ MÉG AZ ELŐKÉSZÜLET
		}
		
		//
		model.addAttribute("daylist", daylistService.findAll());
		model.addAttribute("allcompany", companyService.findAll());
		model.addAttribute("maxsorszam", daylistService.findAll().size());
		model.addAttribute("csakfix", daycompanyService.findDistinctName());
		return "daylistprev";  // EZ MÉG AZ ELŐKÉSZÜLET
		
	}
	
}
