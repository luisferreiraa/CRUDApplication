package com.example.CRUDApplication.service.impl;

import com.example.CRUDApplication.dto.CategoryDTO;
import com.example.CRUDApplication.dto.CategoryNameDTO;
import com.example.CRUDApplication.dto.CategoryWithBooksDTO;
import com.example.CRUDApplication.exception.ObjectNotFoundException;
import com.example.CRUDApplication.exception.RequestDataMissingException;
import com.example.CRUDApplication.model.Category;
import com.example.CRUDApplication.repo.CategoryRepo;
import com.example.CRUDApplication.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Contém a lógica de negócio
// Faz validações relacionadas às regras do domínio
// Interage com repositórios para acessar a base de dados
// Não deve lidar com detalhes de HTTP (como códigos de status)

@Service        // Indica que esta classe é um serviço gerido pelo Spring
public class CategoryServiceImpl implements CategoryService {

    @Autowired      // Injeta automaticamente a dependência do repositório CategoryRepo
    private CategoryRepo categoryRepo;

    @Override
    public List<CategoryDTO> getAllCategories() {
        // Busca todas as categorias da base de dados
        List<Category> categoryList = categoryRepo.findAll();

        // Se não encontrar categorias, lança uma excepção
        if (categoryList.isEmpty()) {
            throw new ObjectNotFoundException("No categories available in the system");
        }

        // Se encontrar, devolve a lista de categorias
        return categoryList.stream()
                .map(CategoryDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryDTO> getCategoryById(Long id) {
        // Busca uma categoria pelo ID
        Optional<Category> categoryDB = categoryRepo.findById(id);

        // Se a cateogira não existir, lança uma excepção
        if (categoryDB.isEmpty()) {
            throw new ObjectNotFoundException("No category found with ID: " + id);
        }

        // Converte Category em CategoryDTO e retorna
        return categoryDB.map(CategoryDTO::new);
    }

    @Override
    public Optional<CategoryWithBooksDTO> getCategoryBooksById(Long categoryId) {
        return categoryRepo.findById(categoryId)
                .map(CategoryWithBooksDTO::new)
                .or(() -> {
                    throw new ObjectNotFoundException("No category found with ID: " + categoryId);
                });
    }

    @Override
    public CategoryDTO addCategory(CategoryNameDTO newCategory) {

        // Cria uma nova entidade a partir do DTO recebido
        Category saveCategory = new Category();
        saveCategory.setName(newCategory.getName());

        // Salva a categoria na base de dados
        Category savedCategory = categoryRepo.save(saveCategory);

        // Converte a entidade persistida para DTO antes de retornar
        return new CategoryDTO(savedCategory);
    }

    @Override
    public CategoryDTO updateCategoryName(Long id, CategoryNameDTO updateData) {

        // Busca a categoria pelo ID e lança uma excepção se não for encontrado
        Category categoryDB = categoryRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Category not found with ID: " + id));

        // Atualiza o nome da categoria
        categoryDB.setName(updateData.getName());

        // Salva a categoria atualizada na base de dados
        Category updatedCategory = categoryRepo.save(categoryDB);

        // Converte a entidade persistida para DTO antes de retornar
        return new CategoryDTO(updatedCategory);
    }

    @Override
    public boolean deleteCategoryById(Long id) {
        // Verifica se a categoria existe antes de remover
        if (categoryRepo.existsById(id)) {
            categoryRepo.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("Category not found with ID: " + id);
    }


}
