package com.whyyoufun.toolkit.easyexcel.reader;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.whyyoufun.toolkit.easyexcel.converter.DataConverter;
import com.whyyoufun.toolkit.easyexcel.exception.ExcelReadException;
import com.whyyoufun.toolkit.easyexcel.reader.listener.GenericExcelReadListener;
import com.whyyoufun.toolkit.easyexcel.reader.param.ReadParams;
import com.whyyoufun.toolkit.easyexcel.reader.processor.BatchDataProcessor;
import com.whyyoufun.toolkit.easyexcel.reader.result.ReadResult;
import com.whyyoufun.toolkit.easyexcel.validater.DefaultExcelValidator;
import com.whyyoufun.toolkit.easyexcel.validater.ExcelValidator;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public class EasyExcelCommonReader implements ExcelCommonReader {

    @Override
    public ReadResult<Map<Integer, Object>> read(MultipartFile file, ReadParams readParams) throws ExcelReadException {
        try {
            //校验器
            ExcelValidator excelValidator = new DefaultExcelValidator();
            excelValidator.init(readParams);
            //监听器
            GenericExcelReadListener<Map<Integer, Object>> listener =
                    new GenericExcelReadListener<>(readParams, excelValidator);
            //执行读取
            doRead(file, readParams, listener);
            //返回结果
            return listener.getReadResult();
        } catch (Exception e){
            throw new ExcelReadException("Failed to read excel file!", e);
        }
    }

    @Override
    public <T> ReadResult<T> readWithConverter(MultipartFile file, ReadParams readParams,
                                               DataConverter<Map<Integer, Object>, T> converter, BatchDataProcessor<T> batchDataProcessor)
            throws ExcelReadException {
        try {
            // 校验器
            ExcelValidator excelValidator = new DefaultExcelValidator();
            excelValidator.init(readParams);
            //监听器
            GenericExcelReadListener<T> listener =
                    new GenericExcelReadListener<>(readParams, excelValidator, converter, batchDataProcessor);
            //执行读取
            doRead(file, readParams, listener);
            //返回结果
            return listener.getReadResult();
        } catch (Exception e){
            throw new ExcelReadException("Failed to read excel file with Converter!", e);
        }
    }

    /**
     * 执行读取
     * @param file
     * @param readParams
     * @param listener
     * @param <T>
     * @throws IOException
     */
    private static <T> void doRead(MultipartFile file, ReadParams readParams, GenericExcelReadListener<T> listener)
            throws IOException {
        ExcelReader excelReader = EasyExcel.read(file.getInputStream())
                .headRowNumber(readParams.getHeadRowNumber())
                .autoCloseStream(false)
                .registerReadListener(listener)
                .build();
        if (readParams.getSheetNo() != null){
            //读取指定页
            ReadSheet readSheet = EasyExcel.readSheet(readParams.getSheetNo()).build();
            excelReader.read(readSheet);
        } else {
            //读取所有页
            for (ReadSheet readSheet : excelReader.excelExecutor().sheetList()){
                excelReader.read(readSheet);
            }
        }
        //完成读取
        excelReader.finish();
    }
}
