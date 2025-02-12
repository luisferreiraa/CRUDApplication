package com.example.CRUDApplication.service.impl;

// Contém a lógica de negócio
// Faz validações relacionadas às regras do domínio
// Interage com repositórios para acessar a base de dados
// Não deve lidar com detalhes de HTTP (como códigos de status)

import com.example.CRUDApplication.dto.UserDTO;
import com.example.CRUDApplication.dto.UserRequest;
import com.example.CRUDApplication.model.User;
import com.example.CRUDApplication.repo.UserRepo;
import com.example.CRUDApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public List<User> getAllUsers() {
        // Busca todos os users da base de dados
        List<User> userList = userRepo.findAll();

        // Se não encontrar users, lança excepção
        if (userList.isEmpty()) {
            throw new NoSuchElementException("No users found");
        }
        // Se encontrar, devolve lista de users
        return userList;
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        // Busca um user pelo ID
        Optional<User> userDB = userRepo.findById(id);

        // Se o user não existir, lança uma excepção
        if (userDB.isEmpty()) {
            throw new NoSuchElementException("No user found");
        }
        // Converte User em UserDTO e retorna
        return userDB.map(UserDTO::new);
    }

    @Override
    public User addUser(UserRequest newUser) {
        // Valida se o username do user foi fornecido
        if (newUser.getUsername() == null || newUser.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("User username is required");
        }

        // Cria uma nova entidade a partir do DTO fornecido
        User saveUser = new User();
        saveUser.setUsername(newUser.getUsername());

        // Salva o user na base de dados e retorna a entidade persistida
        return userRepo.save(saveUser);
    }

    @Override
    public User updateUserUsername(Long id, UserRequest updateData) {
        // Valida se o username foi fornecido
        if (updateData.getUsername() == null || updateData.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }

        // Busca o user pelo ID e lança uma excepção se não for encontrado
        User userDB = userRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        // Atualiza o username do user
        userDB.setUsername(updateData.getUsername());

        // Salva e retorna o user atualizado
        return userRepo.save(userDB);
    }

    @Override
    public boolean deleteUserById(Long id) {
        // Verifica se o user existe antes de remover
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
            return true;
        }
        throw new NoSuchElementException("User not found");
    }



}
