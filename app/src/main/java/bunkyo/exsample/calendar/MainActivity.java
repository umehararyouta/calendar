package bunkyo.exsample.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    DB helper;
    private Date currentDate;
    private String tmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new DB(this);
        //リスナーとインスタンス生成
        findViewById(R.id.nextButton).setOnClickListener(nextButton);
        findViewById(R.id.prevButton).setOnClickListener(prevButton);
        findViewById(R.id.CalenderButton).setOnClickListener(calenderButton);
        findViewById(R.id.ScheduleButton).setOnClickListener(scheduleButton);
        findViewById(R.id.TaskButton).setOnClickListener(taskButton);
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
    public void CountDownSchedule(){
        //現在のDateを取得
        DateManagement dateManagement = new DateManagement();
        Date CurrentDate = dateManagement.nowDate();
        long currentUnixTime = dateManagement.getCurrentUnixTime(CurrentDate);



    }
}