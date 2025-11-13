package com.whyyoufun.toolkit.easyexcel.reader.processor;


import com.whyyoufun.toolkit.easyexcel.converter.DataConverter;
import com.whyyoufun.toolkit.easyexcel.reader.result.SheetData;

import java.util.List;

/**
 * 批次数据处理器
 */
public interface BatchDataProcessor<S,T> {

    /**
     * 处理批次数据
     * @param batchData 原始批次数据
     * @param currentSheetData 当前sheet数据
     * @param converter 数据转换器
     */
    void processBatchData(List<S> batchData, SheetData<T> currentSheetData, DataConverter<S, T> converter);
}
