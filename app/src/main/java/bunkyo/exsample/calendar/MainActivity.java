package bunkyo.exsample.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    DB helper;
    private String TaskTimeUnixTime;
    private Date currentDate;
    private int currentDayOfWeek;
    private TextView TaskTime;
    private TextView ScheduleTime;
    private long currentDateUnixTime;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("ddd:HH:mm:ss");
    private String tmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new DB(this);
        DateManagement dateManagement = new DateManagement();
        //リスナーとインスタンス生成
        findViewById(R.id.nextButton).setOnClickListener(nextButton);
        findViewById(R.id.prevButton).setOnClickListener(prevButton);
        findViewById(R.id.CalenderButton).setOnClickListener(calenderButton);
        findViewById(R.id.ScheduleButton).setOnClickListener(scheduleButton);
        findViewById(R.id.TaskButton).setOnClickListener(taskButton);

        ScheduleTime = findViewById(R.id.ScheduleTime);
        TaskTime = findViewById(R.id.TaskTime);
        TaskTime.setText(dateFormat.format(0));
        currentDate = dateManagement.nowDate();
        currentDateUnixTime = dateManagement.getCurrentUnixTime(currentDate);
        CountDownTask();
        long taskCountNumber  = millisecondDiff();
        final CountDown countDown = new CountDown(taskCountNumber,60000);
        countDown.start();
        String ScheduleTimeText = CountDownSchedule();
        ScheduleTime.setText(ScheduleTimeText);
        setDateAdapter();
    }
    View.OnClickListener nextButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DateManagement dateManagement = new DateManagement();

            dateManagement.LookingDate = dateManagement.nextMonth(dateManagement.LookingDate);
            setDateAdapter();
        }
    };
    View.OnClickListener prevButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DateManagement dateManagement = new DateManagement();
            dateManagement.LookingDate = dateManagement.prevMonth(dateManagement.LookingDate);
            setDateAdapter();

        }
    };
    View.OnClickListener calenderButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DateManagement dateManagement = new DateManagement();
            dateManagement.LookingDate = dateManagement.nowDate();
            setDateAdapter();
        }
    };
    View.OnClickListener scheduleButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), AddDateScheduleActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener taskButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),AddTaskActivity.class);
            startActivity(intent);
        }
    };
    public void setDateAdapter(){
        DateManagement dateManagement = new DateManagement();
        GridView calendarGridView = (GridView) findViewById(R.id.calendarGridView);
        TextView titleText = (TextView) findViewById(R.id.titleText);
        tmp = dateManagement.getTitleText(dateManagement.LookingDate);
        titleText.setText(tmp);
        List<Integer> GridArray = dateManagement.girdArray(dateManagement.LookingDate);
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(MainActivity.this,R.layout.date,GridArray);
        calendarGridView.setAdapter(arrayAdapter);
        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Integer ClickDate =GridArray.get(position);
                Integer ClickPosition = position;
                if (!((ClickDate>=20 && ClickPosition<=7) || (ClickDate<=7 && ClickPosition>=20))){
                    //クリックしたときの月を取得
                    Integer ClickMonth = dateManagement.CurrentMonth(dateManagement.LookingDate);
                    //クリックしたときの曜日を取得(1:日曜日, 2:月曜日, ..., 7:土曜日)
                    Integer ClickDayOfWeek = dateManagement.LookingDayOfWeek(dateManagement.LookingDate,ClickDate);
                    //intentにデータを送り、Activityを起動
                    Intent intent = new Intent(getApplicationContext(),DateScheduleActivity.class);
                    intent.putExtra("Month",ClickMonth);
                    intent.putExtra("Date",ClickDate);
                    intent.putExtra("DayOfWeek",ClickDayOfWeek);
                    intent.putExtra("yyyy.mm",tmp);
                    //画面遷移(DateScheduleActivityに遷移)
                    startActivity(intent);
                }
            }
        });
    }
    public void CountDownTask(){
        //現在のDateを取得
        DateManagement dateManagement = new DateManagement();
        Date CurrentDate = dateManagement.nowDate();
        long currentUnixTime = dateManagement.getCurrentUnixTime(CurrentDate);
        //DBから最も近い未来のデータを引き出す
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues cv = new ContentValues();
        String query = "SELECT * FROM Task WHERE UnixTime > ? ORDER BY UnixTime ASC LIMIT 1";
        String[] selectionArgs = {String.valueOf(currentUnixTime)};
        Cursor cursor = db.rawQuery(query,selectionArgs);
        //UnixTimeを指定する
        TaskTimeUnixTime = cursor.getString(cursor.getColumnIndexOrThrow("UnixTime"));
    }
    public String CountDownSchedule(){
        DateManagement dateManagement = new DateManagement();
        Date CurrentDate = dateManagement.nowDate();
        long currentUnixTime = dateManagement.getCurrentUnixTime(CurrentDate);
        //DBから最も近い未来のデータを引き出す
        int tmp = dateManagement.LookingDate(currentDate);
        currentDayOfWeek = dateManagement.LookingDayOfWeek(currentDate,tmp);
        //くそコード注意
        String StrCurrentDayOfWeek = DayOfWeekIntToStr(currentDayOfWeek);
        //db.execSQL("create table Schedule(ScheduleTitle text,DayOfWeek text,Time text,TeacherName text,TeacherMail text);");
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues cv = new ContentValues();
        String query = "SELECT * FROM Schedule Where DayOfWeek = ? ORDER BY Time ASC LIMIT 1";
        String[] selectionArgs = {(StrCurrentDayOfWeek)};
        Cursor cursor = db.rawQuery(query,selectionArgs);
        String title = cursor.getString(cursor.getColumnIndexOrThrow("Title"));
        String time = cursor.getString(cursor.getColumnIndexOrThrow("Time"));
        String text = time + title;
        return text;
    }

    public long millisecondDiff(){
        long longTaskTimeUnixTime = Long.parseLong(TaskTimeUnixTime);
        long millisecondDiff = longTaskTimeUnixTime - currentDateUnixTime;
        return millisecondDiff;
    }
    class CountDown extends CountDownTimer{

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */

        public CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        //
        @Override
        public void onTick(long millisUntilFinished) {
            TaskTime.setText(dateFormat.format(millisUntilFinished));
        }
        //完了したときの処理
        @Override
        public void onFinish() {
            TaskTime.setText(dateFormat.format(0));
        }
    }
    public String DayOfWeekIntToStr(int DayOfWeek){
        switch (DayOfWeek) {
            case 1:
                return "日";
            case 2:
                return "月";
            case 3:
                return "火";
            case 4:
                return "水";
            case 5:
                return "木";
            case 6:
                return "金";
            case 7:
                return "土";
            default:
                return "無効な数字";
        }
    }
}