package com.xidian.reservation.service;

import com.xidian.reservation.entity.Order;

import java.util.List;

public interface OrderService {

    boolean saveOrder(Order order);

    boolean deleteOrder(Integer orderId);

    boolean updateOrder(Order order);

    List<Order> findHistoryOrders(Long consumerId,int pageNum);

    List<Order> findNotStartOrders(Long consumerId,int pageNum);

}
