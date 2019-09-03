package com.xidian.reservation.dao;

import com.xidian.reservation.entity.Manager;

public interface ManagerMapper {

    int deleteByPrimaryKey(Long managerId);

    int insert(Manager record);

    int insertSelective(Manager record);

    Manager selectByManagerId(Long managerId);

    int updateByPrimaryKeySelective(Manager record);

    int updateByPrimaryKey(Manager record);
}