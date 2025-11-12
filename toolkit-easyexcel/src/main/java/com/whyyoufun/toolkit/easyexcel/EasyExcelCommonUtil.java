package com.whyyoufun.toolkit.easyexcel;

import com.whyyoufun.toolkit.easyexcel.converter.DataConverter;
import com.whyyoufun.toolkit.easyexcel.exception.ExcelReadException;
import com.whyyoufun.toolkit.easyexcel.exception.ExcelWriteException;
import com.whyyoufun.toolkit.easyexcel.reader.EasyExcelCommonReader;
import com.whyyoufun.toolkit.easyexcel.reader.ExcelCommonReader;
import com.whyyoufun.toolkit.easyexcel.reader.param.ReadParams;
import com.whyyoufun.toolkit.easyexcel.reader.processor.BatchDataProcessor;
import com.whyyoufun.toolkit.easyexcel.reader.result.ReadResult;
import com.whyyoufun.toolkit.easyexcel.writer.EasyExcelCommonWriter;
import com.whyyoufun.toolkit.easyexcel.writer.ExcelCommonWriter;
import com.whyyoufun.toolkit.easyexcel.writer.param.WriteParams;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public class EasyExcelCommonUtil {

    private static final ExcelCommonReader excelCommonReader = new EasyExcelCommonReader();

    private static final ExcelCommonWriter excelCommonWriter = new EasyExcelCommonWriter();

    private EasyExcelCommonUtil() {}

    /**
     * 读取原始数据
     * @param file
     * @param readParams
     * @return
     * @throws ExcelReadException
     */
    public static ReadResult<Map<Integer, Object>> read(MultipartFile file, ReadParams readParams) throws ExcelReadException {
        try{
            ReadResult<Map<Integer, Object>> result = excelCommonReader.read(file, readParams);
            return result;
        } catch (ExcelReadException e){
            throw e;
        }
    }

    /**
     * 读取并自定义转换
     * @param file
     * @param converter
     * @param readParams
     * @param batchDataProcessor
     * @return
     * @param <T>
     * @throws ExcelReadException
     */
    public static <T> ReadResult<T> readWithConverter(MultipartFile file,
                                                      DataConverter<Map<Integer, Object>, T> converter,
                                                      ReadParams readParams,
                                                      BatchDataProcessor<T> batchDataProcessor) throws ExcelReadException {
        try {
            ReadResult<T> result = excelCommonReader.readWithConverter(file, readParams, converter, batchDataProcessor);
            return result;
        } catch (ExcelReadException e) {
            throw e;
        }
    }

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
