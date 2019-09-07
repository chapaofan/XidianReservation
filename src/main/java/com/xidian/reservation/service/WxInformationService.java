package com.xidian.reservation.service;

import com.xidian.reservation.entity.WxInformation;

/**
 * @author ：Maolin
 * @className ：WxInformationServe
 * @date ：Created in 2019/9/7 18:34
 * @description： 微信存储openId服务层
 * @version: 1.0
 */
public interface WxInformationService {

    boolean saveWxInformation(WxInformation wxInformation);

    WxInformation findByConsumerId(Long consumerId);
}
