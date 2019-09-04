package com.xidian.reservation.controller;

import com.xidian.reservation.dao.OrderMapper;
import com.xidian.reservation.entity.Order;
import com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody;
import com.xidian.reservation.utils.String2DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author ：Maolin
 * @className ：OrderController
 * @date ：Created in 2019/9/4 20:52
 * @description： order控制层
 * @version: 1.0
 */
@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderMapper orderMapper;


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public UniversalResponseBody test(@RequestParam("roomName") String roomName, @RequestParam("date") String date) {

        return null;
        //return UniversalResponseBody.success(orderMapper.selectNotAllowTime(roomName, String2DateUtils.parseDateStr(date)));
    }


    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public UniversalResponseBody save(@NotNull Order order) {
        System.out.println(order.toString());
        return UniversalResponseBody.success(orderMapper.insert(order));
    }

}
