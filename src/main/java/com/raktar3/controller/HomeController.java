package com.raktar3.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raktar3.entities.Employe;
import com.raktar3.entities.Product;
import com.raktar3.service.ProductService;

@Controller
public class HomeController {

	
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
		return "beerkezes";
	}
	
	@RequestMapping("/newcompany")
	public String newcompany(Model model) {
		model.addAttribute("futar", productService.findAllEmploye());
		return "newCompany";
	}
	
}
