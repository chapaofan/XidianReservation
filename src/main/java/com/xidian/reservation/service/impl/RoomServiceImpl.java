package com.xidian.reservation.service.impl;

import com.xidian.reservation.dao.RoomMapper;
import com.xidian.reservation.entity.Room;
import com.xidian.reservation.exceptionHandler.CommonEnum;
import com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody;
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

    public UniversalResponseBody saveRoom(Room room) {
        Room res = roomMapper.selectByName(room.getRoomName());
        if (res != null) {
            return UniversalResponseBody.error("701", "Room already exists!");
        } else if (roomMapper.insert(room) > 0) {
            return UniversalResponseBody.success();
        } else {
            return UniversalResponseBody.error(CommonEnum.SQL_STATEMENT_ERROR);
        }
    }

    public UniversalResponseBody deleteRoom(int roomId) {
        if (roomMapper.deleteByPrimaryKey(roomId) > 0) {
            return UniversalResponseBody.success();
        }else {
            return UniversalResponseBody.error("Failed room delete!");
        }
    }

    public List<Room> findRooms(int pageNum) {
        return null;//TODO 分页插件
    }
}
