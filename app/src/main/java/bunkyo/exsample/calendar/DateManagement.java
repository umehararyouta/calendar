package bunkyo.exsample.calendar;

import android.widget.Adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateManagement {
    Calendar cl;
    public DateManagement(){
        cl = Calendar.getInstance();
    }


    public Integer CurrentMonth(Date LookingDate){
        cl.setTime(LookingDate);
        int currentMonth = cl.get(Calendar.MONTH)+1;
        return currentMonth;
    }
    //Calender型に次の月を指定させる
    public Date nextMonth(Date LookingDate){
        cl.setTime(LookingDate);
        cl.add(Calendar.MONTH, 1);
        LookingDate =  cl.getTime();
        return LookingDate;
    }

    //Calender型に次の月を指定させる
    public Date prevMonth(Date LookingDate){
        cl.setTime(LookingDate);
        cl.add(Calendar.MONTH, -1);
        LookingDate = cl.getTime();
        return LookingDate;
    }
    //曜日取得(1:日曜日, 2:月曜日, ..., 7:土曜日)
    public int DayOfWeek(){
        int firstDayOfWeek = cl.get(Calendar.DAY_OF_WEEK);
        return firstDayOfWeek;
    }
    public int LookingDayOfWeek(Date LookingDate,Integer day){
        cl.setTime(LookingDate);
        cl.set(Calendar.DATE,day);
        int firstDayOfWeek = cl.get(Calendar.DAY_OF_WEEK);
        return firstDayOfWeek;
    }
    public String getTitleText(Date LookingDate){
        cl.setTime(LookingDate);
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM", Locale.US);
        return format.format(cl.getTime());
    }

    //その月の要素を取得[日曜から月末まで＋1~n日＋月初から次の土曜まで]
    public List<Integer> girdArray(Date LookingDate){
        cl.setTime(LookingDate);
        Date startDate = cl.getTime();
        //月末の日数
        int MonthEnd = cl.getActualMaximum(Calendar.DAY_OF_MONTH);
        List<Integer> day = new ArrayList<>();
        cl.set(Calendar.DATE,1);
        if (DayOfWeek() != 0){
            int tmp = 0;
            cl.add(Calendar.DATE,-1);
            int LastMonthEnd = cl.get(Calendar.DAY_OF_MONTH);
            //日曜日になるまで追加
            while (DayOfWeek() != 7){
                day.add(LastMonthEnd - tmp);
                tmp += 1;
                cl.add(Calendar.DATE,-1);
            }
            Collections.reverse(day);
            cl.setTime(startDate);
        }
        for (int i=0;i<MonthEnd;i++){
            day.add(i+1);
        }
        cl.add(Calendar.MONTH,1);
        cl.set(Calendar.DATE,1);
        if (DayOfWeek() != 1){
            int tmp= 1;
            while (DayOfWeek() != 1){
                day.add(tmp);
                tmp += 1;
                cl.add(Calendar.DATE,1);
            }
        }
        cl.setTime(startDate);
        return day;
    }
    public static Date nowDate(){
        Calendar cl = Calendar.getInstance();
        Date LookingDate = cl.getTime();
        return LookingDate;
    }
    public static Date LookingDate = nowDate();
}
