package com.xidian.reservation.controller;

import com.xidian.reservation.annotation.UserLoginToken;
import com.xidian.reservation.entity.Consumer;
import com.xidian.reservation.entity.Manager;
import com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody;
import com.xidian.reservation.service.ConsumerService;
import com.xidian.reservation.service.ManagerService;
import com.xidian.reservation.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * @author ：Maolin
 * @className ：LoginController
 * @date ：Created in 2019/9/2 10:47
 * @description： 登录控制层
 * @version: 1.0
 */
@RestController
@Slf4j
@RequestMapping("/login")
public class LoginController {
    @Resource
    private ManagerService managerService;

    @Resource
    private ConsumerService consumerService;

    @RequestMapping(value = "/manager", method = RequestMethod.POST)
    public UniversalResponseBody ManagerLogin(@NotNull Manager loginData) throws Exception {
        return managerService.managerLogin(loginData);
    }

    @RequestMapping(value = "/consumer", method = RequestMethod.POST)
    public UniversalResponseBody ConsumerLogin(@NotNull Consumer loginData) throws Exception {
        return consumerService.consumerLogin(loginData);
    }

}
