package com.whyyoufun.toolkit.easyexcel.reader.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import com.whyyoufun.toolkit.easyexcel.converter.DataConverter;
import com.whyyoufun.toolkit.easyexcel.converter.DefaultDataConverter;
import com.whyyoufun.toolkit.easyexcel.exception.ExcelValidationException;
import com.whyyoufun.toolkit.easyexcel.reader.param.ReadParams;
import com.whyyoufun.toolkit.easyexcel.reader.processor.BatchDataProcessor;
import com.whyyoufun.toolkit.easyexcel.reader.processor.DefaultBatchDataProcessor;
import com.whyyoufun.toolkit.easyexcel.reader.result.SheetData;
import com.whyyoufun.toolkit.easyexcel.validater.ExcelValidator;
import com.whyyoufun.toolkit.easyexcel.validater.result.ValidationResult;
import com.whyyoufun.toolkit.easyexcel.reader.result.ReadResult;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 读监听器
 * @param <T>
 */
@Getter
public class GenericExcelReadListener<T> extends AnalysisEventListener<Map<Integer, Object>> {

    /**
     * 读取参数
     */
    private final ReadParams readParams;

    /**
     * 校验器
     */
    private final ExcelValidator excelValidator;

    /**
     * 自定义转换器
     */
    private final DataConverter<Map<Integer,Object>, T> converter;

    /**
     * 自定义批处理器
     */
    private final BatchDataProcessor<T> batchDataProcessor;

    /**
     * 缓存当前批次
     */
    private List<Map<Integer, Object>> cacheBatchData = new ArrayList<>();

    /**
     * 批次大小
     */
    private int batchSize = 1000;

    /**
     * 读取结果
     */
    private ReadResult<T> readResult = new ReadResult<>();

    /**
     * 当前sheet数据
     */
    private SheetData<T> currentSheetData;

    /**
     * 当前行号
     */
    private int currentRowNum = 0;

    /**
     * 自定义转换器
     * @param readParams
     * @param excelValidator
     * @param converter
     * @param batchDataProcessor
     */
    public GenericExcelReadListener(ReadParams readParams, ExcelValidator excelValidator,
                                    DataConverter<Map<Integer, Object>, T> converter,
                                    BatchDataProcessor<T> batchDataProcessor) {
        this.readParams = readParams;
        this.excelValidator = excelValidator;
        this.converter = converter;
        this.batchSize = readParams.getBatchSize();
        this.batchDataProcessor = batchDataProcessor;
    }

    /**
     * 默认转换器
     * @param readParams
     * @param excelValidator
     */
    @SuppressWarnings("unchecked")
    public GenericExcelReadListener(ReadParams readParams, ExcelValidator excelValidator) {
        this.readParams = readParams;
        this.excelValidator = excelValidator;
        this.converter = (DataConverter<Map<Integer, Object>, T>) new DefaultDataConverter();
        this.batchSize = readParams.getBatchSize();
        this.batchDataProcessor = new DefaultBatchDataProcessor<T>();
    }

    /**
     * 每行数据处理
     * @param data
     * @param analysisContext
     */
    @Override
    public void invoke(Map<Integer, Object> data, AnalysisContext analysisContext) {
        currentRowNum++;
        //处理当前行数据
        try {
            processRowData(data, analysisContext);
        } catch (ExcelValidationException e) {
            //特定校验不通过停止处理
            return;
        }

        //处理批次数据
        cacheBatchData.add(data);
        if (cacheBatchData.size() >= batchSize){
            processBatchData(cacheBatchData, analysisContext, currentSheetData);
            cacheBatchData.clear();
        }
    }

    /**
     * 读取完成回调
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //处理最后一批数据
        if (!cacheBatchData.isEmpty()){
            processBatchData(cacheBatchData, analysisContext, currentSheetData);
            cacheBatchData.clear();
        }
    }

    /**
     * 表头行回调
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        //读取到表头行，初始化当前sheet
        int sheetNo = context.readSheetHolder().getSheetNo();
        String sheetName = context.readSheetHolder().getSheetName();
        currentSheetData = new SheetData<>(sheetNo, sheetName);
        readResult.addSheetData(sheetNo, currentSheetData);
    }

    /**
     * 处理行数据
     * @param rowData
     * @param context
     */
    private void processRowData(Map<Integer, Object> rowData, AnalysisContext context) throws ExcelValidationException {
        readResult.incrementTotalRowCount();

        //校验当前行数据
        ValidationResult validationResult = null;
        validationResult = excelValidator.validateRow(rowData, currentRowNum);

        if (validationResult.hasErrors()){
            //校验不通过
            readResult.incrementFailRowCount();

            //读取结果错误数据记录
            readResult.addValidationErrors(validationResult);
            //当前页的错误数据记录
            currentSheetData.addAllErrors(validationResult.getErrors());
        } else {
            //校验通过
            readResult.incrementSuccessRowCount();

            //数据对象转换
            T converted =  converter.convert(rowData);
            //记录转换后的数据
            currentSheetData.addData(converted);
        }
    }

    /**
     * 处理批次数据
     * @param cacheBatchData
     * @param analysisContext
     */
    private void processBatchData(List<Map<Integer, Object>> cacheBatchData, AnalysisContext analysisContext, SheetData<T> currentSheetData) {
        batchDataProcessor.processBatchData(cacheBatchData, currentSheetData);
    }

}
