package com.whyyoufun.toolkit.easyexcel.reader.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 读取参数
 */
@Getter
@Setter
@Builder
public class ReadParams {
    /**
     * sheet编号
     */
    private Integer sheetNo;

    /**
     * 表头行数
     */
    private int headRowNumber;

    /**
     * 批次大小
     */
    private int batchSize = 1000;

    /**
     * 最大行数限制
     */
    private int maxRowNum = 100;
}
