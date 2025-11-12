package com.whyyoufun.toolkit.easyexcel.reader.processor;


import com.whyyoufun.toolkit.easyexcel.reader.result.SheetData;

import java.util.List;
import java.util.Map;

/**
 * 批次数据处理器
 */
public interface BatchDataProcessor<T> {

    /**
     * 处理批次数据
     * @param batchData
     */
    void processBatchData(List<Map<Integer, Object>> batchData, SheetData<T> currentSheetData);
}
