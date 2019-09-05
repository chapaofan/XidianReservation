package com.xidian.reservation.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Data
public class Reserve {
    private Integer reserveId;

    private Long consumerId;

    private String reserveName;

    private String roomName;

    private Long reserveTel;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reserveDate;

    @DateTimeFormat(pattern = "HH:mm")
    private Date reserveStart;

    @DateTimeFormat(pattern = "HH:mm")
    private Date reserveEnd;

    private String reservePurpose;

    private Integer reserveStatus;

}