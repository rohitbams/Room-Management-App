package com.stacs.cs5031.p3.server;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// database operations, finds entities
@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {

    public List<Room> findByAvailability(boolean availability);
    public List<Room> removeById(int id);
}
