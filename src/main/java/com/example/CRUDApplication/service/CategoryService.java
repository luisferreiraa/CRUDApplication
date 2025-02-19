package com.example.CRUDApplication.service;

import com.example.CRUDApplication.dto.CategoryWithBooksDTO;
import com.example.CRUDApplication.dto.CategoryRequest;
import com.example.CRUDApplication.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryWithBooksDTO> getAllCategories();
    Optional<CategoryWithBooksDTO> getCategoryById(Long id);
    Category addCategory(CategoryRequest category);
    Category updateCategoryName(Long id, CategoryRequest updateData);
    boolean deleteCategoryById(Long id);
}
