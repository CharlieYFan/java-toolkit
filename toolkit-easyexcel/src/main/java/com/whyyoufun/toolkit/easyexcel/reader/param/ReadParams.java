package com.whyyoufun.toolkit.easyexcel.reader.param;

import com.whyyoufun.toolkit.easyexcel.validater.rule.ValidationRule;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
     * 自定义校验规则
     */
    private List<ValidationRule> customRules;

    /**
     * 批次大小
     */
    private int batchSize = 1000;

    /**
     * 最大行数限制
     */
    private int maxRowNum = 100;

    /**
     * 是否开启空行校验
     */
    private boolean isSkipEmptyRow = true;
}
