package com.whyyoufun.toolkit.easyexcel.validater.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 校验错误
 */
@Getter
@Setter
@AllArgsConstructor
public class ValidationError {
    /**
     * 行号
     */
    private int rowNum;

    /**
     * 错误信息
     */
    private String message;

    private String field;

    private Object value;

    public ValidationError(int rowNum, String message) {
        this.rowNum = rowNum;
        this.message = message;
    }
}
