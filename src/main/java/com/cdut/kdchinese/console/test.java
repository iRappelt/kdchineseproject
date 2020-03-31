package com.cdut.kdchinese.console;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: iRappelt
 * Date: 2020/03/30 16:14
 * Description:
 * Version: V1.0
 */
public class test {

    public static void main(String[] args) {
        Timestamp time = new Timestamp(new Date().getTime());
        System.out.println(time);
        java.sql.Date date = new java.sql.Date(new Date().getTime());
        System.out.println(date);
    }
}
