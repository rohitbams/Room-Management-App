package com.stacs.cs5031.p3.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stacs.cs5031.p3.server.model.Organiser;


public interface OrganiserRepository extends JpaRepository<Organiser, Integer> {
    Organiser findByUsername(String username);
}
