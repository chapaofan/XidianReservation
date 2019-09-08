package com.xidian.reservation.entity;

import lombok.Data;

@Data
public class WxInformation {
    private Long consumerId;

    private String openId;

    public WxInformation(Long consumerId, String openId) {
        this.consumerId = consumerId;
        this.openId = openId;
    }
}