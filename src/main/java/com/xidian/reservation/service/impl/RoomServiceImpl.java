package com.xidian.reservation.service.impl;

import com.xidian.reservation.dao.RoomMapper;
import com.xidian.reservation.entity.Room;
import com.xidian.reservation.service.RoomService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：Maolin
 * @className ：RoomServiceImpl
 * @date ：Created in 2019/9/1 16:43
 * @description： RoomService 服务层
 * @version: 1.0
 */

@Service
public class RoomServiceImpl implements RoomService {

    @Resource
    private RoomMapper roomMapper;

    public boolean saveRoom(Room room) {
        return roomMapper.insert(room) > 0;
    }

    public boolean deleteRoom(int roomId) {
        return roomMapper.deleteByPrimaryKey(roomId) > 0;
    }

    public List<Room> findRooms(int pageNum) {
        return null;//TODO 分页插件
    }
}
