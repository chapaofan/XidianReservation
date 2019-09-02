package com.xidian.reservation.service.impl;

import com.xidian.reservation.dao.ConsumerMapper;
import com.xidian.reservation.dto.TokenInfo;
import com.xidian.reservation.entity.Consumer;
import com.xidian.reservation.exceptionHandler.Response.CacheResponseBody;
import com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody;
import com.xidian.reservation.service.ConsumerService;
import com.xidian.reservation.utils.MD5Util;
import com.xidian.reservation.utils.TokenUtil;
import org.springframework.cache.annotation.CachePut;
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


    public UniversalResponseBody consumerLogin(Consumer loginData) throws Exception {
            Consumer consumer = consumerMapper.selectByPrimaryKey(loginData.getConsumerId());
            if (consumer == null) {
                return UniversalResponseBody.error("-2", "User does not exist!");
            } else if (!consumer.getConsumerPassword().equals(loginData.getConsumerPassword())) {
                return UniversalResponseBody.error("-1", "wrong password!");
            } else if (!consumer.getConsumerName().equals(loginData.getConsumerName())) {
                return UniversalResponseBody.error("-3", "Please check if the name is correct！");
            }else {
                String token = TokenUtil.getToken(consumer.getConsumerId());
                //密码进行加密输出
                consumer.setConsumerPassword(MD5Util.encrypt(consumer.getConsumerPassword()));
                return UniversalResponseBody.success(new TokenInfo<>(consumer,token));
            }
        }

    public boolean saveConsumer(Consumer consumer) {
        return consumerMapper.insert(consumer) > 0;
    }

    public boolean deleteConsumer(Integer consumerId) {
        return consumerMapper.deleteByPrimaryKey(consumerId) > 0;
    }

    public Consumer findConsumerById(Integer consumerId){
        return consumerMapper.selectByPrimaryKey(consumerId);
    }

    public List<Consumer> findConsumers(int pageNum) {
        return null;//TODO 分页查询，mybatis分页插件实现
    }

}
