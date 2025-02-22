package com.example.CRUDApplication.service.impl;

import com.example.CRUDApplication.dto.AuthorDTO;
import com.example.CRUDApplication.dto.AuthorWithBooksDTO;
import com.example.CRUDApplication.dto.AuthorNameDTO;
import com.example.CRUDApplication.exception.ObjectNotFoundException;
import com.example.CRUDApplication.exception.RequestDataMissingException;
import com.example.CRUDApplication.model.Author;
import com.example.CRUDApplication.repo.AuthorRepo;
import com.example.CRUDApplication.repo.BookRepo;
import com.example.CRUDApplication.service.AuthorService;
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
public class AuthorServiceImpl implements AuthorService {

    @Autowired      // Injeta automaticamente a dependência do repositório AuthorRepo
    private AuthorRepo authorRepo;

    @Autowired      // Injeta automaticamente a dependência do repositório BookRepo
    private BookRepo bookRepo;

    @Override
    public List<AuthorDTO> getAllAuthors() {
        // Busca todos os autores da base de dados
        List<Author> authorsList = authorRepo.findAll();

        // Se não encontrar autores, lança uma excepção
        if (authorsList.isEmpty()) {
            throw new ObjectNotFoundException("No authors available in the system");
        }
        // Se encontrar, devolve a lista de autores
        return authorsList.stream()
                .map(AuthorDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorWithBooksDTO> getAuthorById(Long id) {
        return authorRepo.findById(id)
                .map(AuthorWithBooksDTO::new)
                .or(() -> {
                    throw new ObjectNotFoundException("No author found with ID: " + id);
                });
    }

    @Override
    public AuthorDTO addAuthor(AuthorNameDTO authorRequest) {

        // Cria uma nova entidade a partir do DTO recebido
        Author author = new Author();
        author.setName(authorRequest.getName());

        // Salva o autor na base de dados
        Author savedAuthor = authorRepo.save(author);

        // Converte a entidade persistida para DTO antes de retornar
        return new AuthorDTO(savedAuthor);
    }

    @Override
    public AuthorDTO updateAuthorName(Long id, AuthorNameDTO updateData) {

        // Busca o autor pelo ID e lança excepção se não for encontrado
        Author author = authorRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Author not found with ID: " + id));

        // Atualiza o nome do autor
        author.setName(updateData.getName());

        // Salva o author atualizado na base de dados
        Author updatedAuthor = authorRepo.save(author);

        // Converte a entidade persistida para DTO antes de retornar
        return new AuthorDTO(updatedAuthor);
    }

    @Override
    public boolean deleteAuthorById(Long id) {
        // Verifica se o autor existe antes de remove-lo
        if (authorRepo.existsById(id)) {
            authorRepo.deleteById(id);
            return true;        // Retorna true indicando que a remoção foi bem-sucedida
        }
        throw new ObjectNotFoundException("Author not found with ID: " + id);       // Lança excepção se autor não foi encontado
    }
}
