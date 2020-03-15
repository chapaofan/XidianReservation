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
import java.util.LinkedHashMap;
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

        Map<String, TemplateData> messageData = new LinkedHashMap<>();


//         {{thing2.DATA}}
//         预约人
//         {{name1.DATA}}
//         预约结果
//         {{phrase9.DATA}}
//         预约时间
//         {{date3.DATA}}
//         备注
//         {{thing7.DATA}}
        messageData.put("thing2", new TemplateData(roomName));
        messageData.put("name1", new TemplateData(reserveName));
        messageData.put("phrase9", new TemplateData(reserveResult));
        messageData.put("date3", new TemplateData(reserveDatetime));
        messageData.put("thing7", new TemplateData(remarks));

        wxMssInfo.setData(messageData);
        //log.info(messageData.toString());

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(url, wxMssInfo, String.class);
        //log.info(wxMssInfo.toString());
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