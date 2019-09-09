package com.xidian.reservation.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xidian.reservation.dao.ConsumerMapper;
import com.xidian.reservation.dao.WxInformationMapper;
import com.xidian.reservation.dto.TokenInfo;
import com.xidian.reservation.entity.Consumer;
import com.xidian.reservation.exceptionHandler.CommonEnum;
import com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody;
import com.xidian.reservation.service.ConsumerService;
import com.xidian.reservation.utils.MD5Util;
import com.xidian.reservation.utils.TokenUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

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

    @Resource
    private WxInformationMapper wxInformationMapper;


    public UniversalResponseBody consumerLogin(Consumer loginData) throws Exception {
        Consumer consumer = consumerMapper.selectByPrimaryKey(loginData.getConsumerId());
        if (consumer == null) {
            return UniversalResponseBody.error("-2", "User does not exist!");
        } else if (consumer.getConsumerPassword().equals(loginData.getConsumerPassword())
                && consumer.getConsumerName().equals(loginData.getConsumerName())) {
            String token = TokenUtil.getToken("" + consumer.getConsumerId());
            //密码进行加密输出
            consumer.setConsumerPassword(MD5Util.encrypt(consumer.getConsumerPassword()));
            return UniversalResponseBody.success(new TokenInfo<>(consumer, token));
        } else if (!consumer.getConsumerPassword().equals(loginData.getConsumerPassword())) {
            return UniversalResponseBody.error("-1", "wrong password!");
        } else if (!consumer.getConsumerName().equals(loginData.getConsumerName())) {
            return UniversalResponseBody.error("-3", "Please check if the name is correct！");
        } else {
            return UniversalResponseBody.error(CommonEnum.INTERNAL_SERVER_ERROR);
        }
    }

    public UniversalResponseBody saveConsumer(Consumer consumer) {
        Consumer res = consumerMapper.selectByPrimaryKey(consumer.getConsumerId());
        if (res != null) {
            return UniversalResponseBody.error("701", "User already exists!");
        } else if (consumerMapper.insert(consumer) > 0) {
            return UniversalResponseBody.success();
        } else {
            return UniversalResponseBody.error(CommonEnum.SQL_STATEMENT_ERROR);
        }
    }

    public UniversalResponseBody deleteConsumer(Long consumerId) {
      if (consumerMapper.deleteByPrimaryKey(consumerId) > 0) {
            return UniversalResponseBody.success();
        } else {
            return UniversalResponseBody.error("Failed user delete!");
        }
    }

    public Consumer findConsumerById(Long consumerId) {
        return consumerMapper.selectByPrimaryKey(consumerId);
    }

    public UniversalResponseBody findAllConsumers(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Consumer> pageInfo = new PageInfo<>(consumerMapper.selectAllConsumer());
        return UniversalResponseBody.success(pageInfo);
    }

}

