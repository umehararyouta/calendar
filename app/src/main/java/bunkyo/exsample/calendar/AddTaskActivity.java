package bunkyo.exsample.calendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.sql.Time;
import java.util.Locale;

import kotlinx.coroutines.scheduling.Task;

public class AddTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    DB helper;
    private EditText datePickerItem;
    private EditText timePickerItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tusk);
        helper = new DB(this);
        findViewById(R.id.CalenderButton).setOnClickListener(calenderButton);
        findViewById(R.id.ScheduleButton).setOnClickListener(scheduleButton);
        findViewById(R.id.TaskButton).setOnClickListener(taskButton);
        datePickerItem = findViewById(R.id.DatePickerItem);
        timePickerItem = findViewById(R.id.TimePickerItem);
        findViewById(R.id.SubmitSchedule).setOnClickListener(submitSchedule);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        String tmp = String.format(Locale.US, "%d/%d/%d",year, monthOfYear+1, dayOfMonth);
        datePickerItem.setText(tmp);
        }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String str = String.format(Locale.US, "%d:%d", hourOfDay, minute);
        timePickerItem.setText( str );

    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePick();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePick();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
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
    View.OnClickListener submitSchedule = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText TitleEditText = findViewById(R.id.TitleEditText);
            String Date = datePickerItem.getText().toString();
            String Time = timePickerItem.getText().toString();
            String TaskTitle  = TitleEditText.getText().toString();
            //DBに記録
            //Task(TaskTitle text,DayOfWeek text,Date text,Time text)
            SQLiteDatabase db = helper.getReadableDatabase();
            ContentValues cv = new ContentValues();

            cv.put("TaskTitle",TaskTitle);
            cv.put("Date",Date);
            cv.put("Time",Time);
            db.insert("Task",null,cv);
            db.close();
            Toast.makeText(AddTaskActivity.this,"課題を追加しました。",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    };
}