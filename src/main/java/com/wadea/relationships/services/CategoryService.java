package com.wadea.relationships.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wadea.relationships.models.Category;
import com.wadea.relationships.models.Product;
import com.wadea.relationships.repositories.CategoryRepository;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepo;
	
	public CategoryService(CategoryRepository categoryRepo) {
		this.categoryRepo = categoryRepo;
	}
	
	public Category createCategory(Category category) {
		return categoryRepo.save(category);
	}
	
	public Category updateCategory(Category category) {
		return categoryRepo.save(category);
	}
	
	public Category findCategory(Long id) {
		Optional<Category> optionalCategory = categoryRepo.findById(id);
        if(optionalCategory.isPresent()) {
            return optionalCategory.get();
        } else {
            return null;
        }
	}
	
	public List<Category> findAll(){
		return categoryRepo.findAll();
	}
	
	public Category addProductToCategory(Category category, Product product) {
		List<Product> products = category.getProducts();
		products.add(product);
		category.setProducts(products);
		return categoryRepo.save(category);
	}
}