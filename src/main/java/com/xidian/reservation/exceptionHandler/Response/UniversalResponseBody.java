package com.xidian.reservation.exceptionHandler.Response;

import com.alibaba.fastjson.JSONObject;
import com.xidian.reservation.exceptionHandler.BaseErrorInfoInterface;
import com.xidian.reservation.exceptionHandler.CommonEnum;

/**
 * @author ：Maolin
 * @className ：ResultBody
 * @date ：Created in 2019/9/2 9:06
 * @description： 自定义响应体
 * @version: 1.0
 */
public class UniversalResponseBody {
    /**
     * 响应代码
     */
    private String errCode;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应结果
     */
    private Object result;

    public UniversalResponseBody() {
    }

    public UniversalResponseBody(BaseErrorInfoInterface errorInfo) {
        this.errCode = errorInfo.getErrCode();
        this.message = errorInfo.getResultMsg();
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    /**
     * 成功
     *
     * @return
     */
    public static UniversalResponseBody success() {
        return success(null);
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public static UniversalResponseBody success(Object data) {
        UniversalResponseBody rb = new UniversalResponseBody();
        rb.setErrCode(CommonEnum.SUCCESS.getErrCode());
        rb.setMessage(CommonEnum.SUCCESS.getResultMsg());
        rb.setResult(data);
        return rb;
    }

    /**
     * 失败
     */
    public static UniversalResponseBody error(BaseErrorInfoInterface errorInfo) {
        UniversalResponseBody rb = new UniversalResponseBody();
        rb.setErrCode(errorInfo.getErrCode());
        rb.setMessage(errorInfo.getResultMsg());
        rb.setResult(null);
        return rb;
    }

    /**
     * 失败
     */
    public static UniversalResponseBody error(String code, String message) {
        UniversalResponseBody rb = new UniversalResponseBody();
        rb.setErrCode(code);
        rb.setMessage(message);
        rb.setResult(null);
        return rb;
    }

    /**
     * 失败
     */
    public static UniversalResponseBody error(String message) {
        UniversalResponseBody rb = new UniversalResponseBody();
        rb.setErrCode("-1");
        rb.setMessage(message);
        rb.setResult(null);
        return rb;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
