package com.xidian.reservation.dao;

import com.xidian.reservation.entity.Manager;

public interface ManagerMapper {

    int deleteByPrimaryKey(Integer managerId);

    int insert(Manager record);

    int insertSelective(Manager record);

    Manager selectByManagerId(Integer managerId);

    int updateByPrimaryKeySelective(Manager record);

    int updateByPrimaryKey(Manager record);
}