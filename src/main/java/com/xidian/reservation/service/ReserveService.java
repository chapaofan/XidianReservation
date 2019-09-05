package com.xidian.reservation.service;

import com.xidian.reservation.entity.Reserve;
import com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ReserveService {

    UniversalResponseBody saveReserve(Reserve reserve);

    UniversalResponseBody deleteReserve(Integer reserveId);

    UniversalResponseBody updateReserve(Reserve reserve);

    List<Reserve> findHistoryReserves(Long consumerId, int pageNum);

    List<Reserve> findNotStartReserves(Long consumerId, int pageNum);

    UniversalResponseBody selectNotAllowTime(int roomId, Date day) throws Exception;

    boolean allowInsert(Reserve reserve) throws Exception;

}
