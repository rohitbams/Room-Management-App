package com.stacs.cs5031.p3.server.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stacs.cs5031.p3.server.dto.OrganiserDto;
import com.stacs.cs5031.p3.server.dto.RoomDto;
import com.stacs.cs5031.p3.server.model.Attendee;
import com.stacs.cs5031.p3.server.model.Room;
import com.stacs.cs5031.p3.server.service.AdminService;

public class AdminControllerTest {
    
    private MockMvc mvc;

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(adminController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldGetAllRooms() throws Exception {
        List<RoomDto> rooms = List.of(
                new RoomDto(1, "Room 1", 10, true),
                new RoomDto(2, "Room 2", 20, false));
        when(adminService.getAllRooms()).thenReturn(new ArrayList<>(rooms));
        
        mvc.perform(get("/admin/rooms"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Room 1"))
                .andExpect(jsonPath("$[0].capacity").value(10))
                .andExpect(jsonPath("$[0].available").value(true));

        verify(adminService, times(1)).getAllRooms();
    }
    
    @Test
    void shouldGetAllAttendees() throws Exception {
        List<Attendee> attendees = List.of(
                new Attendee("John Doe", "john.doe", "password123"),
                new Attendee("Jane Smith", "jane.smith", "password456"));
        when(adminService.getAttendees()).thenReturn(new ArrayList<>(attendees));

        mvc.perform(get("/admin/attendees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].username").value("jane.smith"));

        verify(adminService, times(1)).getAttendees();
    }
    
    @Test
    void shouldGetAllOrganisers() throws Exception {
        List<OrganiserDto> organisers = List.of(
                new OrganiserDto(1, "Alice", "alice.organiser"),
                new OrganiserDto(2, "Bob", "bob.organiser"));
        when(adminService.getOrganisers()).thenReturn(new ArrayList<>(organisers));

        mvc.perform(get("/admin/organisers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].username").value("alice.organiser"))
                .andExpect(jsonPath("$[1].name").value("Bob"));

        verify(adminService, times(1)).getOrganisers();
    }
    
    @Test
    void shouldAddRoom() throws Exception {
        Room room = new Room("Test Room", 15);
        when(adminService.addRoom(any(Room.class))).thenReturn(true);

        this.mvc.perform(post("/admin/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isOk())
                .andExpect(content().string("Room added successfully"));

        verify(adminService, times(1)).addRoom(any(Room.class));
    }
    
    @Test
    void shouldFailToAddRoom() throws Exception {
        Room room = new Room("Invalid Room", 0);
        when(adminService.addRoom(any(Room.class))).thenReturn(false);

        mvc.perform(post("/admin/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Failed to add room"));

        verify(adminService, times(1)).addRoom(any(Room.class));
    }
    
    @Test
    void shouldRemoveRoom() throws Exception {
        int roomId = 1;
        when(adminService.removeRoom(roomId)).thenReturn(true);

        this.mvc.perform(delete("/admin/rooms/{roomId}", roomId))
            .andExpect(status().isOk())
            .andExpect(content().string("Room removed successfully"));

        verify(adminService, times(1)).removeRoom(roomId);
    }

    @Test
    void shouldFailToRemoveRoom() throws Exception {
        int roomId = 999;
        when(adminService.removeRoom(roomId)).thenReturn(false);

        mvc.perform(delete("/admin/rooms/{roomId}", roomId))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Failed to remove room"));

        verify(adminService, times(1)).removeRoom(roomId);
    }
}
