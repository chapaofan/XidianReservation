package com.xidian.reservation.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reserveDate;

    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    @DateTimeFormat(pattern = "HH:mm")
    private Date reserveStart;

    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    @DateTimeFormat(pattern = "HH:mm")
    private Date reserveEnd;

    private String reservePurpose;

    //0 初始状态  100 通过审核  500未通过审核
    private Integer reserveStatus;

    public Reserve(Integer reserveId, Long consumerId, String reserveName, String roomName, Long reserveTel, Date reserveDate, Date reserveStart, Date reserveEnd, String reservePurpose, Integer reserveStatus) {
        this.reserveId = reserveId;
        this.consumerId = consumerId;
        this.reserveName = reserveName;
        this.roomName = roomName;
        this.reserveTel = reserveTel;
        this.reserveDate = reserveDate;
        this.reserveStart = reserveStart;
        this.reserveEnd = reserveEnd;
        this.reservePurpose = reservePurpose;
        this.reserveStatus = reserveStatus;
    }

    public Reserve() {
    }
}