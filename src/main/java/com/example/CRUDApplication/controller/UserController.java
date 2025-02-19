package com.example.CRUDApplication.controller;

import com.example.CRUDApplication.dto.UserDTO;
import com.example.CRUDApplication.dto.UserRequest;
import com.example.CRUDApplication.model.User;
import com.example.CRUDApplication.repo.UserRepo;
import com.example.CRUDApplication.service.UserService;
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
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserDTO>> getUserById(@PathVariable Long id) {
        Optional<UserDTO> user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/")
    public ResponseEntity<?> addUser(@RequestBody UserRequest user) {
        User savedUser = userService.addUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserUserName(@PathVariable Long id, @RequestBody UserRequest updateData) {
        User updatedUser = userService.updateUserUsername(id, updateData);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        boolean deletedUser = userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{userId}/books/{bookId}")
    public ResponseEntity<User> addBorrowedBookToUser(@PathVariable Long userId, @PathVariable Long bookId) {
        User updatedUser = userService.addBorrowedBookToUser(userId, bookId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("{userId}/books/{bookId}")
    public ResponseEntity<User> removeBorrowedBookFromUser(@PathVariable Long userId, @PathVariable Long bookId) {
        User updatedUser = userService.removeBorrowedBookFromUser(userId, bookId);
        return ResponseEntity.ok(updatedUser);
    }


}
