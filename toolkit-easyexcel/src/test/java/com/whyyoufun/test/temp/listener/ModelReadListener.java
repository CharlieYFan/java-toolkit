package com.whyyoufun.test.temp.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;


import java.util.ArrayList;
import java.util.List;

public class ModelReadListener<T> extends AnalysisEventListener<T> {

    private final List<T> cache = new ArrayList<>();


    @Override
    public void invoke(T t, AnalysisContext analysisContext) {

        cache.add(t);

        System.out.println("解析到一条数据:" + t);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("所有数据解析完成！");
        System.out.println("共解析 " + cache.size() + " 条数据");
    }

    public List<T> getCache() {
        return cache;
    }
}
