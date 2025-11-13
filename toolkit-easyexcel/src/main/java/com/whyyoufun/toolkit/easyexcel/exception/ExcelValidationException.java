package com.whyyoufun.toolkit.easyexcel.exception;

/**
 * 校验异常
 */
public class ExcelValidationException extends ExcelException{

    public ExcelValidationException(String message) {
        super(message);
    }

    public ExcelValidationException(String message,  Throwable cause) {
        super(message, cause);
    }
}
