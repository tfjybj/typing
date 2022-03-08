package com.tfjybj.typing.utils;

import java.util.Calendar;

public class RecordDate {
    /**获取今日的日期
     *
     * @return今日的日期
     */
    public static String Date(){
        Calendar cal = Calendar.getInstance();
        int month =cal.get(Calendar.MONTH) + 1;
        int day=cal.get(Calendar.DAY_OF_MONTH);
        String monthString=String.valueOf(month);
        String dayString=String.valueOf(day);
        String ee = String.valueOf(cal.get(Calendar.MONTH) + 1)  + cal.get(Calendar.DAY_OF_MONTH);
        if (month<10)
        {
            monthString="0"+String.valueOf(month);
        }
        if (day<10)
        {
            dayString="0"+String.valueOf(day);
        }
        String date=monthString+dayString;
        return date;
    }
}
