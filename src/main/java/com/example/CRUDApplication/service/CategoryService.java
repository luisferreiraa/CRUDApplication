package com.example.CRUDApplication.service;

import com.example.CRUDApplication.dto.CategoryWithBooksDTO;
import com.example.CRUDApplication.dto.CategoryNameDTO;
import com.example.CRUDApplication.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryNameDTO> getAllCategories();
    Optional<CategoryNameDTO> getCategoryById(Long id);
    Optional<CategoryWithBooksDTO> getCategoryBooksById(Long id);
    Category addCategory(CategoryNameDTO category);
    Category updateCategoryName(Long id, CategoryNameDTO newCategoryName);
    boolean deleteCategoryById(Long id);
}
