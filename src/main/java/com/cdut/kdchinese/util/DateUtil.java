package com.cdut.kdchinese.util;

import java.util.Date;

/**
 * 日期生成类
 * 格式为 yyyy-mm-dd hh:mm:ss
 */
public class DateUtil {

    public static String getDate(){
        Date date = new Date();
        return String.format("%tY-%tm-%td %tH:%tM:%tS", date,date,date,date,date,date);
    }

}
