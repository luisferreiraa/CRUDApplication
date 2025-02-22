package com.example.CRUDApplication.service;

import com.example.CRUDApplication.dto.UserDTO;
import com.example.CRUDApplication.dto.UserWithBooksDTO;
import com.example.CRUDApplication.dto.UserUsernameDTO;
import com.example.CRUDApplication.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUsers();
    Optional<UserWithBooksDTO> getUserById(Long id);
    UserDTO addUser(UserUsernameDTO user);
    UserDTO updateUserUsername(Long id, UserUsernameDTO updateData);
    boolean deleteUserById(Long id);
    UserWithBooksDTO addBorrowedBookToUser(Long userId, Long bookId);
    UserWithBooksDTO removeBorrowedBookFromUser(Long userId, Long bookId);

}
