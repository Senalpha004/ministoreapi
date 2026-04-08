package com.ministore.ministoreapi.repository;

import com.ministore.ministoreapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
