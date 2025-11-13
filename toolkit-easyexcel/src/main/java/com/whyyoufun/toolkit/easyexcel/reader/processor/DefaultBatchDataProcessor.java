package com.whyyoufun.toolkit.easyexcel.reader.processor;


import com.whyyoufun.toolkit.easyexcel.converter.DataConverter;
import com.whyyoufun.toolkit.easyexcel.reader.result.SheetData;

import java.util.List;

/**
 * 默认批处理器
 */
public class DefaultBatchDataProcessor<S,T> implements BatchDataProcessor<S,T> {

    @Override
    public void processBatchData(List<S> batchData, SheetData<T> currentSheetData, DataConverter<S, T> converter) {
        //默认不处理
    }
}
