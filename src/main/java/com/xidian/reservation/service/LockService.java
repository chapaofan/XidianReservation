package com.xidian.reservation.service;

import com.alibaba.fastjson.JSON;
import com.xidian.reservation.dao.ReserveMapper;
import com.xidian.reservation.dao.RoomMapper;
import com.xidian.reservation.entity.Reserve;
import com.xidian.reservation.exceptionHandler.BizException;
import com.xidian.reservation.exceptionHandler.Response.LockResponseBodySovler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：Maolin
 * @className ：LockUtil
 * @date ：Created in 2019/9/8 10:14
 * @description： 智能锁获取密码工具类
 * @version: 1.0
 */
@Service
@Slf4j
public class LockService {

    @Value("${LOCK.account}")
    private String LOCK_ACCOUNT;

    @Value("${LOCK.password}")
    private String LOCK_PASSWORD;

    @Value("${LOCK.hotelId}")
    private String LOCK_HOTEL_ID;

    @Value("${LOCK.host}")
    private String LOCK_HOST;

    @Resource
    private ReserveMapper reserveMapper;

    @Resource
    private RoomMapper roomMapper;

    private static RestTemplate restTemplate = new RestTemplate();

    public LockResponseBodySovler getLockResponseBody() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        //定义请求参数类型，这里用json所以是MediaType.APPLICATION_JSON
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = LOCK_HOST + "/v1/api/auth/get/token";

        Map<String, String> postParameters = new HashMap<>();
        postParameters.put("account", LOCK_ACCOUNT);
        postParameters.put("password", LOCK_PASSWORD);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(postParameters, headers);


        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);
        if (responseEntity.getStatusCodeValue() != 200) {
            throw new BizException("Connect Lock api failed!");
        }

        LockResponseBodySovler responseBodySovler = JSON.parseObject(responseEntity.getBody(), LockResponseBodySovler.class);
        return responseBodySovler;
    }


    public String getPassword(Reserve reserve) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        //定义请求参数类型，这里用json所以是MediaType.APPLICATION_JSON
        headers.setContentType(MediaType.APPLICATION_JSON);
        //Reserve reserve = reserveMapper.selectByPrimaryKey(reserveId);


        Integer roomId = roomMapper.selectByName(reserve.getRoomName()).getRoomId();
        Date reserveDate = reserve.getReserveDate();
        Date startTime = reserve.getReserveStart();
        Date endTime = reserve.getReserveEnd();
        String name = reserve.getReserveName();
        String mobile = "" + reserve.getReserveTel();

        String url = LOCK_HOST + "/v1/api/lock/get/password";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(reserveDate);
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");
        String checkInTime = date + " " + formatter2.format(startTime);
        String checkOutTime = date + " " + formatter2.format(endTime);

        Map<String, String> map = getLockResponseBody().getData();
        String token = map.get("accessToken");


        Map<String, String> postParameters = new HashMap<>();
        postParameters.put("token", token);
        postParameters.put("hotelId", LOCK_HOTEL_ID);
        postParameters.put("roomId", "" + roomId);
        postParameters.put("checkInTime", checkInTime);
        postParameters.put("checkOutTime", checkOutTime);
        postParameters.put("name", name);
        postParameters.put("mobile", mobile);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(postParameters, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);
        if (responseEntity.getStatusCodeValue() != 200) {
            throw new BizException("Connect Lock api failed!");
        }

        LockResponseBodySovler responseBodySovler = JSON.parseObject(responseEntity.getBody(), LockResponseBodySovler.class);
        Map<String, String> res = responseBodySovler.getData();

        //log.info(res.get("customerPassword"));
        return res.get("customerPassword");
    }

}
