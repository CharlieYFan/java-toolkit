package com.whyyoufun.toolkit.easyexcel.reader.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.util.ListUtils;
import com.whyyoufun.toolkit.easyexcel.converter.DataConverter;
import com.whyyoufun.toolkit.easyexcel.exception.ExcelValidationException;
import com.whyyoufun.toolkit.easyexcel.reader.param.ReadParams;
import com.whyyoufun.toolkit.easyexcel.reader.processor.BatchDataProcessor;
import com.whyyoufun.toolkit.easyexcel.reader.result.SheetData;
import com.whyyoufun.toolkit.easyexcel.validater.ExcelValidator;
import com.whyyoufun.toolkit.easyexcel.reader.result.ReadResult;

import com.whyyoufun.toolkit.easyexcel.validater.result.ErrorExcelData;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * 读监听器
 * @param <T>
 */
@Getter
public class GenericExcelReadListener<S,T> extends AnalysisEventListener<S> {

    /**
     * 读取参数
     */
    private final ReadParams readParams;

    /**
     * 校验器
     */
    private final ExcelValidator<S> excelValidator;

    /**
     * 自定义转换器
     */
    private final DataConverter<S,T> converter;

    /**
     * 自定义批处理器
     */
    private final BatchDataProcessor<S,T> batchDataProcessor;

    /**
     * 批次大小
     */
    private int batchSize = 1000;

    /**
     * 缓存当前批次
     */
    private List<S> cacheBatchData;

    /**
     * 多sheet读取结果
     */
    private ReadResult<T> readResult = new ReadResult<>();

    /**
     * 当前sheet读取结果
     */
    private SheetData<T> currentSheetData;

    /**
     * 当前行号(不包括空行)
     */
    private int currentRowNum = 0;

    /**
     *
     * @param readParams 读取参数
     * @param excelValidator 校验器
     * @param converter 转换器
     * @param batchDataProcessor 批次数据处理器
     */
    public GenericExcelReadListener(ReadParams readParams,
                                    ExcelValidator<S> excelValidator,
                                    DataConverter<S, T> converter,
                                    BatchDataProcessor<S,T> batchDataProcessor) {
        this.readParams = readParams;
        this.excelValidator = excelValidator;
        this.converter = converter;
        this.batchSize = readParams.getBatchSize();
        this.batchDataProcessor = batchDataProcessor;
        this.cacheBatchData = ListUtils.newArrayListWithExpectedSize(batchSize);
    }

    /**
     * 当前sheet每行数据处理
     * @param data
     * @param analysisContext
     */
    @Override
    public void invoke(S data, AnalysisContext analysisContext) {
        //实际行数
        currentRowNum++;

        //处理当前行数据
        try {
            processRowData(data, analysisContext, currentRowNum);
        } catch (ExcelValidationException e) {
            //特定校验不通过停止处理
            throw e;
        }

        //处理批次数据
        cacheBatchData.add(data);
        if (cacheBatchData.size() >= batchSize){
            processBatchData(cacheBatchData, analysisContext, currentSheetData);
            cacheBatchData = ListUtils.newArrayListWithExpectedSize(batchSize);
        }
    }

    /**
     * 当前sheet读取完成回调
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //处理最后一批数据
        if (!cacheBatchData.isEmpty()){
            processBatchData(cacheBatchData, analysisContext, currentSheetData);
            cacheBatchData = ListUtils.newArrayListWithExpectedSize(batchSize);
        }
    }

    /**
     * 当前sheet表头行回调
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        //读取到表头行，初始化当前sheet
        int sheetNo = context.readSheetHolder().getSheetNo();
        String sheetName = context.readSheetHolder().getSheetName();
        currentSheetData = new SheetData<>(sheetNo, sheetName);
        readResult.addSheetData(sheetNo, currentSheetData);
    }

    /**
     * 处理行数据
     * @param rowData 当前行数据
     * @param context
     * @param currentRowNum 当前实际行数(不包括空行)
     */
    private void processRowData(S rowData, AnalysisContext context, int currentRowNum) throws ExcelValidationException {
        //校验当前行数据
        String reason = excelValidator.validateRow(rowData, currentRowNum);
        //执行自定义数据转换逻辑
        T convertedData = converter.convert(rowData);

        if (reason != null){
            //有错误原因，说明校验没通过
            //物理行号(包括空行)
            Integer rowIndex = context.readRowHolder().getRowIndex();
            ErrorExcelData<T> errorExcelData = new ErrorExcelData<>(rowIndex, reason, convertedData);
            //记录错误数据
            currentSheetData.addErrorData(errorExcelData);
        } else {
            //无错误原因，说明校验通过，记录成功数据
            currentSheetData.addSuccessData(convertedData);
        }
    }

    /**
     * 处理批次数据
     * @param cacheBatchData
     * @param analysisContext
     */
    private void processBatchData(List<S> cacheBatchData, AnalysisContext analysisContext, SheetData<T> currentSheetData) {
        batchDataProcessor.processBatchData(cacheBatchData, currentSheetData, converter);
    }

}
