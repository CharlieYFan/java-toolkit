package com.whyyoufun.toolkit.easyexcel.validater;


import com.whyyoufun.toolkit.easyexcel.exception.ExcelValidationException;
import com.whyyoufun.toolkit.easyexcel.reader.param.ReadParams;
import com.whyyoufun.toolkit.easyexcel.validater.result.ValidationResult;

import java.util.Map;

public interface ExcelValidator {

    void init(ReadParams readParams);

    /**
     * 行校验
     * @param rowData 行数据
     * @param rowNum 行号
     * @return
     */
    ValidationResult validateRow(Map<Integer, Object> rowData, int rowNum) throws ExcelValidationException;
}
