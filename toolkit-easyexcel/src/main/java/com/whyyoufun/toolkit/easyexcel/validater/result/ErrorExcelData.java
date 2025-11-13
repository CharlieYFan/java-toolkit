package com.whyyoufun.toolkit.easyexcel.validater.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 校验不通过的数据
 */
@Getter
@Setter
public class ErrorExcelData<T> {
    /**
     * 行号
     */
    private int rowNum;

    /**
     * 错误信息（具体哪个字段不正确都可以放在错误信息）
     */
    private String message;

    /**
     * 错误数据（错误行原始数据）
     */
    private T errorData;

    public ErrorExcelData(int rowNum, String message, T errorData) {
        this.rowNum = rowNum;
        this.message = message;
        this.errorData = errorData;
    }
}
