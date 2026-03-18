package com.ministore.ministoreapi.repository;

import com.ministore.ministoreapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>{

    //this class handles the database operations like the CRUD operations by accessing the product class



}
