package com.example.CRUDApplication.service.impl;

import com.example.CRUDApplication.dto.AuthorDTO;
import com.example.CRUDApplication.dto.AuthorRequest;
import com.example.CRUDApplication.model.Author;
import com.example.CRUDApplication.model.Book;
import com.example.CRUDApplication.repo.AuthorRepo;
import com.example.CRUDApplication.repo.BookRepo;
import com.example.CRUDApplication.service.AuthorService;
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
public class AuthorServiceImpl implements AuthorService {

    @Autowired      // Injeta automaticamente a dependência do repositório AuthorRepo
    private AuthorRepo authorRepo;

    @Autowired      // Injeta automaticamente a dependência do repositório BookRepo
    private BookRepo bookRepo;

    @Override
    public List<Author> getAllAuthors() {
        // Busca todos os autores da base de dados
        List<Author> authorsList = authorRepo.findAll();

        // Se não encontrar autores, lança uma excepção
        if (authorsList.isEmpty()) {
            throw new NoSuchElementException("No authors found");
        }
        // Se encontrar, devolve a lista de autores
        return authorsList;
    }

    @Override
    public Optional<AuthorDTO> getAuthorById(Long id) {
        // Busca um autor pelo ID
        Optional<Author> authorDB = authorRepo.findById(id);

        // Se o autor não existir, lança excepção
        if (authorDB.isEmpty()) {
            throw new NoSuchElementException("No author found");
        }

        // Converte Author em AuthorDTO e retorna
        return authorDB.map(AuthorDTO::new);
    }

    @Override
    public Author addAuthor(AuthorRequest authorDTO) {
        // Valida se o nome do autor foi fornecido
        if (authorDTO.getName() == null || authorDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Author name is required");
        }

        // Cria uma nova entidade a partir do DTO recebido
        Author author = new Author();
        author.setName(authorDTO.getName());

        // Salva o autor na base de dados e retorna a entidade persistida
        return authorRepo.save(author);
    }

    @Override
    public Author updateAuthorName(Long id, AuthorRequest updateData) {
        // Valida se o nome do autor foi fornecido
        if (updateData.getName() == null || updateData.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }

        // Busca o autor pelo ID e lança excepção se não for encontrado
        Author author = authorRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Author not found"));

        // Atualiza o nome do autor
        author.setName(updateData.getName());

        // Salva e retorna o autor atualizado
        return authorRepo.save(author);
    }

    @Override
    public boolean deleteAuthorById(Long id) {
        // Verifica se o autor existe antes de remove-lo
        if (authorRepo.existsById(id)) {
            authorRepo.deleteById(id);
            return true;        // Retorna true indicando que a remoção foi bem-sucedida
        }
        throw new NoSuchElementException("Author not found");       // Lança excepção se autor não foi encontado
    }
}
