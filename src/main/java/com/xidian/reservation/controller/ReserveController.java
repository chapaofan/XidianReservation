package com.xidian.reservation.controller;

import com.xidian.reservation.annotation.UserLoginToken;
import com.xidian.reservation.entity.Reserve;
import com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody;
import com.xidian.reservation.service.ReserveService;
import com.xidian.reservation.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author ：Maolin
 * @className ：OrderController
 * @date ：Created in 2019/9/4 20:52
 * @description： order控制层
 * @version: 1.0
 */
@RestController
@Slf4j
@RequestMapping("/reserve")
public class ReserveController {

    @Resource
    private ReserveService reserveService;


    /**
     * @Description: 已被预约时间段
     * @Date: 9:24 2019/9/6
     * @Param: [roomId, reserveDate]
     * @return: com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @UserLoginToken
    @RequestMapping(value = "/occupy", method = RequestMethod.POST)
    public UniversalResponseBody occupyTime(@RequestParam("roomId") int roomId, @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("reserveDate") Date reserveDate) throws Exception {
        return reserveService.selectNotAllowTime(roomId, reserveDate);
    }


    /**
     * @Description: 预约教室
     * @Date: 9:24 2019/9/6
     * @Param: [httpServletRequest, reserve]
     * @return: com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @UserLoginToken
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public UniversalResponseBody order(HttpServletRequest httpServletRequest, @NotNull Reserve reserve) {
        String token = httpServletRequest.getHeader("token");
        Long consumerId = Long.parseLong(TokenUtil.getAppUID(token));
        reserve.setConsumerId(consumerId);
        reserve.setReserveStatus(0);
        //log.info(""+consumerId);
        return reserveService.saveReserve(reserve);
    }


    /**
     * @Description: 历史日程
     * @Date:        9:29 2019/9/6
     * @Param:       [httpServletRequest, pageNum, pageSize]
     * @return:      com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @UserLoginToken
    @RequestMapping(value = "/history/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public UniversalResponseBody findHistoryReserves(HttpServletRequest httpServletRequest,
                                                     @PathVariable("pageNum") int pageNum,
                                                     @PathVariable("pageSize") int pageSize) {
        String token = httpServletRequest.getHeader("token");
        Long consumerId = Long.parseLong(TokenUtil.getAppUID(token));
        return reserveService.findHistoryReserves(consumerId, pageNum, pageSize);
    }

    /**
     * @Description: 我的日程
     * @Date:        11:09 2019/9/6
     * @Param:       [httpServletRequest, pageNum, pageSize]
     * @return:      com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @UserLoginToken
    @RequestMapping(value = "/myReserve/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public UniversalResponseBody myReserve(HttpServletRequest httpServletRequest,
                                                     @PathVariable("pageNum") int pageNum,
                                                     @PathVariable("pageSize") int pageSize) {
        String token = httpServletRequest.getHeader("token");
        Long consumerId = Long.parseLong(TokenUtil.getAppUID(token));
        return reserveService.findNotStartReserves(consumerId, pageNum, pageSize);
    }

    /**
     * @Description: 申请队列
     * @Date:        11:34 2019/9/6
     * @Param:       [pageNum, pageSize]
     * @return:      com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @UserLoginToken
    @RequestMapping(value = "/apply/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public UniversalResponseBody applyReserve(@PathVariable("pageNum") int pageNum,
                                           @PathVariable("pageSize") int pageSize) {
        return reserveService.findNotStartAndStatus0(pageNum, pageSize);
    }
}
