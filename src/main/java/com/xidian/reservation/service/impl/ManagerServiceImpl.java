package com.xidian.reservation.service.impl;

import com.xidian.reservation.dao.ManagerMapper;
import com.xidian.reservation.entity.Manager;
import com.xidian.reservation.service.ManagerService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @author ：Maolin
 * @className ：ManagerServiceImpl
 * @date ：Created in 2019/9/1 16:49
 * @description： ManagerService 服务层，暂时没有需求使用
 * @version: 1.0
 */
@Service
public class ManagerServiceImpl implements ManagerService {

    @Resource
    private ManagerMapper managerMapper;

    public boolean saveManager(Manager manager) {
        return managerMapper.insert(manager) > 0;
    }

    public boolean deleteManager(Integer managerId) {
        return managerMapper.deleteByPrimaryKey(managerId) > 0;
    }
}
