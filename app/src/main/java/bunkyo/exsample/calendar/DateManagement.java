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


    //Calender型に次の月を指定させる
    public void nextMonth(){
        cl.add(Calendar.MONTH, 1);
    }

    //Calender型に次の月を指定させる
    public void prevMonth(){
        cl.add(Calendar.MONTH, -1);
    }
    //曜日取得(1:日曜日, 2:月曜日, ..., 7:土曜日)
    public int DayOfWeek(){
        int firstDayOfWeek = cl.get(Calendar.DAY_OF_WEEK);
        return firstDayOfWeek;
    }
    public String getTitleText(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM", Locale.US);
        return format.format(cl.getTime());
    }

    //その月の要素を取得[日曜から1日まで＋1~n日＋n日から土曜まで]
    public List<Integer> girdArray(){
        Date startDate = cl.getTime();
        //月末の日数
        int MonthEnd = cl.getActualMaximum(Calendar.WEEK_OF_MONTH);
        List<Integer> day = new ArrayList<>();
        if (DayOfWeek() != 0){
            int tmp = 0;
            cl.add(Calendar.MONTH,-1);
            int LatMonthEnd = cl.getActualMaximum(Calendar.WEEK_OF_MONTH);
            //日曜日になるまで追加
            while (DayOfWeek() == 7){
                day.add(LatMonthEnd - tmp);
                tmp += 1;
                cl.add(Calendar.DATE,-1);
            }
            Collections.reverse(day);
            cl.setTime(startDate);
        }
        for (int i=0;i<MonthEnd;i++){
            day.add(i+1);
        }
        cl.set(Calendar.DATE,cl.getActualMaximum(Calendar.WEEK_OF_MONTH));
        if (DayOfWeek() != 7){
            int tmp= 1;
            while (DayOfWeek() == 0){
                day.add(tmp);
                tmp += 1;
                cl.add(Calendar.DATE,1);
            }
        }
        cl.setTime(startDate);
        return day;
    }
}
