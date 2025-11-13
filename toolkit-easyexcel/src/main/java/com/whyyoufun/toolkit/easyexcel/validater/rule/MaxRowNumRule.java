package com.whyyoufun.toolkit.easyexcel.validater.rule;


import com.whyyoufun.toolkit.easyexcel.exception.ExcelValidationException;
import com.whyyoufun.toolkit.easyexcel.reader.param.ReadParams;

/**
 * 最大行数校验规则
 */
public class MaxRowNumRule<T> implements ValidationRule<T> {
    private ReadParams readParams;

    @Override
    public void init(ReadParams readParams) {
        this.readParams = readParams;
    }

    @Override
    public String validateRow(T rowData, int currentRowNum) throws ExcelValidationException {
        if (currentRowNum > readParams.getMaxRowNum()){
            throw new ExcelValidationException("max row num");
        }
        return null;
    }
}
