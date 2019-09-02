package com.xidian.reservation.service.impl;

import com.xidian.reservation.dao.ManagerMapper;
import com.xidian.reservation.dto.TokenInfo;
import com.xidian.reservation.entity.Manager;
import com.xidian.reservation.exceptionHandler.Response.CacheResponseBody;
import com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody;
import com.xidian.reservation.service.ManagerService;
import com.xidian.reservation.utils.MD5Util;
import com.xidian.reservation.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author ：Maolin
 * @className ：ManagerServiceImpl
 * @date ：Created in 2019/9/1 16:49
 * @description： ManagerService 服务层，暂时没有需求使用
 * @version: 1.0
 */
@Slf4j
@Service
public class ManagerServiceImpl implements ManagerService {

    @Resource
    private ManagerMapper managerMapper;

    public UniversalResponseBody managerLogin(Manager loginData) throws Exception {
        Manager manager = managerMapper.selectByManagerId(loginData.getManagerId());
        if (manager == null) {
            return UniversalResponseBody.error("-2", "User does not exist!");
        } else if (!manager.getManagerPassword().equals(loginData.getManagerPassword())) {
            return UniversalResponseBody.error("-1", "Wrong password！");
        } else {

            String token = TokenUtil.getToken(manager.getManagerId());
            //密码进行加密输出
            manager.setManagerPassword(MD5Util.encrypt(manager.getManagerPassword()));
            return UniversalResponseBody.success(new TokenInfo<>(manager,token));
        }
    }

    public boolean saveManager(Manager manager) {
        return managerMapper.insert(manager) > 0;
    }

    public boolean deleteManager(Integer managerId) {
        return managerMapper.deleteByPrimaryKey(managerId) > 0;
    }

    public Manager findManagerById(Integer managerId){
        return managerMapper.selectByManagerId(managerId);
    }
}
