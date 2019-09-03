package com.xidian.reservation.entity;

import lombok.Data;

@Data
public class Consumer {
    private Long consumerId;

    private String consumerName;

    private String consumerPassword;

}