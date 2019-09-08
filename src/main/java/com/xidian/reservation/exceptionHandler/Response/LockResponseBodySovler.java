package com.xidian.reservation.exceptionHandler.Response;

import lombok.Data;

import java.util.Map;

/**
 * @author ：Maolin
 * @className ：LockResponseBody
 * @date ：Created in 2019/9/8 10:08
 * @description： 智能锁api响应体
 * @version: 1.0
 */
@Data
public class LockResponseBodySovler<T> {

    private int code;

    private String message;

    private Map<String, String> data;

    public LockResponseBodySovler(int code, String message, Map<String, String> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public LockResponseBodySovler(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
