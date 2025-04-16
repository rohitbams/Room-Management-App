package com.stacs.cs5031.p3.client.terminal;

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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class TerminalClientTest {

    @Mock
    private RestTemplate mockRestTemplate;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private AutoCloseable closeable;

    private String password;
    private String username;
    private String name;
    private String role;

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

    @AfterEach
    public void tearDown() throws Exception {
        System.setOut(originalOut);
        TerminalClient.setCurrentUser(null);
        closeable.close();
    }

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

        assertTrue(result);
        Map<String, String> currentUser = TerminalClient.getCurrentUser();
        assertNotNull(currentUser);
        assertEquals("readyfreddie", currentUser.get("username"));
        assertEquals("ATTENDEE", currentUser.get("role"));
    }

    @Test
    public void testHandleLogin_Failure() {
        when(mockRestTemplate.postForObject(
                contains("/users/login"),
                any(HttpEntity.class),
                eq(Map.class)))
                .thenReturn(null);

        boolean result = TerminalClient.handleLogin(username, password);
        assertFalse(result);
        assertNull(TerminalClient.getCurrentUser());
    }

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
        assertTrue(result);
    }

}