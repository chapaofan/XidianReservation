package com.xidian.reservation.controller;

import com.xidian.reservation.annotation.UserLoginToken;
import com.xidian.reservation.entity.Consumer;
import com.xidian.reservation.entity.Manager;
import com.xidian.reservation.exceptionHandler.Response.CacheResponseBody;
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
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/manager", method = RequestMethod.POST)
    public UniversalResponseBody ManagerLogin(@NotNull Manager loginData) throws Exception{
            return managerService.managerLogin(loginData);
    }

    @RequestMapping(value = "/consumer", method = RequestMethod.POST)
    public UniversalResponseBody ConsumerLogin(@NotNull Consumer loginData) throws Exception {
            return consumerService.consumerLogin(loginData);
    }


    @GetMapping("/check/{session}")
    public UniversalResponseBody checkSession(@PathVariable("session") String session) {
        boolean flag = redisTemplate.hasKey("reservationCache::" + session);
        if (flag) {
            return UniversalResponseBody.success("Unexpired");
        } else {
            log.info("日志信息：访问拦截。提交的session过期" + session);
            return UniversalResponseBody.error("102","Expired");
        }
    }

    @UserLoginToken
    @GetMapping("/test")
    public UniversalResponseBody test(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        //int userId = TokenUtil.getAppUID(token);
        //log.info(token);
        //log.info(""+userId);
        return UniversalResponseBody.success(token);
    }
}
