package com.example.CRUDApplication.controller;

import com.example.CRUDApplication.dto.CategoryRequest;
import com.example.CRUDApplication.dto.UserDTO;
import com.example.CRUDApplication.dto.UserRequest;
import com.example.CRUDApplication.model.Category;
import com.example.CRUDApplication.model.User;
import com.example.CRUDApplication.repo.UserRepo;
import com.example.CRUDApplication.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> userList = userService.getAllUsers();
            return ResponseEntity.ok(userList);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No users found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving publishers");
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            UserDTO user = userService.getUserById(id)
                    .orElseThrow(() -> new NoSuchElementException("No user found with this ID"));

            return ResponseEntity.ok(user);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found with this ID");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving user");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserRequest user) {
        try {
            User savedUser = userService.addUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User username is required");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user");
        }
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<?> updateUserUserName(@PathVariable Long id, @RequestBody UserRequest updateData) {
        try {
            User updatedUser = userService.updateUserUsername(id, updateData);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user");
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            boolean deletedUser = userService.deleteUserById(id);

            if (deletedUser) {
                return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
        }
    }
}
