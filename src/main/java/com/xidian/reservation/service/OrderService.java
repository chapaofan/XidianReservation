package com.xidian.reservation.service;

import com.xidian.reservation.entity.Order;
import com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody;

import java.util.List;

public interface OrderService {

    UniversalResponseBody saveOrder(Order order);

    UniversalResponseBody deleteOrder(Integer orderId);

    UniversalResponseBody updateOrder(Order order);

    List<Order> findHistoryOrders(Long consumerId, int pageNum);

    List<Order> findNotStartOrders(Long consumerId,int pageNum);

}
