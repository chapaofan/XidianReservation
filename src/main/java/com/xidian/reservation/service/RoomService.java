package com.xidian.reservation.service;

import com.xidian.reservation.entity.Room;
import com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody;

import java.util.List;

public interface RoomService {
    UniversalResponseBody saveRoom(Room room);

    UniversalResponseBody deleteRoom(int roomId);

    UniversalResponseBody findRoomByPage(int pageNum, int pageSize);

    UniversalResponseBody findAllRoom();
}
