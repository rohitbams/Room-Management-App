package com.stacs.cs5031.p3.server.service;

import com.stacs.cs5031.p3.server.model.User;
import com.stacs.cs5031.p3.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * The UserService class.
 * This class implements user management operations like
 * registering users, checking is username is taken, finding users by IDs,
 * deleting users, and listing all users.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // register user
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    // check for pre-registered username
    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    // find user by ID
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    // find user by username
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // delete user
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    // list all registered users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //
}
