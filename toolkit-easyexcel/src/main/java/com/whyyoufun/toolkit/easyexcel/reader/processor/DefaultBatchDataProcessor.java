package com.whyyoufun.toolkit.easyexcel.reader.processor;


import com.whyyoufun.toolkit.easyexcel.reader.result.SheetData;

import java.util.List;
import java.util.Map;

/**
 * 默认批处理器
 */
public class DefaultBatchDataProcessor<T> implements BatchDataProcessor<T> {

    @Override
    public void processBatchData(List<Map<Integer, Object>> batchData, SheetData<T> currentSheetData) {
        //默认不处理
    }
}
