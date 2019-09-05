package com.xidian.reservation.service;

import com.xidian.reservation.entity.Consumer;
import com.xidian.reservation.exceptionHandler.Response.CacheResponseBody;
import com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody;

import java.sql.SQLException;
import java.util.List;

public interface ConsumerService {

    UniversalResponseBody consumerLogin(Consumer consumer) throws Exception;

    UniversalResponseBody saveConsumer(Consumer consumer);

    UniversalResponseBody deleteConsumer(Long consumerId);

    Consumer findConsumerById(Long consumerId);

    UniversalResponseBody findAllConsumers(int pageNum, int pageSize);
}
