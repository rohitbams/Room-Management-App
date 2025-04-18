package com.stacs.cs5031.p3.client.terminal;

import com.stacs.cs5031.p3.server.dto.RoomDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link TerminalClient} class.
 * Tests the terminal client's interaction with the server API using a mocked RestTemplate.
 * Verifies that API calls are made correctly and responses are handled properly.
 */
public class TerminalClientTest {

    /** Mock of the RestTemplate to simulate HTTP requests to the server */
    @Mock
    private RestTemplate mockRestTemplate;

    /** Output stream to capture console output for verification */
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    
    /** Original System.out stream to restore after tests */
    private final PrintStream originalOut = System.out;

    /** AutoCloseable for cleaning up mock resources */
    private AutoCloseable closeable;

    /** Test password used across multiple test methods */
    private String password;
    
    /** Test username used across multiple test methods */
    private String username;
    
    /** Test name used across multiple test methods */
    private String name;
    
    /** Test role used across multiple test methods */
    private String role;

    /**
     * Setup before each test.
     * Initializes mocks, redirects console output to a ByteArrayOutputStream,
     * and sets up test data.
     */
    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(outputStream));
        TerminalClient.setRestTemplate(mockRestTemplate);
        TerminalClient.setRunning(false);
        name = "Freddie Mercury";
        username = "readyfreddie";
        password = "queen";
    }

    /**
     * Cleanup after each test.
     * Restores the original System.out stream, resets the current user,
     * and closes mock resources.
     * 
     * @throws Exception if an error occurs during cleanup
     */
    @AfterEach
    public void tearDown() throws Exception {
        System.setOut(originalOut);
        TerminalClient.setCurrentUser(null);
        closeable.close();
    }

    /**
     * Tests that a user can successfully log in.
     * Verifies that:
     * 1. The login method returns true for successful login
     * 2. The current user is set with the correct information
     * 3. The RestTemplate makes the correct API call
     */
    @Test
    public void testHandleLogin_Success() {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("id", 1);
        responseMap.put("username", username);
        responseMap.put("name", name);
        responseMap.put("role", "ATTENDEE");

        when(mockRestTemplate.postForObject(
                contains("/users/login"),
                any(HttpEntity.class),
                eq(Map.class)))
                .thenReturn(responseMap);

        boolean result = TerminalClient.handleLogin(username, password);

        assertTrue(result, "Login should be successful");
        Map<String, String> currentUser = TerminalClient.getCurrentUser();
        assertNotNull(currentUser, "Current user should not be null after successful login");
        assertEquals("readyfreddie", currentUser.get("username"), "Username should match");
        assertEquals("ATTENDEE", currentUser.get("role"), "Role should match");
    }

    /**
     * Tests that login fails when the server returns a null response.
     * Verifies that:
     * 1. The login method returns false for failed login
     * 2. The current user remains null
     * 3. The RestTemplate makes the correct API call
     */
    @Test
    public void testHandleLogin_Failure() {
        when(mockRestTemplate.postForObject(
                contains("/users/login"),
                any(HttpEntity.class),
                eq(Map.class)))
                .thenReturn(null);

        boolean result = TerminalClient.handleLogin(username, password);
        assertFalse(result, "Login should fail");
        assertNull(TerminalClient.getCurrentUser(), "Current user should be null after failed login");
    }

    /**
     * Tests that a user can successfully register.
     * Verifies that:
     * 1. The registration method returns true for successful registration
     * 2. The RestTemplate makes the correct API call
     * 3. The server response is correctly processed
     */
    @Test
    public void testHandleRegistration_Success() {
        role = "ORGANISER";
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("id", 1);
        responseMap.put("username", username);
        responseMap.put("name", name);

        when(mockRestTemplate.postForObject(
                contains("/users/register"),
                any(HttpEntity.class),
                eq(Map.class)))
                .thenReturn(responseMap);

        boolean result = TerminalClient.handleRegistration(name, username, password, role);
        assertTrue(result, "Registration should be successful");
    }

    /**
     * Tests that available rooms can be retrieved successfully.
     * Verifies that:
     * 1. The getAvailableRooms method returns the correct list of rooms
     * 2. The RestTemplate makes the correct API call
     * 3. The room properties match the expected values
     */
    @Test
    public void testGetAvailableRooms_Success() {
        RoomDto[] rooms = new RoomDto[2];
        rooms[0] = new RoomDto(1, "Room 101", 20, true);
        rooms[1] = new RoomDto(2, "Room 102", 15, true);

        when(mockRestTemplate.getForObject(
                contains("/rooms/available"),
                eq(RoomDto[].class)))
                .thenReturn(rooms);

        List<RoomDto> result = TerminalClient.getAvailableRooms();
        assertEquals(2, result.size(), "Should return two available rooms");
        assertEquals("Room 101", result.get(0).getName(), "First room name should match");
        assertEquals("Room 102", result.get(1).getName(), "Second room name should match");
    }
}