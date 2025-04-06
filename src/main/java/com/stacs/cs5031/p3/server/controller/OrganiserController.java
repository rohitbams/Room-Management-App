package com.stacs.cs5031.p3.server.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stacs.cs5031.p3.server.model.Organiser;

@RestController
public class OrganiserController {

    public OrganiserController() {
        // Constructor
    }

    
    @PostMapping("/organiser/create-organiser")
    public ResponseEntity<String> createOrganiser(@RequestBody Organiser organiser) {
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @GetMapping(value = "/organisers", produces = { "application/json" })
    public ResponseEntity<ArrayList<Organiser>> getAllOrganisers() {
        return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<Organiser>());
    }

    @GetMapping(value = "/organiser/available-rooms", produces = { "application/json" })
    public ResponseEntity<ArrayList<String>> getAvailableRooms() {
        return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<String>()); 
    }


}
