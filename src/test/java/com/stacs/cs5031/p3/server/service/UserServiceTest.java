package com.stacs.cs5031.p3.server.service;

import com.stacs.cs5031.p3.server.exception.UserAlreadyExistsException;
import com.stacs.cs5031.p3.server.exception.UserNotFoundException;
import com.stacs.cs5031.p3.server.model.User;
import com.stacs.cs5031.p3.server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User("Test User", "testuser", "password");
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        User result = userService.getUserById(1);
        assertEquals(testUser, result);
        verify(userRepository).findById(1);
    }

    @Test
    void getUserById_ShouldReturnEmpty_WhenUserDoesNotExist() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1));
        verify(userRepository).findById(1);
    }

    @Test
    void getUserByUsername_ShouldReturnUser_WhenUserExists() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        User result = userService.getUserByUsername("testuser");
        assertEquals(testUser, result);
        verify(userRepository).findByUsername("testuser");
    }

    @Test
    void registerUser_ShouldSaveAndReturnUser_WhenUsernameNotTaken() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(userRepository.save(testUser)).thenReturn(testUser);
        User result = userService.registerUser(testUser);
        assertEquals(testUser, result);
        verify(userRepository).findByUsername("testuser");
        verify(userRepository).save(testUser);
    }

    @Test
    void registerUser_ShouldThrowException_WhenUsernameAlreadyTaken() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(testUser));
        verify(userRepository).findByUsername("testuser");
        verify(userRepository, never()).save(testUser);
    }

    @Test
    void isUsernameTaken_ShouldReturnTrue_WhenUsernameExists() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        boolean result = userService.isUsernameTaken("testuser");
        assertTrue(result);
        verify(userRepository).findByUsername("testuser");
    }

    @Test
    void isUsernameTaken_ShouldReturnFalse_WhenUsernameDoesNotExist() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        boolean result = userService.isUsernameTaken("testuser");
        assertFalse(result);
        verify(userRepository).findByUsername("testuser");
    }

    @Test
    void deleteUser_ShouldCallRepositoryDeleteById() {
        doNothing().when(userRepository).deleteById(1);
        userService.deleteUser(1);
        verify(userRepository).deleteById(1);
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        User newUser= new User("New User", "newuser", "password");
        List<User> userList = Arrays.asList(testUser, newUser);
        when(userRepository.findAll()).thenReturn(userList);
        List<User> result = userService.getAllUsers();
        assertEquals(2, result.size());
        assertEquals(userList, result);
        verify(userRepository).findAll();
    }
}
