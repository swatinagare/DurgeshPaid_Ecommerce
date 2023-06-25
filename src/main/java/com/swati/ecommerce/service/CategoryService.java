package com.swati.ecommerce.service;

import com.swati.ecommerce.dao.CategoryDto;
import com.swati.ecommerce.payloads.PageableResponse;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto ,String categoryId);

    PageableResponse<CategoryDto> getAllCategory(int pageNo, int pageSize, String sortBy, String sortDir);

    CategoryDto getSingleCategory(String categoryId);

    void deleteCategory(String categoryId);

    List<CategoryDto> searchCategory(String keywords);


}
