package com.xidian.reservation.dto;

import lombok.Data;

import java.util.Map;

/**
 * @author ：Maolin
 * @className ：WxMssInfo
 * @date ：Created in 2019/9/7 15:31
 * @description： 模板消息dto
 * @version: 1.0
 */
@Data
public class WxMssInfo {

    //用户openid
    private String touser;

    //模版id
    private String template_id;

    //默认跳到小程序首页
    private String page = "";

    //收集到的用户formid
    //private String form_id;

    //放大那个推送字段
    private String emphasis_keyword = "";

    //推送文字
    private Map<String, TemplateData> data;
}
