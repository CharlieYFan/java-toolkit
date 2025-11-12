package com.whyyoufun.toolkit.easyexcel.validater.rule;


import com.whyyoufun.toolkit.easyexcel.exception.ExcelValidationException;
import com.whyyoufun.toolkit.easyexcel.reader.param.ReadParams;
import com.whyyoufun.toolkit.easyexcel.validater.result.ValidationError;
import com.whyyoufun.toolkit.easyexcel.validater.result.ValidationResult;

import java.util.Map;

/**
 * 最大行数校验规则
 */
public class MaxRowNumRule implements ValidationRule {
    private ReadParams readParams;

    @Override
    public void init(ReadParams readParams) {
        this.readParams = readParams;
    }

    @Override
    public ValidationResult validateRow(Map<Integer, Object> rowData, int rowNum) {
        ValidationResult validationResult = new ValidationResult();
        if (rowNum > readParams.getMaxRowNum()) {
            ValidationError error = new ValidationError(rowNum,"max row num");
            validationResult.addError(error);
            //校验不通过抛出异常停止后续读取
            throw new ExcelValidationException("max row num", validationResult);
        }
        return validationResult;
    }
}
