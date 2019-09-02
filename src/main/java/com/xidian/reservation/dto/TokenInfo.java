package com.xidian.reservation.dto;

import lombok.Data;

/**
 * @author ：Maolin
 * @className ：TokenInfo
 * @date ：Created in 2019/9/2 20:40
 * @description： token info
 * @version: 1.0
 */
@Data
public class TokenInfo<T> {

    private T data;

    private String token;

    public TokenInfo(T data, String token) {
        this.data = data;
        this.token = token;
    }

    public TokenInfo(String token) {
        this.token = token;
    }
}