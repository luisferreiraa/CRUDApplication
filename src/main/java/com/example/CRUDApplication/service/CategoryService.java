package com.example.CRUDApplication.service;

import com.example.CRUDApplication.dto.CategoryDTO;
import com.example.CRUDApplication.dto.CategoryWithBooksDTO;
import com.example.CRUDApplication.dto.CategoryNameDTO;
import com.example.CRUDApplication.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    Optional<CategoryDTO> getCategoryById(Long id);
    Optional<CategoryWithBooksDTO> getCategoryBooksById(Long id);
    CategoryDTO addCategory(CategoryNameDTO category);
    CategoryDTO updateCategoryName(Long id, CategoryNameDTO newCategoryName);
    boolean deleteCategoryById(Long id);
}
