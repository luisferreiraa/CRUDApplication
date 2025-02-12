package com.example.CRUDApplication.service;

import com.example.CRUDApplication.dto.UserDTO;
import com.example.CRUDApplication.dto.UserRequest;
import com.example.CRUDApplication.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<UserDTO> getUserById(Long id);
    User addUser(UserRequest user);
    User updateUserUsername(Long id, UserRequest updateData);
    boolean deleteUserById(Long id);
}
