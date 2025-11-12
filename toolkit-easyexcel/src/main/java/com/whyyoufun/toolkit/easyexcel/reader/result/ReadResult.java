package com.whyyoufun.toolkit.easyexcel.reader.result;

import com.whyyoufun.toolkit.easyexcel.validater.result.ValidationError;
import com.whyyoufun.toolkit.easyexcel.validater.result.ValidationResult;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 读取结果
 * @param <T>
 */
@Data
public class ReadResult<T> {

    /**
     * 数据Map<sheetNo, SheetData>
     */
    private Map<Integer, SheetData<T>>  sheetDataMap =  new HashMap<>();

    /**
     * 校验结果
     */
    private ValidationResult validationResult = new ValidationResult();

    /**
     * 行数
     */
    private long totalRowCount;
    private long successRowCount;
    private long failRowCount;

    public void addSheetData(int sheetNo, SheetData<T> sheetData) {
        this.sheetDataMap.put(sheetNo, sheetData);
    }

    public SheetData<T> getSheetData(int sheetNo) {
        return sheetDataMap.get(sheetNo);
    }

    public void addValidateError(ValidationError validationError) {
        this.validationResult.addError(validationError);
    }

    public void addValidationErrors(ValidationResult validationResult) {
        this.validationResult.addErrors(validationResult.getErrors());
    }

    public void incrementSuccessRowCount() {
        this.successRowCount++;
    }

    public void incrementFailRowCount() {
        this.failRowCount++;
    }

    public void incrementTotalRowCount() {
        this.totalRowCount++;
    }

    public boolean hasError() {
        return validationResult != null && validationResult.hasErrors();
    }
}
