package com.whyyoufun.toolkit.easyexcel.validater.rule;

import com.whyyoufun.toolkit.easyexcel.exception.ExcelValidationException;
import com.whyyoufun.toolkit.easyexcel.reader.param.ReadParams;
import com.whyyoufun.toolkit.easyexcel.validater.result.ValidationResult;

import java.util.Map;

/**
 * 校验规则
 */
public interface ValidationRule {

    void init(ReadParams readParams);

    /**
     * 行校验
     * @param rowData
     * @param rowNum
     * @return
     */
    ValidationResult validateRow(Map<Integer, Object> rowData, int rowNum) throws ExcelValidationException;

}
