package com.stacs.cs5031.p3.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stacs.cs5031.p3.server.dto.LoginRequest;
import com.stacs.cs5031.p3.server.exception.UserAlreadyExistsException;
import com.stacs.cs5031.p3.server.exception.UserNotFoundException;
import com.stacs.cs5031.p3.server.model.User;
import com.stacs.cs5031.p3.server.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User testUser;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        testUser = new User("John Doe", "johndoe", "password123");
    }

    @Test
    void shouldRegisterNewUser_whenUsernameNotTaken() throws Exception {
        when(userService.registerUser(any(User.class))).thenReturn(testUser);
        User newUser = new User("New User", "newuser", "password");
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated());
        verify(userService).registerUser(any(User.class));
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        User user2 = new User("Jane Doe", "janedoe", "password456");
        when(userService.getAllUsers()).thenReturn(Arrays.asList(testUser, user2));
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
        verify(userService).getAllUsers();
    }

    @Test
    void shouldReturnUserById_whenUserExists() throws Exception {
        when(userService.getUserById(1)).thenReturn(testUser);
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk());
        verify(userService).getUserById(1);
    }

    @Test
    void shouldReturnNotFound_whenUserIdDoesNotExist() throws Exception {
        when(userService.getUserById(1000)).thenThrow(new UserNotFoundException(1000));
        mockMvc.perform(get("/api/users/1000"))
                .andExpect(status().isNotFound());
        verify(userService).getUserById(1000);
    }

    @Test
    void shouldReturnUserByUsername_whenUserExists() throws Exception {
        when(userService.getUserByUsername("johndoe")).thenReturn(testUser);
        mockMvc.perform(get("/api/users/by-username/johndoe"))
                .andExpect(status().isOk());
        verify(userService).getUserByUsername("johndoe");
    }

    @Test
    void shouldReturnNotFound_whenUsernameDoesNotExist() throws Exception {
        when(userService.getUserByUsername("unknown")).thenThrow(new UserNotFoundException("unknown"));
        mockMvc.perform(get("/api/users/by-username/unknown"))
                .andExpect(status().isNotFound());
        verify(userService).getUserByUsername("unknown");
    }

    @Test
    void shouldDeleteUser_whenUserExists() throws Exception {
        when(userService.getUserById(1)).thenReturn(testUser);
        doNothing().when(userService).deleteUser(1);
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
        verify(userService).getUserById(1);
        verify(userService).deleteUser(1);
    }

    @Test
    void shouldReturnNotFound_whenDeletingNonExistentUser() throws Exception {
        when(userService.getUserById(1000)).thenThrow(new UserNotFoundException(1000));
        mockMvc.perform(delete("/api/users/1000"))
                .andExpect(status().isNotFound());
        verify(userService).getUserById(1000);
        verify(userService, never()).deleteUser(1000);
    }

    @Test
    void shouldReturnConflict_whenUsernameAlreadyTaken() throws Exception {
        when(userService.registerUser(any(User.class))).thenThrow(
                new UserAlreadyExistsException("johndoe"));
        User newUser = new User("John Doe", "johndoe", "password");
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isConflict());
        verify(userService).registerUser(any(User.class));
    }

    @Test
    void shouldLoginSuccessfully_withValidCredentials() throws Exception {
        when(userService.getUserByUsername("johndoe")).thenReturn(testUser);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("johndoe");
        loginRequest.setPassword("password123");
        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk());
        verify(userService).getUserByUsername("johndoe");
    }

    @Test
    void shouldReturnUnauthorized_withInvalidPassword() throws Exception {
        when(userService.getUserByUsername("johndoe")).thenReturn(testUser);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("johndoe");
        loginRequest.setPassword("wrongpassword");
        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
        verify(userService).getUserByUsername("johndoe");
    }

    @Test
    void shouldReturnUnauthorized_withNonExistentUsername() throws Exception {
        when(userService.getUserByUsername("unknown")).thenThrow(new UserNotFoundException("unknown"));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("unknown");
        loginRequest.setPassword("anypassword");
        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
        verify(userService).getUserByUsername("unknown");
    }

    @Test
    void shouldLogoutSuccessfully() throws Exception {
        mockMvc.perform(post("/api/users/logout")
                        .header("Authorization", "some-token-value"))
                .andExpect(status().isOk())
                .andExpect(content().string("Logged out successfully"));
    }

}
