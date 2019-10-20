package com.wadea.relationships.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wadea.relationships.models.Category;
import com.wadea.relationships.models.Product;
import com.wadea.relationships.services.CategoryService;
import com.wadea.relationships.services.ProductService;

@Controller
public class ProductController {
	private final ProductService proService;
	private final CategoryService catService;
	
	public ProductController(ProductService proService, CategoryService catService) {
		this.proService = proService;
		this.catService = catService;
	}
	
	@RequestMapping("/products/new")
	public String addNewProduct(@ModelAttribute("product") Product product) {
		return "products/new_product.jsp";
	}
	
	@RequestMapping(value="/products/new", method=RequestMethod.POST)
	public String createProduct(@Valid @ModelAttribute("product") Product product, BindingResult result) {
		if (result.hasErrors()) {
            return "products/new_product.jsp";
        } else {
           proService.createProduct(product);
            return "redirect:/products/new";
        }
	}
	
	@RequestMapping("/products/{id}")
	public String viewProduct(@PathVariable("id") Long id, @ModelAttribute Product product, Model model) {
		Product productToShow = proService.findProduct(id);
		ArrayList<Category> allCategories = (ArrayList<Category>) catService.findAll();
		ArrayList<Category> availableCategories = new ArrayList<Category>();
		ArrayList<Category> productCats = new ArrayList<Category>();
		for(int i = 0; i < allCategories.size(); i++){
			if(allCategories.get(i).getProducts().contains(productToShow)) {
				productCats.add(allCategories.get(i));
			}
			else {
				availableCategories.add(allCategories.get(i));
			}
		}
		model.addAttribute("product", productToShow);
		model.addAttribute("availableCategories",(List<Category>) availableCategories);
		model.addAttribute("productCats",(List<Category>) productCats);
		return "products/show_product.jsp";
	}
	
	@RequestMapping(value="/products/{id}", method=RequestMethod.PUT)
	public String addCategory(@PathVariable("id") Long id, @Valid @ModelAttribute("product") Product product, BindingResult result, @RequestParam("categoryID") Long catID) {
		if (result.hasErrors()) {
			return "products/show_product.jsp";
		}
		else {
			Product productToUpdate = proService.findProduct(id);
			Category categoryToAdd = catService.findCategory(catID);
			List<Category> productCats = productToUpdate.getCategories();
			productCats.add(categoryToAdd);
			proService.updateProduct(productToUpdate);
			return "redirect:/products/"+id;
		}
	}
	
}
