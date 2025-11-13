package com.whyyoufun.toolkit.easyexcel;

import com.whyyoufun.toolkit.easyexcel.converter.DefaultDataConverter;
import com.whyyoufun.toolkit.easyexcel.exception.ExcelReadException;
import com.whyyoufun.toolkit.easyexcel.exception.ExcelWriteException;
import com.whyyoufun.toolkit.easyexcel.reader.EasyExcelCommonReader;
import com.whyyoufun.toolkit.easyexcel.reader.ExcelCommonReader;
import com.whyyoufun.toolkit.easyexcel.reader.param.ReadParams;
import com.whyyoufun.toolkit.easyexcel.reader.processor.BatchDataProcessor;
import com.whyyoufun.toolkit.easyexcel.reader.processor.DefaultBatchDataProcessor;
import com.whyyoufun.toolkit.easyexcel.reader.result.ReadResult;
import com.whyyoufun.toolkit.easyexcel.reader.result.SheetData;
import com.whyyoufun.toolkit.easyexcel.validater.rule.ValidationRule;
import com.whyyoufun.toolkit.easyexcel.writer.EasyExcelCommonWriter;
import com.whyyoufun.toolkit.easyexcel.writer.ExcelCommonWriter;
import com.whyyoufun.toolkit.easyexcel.writer.param.WriteParams;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;

public class EasyExcelCommonUtil {

    private static final ExcelCommonReader excelCommonReader = new EasyExcelCommonReader();

    private static final ExcelCommonWriter excelCommonWriter = new EasyExcelCommonWriter();

    //默认批次大小
    public static final int DEFAULT_BATCH_SIZE = 1000;

    private EasyExcelCommonUtil() {}

