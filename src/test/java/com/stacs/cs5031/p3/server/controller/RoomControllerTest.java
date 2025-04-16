package com.stacs.cs5031.p3.server.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.List;

import javax.print.attribute.standard.Media;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.stacs.cs5031.p3.server.dto.RoomDto;
import com.stacs.cs5031.p3.server.exception.RoomNotAvailableException;
import com.stacs.cs5031.p3.server.exception.RoomNotFoundException;
import com.stacs.cs5031.p3.server.service.RoomService;

@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private RoomService roomService;

    @Test
    void shouldGetRoomWithValidId() throws Exception {
        when(roomService.findRoomDtoById(1)).thenReturn(new RoomDto(1, "Room 1", 10, true));

        mvc.perform(MockMvcRequestBuilders.get("/rooms/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Room 1")))
                .andExpect(jsonPath("$.capacity", is(10)))
                .andExpect(jsonPath("$.available", is(true)));
    }

    @Test
    void shouldReturnNotFoundWithInvalidRoomId() throws Exception {
        when(roomService.findRoomById(999)).thenThrow(RoomNotFoundException.class);

        mvc.perform(MockMvcRequestBuilders.get("/rooms/999")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllRooms_whenRoomsExist() throws Exception {
        List<RoomDto> expectedRooms = Arrays.asList(
                new RoomDto(1, "Room 1", 10, true),
                new RoomDto(2, "Room 2", 20, true),
                new RoomDto(3, "Room 3", 30, false));
        when(roomService.findAllRooms()).thenReturn(expectedRooms);

        mvc.perform(get("/rooms/all")
                .accept(MediaType.APPLICATION_JSON))
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
    void shouldReturnNoContent_whenNoRoomExists() throws Exception {
        when(roomService.findAllRooms()).thenReturn(List.of()); // return empty list

        mvc.perform(get("/rooms/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNoContent_whenNoRoomIsAvailable() throws Exception {
        when(roomService.findAvailableRooms()).thenReturn(List.of()); // return empty list

        mvc.perform(get("/rooms/available")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetAvailableRooms_whenAvailableRoomsExist() throws Exception {
        List<RoomDto> expectedRooms = Arrays.asList(
            new RoomDto(1, "Room 1", 10, true),
            new RoomDto(2, "Room 2", 20, true),
            new RoomDto(3, "Room 3", 30, false)
        );
        when(roomService.findAvailableRooms()).thenReturn(expectedRooms);

        mvc.perform(get("/rooms/available")
                .accept(MediaType.APPLICATION_JSON))
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
    void shouldBookRoomWithValidId() throws Exception {
        when(roomService.bookRoom(1)).thenReturn(new RoomDto(1, "Room 1", 10, true));

        mvc.perform(post("/rooms/1/book")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Room 1")))
                .andExpect(jsonPath("$.capacity", is(10)))
                .andExpect(jsonPath("$.available", is(true)));
    }

    @Test
    void shouldReturnBadRequest_whenBookingUnavailableRoom() throws Exception {
        when(roomService.bookRoom(2)).thenThrow(RoomNotAvailableException.class);
        mvc.perform(post("/rooms/2/book")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnNotFound_whenBookingRoomWithInvalidId() throws Exception {
        when(roomService.bookRoom(999)).thenThrow(RoomNotFoundException.class);
        mvc.perform(post("/rooms/999/book")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldMakeRoomAvailable() throws Exception {
        when(roomService.makeRoomAvailable(1)).thenReturn(new RoomDto(1, "Room 1", 10, true));
        mvc.perform(post("/rooms/1/makeAvailable")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnNotFound_whenMakingRoomAvailableWithInvalidId() throws Exception {
        when(roomService.makeRoomAvailable(999)).thenThrow(RoomNotFoundException.class);
        mvc.perform(post("/rooms/999/makeAvailable")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
