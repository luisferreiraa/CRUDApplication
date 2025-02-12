package com.example.CRUDApplication.service.impl;

import com.example.CRUDApplication.dto.CategoryDTO;
import com.example.CRUDApplication.dto.CategoryRequest;
import com.example.CRUDApplication.model.Category;
import com.example.CRUDApplication.repo.CategoryRepo;
import com.example.CRUDApplication.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

// Contém a lógica de negócio
// Faz validações relacionadas às regras do domínio
// Interage com repositórios para acessar a base de dados
// Não deve lidar com detalhes de HTTP (como códigos de status)

@Service        // Indica que esta classe é um serviço gerido pelo Spring
public class CategoryServiceImpl implements CategoryService {

    @Autowired      // Injeta automaticamente a dependência do repositório CategoryRepo
    private CategoryRepo categoryRepo;

    @Override
    public List<Category> getAllCategories() {
        // Busca todas as categorias da base de dados
        List<Category> categoryList = categoryRepo.findAll();

        // Se não encontrar categorias, lança uma excepção
        if (categoryList.isEmpty()) {
            throw new NoSuchElementException("No categories found");
        }

        // Se encontrar, devolve a lista de categorias
        return categoryList;
    }

    @Override
    public Optional<CategoryDTO> getCategoryById(Long id) {
        // Busca uma categoria pelo ID
        Optional<Category> categoryDB = categoryRepo.findById(id);

        // Se a cateogira não existir, lança uma excepção
        if (categoryDB.isEmpty()) {
            throw new NoSuchElementException("No category found");
        }

        // Converte Category em CategoryDTO e retorna
        return categoryDB.map(CategoryDTO::new);
    }

    @Override
    public Category addCategory(CategoryRequest newCategory) {
        // Valida se o nome da categoria foi fornecido
        if (newCategory.getName() == null || newCategory.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name is required");
        }

        // Cria uma nova entidade a partir do DTO recebido
        Category saveCategory = new Category();
        saveCategory.setName(newCategory.getName());

        // Salva a categoria na base de dados e retorna a entidade persistida
        return categoryRepo.save(saveCategory);
    }

    @Override
    public Category updateCategoryName(Long id, CategoryRequest updateData) {
        // Valida se o nome da categorua foi fornecido
        if (updateData.getName() == null || updateData.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }

        // Busca a categoria pelo ID e lança uma excepção se não for encontrado
        Category categoryDB = categoryRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found"));

        // Atualiza o nome da categoria
        categoryDB.setName(updateData.getName());

        // Salva e retorna a categoria atualizada
        return categoryRepo.save(categoryDB);
    }

    @Override
    public boolean deleteCategoryById(Long id) {
        // Verifica se a categoria existe antes de remover
        if (categoryRepo.existsById(id)) {
            categoryRepo.deleteById(id);
            return true;
        }
        throw new NoSuchElementException("Category not found");
    }


}
