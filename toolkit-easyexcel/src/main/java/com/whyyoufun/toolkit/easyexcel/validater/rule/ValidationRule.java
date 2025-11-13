package com.whyyoufun.toolkit.easyexcel.validater.rule;

import com.whyyoufun.toolkit.easyexcel.exception.ExcelValidationException;
import com.whyyoufun.toolkit.easyexcel.reader.param.ReadParams;

import java.util.Map;

/**
 * 校验规则
 */
public interface ValidationRule<T> {

    void init(ReadParams readParams);

    /**
     * 行校验
     * @param rowData 当前行数据
     * @param currentRowNum 当前行号
     * @return 错误原因
     */
    String validateRow(T rowData, int currentRowNum) throws ExcelValidationException;

}
