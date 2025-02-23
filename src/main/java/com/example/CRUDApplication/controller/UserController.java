package com.example.CRUDApplication.controller;

import com.example.CRUDApplication.dto.UserDTO;
import com.example.CRUDApplication.dto.UserWithBooksDTO;
import com.example.CRUDApplication.dto.UserUsernameDTO;
import com.example.CRUDApplication.model.User;
import com.example.CRUDApplication.repo.UserRepo;
import com.example.CRUDApplication.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserWithBooksDTO>> getUserById(@PathVariable Long id) {
        Optional<UserWithBooksDTO> user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/")
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserUsernameDTO user) {
        UserDTO savedUser = userService.addUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserUserName(@PathVariable Long id, @Valid @RequestBody UserUsernameDTO updateData) {
        UserDTO updatedUser = userService.updateUserUsername(id, updateData);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        boolean deletedUser = userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{userId}/books/{bookId}")
    public ResponseEntity<UserWithBooksDTO> addBorrowedBookToUser(@PathVariable Long userId, @PathVariable Long bookId) {
        UserWithBooksDTO updatedUser = userService.addBorrowedBookToUser(userId, bookId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("{userId}/books/{bookId}")
    public ResponseEntity<UserWithBooksDTO> removeBorrowedBookFromUser(@PathVariable Long userId, @PathVariable Long bookId) {
        UserWithBooksDTO updatedUser = userService.removeBorrowedBookFromUser(userId, bookId);
        return ResponseEntity.ok(updatedUser);
    }


}