    /**
     * 读取第0页数据 (转指定数据类型)
     * @param file 读取文件
     * @param clazz 指定转换类型
     * @return 读取结果
     * @param <S>
     */
    public static <S> SheetData<S> readDefaultSheet(MultipartFile file, Class<S> clazz) {
        try {
            ReadParams defaultReadParams = ReadParams.builder()
                    .sheetNo(0)
                    .headRowNumber(1)
                    .batchSize(DEFAULT_BATCH_SIZE)
                    .maxRowNum(Integer.MAX_VALUE)
                    .build();

            ReadResult<S> readResult = excelCommonReader.read(
                            file,
                            defaultReadParams,
                            new DefaultDataConverter<>(),
                            new DefaultBatchDataProcessor<>(),
                            clazz,
                            new ArrayList<>());

            return readResult.getSheetData(0);
        } catch (ExcelReadException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取第0页数据 (转指定数据类型 指定表头行)
     * @param file 读取文件
     * @param clazz 指定转换类型
     * @return 读取结果
     * @param <S>
     */
    public static <S> SheetData<S> readDefaultSheet(MultipartFile file, Class<S> clazz, int headRowNumber) {
        try {
            ReadParams defaultReadParams = ReadParams.builder()
                    .sheetNo(0)
                    .headRowNumber(headRowNumber)
                    .batchSize(DEFAULT_BATCH_SIZE)
                    .maxRowNum(Integer.MAX_VALUE)
                    .build();

            ReadResult<S> readResult = excelCommonReader.read(
                    file,
                    defaultReadParams,
                    new DefaultDataConverter<>(),
                    new DefaultBatchDataProcessor<>(),
                    clazz,
                    new ArrayList<>());

            return readResult.getSheetData(0);
        } catch (ExcelReadException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取第0页数据 (转指定数据类型 自定义校验规则)
     * @param file 读取文件
     * @param clazz 读取数据类型
     * @param customValidationRule 自定义校验规则
     * @return
     * @param <S>
     */
    public static <S> SheetData<S> readDefaultSheet(MultipartFile file, Class<S> clazz, ValidationRule<S> customValidationRule) {
        try {
            ReadParams defaultReadParams = ReadParams.builder()
                    .sheetNo(0)
                    .headRowNumber(1)
                    .batchSize(DEFAULT_BATCH_SIZE)
                    .maxRowNum(Integer.MAX_VALUE)
                    .build();

            List<ValidationRule<S>> customValidationRules = new ArrayList<>();
            customValidationRules.add(customValidationRule);

            ReadResult<S> readResult = excelCommonReader.read(
                    file,
                    defaultReadParams,
                    new DefaultDataConverter<>(),
                    new DefaultBatchDataProcessor<>(),
                    clazz,
                    customValidationRules);

            return readResult.getSheetData(0);
        } catch (ExcelReadException e){
            throw new RuntimeException(e);
        }
    }


    /**
     * 分批读取第0页数据 (指定数据类型 指定批处理方法)
     * @param file 读取文件
     * @param clazz 指定转换类型
     * @param batchSize 批次大小
     * @param batchDataProcessor 自定义批处理方法
     * @return
     * @param <S>
     */
    public static <S> SheetData<S> batchReadDefaultSheet(MultipartFile file,
                                                    Class<S> clazz,
                                                    int batchSize,
                                                    BatchDataProcessor<S,S> batchDataProcessor) {
        try {
            ReadParams defaultReadParams = ReadParams.builder()
                    .sheetNo(0)
                    .headRowNumber(1)
                    .batchSize(batchSize)
                    .maxRowNum(Integer.MAX_VALUE)
                    .build();

            ReadResult<S> readResult = excelCommonReader.read(
                    file,
                    defaultReadParams,
                    new DefaultDataConverter<>(),
                    batchDataProcessor,
                    clazz,
                    new ArrayList<>());

            return readResult.getSheetData(0);
        } catch (ExcelReadException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取所有sheet数据 (转指定数据类型)
     * @param file 读取文件
     * @param clazz 指定转换类型
     * @return 读取结果
     * @param <S>
     */
    public static <S> ReadResult<S> readMultiSheet(MultipartFile file, Class<S> clazz) {
        try {
            ReadParams defaultReadParams = ReadParams.builder()
                    .headRowNumber(1)
                    .batchSize(DEFAULT_BATCH_SIZE)
                    .maxRowNum(Integer.MAX_VALUE)
                    .build();

            ReadResult<S> readResult = excelCommonReader.read(
                    file,
                    defaultReadParams,
                    new DefaultDataConverter<>(),
                    new DefaultBatchDataProcessor<>(),
                    clazz,
                    new ArrayList<>());

            return readResult;
        } catch (ExcelReadException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取第0页数据（不转换的原始数据）
     * @param file 读取文件
     * @return Map<Integer, Objects>类型
     */
    public static SheetData<Map<Integer, Objects>> readDefaultSheetNoConverter(MultipartFile file) {
        try {
            ReadParams defaultReadParams = ReadParams.builder()
                    .sheetNo(0)
                    .headRowNumber(1)
                    .batchSize(DEFAULT_BATCH_SIZE)
                    .maxRowNum(Integer.MAX_VALUE)
                    .build();

            ReadResult<Map<Integer, Objects>> readResult = excelCommonReader.read(
                    file,
                    defaultReadParams,
                    new DefaultDataConverter<>(),
                    new DefaultBatchDataProcessor<>(),
                    null,
                    new ArrayList<>());

            return readResult.getSheetData(0);
        } catch (ExcelReadException e){
            throw new RuntimeException(e);
        }
    }




    /* ============================ FILE读取 ==============================*/

    /**
     * 默认读取第0页数据
     * 转指定数据类型
     */
    public static <S> SheetData<S> readDefaultSheetFormFile(String fileName, Class<S> clazz) {
        try {
            ReadParams defaultReadParams = ReadParams.builder()
                    .sheetNo(0)
                    .headRowNumber(1)
                    .batchSize(DEFAULT_BATCH_SIZE)
                    .maxRowNum(Integer.MAX_VALUE)
                    .build();

            ReadResult<S> readResult = excelCommonReader.readFromFile(
                    fileName,
                    defaultReadParams,
                    new DefaultDataConverter<>(),
                    new DefaultBatchDataProcessor<>(),
                    clazz,
                    new ArrayList<>());

            return readResult.getSheetData(0);
        } catch (ExcelReadException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 默认读取第0页数据(不转换)
     * 转指定数据类型
     */
    public static SheetData<Map<Integer, Objects>> readDefaultSheetFormFile(String fileName) {
        try {
            ReadParams defaultReadParams = ReadParams.builder()
                    .sheetNo(0)
                    .headRowNumber(1)
                    .batchSize(DEFAULT_BATCH_SIZE)
                    .maxRowNum(Integer.MAX_VALUE)
                    .build();

            ReadResult<Map<Integer, Objects>> readResult = excelCommonReader.readFromFile(
                    fileName,
                    defaultReadParams,
                    new DefaultDataConverter<>(),
                    new DefaultBatchDataProcessor<>(),
                    null,
                    new ArrayList<>());

            return readResult.getSheetData(0);
        } catch (ExcelReadException e){
            throw new RuntimeException(e);
        }
    }


    /* ============================ WRITE ==============================*/

    /**
     * 写入到response(多sheet)
     * @param response
     * @param fileName
     * @param sheetData
     * @param head
     * @param writeParams
     * @throws ExcelWriteException
     */
    public static void writeToResponse(HttpServletResponse response, String fileName, Map<String, List<?>> sheetData, Class<?> head, WriteParams writeParams) throws ExcelWriteException {
        try {

            excelCommonWriter.write(response, fileName, sheetData, head, writeParams);

        } catch (ExcelWriteException e) {
            throw e;
        }
    }

    /**
     * 写入到file(多sheet)
     * @param file
     * @param sheetData
     * @param head
     * @param writeParams
     * @throws ExcelWriteException
     */
    public static void writeToFile(File file, Map<String, List<?>> sheetData, Class<?> head, WriteParams writeParams) throws ExcelWriteException {
        try {

            excelCommonWriter.write(file, sheetData, head, writeParams);

        } catch (ExcelWriteException e) {
            throw e;
        }
    }

}
