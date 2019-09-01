package com.xidian.reservation.service.impl;

import com.xidian.reservation.dao.ConsumerMapper;
import com.xidian.reservation.entity.Consumer;
import com.xidian.reservation.service.ConsumerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：Maolin
 * @className ：ConsumerServiceImpl
 * @date ：Created in 2019/9/1 16:22
 * @description： ConsumerService 服务层
 * @version: 1.0
 */

@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Resource
    private ConsumerMapper consumerMapper;

    public boolean saveConsumer(Consumer consumer) {
        return consumerMapper.insert(consumer) > 0;
    }

    public boolean deleteConsumer(Integer consumerId) {
        return consumerMapper.deleteByPrimaryKey(consumerId) > 0;
    }

    public List<Consumer> findConsumers(int pageNum) {
        return null;//TODO 分页查询，mybatis分页插件实现
    }

}
