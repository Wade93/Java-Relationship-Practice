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
public class CategoryController {
	private final CategoryService catService;
	private final ProductService proService;

	public CategoryController(CategoryService catService, ProductService proService) {
		this.catService = catService;
		this.proService = proService;
	}

	@RequestMapping("/categories/new")
	public String addNewCategory(@ModelAttribute("category") Category category) {
		return "categories/new_category.jsp";
	}
	
	@RequestMapping(value="/categories/new", method=RequestMethod.POST)
	public String createCategory(@Valid @ModelAttribute("category") Category category, BindingResult result) {
		if (result.hasErrors()) {
            return "categories/new_category.jsp";
        } else {
            catService.createCategory(category);
            return "redirect:/categories/new";
        }
	}
	
	@RequestMapping("/categories/{id}")
	public String viewCategory(@PathVariable("id") Long id, @ModelAttribute("category") Category category, Model model) {
		Category categoryToShow = catService.findCategory(id);
		ArrayList<Product> allProducts = (ArrayList<Product>) proService.findAll();
		ArrayList<Product> availableProducts = new ArrayList<Product>();
		ArrayList<Product> catProducts = new ArrayList<Product>();
		for(int i = 0; i < allProducts.size(); i++){
			if(allProducts.get(i).getCategories().contains(categoryToShow)) {
				catProducts.add(allProducts.get(i));
			}
			else {
				availableProducts.add(allProducts.get(i));
			}
		}
		model.addAttribute("category", categoryToShow);
		model.addAttribute("availableProducts", (List<Product>) availableProducts);
		model.addAttribute("catProducts", (List<Product>) catProducts);
		return "categories/show_category.jsp";
	}
	
	@RequestMapping(value="/categories/{id}", method=RequestMethod.PUT)
	public String addProduct(@PathVariable("id") Long id, @Valid @ModelAttribute("category") Category category, BindingResult result, @RequestParam("productID") Long pID) {
		if (result.hasErrors()) {
			return "categories/show_category.jsp";
		}
		else {
			Category categoryToUpdate = catService.findCategory(id);
			Product productToAdd = proService.findProduct(pID);
			List<Product> catProducts = categoryToUpdate.getProducts();
			catProducts.add(productToAdd);
			catService.updateCategory(categoryToUpdate);
			return "redirect:/categories/"+id;
		}
	}
	
}
