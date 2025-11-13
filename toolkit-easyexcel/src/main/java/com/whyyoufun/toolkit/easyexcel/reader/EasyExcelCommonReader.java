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

public class EasyExcelCommonReader implements ExcelCommonReader {

    @Override
    public <S, T> ReadResult<T> read(MultipartFile file, ReadParams readParams, DataConverter<S, T> converter,
                                     BatchDataProcessor<S, T> batchDataProcessor, Class<S> clazz) throws ExcelReadException {
        try {
            //校验器
            ExcelValidator<S> excelValidator = new DefaultExcelValidator<>();
            excelValidator.init(readParams);
            //监听器
            GenericExcelReadListener<S, T> listener =
                    new GenericExcelReadListener<>(readParams, excelValidator, converter, batchDataProcessor);
            //执行读取
            doRead(file, readParams, listener, clazz);
            //返回结果
            return listener.getReadResult();
        } catch (Exception e) {
            throw new ExcelReadException("Failed to read excel file!", e);
        }
    }

    @Override
    public <S, T> ReadResult<T> readFromFile(String fileName, ReadParams readParams, DataConverter<S, T> converter,
                                             BatchDataProcessor<S, T> batchDataProcessor, Class<S> clazz) throws ExcelReadException {
        //校验器
        ExcelValidator<S> excelValidator = new DefaultExcelValidator<>();
        excelValidator.init(readParams);
        //监听器
        GenericExcelReadListener<S, T> listener =
                new GenericExcelReadListener<>(readParams, excelValidator, converter, batchDataProcessor);

        ExcelReader excelReader = EasyExcel.read(fileName, clazz,listener)
                .headRowNumber(readParams.getHeadRowNumber())
                .autoCloseStream(false)
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
        //返回结果
        return listener.getReadResult();
    }

    /**
     * 执行读取
     * @param file
     * @param readParams
     * @param listener
     * @throws IOException
     */
    private static <S, T> void doRead(MultipartFile file, ReadParams readParams,
                                      GenericExcelReadListener<S,T> listener, Class<S> clazz)
            throws IOException {
        ExcelReader excelReader = EasyExcel.read(file.getInputStream(), clazz, listener)
                .headRowNumber(readParams.getHeadRowNumber())
                .autoCloseStream(false)
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
