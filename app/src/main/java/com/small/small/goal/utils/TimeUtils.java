package com.small.small.goal.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　　　 ████━████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛   Author:XiaoFei Zhai
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 */
public class TimeUtils {

    public static Timestamp getTodayStartTime(){
       return getStartTime(new Date());
    }

    public static Timestamp getStartTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);
        date = calendar.getTime();
        return new Timestamp(date.getTime());
    }

    public static Timestamp getHourStart(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);
        date = calendar.getTime();
        return new Timestamp(date.getTime());
    }


    public static Timestamp getMonthStartTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);
        date = calendar.getTime();
        return new Timestamp(date.getTime());
    }

    public static Timestamp getEndTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND,0);
        date = calendar.getTime();
        return new Timestamp(date.getTime());
    }
    public static Timestamp getMonthEndTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND,0);
        date = calendar.getTime();
        return new Timestamp(date.getTime());
    }

    public static Timestamp getYesterdayStartTime(){
        Date date                       =   new Date();//取时间
        Calendar calendar               =   Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,-1);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);
        date=calendar.getTime();
        return new Timestamp(date.getTime());
    }
    public static Timestamp getYesterdayEndTime(){
        Date date                       =   new Date();//取时间
        Calendar calendar               =   Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,-1);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND,0);
        date=calendar.getTime();
        return new Timestamp(date.getTime());
    }

    public static String dateFormat(String format,Date date){
        if(null==date){
            date                        =   new Date();
        }
        if(null==format){
            format                      =   "yyyy-MM-dd";
        }
        SimpleDateFormat dateFormat     =   new SimpleDateFormat(format);

        return dateFormat.format(date);
    }

    public static String dateFormat(String format){
        return dateFormat(format,null);
    }
    public static Timestamp currentTime(){
        long now                        =   System.currentTimeMillis();
        return new Timestamp(now);
    }
    public static Timestamp addMonth(Timestamp timestamp,Integer month){
        Calendar calendar               =   Calendar.getInstance();
        calendar.setTime(timestamp);
        calendar.add(Calendar.MONTH,month);
        Date date                       =   calendar.getTime();
        return new Timestamp(date.getTime());
    }
}
