package com.whyyoufun.toolkit.easyexcel.reader.result;

import com.whyyoufun.toolkit.easyexcel.validater.result.ErrorExcelData;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 多sheet读取结果
 * @param <T>
 */
@Data
public class ReadResult<T> {

    /**
     * 数据Map<sheetNo, SheetData>
     */
    private Map<Integer, SheetData<T>>  sheetDataMap =  new HashMap<>();

    public void addSheetData(int sheetNo, SheetData<T> sheetData) {
        this.sheetDataMap.put(sheetNo, sheetData);
    }

    public SheetData<T> getSheetData(int sheetNo) {
        return sheetDataMap.get(sheetNo);
    }
}
