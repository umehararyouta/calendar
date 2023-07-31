package bunkyo.exsample.calendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.sql.Time;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private EditText datePickerItem;
    private EditText timePickerItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tusk);

        datePickerItem = findViewById(R.id.DatePickerItem);
        timePickerItem = findViewById(R.id.TimePickerItem);
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
}