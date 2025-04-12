package com.stacs.cs5031.p3.server.controller;

import com.stacs.cs5031.p3.server.dto.UserDto;
import com.stacs.cs5031.p3.server.exception.UserAlreadyExistsException;
import com.stacs.cs5031.p3.server.mapper.UserDtoMapper;
import com.stacs.cs5031.p3.server.model.User;
import com.stacs.cs5031.p3.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * The UserController class.
 * This class handles all CRUD operations for users
 * like registering, deleting, listing,
 * and simple logging in and out.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User savedUser = userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(UserDtoMapper.mapToDTO(savedUser));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDto> userDtos = users.stream()
                .map(UserDtoMapper::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

}