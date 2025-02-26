package com.example.CRUDApplication.service.impl;

// Contém a lógica de negócio
// Faz validações relacionadas às regras do domínio
// Interage com repositórios para acessar a base de dados
// Não deve lidar com detalhes de HTTP (como códigos de status)

import com.example.CRUDApplication.dto.UserCreateRequestDTO;
import com.example.CRUDApplication.dto.UserDTO;
import com.example.CRUDApplication.dto.UserWithBooksDTO;
import com.example.CRUDApplication.dto.UserUsernameDTO;
import com.example.CRUDApplication.exception.ObjectNotFoundException;
import com.example.CRUDApplication.exception.RecordAlreadyExistsException;
import com.example.CRUDApplication.exception.ResourceNotAvailableException;
import com.example.CRUDApplication.model.Book;
import com.example.CRUDApplication.model.UserRole;
import com.example.CRUDApplication.model.User;
import com.example.CRUDApplication.repo.BookRepo;
import com.example.CRUDApplication.repo.UserRepo;
import com.example.CRUDApplication.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BookRepo bookRepo;

    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        // Busca todos os users da base de dados
        Page<User> userList = userRepo.findAll(pageable);

        // Se não encontrar users, lança excepção
        if (userList.isEmpty()) {
            throw new NoSuchElementException("No users found");
        }
        // Se encontrar, devolve lista de users
        return userList.map(UserDTO::new);
    }

    @Override
    public Optional<UserWithBooksDTO> getUserById(Long id) {
        return userRepo.findById(id)
                .map(UserWithBooksDTO::new)
                .or(() -> {
                    throw new ObjectNotFoundException("No user found with ID: " + id);
                });
    }

    @Override
    public UserDTO addUser(UserCreateRequestDTO newUser) {

        // Cria uma nova entidade User
        User user = new User();
        user.setUsername(newUser.getUsername());

        // Encripta a password antes de salvar
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));

        // Define role (se não vier definido, assume USER por padrão
        user.setRole(newUser.getRole() != null ? newUser.getRole() : UserRole.USER);

        // Salva o user na base de dados
        User savedUser = userRepo.save(user);

        // Converte a entidade persistida para DTO antes de retornar
        return new UserDTO(savedUser);
    }

    @Override
    public UserDTO updateUserUsername(Long id, UserUsernameDTO updateData) {

        // Busca o user pelo ID e lança uma excepção se não for encontrado
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User not found with ID: " + id));

        // Atualiza o username do user
        user.setUsername(updateData.getUsername());

        // Salva o user atualizado na base de dados
        User updatedUser = userRepo.save(user);

        // Converte a entidade persistida para DTO antes de retornar
        return new UserDTO(updatedUser);
    }

    @Override
    public boolean deleteUserById(Long id) {
        // Verifica se o user existe antes de remover
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("User not found with ID: " + id);
    }

//    @Override
//    @Transactional
//    public UserWithBooksDTO addBorrowedBookToUser(Long userId, Long bookId) {
//        User userDB = userRepo.findById(userId)
//                .orElseThrow(() -> new ObjectNotFoundException("User not found with ID: " + userId));
//
//        Book bookDB = bookRepo.findById(bookId)
//                .orElseThrow(() -> new ObjectNotFoundException("Book not found with ID: " + bookId));
//
//        // Verifica se o livro já está na lista
//        if (userDB.getBorrowedBooks().contains(bookDB)) {
//            throw new RecordAlreadyExistsException("Book with ID " + bookId + " is already borrowed by the user with ID: " + userId);
//        }
//
//        // Verifica se ainda existem cópias disponíveis
//        if (bookDB.getAvailableCopies() <= 0) {
//            throw new ResourceNotAvailableException("No copies available for book with ID: " + bookId);
//        }
//
//        // Adiciona o livro à lista de livros emprestados pelo utilizador
//        userDB.getBorrowedBooks().add(bookDB);
//
//        User updatedUser = userRepo.save(userDB);
//
//        return new UserWithBooksDTO(updatedUser);
//    }
//
//    @Override
//    @Transactional
//    public UserWithBooksDTO removeBorrowedBookFromUser(Long userId, Long bookId) {
//        User userDB = userRepo.findById(userId)
//                .orElseThrow(() -> new ObjectNotFoundException("User not found with ID: " + userId));
//
//        Book bookDB = bookRepo.findById(bookId)
//                .orElseThrow(() -> new ObjectNotFoundException("Book not found with ID: " + bookId));
//
//        // Verifica se o livro não está na lista
//        if (!userDB.getBorrowedBooks().contains(bookDB)) {
//            throw new ObjectNotFoundException("Book with ID: " + bookId + " is not borrowed by the user with ID: " + userId);
//        }
//
//        userDB.getBorrowedBooks().remove(bookDB);
//
//        User updatedUser = userRepo.save(userDB);
//
//        return new UserWithBooksDTO(updatedUser);
//    }


}
