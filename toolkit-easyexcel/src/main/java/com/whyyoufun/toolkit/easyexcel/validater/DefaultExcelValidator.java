package com.whyyoufun.toolkit.easyexcel.validater;


import com.whyyoufun.toolkit.easyexcel.exception.ExcelValidationException;
import com.whyyoufun.toolkit.easyexcel.reader.param.ReadParams;
import com.whyyoufun.toolkit.easyexcel.validater.rule.MaxRowNumRule;
import com.whyyoufun.toolkit.easyexcel.validater.rule.ValidationRule;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认校验器
 */
public class DefaultExcelValidator<S> implements ExcelValidator<S> {
    private ReadParams readParams;

    /**
     * 默认规则
     */
    private final List<ValidationRule<S>> defaultRules = new ArrayList<>();

    /**
     * 业务自定义规则
     */
    private final List<ValidationRule<S>> customRules = new ArrayList<>();

    public DefaultExcelValidator() {
        //默认规则 - 最大行数校验
        defaultRules.add(new MaxRowNumRule<S>());
    }

    @Override
    public void init(ReadParams readParams, List<ValidationRule<S>> customValidationRules) {
        this.readParams = readParams;
        this.customRules.addAll(customValidationRules);

        // 初始化默认校验规则
        for (ValidationRule<S> rule : defaultRules) {
            rule.init(readParams);
        }

        // 初始化业务自定义校验规则
        for (ValidationRule<S> rule : customRules) {
            rule.init(readParams);
        }

    }

    @Override
    public String validateRow(S rowData, int rowNum) throws ExcelValidationException {
        for (ValidationRule<S> rule : defaultRules) {
            //针对每条规则对当前行进行校验
            String reason = rule.validateRow(rowData, rowNum);
            if (reason != null) {
                //有一条规则不满足停止后续校验
                return reason;
            }
        }

        //有则进行业务自定义校验
        if (!customRules.isEmpty()){
            for (ValidationRule<S> rule : customRules){
                String reason = rule.validateRow(rowData, rowNum);
                if (reason != null) {
                    return reason;
                }
            }
        }

        return null;
    }
}
