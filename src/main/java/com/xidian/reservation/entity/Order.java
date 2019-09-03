package com.xidian.reservation.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Order {
    private Integer orderId;

    private Long consumerId;

    private String orderName;

    private String roomName;

    private Long orderTel;

    private Date orderDate;

    private Date orderStartTime;

    private Date orderEndTime;

    private String orderPurpose;

    private Integer orderStatus;
}