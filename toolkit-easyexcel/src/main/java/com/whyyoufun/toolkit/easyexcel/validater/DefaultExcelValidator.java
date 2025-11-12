package com.whyyoufun.toolkit.easyexcel.validater;


import com.whyyoufun.toolkit.easyexcel.exception.ExcelValidationException;
import com.whyyoufun.toolkit.easyexcel.reader.param.ReadParams;
import com.whyyoufun.toolkit.easyexcel.validater.result.ValidationResult;
import com.whyyoufun.toolkit.easyexcel.validater.rule.EmptyRowRule;
import com.whyyoufun.toolkit.easyexcel.validater.rule.MaxRowNumRule;
import com.whyyoufun.toolkit.easyexcel.validater.rule.ValidationRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 默认校验器
 */
public class DefaultExcelValidator implements ExcelValidator {
    private ReadParams readParams;
    private List<ValidationRule> rules = new ArrayList<>();

    public DefaultExcelValidator() {
        //空行校验
        rules.add(new EmptyRowRule());
        //最大行数校验
        rules.add(new MaxRowNumRule());
    }

    @Override
    public void init(ReadParams readParams) {
        this.readParams = readParams;

        // 初始化校验规则
        for (ValidationRule rule : rules) {
            rule.init(readParams);
        }

        // 初始化自定义校验规则
        if (readParams.getCustomRules() != null){
            for (ValidationRule rule : readParams.getCustomRules()) {
                rule.init(readParams);

                rules.add(rule);
            }
        }
    }

    @Override
    public ValidationResult validateRow(Map<Integer, Object> rowData, int rowNum) throws ExcelValidationException {
        ValidationResult result = new ValidationResult();
        for (ValidationRule rule : rules) {
            //针对每条规则对当前行进行校验
            ValidationResult ruleResult = rule.validateRow(rowData, rowNum);
            if (ruleResult != null && ruleResult.hasErrors()) {
                //有一条规则不满足停止后续校验
                result.addErrors(ruleResult.getErrors());
                break;
            }
        }
        return result;
    }
}
