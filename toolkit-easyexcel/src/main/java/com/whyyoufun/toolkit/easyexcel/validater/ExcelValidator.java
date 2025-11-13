package com.whyyoufun.toolkit.easyexcel.validater;


import com.whyyoufun.toolkit.easyexcel.exception.ExcelValidationException;
import com.whyyoufun.toolkit.easyexcel.reader.param.ReadParams;


public interface ExcelValidator<S> {

    void init(ReadParams readParams);

    /**
     * 行校验
     * @param rowData 当前行数据
     * @param currentRowNum 当前行号
     * @return 错误原因
     */
    String validateRow(S rowData, int currentRowNum) throws ExcelValidationException;
}
