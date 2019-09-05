package com.xidian.reservation.service.impl;

import com.xidian.reservation.dao.ReserveMapper;
import com.xidian.reservation.dao.RoomMapper;
import com.xidian.reservation.entity.Reserve;
import com.xidian.reservation.entity.Room;
import com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody;
import com.xidian.reservation.service.ReserveService;
import com.xidian.reservation.utils.String2DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ：Maolin
 * @className ：OrderServiceImpl
 * @date ：Created in 2019/9/1 18:54
 * @description： OrderService 服务层
 * @version: 1.0
 */
@Service
public class ReserveServiceImpl implements ReserveService {

    @Resource
    private ReserveMapper reserveMapper;

    @Resource
    private RoomMapper roomMapper;

    public UniversalResponseBody saveReserve(Reserve reserve) {
        Date start = reserve.getReserveStart();
        Date end = reserve.getReserveEnd();
        Room room = roomMapper.selectByName(reserve.getRoomName());
        List<Reserve> res = reserveMapper.selectNotAllowTime(room.getRoomId(), reserve.getReserveDate());
        boolean flag = true;
        for (Reserve result : res) {
            Date startR = result.getReserveStart();
            Date endR = result.getReserveEnd();
            //SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            //String time = formatter.format(date);

            if (!(String2DateUtils.compTime(startR, end) || String2DateUtils.compTime(start, endR))) {
                flag = false;
                break;
            }
        }
        if (flag) {
            reserveMapper.insert(reserve);
            return UniversalResponseBody.success();
        } else {
            return UniversalResponseBody.error("701", "Time occupy!");
        }
    }

    public UniversalResponseBody deleteReserve(Integer reserveId) {
        //return orderMapper.deleteByPrimaryKey(orderId)>0;
        return null;
    }

    public UniversalResponseBody updateReserve(Reserve reserve) {
        return null;
        //return orderMapper.updateByPrimaryKey(order) > 0;
    }

    public List<Reserve> findHistoryReserves(Long consumerId, int pageNum) {
        return null;//TODO return selectHistoryOrders;
    }

    public List<Reserve> findNotStartReserves(Long consumerId, int pageNum) {
        return null;//TODO return orderMapper.selectNotStartOrders;
    }

    public UniversalResponseBody selectNotAllowTime(int roomId, Date reserveDate) throws Exception {
        Set<Integer> res = new HashSet<>();
        List<Reserve> sqlRes = reserveMapper.selectNotAllowTime(roomId, reserveDate);
        for (Reserve reserve : sqlRes) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH");
            int start = Integer.parseInt(formatter.format(reserve.getReserveStart()));
            int end = Integer.parseInt(formatter.format(reserve.getReserveEnd()));
            for (int i = start; i <= end; i++) {
                res.add(i);
            }
        }
        return UniversalResponseBody.success(res);
    }

    public boolean allowInsert(Reserve reserve) throws Exception {
        return false;
        //TODO
    }
}
