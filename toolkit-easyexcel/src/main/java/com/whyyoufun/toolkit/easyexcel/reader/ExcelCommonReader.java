package com.whyyoufun.toolkit.easyexcel.reader;

import com.whyyoufun.toolkit.easyexcel.converter.DataConverter;
import com.whyyoufun.toolkit.easyexcel.exception.ExcelReadException;
import com.whyyoufun.toolkit.easyexcel.reader.param.ReadParams;
import com.whyyoufun.toolkit.easyexcel.reader.processor.BatchDataProcessor;
import com.whyyoufun.toolkit.easyexcel.reader.result.ReadResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ExcelCommonReader {

    /**
     * 读取数据
     * @param file
     * @param readParams
     * @return
     * @throws ExcelReadException
     */
    ReadResult<Map<Integer,Object>> read(MultipartFile file, ReadParams readParams) throws ExcelReadException;

    /**
     * 读取并自定义转换
     * @param file
     * @param readParams
     * @param converter
     * @return
     * @param <T>
     * @throws ExcelReadException
     */
    <T> ReadResult<T> readWithConverter(MultipartFile file, ReadParams readParams, DataConverter<Map<Integer, Object>
                , T> converter, BatchDataProcessor<T> batchDataProcessor) throws ExcelReadException;
}
