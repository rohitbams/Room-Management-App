package com.stacs.cs5031.p3.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stacs.cs5031.p3.server.model.Organiser;
import com.stacs.cs5031.p3.server.repository.OrganiserRepository;

/**
 * This class is responsible for testing the Organiser Controller - integration
 * tests.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class OrganiserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrganiserRepository organiserRepository;

    private Organiser organiser1, organiser2, organiser3, organiser4;
    // private Room room1, room2;

    @BeforeEach
    void setUp() {
        organiserRepository.deleteAll();
        organiser1 = new Organiser("James Dean", "james.dean", "password123");
        organiser2 = new Organiser("Holly Dean", "", "password123");
        organiser4 = new Organiser("Jim Dean", "jim.dean", null);
        organiser3 = new Organiser("Johnny Doe", "johnny.doe", "passwordABC");
    }

    @Test
    void shouldCreateOrganiser() throws Exception {

        this.mockMvc.perform(
                post("/organiser/create-organiser")
                        .content(new ObjectMapper().writeValueAsString(organiser1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("SUCCESS!"));
    }

    @Test
    void shouldNotCreateOrganiserIfCredentialAreInvalid() throws Exception {

        this.mockMvc.perform(
                post("/organiser/create-organiser")
                        .content(new ObjectMapper().writeValueAsString(organiser2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect((content().string("Organiser username is invalid")));

        organiser2.setName(null);

        this.mockMvc.perform(
                post("/organiser/create-organiser")
                        .content(new ObjectMapper().writeValueAsString(organiser2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect((content().string("Organiser name is invalid")));

        this.mockMvc.perform(
                post("/organiser/create-organiser")
                        .content(new ObjectMapper().writeValueAsString(organiser4))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect((content().string("Organiser password is invalid")));
    }

    @Test
    void shouldGetAllOrganisers() throws Exception {
        this.mockMvc.perform(
                post("/organiser/create-organiser")
                        .content(new ObjectMapper().writeValueAsString(organiser1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("SUCCESS!"));

        this.mockMvc.perform(
                post("/organiser/create-organiser")
                        .content(new ObjectMapper().writeValueAsString(organiser3))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("SUCCESS!"));

        this.mockMvc.perform(
                get("/organisers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].username").value(organiser1.getUsername()))
                .andExpect(jsonPath("$.[1].username").value(organiser3.getUsername()));

    }

    @Test
    void shouldGetAllOrganisersIfNoneExist() throws Exception {
        this.mockMvc.perform(
                get("/organisers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    // @Test
    // void shouldGetAllAvailableRooms() throws Exception {
    // this.mockMvc.perform(
    // get("/organisers/available-rooms"))
    // .andExpect(status().isOk())
    // .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    // .andExpect(jsonPath("$.[0].id").value(room1.getID()))
    // .andExpect(jsonPath("$.[1].id").value(room2.getID()));
    // }

    @Test
    void shouldGetAllBookingsWithoutIssue() {

    }

    @Test
    void shouldGetBookingDetailsWithoutIssue() {

    }

    @Test
    void shouldThrowExceptionWhenBookingDetailsAreInvalid() {

    }

    @Test
    void shouldCreateBookingWithoutIssue() {

    }

    @Test
    void shouldCancelBookingWithoutIssue() {

    }

}
