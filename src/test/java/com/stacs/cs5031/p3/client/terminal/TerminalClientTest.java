package com.stacs.cs5031.p3.client.terminal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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


}