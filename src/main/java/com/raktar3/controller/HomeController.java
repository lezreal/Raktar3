package com.raktar3.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raktar3.entities.Company;
import com.raktar3.entities.Employe;
import com.raktar3.entities.Product;
import com.raktar3.entities.Stock;
import com.raktar3.service.EmployeService;
import com.raktar3.service.ProductService;
import com.raktar3.service.StockService;

@Controller
public class HomeController {

	@Autowired
	EmployeService employeService;
	
	@Autowired
	StockService stockService;
	
	@Autowired
	ProductService productService;
	
	@RequestMapping("/")
	public String home() {
		return "index";
	}
	
	@RequestMapping("/newproduct")
	public String newProduct(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("product_list", productService.findAll());
		return "newProduct";
	}
	
	@RequestMapping("/productlist")
	public String productList(Model model) {
		model.addAttribute("productlist", productService.findAll());
		ArrayList<Long> boxi=new ArrayList<Long>();
		model.addAttribute("boxok", boxi);
		return "productList";
	}
	
	@RequestMapping("/newemp")
	public String newEmp(Model model) {
		model.addAttribute("newemp", new Employe());
		model.addAttribute("emplist", productService.findAllEmploye());
		return "newEmp";
	}
	
	@RequestMapping("/beerkezes")
	public String beerkezes(Model model) {
		model.addAttribute("stock", new Stock());
		model.addAttribute("emps", employeService.findAllEmploye());
		model.addAttribute("products", productService.findAll());
		return "beerkezes";
	}
	
	@RequestMapping("/newcompany")
	public String newcompany(Model model) {
		model.addAttribute("futar", productService.findAllEmploye());
		model.addAttribute("company", new Company());
		return "newCompany";
	}
	
	@RequestMapping("/companylist")
	public String companylist(Model model) {
		model.addAttribute("companies", productService.findAllCompany());
		return "companyList";
	}
	
}
