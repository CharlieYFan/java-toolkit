package com.whyyoufun.test.temp.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class NoModelReadListener extends AnalysisEventListener<Map<Integer, Object>> {

    @Override
    public void invoke(Map<Integer, Object> integerObjectMap, AnalysisContext analysisContext) {
        Integer rowIndex = analysisContext.readRowHolder().getRowIndex();
        rowIndex++;
        System.out.println("第" +  rowIndex + "行数据：" + integerObjectMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("所有数据解析完成！");
    }
}
