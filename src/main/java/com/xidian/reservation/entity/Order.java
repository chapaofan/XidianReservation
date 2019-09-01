package com.xidian.reservation.entity;

import java.util.Date;

public class Order {
    private Integer id;

    private String userNo;

    private String roomName;

    private String 
orderName;

    private Integer orderTel;

    private Date orderDate;

    private Date orderStartTime;

    private Date orderEndTime;

    private String orderPurpose;

    private Integer orderStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName == null ? null : roomName.trim();
    }

    public String get
orderName() {
        return 
orderName;
    }

    public void set
orderName(String 
orderName) {
        this.
orderName = 
orderName == null ? null : 
orderName.trim();
    }

    public Integer getOrderTel() {
        return orderTel;
    }

    public void setOrderTel(Integer orderTel) {
        this.orderTel = orderTel;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getOrderStartTime() {
        return orderStartTime;
    }

    public void setOrderStartTime(Date orderStartTime) {
        this.orderStartTime = orderStartTime;
    }

    public Date getOrderEndTime() {
        return orderEndTime;
    }

    public void setOrderEndTime(Date orderEndTime) {
        this.orderEndTime = orderEndTime;
    }

    public String getOrderPurpose() {
        return orderPurpose;
    }

    public void setOrderPurpose(String orderPurpose) {
        this.orderPurpose = orderPurpose == null ? null : orderPurpose.trim();
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}