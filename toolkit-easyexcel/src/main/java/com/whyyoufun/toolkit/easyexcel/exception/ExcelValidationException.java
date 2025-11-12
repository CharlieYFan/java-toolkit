package com.whyyoufun.toolkit.easyexcel.exception;

import com.whyyoufun.toolkit.easyexcel.validater.result.ValidationResult;

/**
 * 校验异常
 */
public class ExcelValidationException extends ExcelException{
    private final ValidationResult validationResult;

    public ExcelValidationException(String message, ValidationResult validationResult) {
        super(message);
        this.validationResult = validationResult;
    }

    public ExcelValidationException(String message, ValidationResult validationResult, Throwable cause) {
        super(message, cause);
        this.validationResult = validationResult;
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }
}
