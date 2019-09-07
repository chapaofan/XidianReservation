package com.xidian.reservation.service;

import com.xidian.reservation.entity.Reserve;
import com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody;

import java.util.Date;

public interface ReserveService {

    UniversalResponseBody saveReserve(Reserve reserve);

    UniversalResponseBody deleteReserve(Integer reserveId);

    boolean updateReserve(Reserve reserve);

    UniversalResponseBody findHistoryReserves(Long consumerId, int pageNum, int pageSize);

    UniversalResponseBody findNotStartReserves(Long consumerId, int pageNum, int pageSize);

    UniversalResponseBody findNotStartAndStatus0(int pageNum, int pageSize);

    UniversalResponseBody selectNotAllowTime(int roomId, Date day) throws Exception;

    UniversalResponseBody findReserveDetails(Integer reserveId, String otherThing, String shortMessage);

    boolean updateStatus(Integer reserveId,Integer status);

    Reserve findReserveByReserveId(Integer reserveId);

}
