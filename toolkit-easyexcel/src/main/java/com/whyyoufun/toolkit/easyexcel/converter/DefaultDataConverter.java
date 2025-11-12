package com.whyyoufun.toolkit.easyexcel.converter;

import java.util.Map;

/**
 * 默认数据转换器
 */
public class DefaultDataConverter implements DataConverter<Map<Integer, Object>, Map<Integer, Object>>{

    @Override
    public Map<Integer, Object> convert(Map<Integer, Object> source) {
        //返回原始数据
        return source;
    }
}
