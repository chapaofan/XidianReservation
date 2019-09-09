package com.xidian.reservation.dao;

import com.xidian.reservation.entity.WxInformation;

public interface WxInformationMapper {

    int deleteByPrimaryKey(Integer reserveId);

    int insert(WxInformation record);

    int insertSelective(WxInformation record);

    WxInformation selectByPrimaryKey(Integer reserveId);

    int updateByPrimaryKeySelective(WxInformation record);

    int updateByPrimaryKey(WxInformation record);
}