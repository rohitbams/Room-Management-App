package com.stacs.cs5031.p3.server;

import org.springframework.data.repository.CrudRepository;

// database operations, finds entities
public interface RoomRepository extends CrudRepository<Room, Integer> {}
