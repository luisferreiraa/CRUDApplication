package com.example.CRUDApplication.controller;

import com.example.CRUDApplication.dto.CategoryDTO;
import com.example.CRUDApplication.dto.CategoryRequest;
import com.example.CRUDApplication.model.Category;
import com.example.CRUDApplication.repo.CategoryRepo;
import com.example.CRUDApplication.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCategories() {
        try {
            List<Category> categoryList = categoryService.getAllCategories();
            return ResponseEntity.ok(categoryList);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving categories");
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        try {
            CategoryDTO category = categoryService.getCategoryById(id)
                    .orElseThrow(() -> new NoSuchElementException("No category found with this ID"));

            return ResponseEntity.ok(category);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving category");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody CategoryRequest category) {
        try {
            Category savedCategory = categoryService.addCategory(category);
            return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating category");
        }
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<?> updateCategoryName(@PathVariable Long id, @RequestBody CategoryRequest updateData) {
        try {
            Category updatedCategory = categoryService.updateCategoryName(id, updateData);
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating category");
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
        try {
            boolean deletedCategory = categoryService.deleteCategoryById(id);

            if (deletedCategory) {
                return ResponseEntity.status(HttpStatus.OK).body("Category deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting category");
        }
    }
}
