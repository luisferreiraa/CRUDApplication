package com.example.CRUDApplication.service;

import com.example.CRUDApplication.dto.UserCreateRequestDTO;
import com.example.CRUDApplication.dto.UserDTO;
import com.example.CRUDApplication.dto.UserWithBooksDTO;
import com.example.CRUDApplication.dto.UserUsernameDTO;
import com.example.CRUDApplication.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Page<UserDTO> getAllUsers(Pageable pageable);
    Optional<UserWithBooksDTO> getUserById(Long id);
    UserDTO addUser(UserCreateRequestDTO user);
    UserDTO updateUserUsername(Long id, UserUsernameDTO updateData);
    boolean deleteUserById(Long id);
//    UserWithBooksDTO addBorrowedBookToUser(Long userId, Long bookId);
//    UserWithBooksDTO removeBorrowedBookFromUser(Long userId, Long bookId);

}
