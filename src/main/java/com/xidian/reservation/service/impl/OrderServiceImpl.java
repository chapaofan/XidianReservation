package com.xidian.reservation.service.impl;

import com.xidian.reservation.dao.OrderMapper;
import com.xidian.reservation.entity.Order;
import com.xidian.reservation.service.OrderService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：Maolin
 * @className ：OrderServiceImpl
 * @date ：Created in 2019/9/1 18:54
 * @description： OrderService 服务层
 * @version: 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    public boolean saveOrder(Order order){
        return orderMapper.insert(order)>0;
    }

    public boolean deleteOrder(Integer orderId){
        return orderMapper.deleteByPrimaryKey(orderId)>0;
    }

    public boolean updateOrder(Order order){
        return orderMapper.updateByPrimaryKey(order) > 0;
    }

    public List<Order> findHistoryOrders(Integer consumerId, int pageNum){
        return null;//TODO return selectHistoryOrders;
    }

    public List<Order> findNotStartOrders(Integer consumerId,int pageNum){
        return null;//TODO return orderMapper.selectNotStartOrders;
    }
}
