package com.xidian.reservation.dao;

import com.xidian.reservation.entity.Reserve;

import java.util.Date;
import java.util.List;

public interface ReserveMapper {
    int deleteByPrimaryKey(Integer reserveId);

    int insert(Reserve record);

    int insertSelective(Reserve record);

    Reserve selectByPrimaryKey(Integer reserveId);

    List<Reserve> selectNotAllowTime(Integer roomId, Date reserveDate);

    int updateByPrimaryKeySelective(Reserve record);

    int updateByPrimaryKey(Reserve record);

}