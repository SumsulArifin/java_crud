package com.example.crud_mybites.controller;

import com.example.crud_mybites.dto.UserWithRole;
import com.example.crud_mybites.model.User;
import com.example.crud_mybites.model.UserRole;
import com.example.crud_mybites.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {

            this.userService.create(user);
            return new  ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // Log to console
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

//    @PostMapping("/users")
//    public ResponseEntity<?> createUserWithRole(@RequestBody UserWithRole userWithRole) throws Exception {
//        userService.createUserWithRole(userWithRole);
//        return ResponseEntity.ok("User and Role created successfully");
//    }

//    @PostMapping("/users")
//    public ResponseEntity<String> createUserWithRole(@RequestBody User user, UserRole role ) {
//        try {
//            userService.createUserWithRole(user, role );
//            return ResponseEntity.ok("User and Role created successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error creating user and role: " + e.getMessage());
//        }
//    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User user) {
        user.setId(id);  // Make sure the user ID is set from the path
        userService.updateUser(user);
        return ResponseEntity.ok("User updated successfully");
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        User user = userService.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}
