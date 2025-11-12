package com.whyyoufun.toolkit.easyexcel.reader.result;

import com.whyyoufun.toolkit.easyexcel.validater.result.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * sheet数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SheetData<T> {
    /**
     * sheet编号
     */
    private int sheetNo;

    /**
     * sheet名
     */
    private String sheetName;

    /**
     * 数据列表
     */
    private List<T> data = new ArrayList<>();

    /**
     * 错误数据
     */
    private List<ValidationError> errors = new ArrayList<>();

    public SheetData(int sheetNo, String sheetName) {
        this.sheetNo = sheetNo;
        this.sheetName = sheetName;
    }

    public void addData(T item) {
        this.data.add(item);
    }

    public void addAllData(List<T> items) {
        this.data.addAll(items);
    }

    public void addError(ValidationError error) {
        this.errors.add(error);
    }

    public void addAllErrors(List<ValidationError> errors) {
        this.errors.addAll(errors);
    }
}
