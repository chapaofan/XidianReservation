package com.xidian.reservation.service;

import com.xidian.reservation.entity.Consumer;
import com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody;

public interface ConsumerService {

    UniversalResponseBody consumerLogin(Consumer loginData) throws Exception;

    UniversalResponseBody saveConsumer(Consumer consumer);

    UniversalResponseBody deleteConsumer(Long consumerId);

    Consumer findConsumerById(Long consumerId);

    UniversalResponseBody findAllConsumers(int pageNum, int pageSize);
}
