package com.stacs.cs5031.p3.server.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stacs.cs5031.p3.server.dto.BookingDto;
import com.stacs.cs5031.p3.server.model.Organiser;
import com.stacs.cs5031.p3.server.repository.OrganiserRepository;
import com.stacs.cs5031.p3.server.service.BookingService;
import com.stacs.cs5031.p3.server.service.RoomService;

/**
 * This class is responsible for testing the Organiser Controller - integration
 * tests.
 * 
 * @author 190031593
 */
@SpringBootTest
@AutoConfigureMockMvc
public class OrganiserControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private OrganiserRepository organiserRepository;

        @Autowired
        private RoomService roomService;

        @Autowired
        private BookingService bookingService;

        private Organiser organiser1, organiser2, organiser3, organiser4;

        @BeforeEach
        void setUp() {
                organiserRepository.deleteAll();

                organiser1 = new Organiser("James Dean", "james.dean", "password123");
                organiser2 = new Organiser("Holly Dean", "", "password123");
                organiser4 = new Organiser("Jim Dean", "jim.dean", null);
                organiser3 = new Organiser("Johnny Doe", "johnny.doe", "passwordABC");
        }

        /**
         * This test is responsible for testing that an organiser can be created without
         * issue.
         * 
         * @throws Exception
         */
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

        /**
         * This test is responsible for testing that an exception is thrown when the
         * organiser information is invalid. I.e. username, name or password is
         * null/empty.
         * 
         * @throws Exception
         */
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

        /**
         * This test is reponsible for testing that all organisers can be retrieved from
         * the
         * database without issue.
         * 
         * @throws Exception
         */
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

        /**
         * This test is responsible for testing that all organisers can be retrieved
         * from the
         * database without issue. In this case, there are no organisers in the
         * database.
         *
         * @throws Exception
         */
        @Test
        void shouldGetAllOrganisersIfNoneExist() throws Exception {
                this.mockMvc.perform(
                                get("/organisers"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$").isEmpty());
        }

        /**
         * This test is responsible for testing that all available rooms can be
         * retrieved
         */
        @Test
        void shouldGetAllAvailableRooms() throws Exception {
                roomService.createRoom("R1", 100);
                roomService.createRoom("R2", 200);

                this.mockMvc.perform(
                                get("/organiser/available-rooms"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.[0].name").value("R1"))
                                .andExpect(jsonPath("$.[1].name").value("R2"));
        }

        /**
         * This test is responsible for testing that a booking can be created without
         * issue.
         */
        @Test
        void shouldCreateBookingWithoutIssue() throws Exception {
                organiserRepository.save(organiser1);
                roomService.createRoom("R1", 100);
                BookingDto.BookingRequest bookingRequest = new BookingDto.BookingRequest("Event 1", 1L, new Date(), 2,
                                "Test");

                this.mockMvc.perform(
                                post("/organiser/create-booking" + "/" + 1)
                                                .content(new ObjectMapper().writeValueAsString(bookingRequest))
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isCreated())
                                .andExpect(content().string("SUCCESS!"));

        }

        /**
         * This test is responsible for testing that a booking cannot be retrieved if it
         * does not
         * exist in the database.
         * 
         * @throws Exception
         */
        @Test
        void shouldNotGetBookingDetailsIfBookingDoesNotExist() throws Exception {
                this.mockMvc.perform(
                                get("/organiser/" + 1 + "my-bookings/" + 1))
                                .andExpect(status().isNotFound());
        }

        /**
         * This test is responsible for testing that a booking can be retrieved without
         * issue.
         */
        @Test
        void shouldGetBookingDetailsWithoutIssue() throws Exception {
                organiserRepository.save(organiser1);
                roomService.createRoom("R1", 100);
                BookingDto.BookingRequest bookingRequest = new BookingDto.BookingRequest("Event 1", 1L, new Date(), 2,
                                "Testing 1");
                bookingService.createBooking(bookingRequest, 1L);
                this.mockMvc.perform(
                                get("/organiser/" + 1 + "/my-bookings/" + 1))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.eventName").value("Event 1"))
                                .andExpect(jsonPath("$.roomId").value(1L));

        }

        /**
         * This test is responsible for testing that a booking can be cancelled without
         * issue.
         * 
         * @throws Exception
         */
        @Test
        void shouldCancelBookingWithoutIssue() throws Exception {

                organiserRepository.save(organiser1);
                roomService.createRoom("R1", 100);
                BookingDto.BookingRequest bookingRequest = new BookingDto.BookingRequest("Event 1", 1L, new Date(), 2,
                                "Test");

                bookingService.createBooking(bookingRequest, 0L);

                this.mockMvc.perform(
                                delete("/organiser/cancel-booking/" + 1 + "/" + 1))
                                .andExpect(status().isNoContent())
                                .andExpect(content().string("SUCCESS!"));

        }

        /**
         * This test is responsible for testing that all bookings can be retrieved from
         * the
         * database without issue.
         * 
         * @throws Exception
         */
        @Test
        void shouldGetAllBookingsWithoutIssue() throws Exception {
                BookingDto.BookingRequest bookingRequest = new BookingDto.BookingRequest("Event 1", 1L, new Date(), 2,
                                "Testing 1");
                BookingDto.BookingRequest bookingRequest2 = new BookingDto.BookingRequest("Event 2", 2L, new Date(), 2,
                                "Testing 2");
                roomService.createRoom("R1", 100);
                roomService.createRoom("R2", 200);
                organiserRepository.save(organiser1);
                bookingService.createBooking(bookingRequest, 1L);
                bookingService.createBooking(bookingRequest2, 1L);

                this.mockMvc.perform(
                                get("/organiser/my-bookings/" + 1))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.[0].eventName").value("Event 1"))
                                .andExpect(jsonPath("$.[1].eventName").value("Event 2"));

        }

}
