package com.whyyoufun.toolkit.easyexcel.converter;

/**
 * 默认数据转换器
 */
public class DefaultDataConverter<T> implements DataConverter<T,T>{

    @Override
    public T convert(T source) {
        //默认不转换，返回原始数据
        return source;
    }
}
