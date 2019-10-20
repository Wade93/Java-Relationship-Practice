package com.wadea.relationships.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wadea.relationships.models.Product;
import com.wadea.relationships.models.Category;
import com.wadea.relationships.repositories.ProductRepository;

@Service
public class ProductService {
	private final ProductRepository productRepo;
	
	public ProductService(ProductRepository productRepo) {
		this.productRepo = productRepo;
	}
	
	public Product createProduct(Product product) {
		return productRepo.save(product);
	}
	
	public Product updateProduct(Product product) {
		return productRepo.save(product);
	}
	
	public Product findProduct(Long id) {
		Optional<Product> optionalProduct = productRepo.findById(id);
        if(optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            return null;
        }
	}
	
	public List<Product> findAll(){
		return productRepo.findAll();
	}
	
	public Product addCategoryToProduct(Product product, Category category) {
		List<Category> categories = product.getCategories();
		categories.add(category);
		product.setCategories(categories);
		return productRepo.save(product);
	}
	
}
