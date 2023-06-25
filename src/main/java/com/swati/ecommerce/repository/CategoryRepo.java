package com.swati.ecommerce.repository;

import com.swati.ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category, String> {
    List<Category> findByTitleContaining(String keywords);
}
