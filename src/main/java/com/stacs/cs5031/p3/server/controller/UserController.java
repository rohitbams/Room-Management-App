package com.stacs.cs5031.p3.server.controller;

import com.stacs.cs5031.p3.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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



}