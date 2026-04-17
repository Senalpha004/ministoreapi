package com.ministore.ministoreapi.service;


import com.ministore.ministoreapi.dto.CategoryRequest;
import com.ministore.ministoreapi.exception.NotFoundException;
import com.ministore.ministoreapi.model.Category;
import com.ministore.ministoreapi.repository.CategoryRepository;
import com.ministore.ministoreapi.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository catRepo;
    private final ProductRepository prodRepo;

    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.catRepo = categoryRepository;
        this.prodRepo = productRepository;
    }

    public List<Category> getAll(){
        return catRepo.findAll();
    }

    public Category getById(Long id){
        return catRepo.findById(id).orElseThrow(
                () -> new NotFoundException("Category not found!")
        );
    }

    public Category create(CategoryRequest req){

        if (catRepo.existsByNameIgnoreCase(req.getName().trim())) {
            throw new IllegalArgumentException("Category name already exists.");
        }

        Category cat = new Category();
        cat.setName(req.getName().trim());
        return catRepo.save(cat);
    }

    public Category update(Long id, CategoryRequest req){
        Category existing = getById(id);

        if (catRepo.existsByNameIgnoreCase(req.getName().trim()) && catRepo.existsById(id)) {
            throw new IllegalArgumentException("Category name already exists!");
        }

        existing.setName(req.getName().trim());
        return catRepo.save(existing);
    }

    public void delete(Long id){
        Category existing = getById(id);

        if (prodRepo.existsByCategory(existing)){
            throw new IllegalArgumentException("Category cannot be deleted. Products exists in it!");
        }
        catRepo.delete(existing);
    }

}
