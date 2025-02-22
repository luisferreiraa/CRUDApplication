package com.example.CRUDApplication.controller;

import com.example.CRUDApplication.dto.CategoryDTO;
import com.example.CRUDApplication.dto.CategoryWithBooksDTO;
import com.example.CRUDApplication.dto.CategoryNameDTO;
import com.example.CRUDApplication.model.Category;
import com.example.CRUDApplication.repo.CategoryRepo;
import com.example.CRUDApplication.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Trata requisições HTTP (recebe e responde)
// Converte dados da requisição para o formato adequado
// Chama os serviços para processar a lógica de negócio
// Retorna as respostas HTTP apropriadas

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categoryList = categoryService.getAllCategories();
        return ResponseEntity.ok(categoryList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CategoryDTO>> getCategoryById(@PathVariable Long id) {
        Optional<CategoryDTO> category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<Optional<CategoryWithBooksDTO>> getCategoryWithBooksById(@PathVariable Long id) {
        Optional<CategoryWithBooksDTO> category = categoryService.getCategoryBooksById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping("/")
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryNameDTO category) {
        CategoryDTO savedCategory = categoryService.addCategory(category);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategoryName(@PathVariable Long id, @Valid @RequestBody CategoryNameDTO updateData) {
        CategoryDTO updatedCategory = categoryService.updateCategoryName(id, updateData);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
        boolean deletedCategory = categoryService.deleteCategoryById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
