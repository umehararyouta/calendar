package bunkyo.exsample.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    DB helper;
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
            Intent intent = new Intent(getApplicationContext(),AddDateSchduleActivity.class);
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
        titleText.setText(dateManagement.getTitleText(dateManagement.LookingDate));
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(MainActivity.this,R.layout.date,dateManagement.girdArray(dateManagement.LookingDate));
        calendarGridView.setAdapter(arrayAdapter);
//        calendarGridView.setOnClickListener(new AdapterView.OnItemClickListener(){
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//
//        });
    }
//    public void insertDB(){
//        SQLiteDatabase db = helper.getReadableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put();
//        db.insert();
//        db.close();
//    }
//    public void showDB(){
//
//    }
}