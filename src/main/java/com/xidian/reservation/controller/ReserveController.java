package com.xidian.reservation.controller;

import com.xidian.reservation.annotation.UserLoginToken;
import com.xidian.reservation.dao.ReserveMapper;
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


    @UserLoginToken
    @RequestMapping(value = "/occupy", method = RequestMethod.POST)
    public UniversalResponseBody occupyTime(@RequestParam("roomId") int roomId, @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("reserveDate") Date reserveDate) throws Exception {
        return reserveService.selectNotAllowTime(roomId, reserveDate);
    }


    @UserLoginToken
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public UniversalResponseBody order(HttpServletRequest httpServletRequest, @NotNull Reserve reserve) {
        String token = httpServletRequest.getHeader("token");
        Long consumerId = Long.parseLong(TokenUtil.getAppUID(token));
        reserve.setConsumerId(consumerId);
        log.info(""+consumerId);
        return reserveService.saveReserve(reserve);
    }

}
