package com.xidian.reservation.dto;

import lombok.Data;

/**
 * @author ：Maolin
 * @className ：WxResponseInfo
 * @date ：Created in 2019/9/7 15:59
 * @description： 微信返回数据对象
 * @version: 1.0
 */
@Data
public class WxResponseInfo {

    private String openid;

    private String session_key;

    private String unionid;

    private String errcode;

    private String errmsg;
}
