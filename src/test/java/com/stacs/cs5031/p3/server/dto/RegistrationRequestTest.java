package com.stacs.cs5031.p3.server.dto;

import com.stacs.cs5031.p3.server.dto.RegistrationRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for RegistrationRequest.
 */
public class RegistrationRequestTest {

    @Test
    void shouldCreateRegistrationRequest() {
        String name = "Mary Oliver";
        String username = "maryoliver";
        String password = "wildegueese";
        String role = "ORGANISER";
        RegistrationRequest request = new RegistrationRequest(name, username, password, role);
        assertNotNull(request, "registration request should not be null");
    }

    @Test
    void shouldGetRole() {
        RegistrationRequest request = new RegistrationRequest("Mary Oliver", "maryoliver", "wildgueese", "Attendee");
        String result = request.getRole();
        assertEquals("Attendee", result, "should return the correct role");
    }

    @Test
    void shouldSetRole() {
        RegistrationRequest request = new RegistrationRequest("John Green", "johngreen", "nerdfighter", "ORGANISER");
        request.setRole("ATTENDEE");
        assertEquals("ATTENDEE", request.getRole(), "should update the role");
    }


}