package com.whyyoufun.toolkit.easyexcel.exception;

/**
 * 写异常
 */
public class ExcelReadException extends ExcelException{

    public ExcelReadException(String message) {
        super(message);
    }

    public ExcelReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
