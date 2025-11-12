package com.whyyoufun.toolkit.easyexcel.validater.result;

import lombok.Data;

import java.util.List;

/**
 * 校验结果
 */
@Data
public class ValidationResult {
    private List<ValidationError> errors;

    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }

    public void addError(ValidationError error) {
        errors.add(error);
    }

    public void addErrors(List<ValidationError> errors) {
        this.errors.addAll(errors);
    }
}
