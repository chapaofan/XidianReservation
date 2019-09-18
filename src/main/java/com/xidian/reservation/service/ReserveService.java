package com.xidian.reservation.service;

import com.xidian.reservation.entity.Reserve;
import com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody;

import java.util.Date;

public interface ReserveService {

    UniversalResponseBody reserveRoom(Reserve reserve, String formId, String code) throws Exception;

    UniversalResponseBody deleteReserve(Integer reserveId);

    UniversalResponseBody changeReserve(Reserve reserve, String formId, String reserveDateTime, String reserveResult, String WxMSS)throws Exception;

    UniversalResponseBody findHistoryReserves(Long consumerId, int pageNum, int pageSize);

    UniversalResponseBody findNotStartReserves(Long consumerId, int pageNum, int pageSize);

    UniversalResponseBody findNotStartAndStatus0(int pageNum, int pageSize);

    UniversalResponseBody selectNotAllowTime(int roomId, Date day) throws Exception;

    UniversalResponseBody findReserveDetails(Integer reserveId, String otherThing, String shortMessage);

    boolean updateStatus(Integer reserveId, Integer status);

    Reserve findReserveByReserveId(Integer reserveId);

}
