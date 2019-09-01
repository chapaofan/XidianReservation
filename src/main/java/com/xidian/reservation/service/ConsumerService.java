package com.xidian.reservation.service;

import com.xidian.reservation.entity.Consumer;
import java.util.List;

public interface ConsumerService {

    boolean saveConsumer(Consumer consumer);

    boolean deleteConsumer(Integer consumerId);

    List<Consumer> findConsumers(int pageNum);
}
