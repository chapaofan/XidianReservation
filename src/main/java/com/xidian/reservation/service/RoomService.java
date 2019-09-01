package com.xidian.reservation.service;

import com.xidian.reservation.entity.Room;
import java.util.List;

public interface RoomService {
    boolean saveRoom(Room room);

    boolean deleteRoom(int roomId);

    List<Room> findRooms(int pageNum);
}
