package com.whyyoufun.toolkit.easyexcel.converter;

/**
 * 自定义数据转换器
 * @param <S>
 * @param <T>
 */
public interface DataConverter<S,T> {

    /**
     * 自定义数据转换逻辑
     * @param source
     * @return
     */
    T convert(S source);
}
