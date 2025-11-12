package com.whyyoufun.toolkit.easyexcel.validater.rule;


import com.whyyoufun.toolkit.easyexcel.reader.param.ReadParams;
import com.whyyoufun.toolkit.easyexcel.validater.result.ValidationError;
import com.whyyoufun.toolkit.easyexcel.validater.result.ValidationResult;

import java.util.Map;

/**
 * 空行校验
 */
public class EmptyRowRule implements ValidationRule{
    private ReadParams readParams;

    @Override
    public void init(ReadParams readParams) {
        this.readParams = readParams;
    }

    @Override
    public ValidationResult validateRow(Map<Integer, Object> rowData, int rowNum) {
        ValidationResult validationResult = new ValidationResult();
        if (isEmptyRow(rowData)) {
            ValidationError error = new ValidationError(rowNum,"Empty Row Skipped");
            validationResult.addError(error);
        }
        return validationResult;
    }

    /**
     * 空行校验逻辑
     */
    private boolean isEmptyRow(Map<Integer, Object> rowData) {
        return rowData == null || rowData.isEmpty();
    }
}
