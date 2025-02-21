package com.example.CRUDApplication.service;

import com.example.CRUDApplication.dto.UserDTO;
import com.example.CRUDApplication.dto.UserUsernameDTO;
import com.example.CRUDApplication.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<UserDTO> getUserById(Long id);
    User addUser(UserUsernameDTO user);
    User updateUserUsername(Long id, UserUsernameDTO updateData);
    boolean deleteUserById(Long id);
    User addBorrowedBookToUser(Long userId, Long bookId);

    User removeBorrowedBookFromUser(Long userId, Long bookId);

}
