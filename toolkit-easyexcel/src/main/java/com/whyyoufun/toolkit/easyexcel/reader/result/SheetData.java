package com.whyyoufun.toolkit.easyexcel.reader.result;

import com.whyyoufun.toolkit.easyexcel.validater.result.ErrorExcelData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 单sheet数据
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
     * 数据列表(全部)
     */
    private List<T> totalData = new ArrayList<>();

    /**
     * 数据列表（校验不通过）
     */
    private List<ErrorExcelData<T>> errorData = new ArrayList<>();

    /**
     * 数据列表（校验通过）
     */
    private List<T> successData = new ArrayList<>();


    public SheetData(int sheetNo, String sheetName) {
        this.sheetNo = sheetNo;
        this.sheetName = sheetName;
    }

    public void addSuccessData(T item) {
        this.totalData.add(item);
        this.successData.add(item);
    }

    public void addErrorData(ErrorExcelData<T> errorExcelData) {
        this.totalData.add(errorExcelData.getErrorData());
        errorData.add(errorExcelData);
    }
}
