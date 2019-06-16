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
import com.raktar3.service.CompanyService;
import com.raktar3.service.DaycompanyService;
import com.raktar3.service.DaylistService;
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
	public String daylistbeszur(@RequestParam("ujcomp") String ujcomp,@RequestParam("elozo") String elozo, Model model, @RequestParam("napnev") String name) {
		Daylist beszurando = new Daylist();
		daylistService.sorszamNovel(Integer.parseInt(elozo));
		beszurando.setCompany(companyService.findById(Integer.parseInt(ujcomp)));
		beszurando.setSorszam(Integer.parseInt(elozo)+1);
		daylistService.addToDb(beszurando);
		
		model.addAttribute("listanev", name);
		model.addAttribute("daylist", daylistService.findAll());
		model.addAttribute("allcompany", companyService.findAll());
		model.addAttribute("maxsorszam", daylistService.findAll().size());
		model.addAttribute("csakfix", daycompanyService.findDistinctName());
		return "daylistprev";
	}
	
	@RequestMapping("/daylistujbeszur")
	public String ujbeszur(Model model, @RequestParam("miutan") int miutan, @RequestParam("maxelem") int maxelem, @RequestParam("listanev") String listanev) {
		
		model.addAttribute("company", new Company());
		model.addAttribute("listanev", listanev);
		model.addAttribute("miutan", miutan);
		model.addAttribute("maxelem", maxelem);
		model.addAttribute("company", new Company());
		return "newCompanyToList";
	}
	
	@RequestMapping("/daylistkivesz")
	public String daylistkivesz(@RequestParam("kivesz") String kivesz, Model model, @RequestParam("sorszam") String sorszam, @RequestParam("napnev") String name) {
		
		daylistService.sorszamCsokkent(Integer.parseInt(sorszam));
		daylistService.deleteSelected(daylistService.findById(Integer.parseInt(kivesz)));
		
		model.addAttribute("listanev", name);
		model.addAttribute("daylist", daylistService.findAll());
		model.addAttribute("allcompany", companyService.findAll());
		model.addAttribute("maxsorszam", daylistService.findAll().size());
		model.addAttribute("csakfix", daycompanyService.findDistinctName());
		return "daylistprev";
	}

	
	@RequestMapping("/daylistfel")
	public String daylistfel(Model model, @RequestParam("sorszam") String sorszam, @RequestParam("napnev") String name) {
		
		Daylist regiday = daylistService.findFirstBySorszam(Integer.parseInt(sorszam)-1);
		Daylist ujday = daylistService.findFirstBySorszam(Integer.parseInt(sorszam));
		
		regiday.setSorszam(regiday.getSorszam()+1);
		ujday.setSorszam(ujday.getSorszam()-1);
		
		daylistService.addToDb(regiday);
		daylistService.addToDb(ujday);
		
		model.addAttribute("listanev", name);
		model.addAttribute("daylist", daylistService.findAll());
		model.addAttribute("allcompany", companyService.findAll());
		model.addAttribute("maxsorszam", daylistService.findAll().size());
		model.addAttribute("csakfix", daycompanyService.findDistinctName());
		return "daylistprev";
	}
	
	@RequestMapping("/daylistle")
	public String daylistle(Model model, @RequestParam("sorszam") String sorszam, @RequestParam("napnev") String name) {
		
		Daylist regiday = daylistService.findFirstBySorszam(Integer.parseInt(sorszam)+1);
		Daylist ujday = daylistService.findFirstBySorszam(Integer.parseInt(sorszam));
		
		regiday.setSorszam(regiday.getSorszam()-1);
		ujday.setSorszam(ujday.getSorszam()+1);
		
		daylistService.addToDb(regiday);
		daylistService.addToDb(ujday);
		
		model.addAttribute("listanev", name);
		model.addAttribute("daylist", daylistService.findAll());
		model.addAttribute("allcompany", companyService.findAll());
		model.addAttribute("maxsorszam", daylistService.findAll().size());
		model.addAttribute("csakfix", daycompanyService.findDistinctName());
		return "daylistprev";
	}
	
	@RequestMapping("/keszdaylist")
	public String keszdaylist(Model model, @RequestParam("napnev") String name) {
		 
		model.addAttribute("listanev", name);
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
	public String daylistsave(@RequestParam("newlistname") String name, Model model) {
		if (!daycompanyService.vaneilyennev(name) || name==null || name.isEmpty()) {
			model.addAttribute("savefail", "");
			model.addAttribute("listanev", name);
			model.addAttribute("daylist", daylistService.findAll());
			model.addAttribute("allcompany", companyService.findAll());
			model.addAttribute("maxsorszam", daylistService.findAll().size());
			model.addAttribute("csakfix", daycompanyService.findDistinctName());
			return "daylistprev";  // EZ MÉG AZ ELŐKÉSZÜLET
		
		}
		
		/// IDE JÖN A LISTANÉV ELLENÖRTZSŐá
		List<Daylist> lista = daylistService.findAll();
		
		for (Daylist x:lista) {
			daycompanyService.addNewDaycompany(new Daycompany(x.getCompany(),x.getSorszam(),name));
		}
		model.addAttribute("saveok", "");
		model.addAttribute("listanev", name);
		model.addAttribute("daylist", daylistService.findAll());
		model.addAttribute("allcompany", companyService.findAll());
		model.addAttribute("maxsorszam", daylistService.findAll().size());
		model.addAttribute("csakfix", daycompanyService.findDistinctName());
		return "daylistprev";  // EZ MÉG AZ ELŐKÉSZÜLET
	}
	
	@RequestMapping("/daylistupdate")   // EZ AMIKOR ÚJ FIXLISTÁT CSINÁLOK
	public String daylistupdate(@RequestParam("name") String name, Model model) {
		daycompanyService.deleteSelectedName(name);
		List<Daylist> lista = daylistService.findAll();
		
		for (Daylist x:lista) {
			daycompanyService.addNewDaycompany(new Daycompany(x.getCompany(),x.getSorszam(),name));
		}
		
		model.addAttribute("updateok", "");
		model.addAttribute("listanev", name);
		model.addAttribute("daylist", daylistService.findAll());
		model.addAttribute("allcompany", companyService.findAll());
		model.addAttribute("maxsorszam", daylistService.findAll().size());
		model.addAttribute("csakfix", daycompanyService.findDistinctName());
		
		return "daylistprev";
	}
	
	///////////////////// DAYS //////////////////////////////////////
	
	
	
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
	
	@RequestMapping("/selectedfixlist")   // IDE KELL MAJD JÖJJEK, HA MEGVAN A BESZÚRÁS
	public String selectedfixlist(Model model, @RequestParam("name") String name, @RequestParam("action") String gomb) {  // a NAME-ben adja át a lista nevét
		
		if (gomb.equals("load")) {
		daylistService.deleteAll();
		List<Daycompany> selectedlist = daycompanyService.findSelectedNAme(name);
		
		for (Daycompany x:selectedlist) {
			Daylist dl = new Daylist(x.getCompany(),x.getSorszam());
			daylistService.addToDb(dl);
		}
		model.addAttribute("listanev", name);
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
	public String sorszamcsere(@RequestParam("ujsorszam") int ujsorszam,@RequestParam("keroid") int kid, Model model, @RequestParam("napnev") String name) {
		
		if (ujsorszam<=0) {   // ha HÜLYESÉGET üt be
			model.addAttribute("listanev", name);
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
			model.addAttribute("listanev", name);
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
				model.addAttribute("listanev", name);
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
				model.addAttribute("listanev", name);
				model.addAttribute("daylist", daylistService.findAll());
				model.addAttribute("allcompany", companyService.findAll());
				model.addAttribute("maxsorszam", daylistService.findAll().size());
				model.addAttribute("csakfix", daycompanyService.findDistinctName());
				return "daylistprev";  // EZ MÉG AZ ELŐKÉSZÜLET
			}
			
			
		}
		

		model.addAttribute("listanev", name);
		model.addAttribute("daylist", daylistService.findAll());
		model.addAttribute("allcompany", companyService.findAll());
		model.addAttribute("maxsorszam", daylistService.findAll().size());
		model.addAttribute("csakfix", daycompanyService.findDistinctName());
		return "daylistprev";  // EZ MÉG AZ ELŐKÉSZÜLET
		
	}
	
	@RequestMapping("/changeAddress")
	public String changeaddress(@RequestParam("compid") int compid, Model model,@RequestParam("address") String address,@RequestParam("napnev") String listanev) {
		
		
		
		if (address!=null && !address.isEmpty()) companyService.updateAddress(compid,address);
		
		model.addAttribute("listanev", listanev);
		model.addAttribute("daylist", daylistService.findAll());
		model.addAttribute("allcompany", companyService.findAll());
		model.addAttribute("maxsorszam", daylistService.findAll().size());
		model.addAttribute("csakfix", daycompanyService.findDistinctName());
		return "daylistprev";  // EZ MÉG AZ ELŐKÉSZÜLET
		
	}
	@RequestMapping("/changeComment")
	public String changecomment(@RequestParam("compid") int compid, Model model,@RequestParam("comment") String comment,@RequestParam("napnev") String listanev) {
		
		
		
		if (comment!=null && !comment.isEmpty()) companyService.updateComment(compid,comment);
		
		model.addAttribute("listanev", listanev);
		model.addAttribute("daylist", daylistService.findAll());
		model.addAttribute("allcompany", companyService.findAll());
		model.addAttribute("maxsorszam", daylistService.findAll().size());
		model.addAttribute("csakfix", daycompanyService.findDistinctName());
		return "daylistprev";  // EZ MÉG AZ ELŐKÉSZÜLET
		
	}
	
	@RequestMapping("/changeName")
	public String changename(@RequestParam("compid") int compid, Model model,@RequestParam("name") String name,@RequestParam("napnev") String listanev) {
		
		
		
		if (name!=null && !name.isEmpty()) companyService.updateName(compid,name);
		
		model.addAttribute("listanev", listanev);
		model.addAttribute("daylist", daylistService.findAll());
		model.addAttribute("allcompany", companyService.findAll());
		model.addAttribute("maxsorszam", daylistService.findAll().size());
		model.addAttribute("csakfix", daycompanyService.findDistinctName());
		return "daylistprev";  // EZ MÉG AZ ELŐKÉSZÜLET
		
	}
	
	@RequestMapping("/changeTartozas")
	public String changetartozas(@RequestParam("compid") int compid, Model model,@RequestParam("tartozas") String tartozas,@RequestParam("napnev") String listanev) {
		
		
		
		companyService.updateTartozas(compid,tartozas);
		
		model.addAttribute("listanev", listanev);
		model.addAttribute("daylist", daylistService.findAll());
		model.addAttribute("allcompany", companyService.findAll());
		model.addAttribute("maxsorszam", daylistService.findAll().size());
		model.addAttribute("csakfix", daycompanyService.findDistinctName());
		return "daylistprev";  // EZ MÉG AZ ELŐKÉSZÜLET
		
	}
	
	@RequestMapping("/fooldalnyomtatas")
	public String fixlistafooldalrol(Model model, @RequestParam("napnev") String name) {
		
		daylistService.deleteAll();
		for (Daycompany x:daycompanyService.findSelectedNAme(name)) {
			Daylist tempdaylist = new Daylist();
			tempdaylist.setCompany(x.getCompany());
			tempdaylist.setSorszam(x.getSorszam());
			daylistService.addToDb(tempdaylist);
		}
		
		
		model.addAttribute("listanev", name);
		model.addAttribute("companies", daycompanyService.findSelectedNAme(name));
		return "daylist";
	}
		
	
}
