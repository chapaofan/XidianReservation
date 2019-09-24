package com.xidian.reservation.dao;

import com.github.pagehelper.Page;
import com.xidian.reservation.entity.Reserve;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ReserveMapper {

    int deleteByPrimaryKey(Integer reserveId);

    int insert(Reserve record);

    int insertSelective(Reserve record);

    Reserve selectByPrimaryKey(Integer reserveId);

    List<Reserve> selectNotAllowTime(Integer roomId, Date reserveDate,Integer reserveId);

    int updateByPrimaryKeySelective(Reserve record);

    int updateByPrimaryKey(Reserve record);

    Page<Reserve> findHistoryReserveByConsumerId(Long consumerId);

    Page<Reserve> findNotStartReserveByConsumer(Long consumerId);

    Page<Reserve> findNotStartAndStatus0();

    Page<Reserve> findNotStartAndStatus0ByRoom(String roomName);

    List<Reserve> findByDate(Date reserveDate);

    List<Reserve> findReserveByDateTime(Integer roomId,Date reserveDate,String startTime,String endTime);
}