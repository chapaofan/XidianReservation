package com.xidian.reservation.dao;

import com.github.pagehelper.Page;
import com.xidian.reservation.entity.Room;

import java.util.List;

public interface RoomMapper {
    int deleteByPrimaryKey(Integer roomId);

    int insert(Room record);

    int insertSelective(Room record);

    Room selectByPrimaryKey(Integer roomId);

    Room selectByName(String roomName);

    int updateByPrimaryKeySelective(Room record);

    int updateByPrimaryKey(Room record);

    Page<Room> findAllRoom();
}