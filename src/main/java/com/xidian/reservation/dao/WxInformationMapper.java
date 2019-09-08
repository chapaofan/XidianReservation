package com.xidian.reservation.dao;

import com.xidian.reservation.entity.WxInformation;

public interface WxInformationMapper {
    int deleteByPrimaryKey(Long consumerId);

    int insert(WxInformation record);

    int insertSelective(WxInformation record);

    WxInformation selectByPrimaryKey(Long consumerId);

    int updateByPrimaryKeySelective(WxInformation record);

    int updateByPrimaryKey(WxInformation record);
}