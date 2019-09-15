package com.xidian.reservation.dto;

import com.xidian.reservation.entity.Reserve;
import lombok.Data;
import java.util.Date;

/**
 * @author ：Maolin
 * @className ：ReserveInfo
 * @date ：Created in 2019/9/7 9:39
 * @description： 申请详情dto
 * @version: 1.0
 */
@Data
public class ReserveInfo extends Reserve {

    private String otherThing;

    private String shortMessage;

    public ReserveInfo(Integer reserveId, Long consumerId, String reserveName, String roomName, Long reserveTel, Date reserveDate,
                       Date reserveStart, Date reserveEnd, String reservePurpose, Integer reserveStatus, String otherThing, String shortMessage,String openPwd) {
        super(reserveId, consumerId, reserveName, roomName, reserveTel, reserveDate, reserveStart, reserveEnd, reservePurpose, reserveStatus,openPwd);
        this.otherThing = otherThing;
        this.shortMessage = shortMessage;
    }

    public ReserveInfo() {
    }

    public ReserveInfo(Reserve reserve, String otherThing, String shortMessage) {
        super(reserve.getReserveId(),reserve.getConsumerId(),reserve.getReserveName(),reserve.getRoomName(),reserve.getReserveTel(),
                reserve.getReserveDate(),reserve.getReserveStart(),reserve.getReserveEnd(),reserve.getReservePurpose(),reserve.getReserveStatus(),reserve.getOpenPwd());
        this.otherThing = otherThing;
        this.shortMessage = shortMessage;
    }
}
