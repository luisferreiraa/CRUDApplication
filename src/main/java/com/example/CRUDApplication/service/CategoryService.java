package com.example.CRUDApplication.service;

import com.example.CRUDApplication.dto.CategoryDTO;
import com.example.CRUDApplication.dto.CategoryWithBooksDTO;
import com.example.CRUDApplication.dto.CategoryNameDTO;
import com.example.CRUDApplication.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Page<CategoryDTO> getAllCategories(Pageable pageable);
    Optional<CategoryDTO> getCategoryById(Long id);
    Optional<CategoryWithBooksDTO> getCategoryBooksById(Long id);
    CategoryDTO addCategory(CategoryNameDTO category);
    CategoryDTO updateCategoryName(Long id, CategoryNameDTO newCategoryName);
    boolean deleteCategoryById(Long id);
}
