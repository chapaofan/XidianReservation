package com.xidian.reservation.exceptionHandler.Response;

/**
 * @description: 用于缓存响应
 * @author: maolin
 * @create: 2019-04-16 17:18
 **/
public class CacheResponseBody<T> {

    private int errCode;

    private String session_key;

    private T result;

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public CacheResponseBody(String session_key) {
        this.session_key = session_key;
    }

    public CacheResponseBody(int errCode, String session_key, T result) {
        this.errCode = errCode;
        this.session_key = session_key;
        this.result = result;
    }

    public CacheResponseBody(){}

    public CacheResponseBody(int errCode, String session_key) {
        this.errCode = errCode;
        this.session_key = session_key;
        this.result = null;
    }
}
