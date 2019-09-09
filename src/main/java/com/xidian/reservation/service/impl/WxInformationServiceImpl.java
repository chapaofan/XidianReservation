package com.xidian.reservation.service.impl;

import com.xidian.reservation.dao.WxInformationMapper;
import com.xidian.reservation.entity.WxInformation;
import com.xidian.reservation.service.WxInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ：Maolin
 * @className ：WxInformationServeImpl
 * @date ：Created in 2019/9/7 18:36
 * @description： WxInformationServe服务层
 * @version: 1.0
 */
@Slf4j
@Service
public class WxInformationServiceImpl implements WxInformationService {

    @Resource
    private WxInformationMapper wxInformationMapper;

    public boolean saveWxInformation(WxInformation wxInformation){
        return wxInformationMapper.insert(wxInformation)>0;
    }

    public WxInformation findByConsumerId(Integer reserveId){
        return wxInformationMapper.selectByPrimaryKey(reserveId);
    }
}
