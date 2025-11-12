package com.whyyoufun.toolkit.easyexcel.exception;

/**
 * 读异常
 */
public class ExcelWriteException extends ExcelException {

    public ExcelWriteException(String message) {
        super(message);
    }

    public ExcelWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
