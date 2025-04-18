package com.stacs.cs5031.p3.server.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stacs.cs5031.p3.server.model.Room;

// database operations, finds entities
@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {

    public List<Room> findByAvailability(boolean availability);
}
