package com.mantianhong.utiltools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by AKENZHANG on 2016/8/29.
 */
public class DateTimeUtil {

    public static String getDateString(){
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy/MM/dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);

        return str;
    }

    public static String getDateTimeString(){
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);

        return str;
    }

}
