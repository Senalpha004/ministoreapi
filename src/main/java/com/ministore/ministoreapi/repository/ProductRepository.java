package com.ministore.ministoreapi.repository;

import com.ministore.ministoreapi.model.Category;
import com.ministore.ministoreapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>{

    boolean existsByCategory(Category category);
}
