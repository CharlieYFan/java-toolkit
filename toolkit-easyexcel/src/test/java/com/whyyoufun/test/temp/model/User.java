package com.whyyoufun.test.temp.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    @ExcelProperty("应用包名")
    private String appPackageName;

    @ExcelProperty("应用名称")
    private String displayName;

    @ExcelProperty("压审来源")
    private String auditDelayFlow;

    @ExcelProperty("压审有无期限")
    private String auditDelayDeadline;

    @ExcelProperty("开始时间")
    private String startTime;

    @ExcelProperty("结束时间")
    private String endTime;

    @ExcelProperty("压审原因")
    private String auditDelayReason;

    @ExcelProperty("备注")
    private String note;

    @ExcelProperty("应用状态")
    private String appStatus;

    @ExcelProperty("实际释放时间")
    private String actualReleaseTime;

    @ExcelProperty("失效原因")
    private String failureReason;
}
