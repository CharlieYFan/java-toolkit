package com.whyyoufun.toolkit.easyexcel.writer;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.whyyoufun.toolkit.easyexcel.exception.ExcelWriteException;
import com.whyyoufun.toolkit.easyexcel.writer.param.WriteParams;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public class EasyExcelCommonWriter implements ExcelCommonWriter {

    private static final String DEFAULT_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final String DEFAULT_CHARSET = "utf-8";

    @Override
    public void write(HttpServletResponse response, String fileName, Map<String, List<?>> sheetData, Class<?> head, WriteParams writeParams) throws
                                                                                                                                             ExcelWriteException {
        try {
            //设置响应头
            setResponseHeader(response, fileName);

            ExcelWriter excelWriter = createExcelWriter(response, head);

            //分sheet写入
            writeSheetData(sheetData, excelWriter);

        } catch (IOException e){

        }
    }


    @Override
    public void write(File file, Map<String, List<?>> sheetData, Class<?> head, WriteParams writeParams)
            throws ExcelWriteException {
        try (ExcelWriter excelWriter = createExcelWriter(file, head);){

            //分sheet写入
            writeSheetData(sheetData, excelWriter);
        }catch (Exception e){

        }
    }

    /**
     * 分sheet写入
     * @param sheetData
     * @param excelWriter
     */
    private static void writeSheetData(Map<String, List<?>> sheetData, ExcelWriter excelWriter) {
        //分sheet写入
        int sheetNo = 0;
        for (Map.Entry<String, List<?>> entry : sheetData.entrySet()) {
            String sheetName = entry.getKey();
            List<?> data = entry.getValue();
            WriteSheet writeSheet = EasyExcelFactory.writerSheet(sheetNo++, sheetName).build();

            //写入
            excelWriter.write(data, writeSheet);
        }
    }

    /**
     * 创建response写入器
     * @param response
     * @param head
     * @return
     * @throws IOException
     */
    private static ExcelWriter createExcelWriter(HttpServletResponse response, Class<?> head) throws IOException {
        ExcelWriterBuilder excelWriterBuilder = EasyExcelFactory.write(response.getOutputStream(), head)
                .autoCloseStream(false);

        return excelWriterBuilder.build();
    }

    /**
     * 创建file写入器
     * @param file
     * @param head
     * @return
     * @throws IOException
     */
    private static ExcelWriter createExcelWriter(File file, Class<?> head) throws IOException {
        ExcelWriterBuilder excelWriterBuilder = EasyExcelFactory.write(file, head)
                .autoCloseStream(false);

        return excelWriterBuilder.build();
    }

    /**
     * 设置响应头
     * @param response
     * @param fileName
     * @throws IOException
     */
    private void setResponseHeader(HttpServletResponse response, String fileName) throws IOException{
        response.setContentType(DEFAULT_CONTENT_TYPE);
        response.setCharacterEncoding(DEFAULT_CHARSET);

        String encodedFileName = URLEncoder.encode(fileName, DEFAULT_CHARSET).replaceAll("\\+", "%20");

        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + encodedFileName);
    }
}
