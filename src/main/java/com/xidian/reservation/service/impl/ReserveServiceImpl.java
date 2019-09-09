package com.xidian.reservation.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xidian.reservation.dao.ReserveMapper;
import com.xidian.reservation.dao.RoomMapper;
import com.xidian.reservation.dao.WxInformationMapper;
import com.xidian.reservation.dto.ReserveInfo;
import com.xidian.reservation.entity.Reserve;
import com.xidian.reservation.entity.Room;
import com.xidian.reservation.entity.WxInformation;
import com.xidian.reservation.exceptionHandler.CommonEnum;
import com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody;
import com.xidian.reservation.service.ReserveService;
import com.xidian.reservation.utils.String2DateUtils;
import com.xidian.reservation.utils.WeChatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    @Resource
    private WxInformationMapper wxInformationMapper;

    @Resource
    private WeChatUtil weChatUtil;

    @Transactional
    public UniversalResponseBody reserveRoom(Reserve reserve,String formId, String code) throws Exception {
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
                String openId = weChatUtil.getOpenId(code);
                if (openId != null) {
                    reserveMapper.insert(reserve);
                    WxInformation wxInformation = new WxInformation(reserve.getReserveId(), reserve.getConsumerId(), openId,formId);
                    wxInformationMapper.insert(wxInformation);
                    return UniversalResponseBody.success();
                } else {
                    return UniversalResponseBody.error("-4", "Code expired");
                }
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
        return UniversalResponseBody.success(reserveMapper.deleteByPrimaryKey(reserveId));
    }

    public boolean updateReserve(Reserve reserve) {
        return reserveMapper.updateByPrimaryKey(reserve) > 0;
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

    public UniversalResponseBody findReserveDetails(Integer reserveId, String otherThing, String shortMessage) {
        return UniversalResponseBody.success(new ReserveInfo(reserveMapper.selectByPrimaryKey(reserveId), otherThing, shortMessage));
    }

    public boolean updateStatus(Integer reserveId, Integer status) {
        Reserve reserve = reserveMapper.selectByPrimaryKey(reserveId);
        if (reserve == null) {
            log.error("reserveId查询结果为空！");
            return false;
        } else {
            reserve.setReserveStatus(status);
            return reserveMapper.updateByPrimaryKey(reserve) > 0;
        }
    }

    public Reserve findReserveByReserveId(Integer reserveId) {
        return reserveMapper.selectByPrimaryKey(reserveId);
    }
}
