package com.whyyoufun.toolkit.easyexcel.writer;


import com.whyyoufun.toolkit.easyexcel.exception.ExcelWriteException;
import com.whyyoufun.toolkit.easyexcel.writer.param.WriteParams;

import java.io.File;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public interface ExcelCommonWriter {

    /**
     * 写入到HttpServletResponse(多sheet)
     * @param response
     * @param fileName
     * @param sheetData
     * @param head
     * @param writeParams
     * @throws ExcelWriteException
     */
    void write(HttpServletResponse response, String fileName, Map<String, List<?>> sheetData, Class<?> head, WriteParams writeParams) throws ExcelWriteException;

    /**
     * 写入到File(多sheet)
     * @param file
     * @param sheetData
     * @param head
     * @param writeParams
     * @throws ExcelWriteException
     */
    void write(File file, Map<String, List<?>> sheetData, Class<?> head, WriteParams writeParams) throws
                                                                                                  ExcelWriteException;

}
