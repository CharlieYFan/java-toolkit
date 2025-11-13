package com.whyyoufun.toolkit.easyexcel.reader;

import com.whyyoufun.toolkit.easyexcel.converter.DataConverter;
import com.whyyoufun.toolkit.easyexcel.exception.ExcelReadException;
import com.whyyoufun.toolkit.easyexcel.reader.param.ReadParams;
import com.whyyoufun.toolkit.easyexcel.reader.processor.BatchDataProcessor;
import com.whyyoufun.toolkit.easyexcel.reader.result.ReadResult;
import com.whyyoufun.toolkit.easyexcel.validater.rule.ValidationRule;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExcelCommonReader {

    /**
     * 读取并自定义转换
     * @param file 读取文件
     * @param readParams 读取参数
     * @param converter 自定义数据转换器
     * @param batchDataProcessor 自定义批处理数据处理器
     * @param clazz 指定读取类型
     * @param customValidationRules 自定义校验规则
     * @return
     * @param <T>
     * @throws ExcelReadException
     */
    <S,T> ReadResult<T> read(MultipartFile file, ReadParams readParams, DataConverter<S, T> converter,
                             BatchDataProcessor<S,T> batchDataProcessor, Class<S> clazz, List<ValidationRule<S>> customValidationRules) throws ExcelReadException;

    /**
     * 读取并自定义转换
     * @param fileName 本地文件名
     * @param readParams 读取参数
     * @param converter 自定义数据转换器
     * @param batchDataProcessor 自定义批处理数据处理器
     * @param clazz 指定读取类型
     * @param customValidationRules 自定义校验规则
     * @return
     * @param <T>
     * @throws ExcelReadException
     */
    <S,T> ReadResult<T> readFromFile(String fileName, ReadParams readParams, DataConverter<S, T> converter,
                              BatchDataProcessor<S,T> batchDataProcessor, Class<S> clazz, List<ValidationRule<S>> customValidationRules) throws ExcelReadException;


}
