package com.whyyoufun.test.temp;

import com.alibaba.excel.EasyExcel;
import com.whyyoufun.test.temp.listener.ModelReadListener;
import com.whyyoufun.test.temp.listener.NoModelReadListener;
import com.whyyoufun.test.temp.model.User;
import com.whyyoufun.toolkit.easyexcel.EasyExcelCommonUtil;
import com.whyyoufun.toolkit.easyexcel.reader.result.SheetData;
import org.junit.Test;

import java.util.List;

public class ReadFileListenerTest {

    String fileName = "/home/mi/文档/测试/压审包名配置表.xlsx";

    @Test
    public void testReadListener() {
        ModelReadListener<User> userModelReadListener = new ModelReadListener<>();

        EasyExcel.read(fileName, User.class, userModelReadListener)
                .sheet()
                .doRead();

        List<User> userList = userModelReadListener.getCache();

        userList.forEach(System.out::println);
    }

    @Test
    public void testNoModelReadListener() {

        NoModelReadListener noModelReadListener = new NoModelReadListener();
        EasyExcel.read(fileName, noModelReadListener)
                .sheet()
                .headRowNumber(1)
                .doRead();

    }

    @Test
    public void testUtils() {
        SheetData<User> userSheetData = EasyExcelCommonUtil.readDefaultSheetFormFile(fileName, User.class);

        List<User> totalData = userSheetData.getTotalData();
        for (int i = 0; i < totalData.size(); i++) {
            User user = totalData.get(i);
            System.out.println(user.getAppPackageName());
            System.out.println(user.getDisplayName());
            System.out.println(user.getAuditDelayFlow());
        }
    }
}
