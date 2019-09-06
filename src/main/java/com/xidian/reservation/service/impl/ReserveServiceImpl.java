package com.xidian.reservation.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xidian.reservation.dao.ReserveMapper;
import com.xidian.reservation.dao.RoomMapper;
import com.xidian.reservation.entity.Reserve;
import com.xidian.reservation.entity.Room;
import com.xidian.reservation.exceptionHandler.CommonEnum;
import com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody;
import com.xidian.reservation.service.ReserveService;
import com.xidian.reservation.utils.String2DateUtils;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        if (room == null) {
            log.error("无此教室，插入失败！");
            return UniversalResponseBody.error(CommonEnum.NOT_FOUND);
        } else {
            if (String2DateUtils.compTime(start, end)) {
                log.error("结束时间在开始时间之前！");
                return UniversalResponseBody.error(CommonEnum.BODY_NOT_MATCH);
            }
            List<Reserve> res = reserveMapper.selectNotAllowTime(room.getRoomId(), reserve.getReserveDate());
            boolean flag = true;
            for (Reserve result : res) {
                if (result.getReserveStatus() == 200 || result.getReserveStatus() == 500) {
                    continue;
                }
                Date startR = result.getReserveStart();
                Date endR = result.getReserveEnd();

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


    public UniversalResponseBody deleteReserve(Integer reserveId) {
        //return orderMapper.deleteByPrimaryKey(orderId)>0;
        return null;
    }

    public UniversalResponseBody updateReserve(Reserve reserve) {
        if (reserveMapper.updateByPrimaryKey(reserve) > 0) {
            return UniversalResponseBody.success();
        } else {
            return UniversalResponseBody.error("Failed reserve update!");
        }
    }

    public UniversalResponseBody findHistoryReserves(Long consumerId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Reserve> pageInfo = new PageInfo<>(reserveMapper.findHistoryReserveByConsumerId(consumerId));
        return UniversalResponseBody.success(pageInfo);
    }

    public UniversalResponseBody findNotStartReserves(Long consumerId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Reserve> pageInfo = new PageInfo<>(reserveMapper.findNotStartReserveByConsumer(consumerId));
        return UniversalResponseBody.success(pageInfo);
    }

    public UniversalResponseBody findNotStartAndStatus0(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Reserve> pageInfo = new PageInfo<>(reserveMapper.findNotStartAndStatus0());
        return UniversalResponseBody.success(pageInfo);
    }


}
