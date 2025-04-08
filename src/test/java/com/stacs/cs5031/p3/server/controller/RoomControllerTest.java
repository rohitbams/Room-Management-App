package com.stacs.cs5031.p3.server.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.stacs.cs5031.p3.server.dto.RoomDTO;
import com.stacs.cs5031.p3.server.service.RoomService;

@WebMvcTest(RoomController.class)
public class RoomControllerTest {

    @Autowired
    private MockMvc mvc;
    
    @MockitoBean
    private RoomService roomService;

    @Test
    void shouldGetRoom() throws Exception {
        RoomDTO expectedResult = new RoomDTO(1, "Room 1", 10, true);
        when(roomService.findRoomById(1)).thenReturn(expectedResult);

        mvc.perform(get("/rooms/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Room 1")))
                .andExpect(jsonPath("$.capacity", is(10)))
                .andExpect(jsonPath("$.available", is(true)));
    }
    
    @Test
    void shouldGetAllRooms() throws Exception {
        // given
        List<RoomDTO> expectedRooms = Arrays.asList(
            new RoomDTO(1, "Room 1", 10, true),
            new RoomDTO(2, "Room 2", 20, true),
            new RoomDTO(3, "Room 3", 30, false)
        );
        when(roomService.findAllRooms()).thenReturn(expectedRooms);

        // when/then
        mvc.perform(get("/rooms/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Room 1")))
                .andExpect(jsonPath("$[0].capacity", is(10)))
                .andExpect(jsonPath("$[0].available", is(true)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[2].id", is(3)));
    }
    
    @Test
    void shouldGetAvailableRooms() throws Exception {
        // given
        List<RoomDTO> expectedRooms = Arrays.asList(
            new RoomDTO(1, "Room 1", 10, true),
            new RoomDTO(2, "Room 2", 20, true),
            new RoomDTO(3, "Room 3", 30, false)
        );
        when(roomService.findAvailableRooms()).thenReturn(expectedRooms);
    
        // when/then
        mvc.perform(get("/rooms/available")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].available", is(true)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].available", is(true)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].available", is(false)));
            }
            
            @Test
            void shouldBookRoom() throws Exception {
                RoomDTO expectedRoom = new RoomDTO(1, "Room 1", 10, true);
                when(roomService.bookRoom(1)).thenReturn(expectedRoom);
                
                mvc.perform(post("/rooms/{id}/book")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Room 1")))
                    .andExpect(jsonPath("$.capacity", is(10)))
                    .andExpect(jsonPath("$.available", is(true)));
                }


}
