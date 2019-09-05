package com.xidian.reservation.dao;

import com.github.pagehelper.Page;
import com.xidian.reservation.entity.Consumer;

import java.util.List;

public interface ConsumerMapper {
    int deleteByPrimaryKey(Long consumerId);

    int insert(Consumer record);

    int insertSelective(Consumer record);

    Consumer selectByPrimaryKey(Long consumerId);

    int updateByPrimaryKeySelective(Consumer record);

    int updateByPrimaryKey(Consumer record);

    Page<Consumer> selectAllConsumer();
}