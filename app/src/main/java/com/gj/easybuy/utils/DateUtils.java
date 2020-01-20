package com.gj.easybuy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2018/9/27
 * @Describe
 */
public class DateUtils {

    public final static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public final static String yyyy_MM_dd = "yyyy-MM-dd";
    public final static String yyyy_MM = "yyyy-MM";
    public final static String yyyy_MM_dd_Slash = "yyyy/MM/dd";


    /**
     * 返回当前时间戳
     *
     * @return
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }


    /**
     * 将时间戳转为字符串 ，格式：yyyy-MM-dd
     */
    public static String getStrTime_yyyy_MM_dd(long cc_time) {
        return getStrTime(cc_time, yyyy_MM_dd);

    }

    /*
     * 将时间戳转为字符串 ，格式：yyyy-MM-dd HH:mm
     */
    public static String getStrTime_ymd_hm(long cc_time) {
        String re_StrTime = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        // 例如：cc_time=1291778220
        re_StrTime = sdf.format(new Date(cc_time));
        return re_StrTime;

    }

    /*
     * 将时间戳转为字符串 ，格式：yyyy-MM-dd HH:mm:ss
     */
    public static String getStrTime_ymd_hms(long cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
        // 例如：cc_time=1291778220
        re_StrTime = sdf.format(new Date(cc_time));
        return re_StrTime;

    }

    /*
     * 将时间戳转为字符串 ，格式：yyyy.MM.dd
     */
    public static String getStrTime_ymd(long cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        // 例如：cc_time=1291778220
        re_StrTime = sdf.format(new Date(cc_time));
        return re_StrTime;
    }

    /*
     * 将时间戳转为字符串 ，格式：yyyy
     */
    public static String getStrTime_y(long cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        // 例如：cc_time=1291778220
        re_StrTime = sdf.format(new Date(cc_time));
        return re_StrTime;
    }

    /*
     * 将时间戳转为字符串 ，格式：yyyy-MM
     */
    public static String getStrTime_ym(long cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        // 例如：cc_time=1291778220
        re_StrTime = sdf.format(new Date(cc_time));
        return re_StrTime;
    }

    /*
     * 将时间戳转为字符串 ，格式：MM-dd
     */
    public static String getStrTime_md(long cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        // 例如：cc_time=1291778220
        re_StrTime = sdf.format(new Date(cc_time));
        return re_StrTime;
    }

    /*
     * 将时间戳转为字符串 ，格式：HH:mm
     */
    public static String getStrTime_hm(long cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        // 例如：cc_time=1291778220
        re_StrTime = sdf.format(new Date(cc_time));
        return re_StrTime;
    }

    /*
     * 将时间戳转为字符串 ，格式：HH:mm:ss
     */
    public static String getStrTime_hms(long cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        // 例如：cc_time=1291778220
        re_StrTime = sdf.format(new Date(cc_time));
        return re_StrTime;
    }

    /*
     * 将时间戳转为字符串 ，格式：MM-dd HH:mm:ss
     */
    public static String getNewsDetailsDate(long cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
        // 例如：cc_time=1291778220
        re_StrTime = sdf.format(new Date(cc_time));
        return re_StrTime;
    }

    /*
     * 将字符串转为时间戳
     */
    public static String getTime() {
        String re_time = null;
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Date d;
        d = new Date(currentTime);
        long l = d.getTime();
        String str = String.valueOf(l);
        re_time = str.substring(0, 10);
        return re_time;
    }

    /*
     * 将时间戳转为字符串 ，格式：yyyy.MM.dd  星期几
     */
    public static String getSection(long cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd  EEEE");
//		对于创建SimpleDateFormat传入的参数：EEEE代表星期，如“星期四”；MMMM代表中文月份，如“十一月”；MM代表月份，如“11”；
//		yyyy代表年份，如“2010”；dd代表天，如“25”
        // 例如：cc_time=1291778220
        re_StrTime = sdf.format(new Date(cc_time));
        return re_StrTime;
    }

    /**
     * 字符串转秒数
     *
     * @param ss_time
     * @return
     */
    public static long getLongSecond(String ss_time) {
        long re_StrTime = 0;
        try {
            Date date = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss).parse(ss_time);
            re_StrTime = date.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_StrTime;
    }

    /**
     * 字符串转date
     *
     * @param str_date
     * @return
     */
    public static Date getStrDate(String str_date) {
        try {
            return new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss).parse(str_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }


    /**
     * 按照规定的格式转换日期
     *
     * @param cc_time
     * @param pattern
     * @return
     */
    public static String getStrTime(long cc_time, String pattern) {
        String re_StrTime = "";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        // 例如：cc_time=1291778220
        re_StrTime = sdf.format(new Date(cc_time));
        return re_StrTime;
    }


}
