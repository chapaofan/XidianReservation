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
import com.xidian.reservation.service.LockService;
import com.xidian.reservation.service.ReserveService;
import com.xidian.reservation.service.WxPushService;
import com.xidian.reservation.utils.String2DateUtils;
import com.xidian.reservation.utils.WeChatUtil;
import com.xidian.reservation.utils.DateAndTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private LockService lockService;

    @Resource
    private ReserveMapper reserveMapper;

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private WxInformationMapper wxInformationMapper;

    @Resource
    private WeChatUtil weChatUtil;

    @Resource
    private WxPushService wxPushService;


    @Transactional
    public UniversalResponseBody reserveRoom(Reserve reserve, String formId, String code) throws Exception {
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
            List<Reserve> res = reserveMapper.selectNotAllowTime(room.getRoomId(), reserve.getReserveDate(), 0);
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
                    WxInformation wxInformation = new WxInformation(reserve.getReserveId(), reserve.getConsumerId(), openId, formId);
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
        List<Reserve> sqlRes = reserveMapper.selectNotAllowTime(roomId, reserveDate, 0);
        for (Reserve reserve : sqlRes) {
            SimpleDateFormat hourFormatter = new SimpleDateFormat("HH");
            SimpleDateFormat minuteFormatter = new SimpleDateFormat("mm");

            int startHour = (Integer.parseInt(hourFormatter.format(reserve.getReserveStart())) - 8) * 2;
            int startMinute = Integer.parseInt(minuteFormatter.format(reserve.getReserveStart()));
            int endHour = (Integer.parseInt(hourFormatter.format(reserve.getReserveEnd())) - 8) * 2 - 1;
            int endMinute = Integer.parseInt(minuteFormatter.format(reserve.getReserveEnd()));
            if (startMinute - 30 > 0) {
                startHour = startHour + 1;
            }
            if (endMinute - 30 > 0) {
                endHour = endHour + 1;
            }
            for (int i = startHour; i <= endHour; i++) {
                if (i >= 0) {
                    res.add(i);
                }
            }
        }
        return UniversalResponseBody.success(res);
    }


    public UniversalResponseBody deleteReserve(Integer reserveId) {
        return UniversalResponseBody.success(reserveMapper.deleteByPrimaryKey(reserveId));
    }

    @Transactional
    public UniversalResponseBody changeReserve(Reserve reserve, String formId,
                                               String reserveDateTime, String reserveResult,
                                               String WxMSS) throws Exception {
        Date start = reserve.getReserveStart();
        Date end = reserve.getReserveEnd();
        Room room = roomMapper.selectByName(reserve.getRoomName());
        if (room == null) {
            log.error("无此教室，更新失败！");
            return UniversalResponseBody.error(CommonEnum.NOT_FOUND);
        } else {
            if (String2DateUtils.compTime(start, end)) {
                log.error("结束时间在开始时间之前！");
                return UniversalResponseBody.error(CommonEnum.BODY_NOT_MATCH);
            }
            List<Reserve> res = reserveMapper.selectNotAllowTime(room.getRoomId(), reserve.getReserveDate(), reserve.getReserveId());
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
            //更改时间更新密码
            reserve.setOpenPwd(lockService.getPassword(reserve));

            if (flag && reserveMapper.updateByPrimaryKey(reserve) > 0) {
                String result = wxPushService.wxPushOneUser(reserve.getReserveId(), formId,
                        reserve.getRoomName(), reserve.getReserveName(), reserveResult,
                        reserveDateTime, WxMSS+"密码："+reserve.getOpenPwd());
                return UniversalResponseBody.success();
            } else {
                return UniversalResponseBody.error("701", "Time occupy!");
            }
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

    public UniversalResponseBody findNotStartAndStatus0ByRoom(String roomName, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Reserve> pageInfo = new PageInfo<>(reserveMapper.findNotStartAndStatus0ByRoom(roomName));
        return UniversalResponseBody.success(pageInfo);
    }

    public UniversalResponseBody findReserveDetails(Integer reserveId, String otherThing, String shortMessage) {
        Reserve reserve = reserveMapper.selectByPrimaryKey(reserveId);
        return UniversalResponseBody.success(new ReserveInfo(reserve, otherThing, shortMessage + reserve.getOpenPwd()));
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

    public UniversalResponseBody findReserveByWeekAndDateTime(int roomId, int weekNum) throws Exception {
        //weekNum从0开始
        Date date = DateAndTimeUtil.addDay(new Date(System.currentTimeMillis()), weekNum * 7);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        List<String> weekdays = DateAndTimeUtil.getDateToWeek(date);
        Map<String, Object> res = new HashMap<>();

        for (String day : weekdays) {
            Date weekday = formatter.parse(day);
            Map<String, Object> resultPart = new HashMap<>();
            for (int i = 8; i < 22; i++) {
                resultPart.put(i + "", reserveMapper.findReserveByDateTime(roomId, weekday, i + "", (i + 1) + ""));
            }
            res.put(DateAndTimeUtil.getWeekdayByDate(weekday), resultPart);
        }

        return UniversalResponseBody.success(res);
    }


}
