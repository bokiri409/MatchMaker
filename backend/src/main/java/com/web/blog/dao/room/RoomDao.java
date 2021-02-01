package com.web.blog.dao.room;

import java.util.Collection;

import com.web.blog.model.room.Room;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomDao extends JpaRepository<Room, Integer> {
    public Collection<Room> findByRoomNameIn(Collection<String> roomName);
}
