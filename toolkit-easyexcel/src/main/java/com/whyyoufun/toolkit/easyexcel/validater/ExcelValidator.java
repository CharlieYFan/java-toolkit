package com.whyyoufun.toolkit.easyexcel.validater;


import com.whyyoufun.toolkit.easyexcel.exception.ExcelValidationException;
import com.whyyoufun.toolkit.easyexcel.reader.param.ReadParams;
import com.whyyoufun.toolkit.easyexcel.validater.rule.ValidationRule;

import java.util.List;

public interface ExcelValidator<S> {

    /**
     *
     * @param readParams 读取参数
     * @param customValidationRules 自定义校验规则
     */
    void init(ReadParams readParams, List<ValidationRule<S>> customValidationRules);

    /**
     * 行校验
     * @param rowData 当前行数据
     * @param currentRowNum 当前行号
     * @return 错误原因
     */
    String validateRow(S rowData, int currentRowNum) throws ExcelValidationException;
}
