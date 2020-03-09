package com.xidian.reservation.service;

import com.alibaba.fastjson.JSON;
import com.xidian.reservation.dao.WxInformationMapper;
import com.xidian.reservation.dto.TemplateData;
import com.xidian.reservation.dto.WxAccessToken;
import com.xidian.reservation.dto.WxMssInfo;
import com.xidian.reservation.utils.WeChatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：Maolin
 * @className ：WxPushService
 * @date ：Created in 2019/9/7 15:36
 * @description： 推送模板消息
 * @version: 1.0
 */
@Service
@Slf4j
public class WxPushService {

    private static RestTemplate restTemplate = new RestTemplate();

    @Value("${wx.appid}")
    private String APPID;

    @Value("${wx.secret}")
    private String APPSECRET;

    @Value("${wx.templateId}")
    private String TEMPLATEID;

    @Resource
    private WeChatUtil weChatUtil;

    @Resource
    private WxInformationMapper wxInformationMapper;

    /**
     * 微信小程序推送单个用户
     */
    public void wxPushOneUser(Integer reserveId, String roomName, String reserveName, String reserveResult, String reserveDatetime, String remarks) throws Exception {


        String openid = wxInformationMapper.selectByPrimaryKey(reserveId).getOpenId();

        //获取access_token
        String access_token = getAccess_token(APPID, APPSECRET);
        //String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + access_token;
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + access_token;
        //拼接推送的模版
        WxMssInfo wxMssInfo = new WxMssInfo();
        wxMssInfo.setTouser(openid);//用户openid
        wxMssInfo.setTemplate_id(TEMPLATEID);//模版id
        //wxMssInfo.setForm_id(formId);//formid

        Map<String, TemplateData> messageData = new HashMap<>();

        /**
         *预约地点 {{keyword1.DATA}}
         *预约人{{keyword2.DATA}}
         *预约结果{{keyword3.DATA}}
         *预约时间{{keyword4.DATA}}
         *备注{{keyword5.DATA}}
         */
        TemplateData keyword1 = new TemplateData();
        keyword1.setValue(roomName);
        messageData.put("keyword1", keyword1);


        TemplateData keyword2 = new TemplateData();
        keyword2.setValue(reserveName);
        messageData.put("keyword2", keyword2);


        TemplateData keyword3 = new TemplateData();
        keyword3.setValue(reserveResult);
        messageData.put("keyword3", keyword3);

        TemplateData keyword4 = new TemplateData();
        keyword4.setValue(reserveDatetime);
        messageData.put("keyword4", keyword4);

        TemplateData keyword5 = new TemplateData();
        keyword5.setValue(remarks);
        messageData.put("keyword5", keyword5);

        wxMssInfo.setData(messageData);

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(url, wxMssInfo, String.class);
        log.info("小程序推送结果={}", responseEntity.getBody());
        responseEntity.getBody();
    }



    /**
     * 获取access_token
     */
    private String getAccess_token(String appId, String appSecret) throws IOException {
        //获取access_token
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
                "&appid=" + appId + "&secret=" + appSecret;

        String json = restTemplate.getForObject(url, String.class);
        WxAccessToken accessToken = JSON.parseObject(json, WxAccessToken.class);

        return accessToken.getAccess_token();
    }
}