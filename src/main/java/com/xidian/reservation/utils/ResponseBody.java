package com.xidian.reservation.utils;

/**
 * @author ：Maolin
 * @className ：ResponseBody
 * @date ：Created in 2019/9/1 12:31
 * @description： 请求响应体
 * @version: 1.0
 */
public class ResponseBody<T> {
    private int errCode;

    private String msg;

    private T data;

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseBody(int errCode, String msg, T data) {
        this.errCode = errCode;
        this.msg = msg;
        this.data = data;
    }

    public ResponseBody(int errCode, String msg) {
        this.errCode = errCode;
        this.msg = msg;
    }

}
