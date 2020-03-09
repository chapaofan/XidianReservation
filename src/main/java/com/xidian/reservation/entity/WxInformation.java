package com.xidian.reservation.entity;

import lombok.Data;

@Data
public class WxInformation {
    private Integer reserveId;

    private Long consumerId;

    private String openId;

    private String formId;

    public WxInformation(Integer reserveId, Long consumerId, String openId, String formId) {
        this.reserveId = reserveId;
        this.consumerId = consumerId;
        this.openId = openId;
        this.formId = formId;
    }


    public WxInformation(Integer reserveId, Long consumerId, String openId) {
        this.reserveId = reserveId;
        this.consumerId = consumerId;
        this.openId = openId;
        this.formId = "";
    }
}