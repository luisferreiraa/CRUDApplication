package com.example.CRUDApplication.controller;

import com.example.CRUDApplication.model.Category;
import com.example.CRUDApplication.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepo categoryRepo;

    @GetMapping("/getAll")
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            List<Category> categoryList = categoryRepo.findAll();

            if (categoryList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")        // Endpoint para adicionar um novo category
    public ResponseEntity<?> addCategory(@RequestBody Category category) {       // Objeto Category enviado no corpo da requisição
        try {
            if (category.getName() == null || category.getName().trim().isEmpty()) {
                return new ResponseEntity<>("Category name is required", HttpStatus.BAD_REQUEST);
            }
            Category savedCategory = categoryRepo.save(category);
            return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);        // Retorna category guardado com 201 (CREATED)
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving category", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        try {
            Optional<Category> category = categoryRepo.findById(id);

            return category.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<Category> updateCategoryById(@PathVariable Long id, @RequestBody Category newCategoryData) {
        Optional<Category> oldCategoryData = categoryRepo.findById(id);

        if (oldCategoryData.isPresent()) {
            Category updatedCategory = oldCategoryData.get();
            updatedCategory.setName(newCategoryData.getName());

            Category savedCategory = categoryRepo.save(updatedCategory);
            return new ResponseEntity<>(savedCategory, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<HttpStatus> deleteCategoryById(@PathVariable Long id) {
        try {
            categoryRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
