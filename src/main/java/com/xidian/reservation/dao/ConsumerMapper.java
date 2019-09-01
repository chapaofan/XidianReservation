package com.xidian.reservation.dao;

import com.xidian.reservation.entity.Consumer;

public interface ConsumerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Consumer record);

    int insertSelective(Consumer record);

    Consumer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Consumer record);

    int updateByPrimaryKey(Consumer record);
}