package bunkyo.exsample.calendar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class DateScheduleActivity extends AppCompatActivity {
    DB helper;
    private Integer monthReceivedItem;
    private Integer dateReceivedItem;
    private Integer dayOfWeekReceivedItem;
    private String ym;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_schedule);
        helper = new DB(this);

        findViewById(R.id.CalenderButton).setOnClickListener(calenderButton);
        findViewById(R.id.ScheduleButton).setOnClickListener(scheduleButton);
        findViewById(R.id.TaskButton).setOnClickListener(taskButton);
        findViewById(R.id.DateScheduleButton).setOnClickListener(dateScheduleButton);
        findViewById(R.id.DateTaskButton).setOnClickListener(dateTaskButton);
        findViewById(R.id.BackButton).setOnClickListener(backButton);
        findViewById(R.id.DateScheduleAddSchedule).setOnClickListener(dateScheduleAddSchedule);


        TextView DateTextView = (TextView) findViewById(R.id.DateTextView);
        LinearLayout scrollView = (LinearLayout)findViewById(R.id.ScrollView);

//      送ったデータ
//      intent.putExtra("Month",ClickMonth);
//      intent.putExtra("Date",ClickDate);
//      intent.putExtra("DayOfWeek",ClickDayOfWeek);
        Intent receivedIntent = getIntent();
        monthReceivedItem = receivedIntent.getIntExtra("Month",0);
        dateReceivedItem = receivedIntent.getIntExtra("Date",0);
        dayOfWeekReceivedItem = receivedIntent.getIntExtra("DayOfWeek",0);
        ym = receivedIntent.getStringExtra("yyyy.mm");
        String DayOfWeek = DayOfWeekIntToStr(dayOfWeekReceivedItem);
        String tmp = monthReceivedItem + "月" + dateReceivedItem +"日" + "("+ DayOfWeek +")";
        DateTextView.setText(tmp);



        //データベースから曜日に合ったデータを取得
        //db.execSQL("create table Schedule(ScheduleTitle text,DayOfWeek text,Time text,TeacherName text,TeacherMail text);");
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues cv = new ContentValues();
        String query = "SELECT * FROM Schedule WHERE DayOfWeek = ? ORDER BY Time";
        String[] selectionArgs = {DayOfWeek};
        Cursor cursor = db.rawQuery(query,selectionArgs);
        String scheduleDataText = "";
        //cursorからデータを取り出す

        if (cursor != null && cursor.moveToFirst()){
            do{
                String scheduleTitle = cursor.getString(cursor.getColumnIndexOrThrow("ScheduleTitle"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("Time"));
                String teacherName = cursor.getString(cursor.getColumnIndexOrThrow("TeacherName"));
                String teacherMail = cursor.getString(cursor.getColumnIndexOrThrow("TeacherMail"));
                scheduleDataText = time + " " + scheduleTitle + " " + teacherName;
                TextView textView = new TextView(this);
                textView.setText(scheduleDataText);
                textView.setBackgroundColor(Color.parseColor("#FFB2CC"));
                textView.setRawInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                scrollView.addView(textView);
            }while (cursor.moveToNext());
        }
    }
    //授業、課題ボタンクリック時の処理
    View.OnClickListener dateScheduleButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        }
    };
    View.OnClickListener dateTaskButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),DateTaskActivity.class);
            intent.putExtra("Month",monthReceivedItem);
            intent.putExtra("Date",dateReceivedItem);
            intent.putExtra("DayOfWeek",dayOfWeekReceivedItem);
            intent.putExtra("yyyy.mm",ym);
            startActivity(intent);
        }
    };
    View.OnClickListener backButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener dateScheduleAddSchedule = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),AddDateScheduleActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener calenderButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
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
