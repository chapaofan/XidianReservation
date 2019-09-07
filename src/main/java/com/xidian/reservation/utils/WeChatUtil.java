package com.xidian.reservation.utils;

import com.alibaba.fastjson.JSON;
import com.xidian.reservation.dto.WxResponseInfo;
import com.xidian.reservation.exceptionHandler.BizException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author ：Maolin
 * @className ：WeChatUtil
 * @date ：Created in 2019/9/7 16:01
 * @description： 微信登录获取数据工具包
 * @version: 1.0
 */
@Component
public class WeChatUtil {
    @Value("${wx.url}")
    private String WECHAT_OPENID_URL;

    private static RestTemplate restTemplate = new RestTemplate();

    public WxResponseInfo getWeChatResponseBody(String code) throws Exception {

        String url = WECHAT_OPENID_URL + code;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        if (responseEntity.getStatusCodeValue() != 200) {
            throw new BizException("Connect weChat failed");
        }

        WxResponseInfo wxResponseInfo = JSON.parseObject(responseEntity.getBody(), WxResponseInfo.class);
        return wxResponseInfo;
    }

    public String getOpenId(String code) throws Exception{
        return getWeChatResponseBody(code).getOpenid();
    }
}
