package com.xidian.reservation.exceptionHandler;

import com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * @author ：Maolin
 * @className ：GlobalExceptionHandler
 * @date ：Created in 2019/9/2 9:08
 * @description： 自定义全局异常处理
 * @version: 1.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义的业务异常
     * @param req
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BizException.class)
    public UniversalResponseBody bizExceptionHandler(HttpServletRequest req, BizException e){
        log.error("发生业务异常！原因是：{}",e.getErrorMsg());
        return UniversalResponseBody.error(e.getErrorCode(),e.getErrorMsg());
    }

    /**
     * 处理空指针的异常
     * @param req
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value =NullPointerException.class)
    public UniversalResponseBody exceptionHandler(HttpServletRequest req, NullPointerException e){
        log.error("发生空指针异常！原因是:",e);
        return UniversalResponseBody.error(CommonEnum.BODY_NOT_MATCH);
    }


    /**
     * 处理其他异常
     * @param req
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value =Exception.class)
    public UniversalResponseBody exceptionHandler(HttpServletRequest req, Exception e){
        log.error("未知异常！原因是:",e);
        return UniversalResponseBody.error(CommonEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 处理SQL语句异常
     * @param req
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value =SQLException.class)
    public UniversalResponseBody exceptionHandler(HttpServletRequest req, SQLException e){
        log.error("mysql语句异常，原因：",e);
        return UniversalResponseBody.error(CommonEnum.SQL_STATEMENT_ERROR);
    }
    //DuplicateKeyException
      //      SQLIntegrityConstraintViolationException
}
