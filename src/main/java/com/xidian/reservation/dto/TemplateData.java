package com.xidian.reservation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：Maolin
 * @className ：TemplateData
 * @date ：Created in 2019/9/7 15:33
 * @description： 模板消息data
 * @version: 1.0
 */
@NoArgsConstructor
@Data
public class TemplateData {

    private String value;

    public TemplateData(String value) {
        this.value = value;
    }
}
