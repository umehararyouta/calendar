package bunkyo.exsample.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //リスナーとインスタンス生成
        findViewById(R.id.nextButton).setOnClickListener(next);
        findViewById(R.id.prevButton).setOnClickListener(prev);
        GridView calendarGridView = (GridView) findViewById(R.id.calendarGridView);
        TextView titleText = (TextView) findViewById(R.id.titleText);


        DateManagement datemanagement = new DateManagement();
        titleText.setText(datemanagement.getTitleText());
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this,R.layout.date,datemanagement.girdArray());
        calendarGridView.setAdapter(arrayAdapter);
    }
    View.OnClickListener next = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DateManagement dateManagement = new DateManagement();
            dateManagement.nextMonth();

            GridView calendarGridView = (GridView) findViewById(R.id.calendarGridView);
            TextView titleText = (TextView) findViewById(R.id.titleText);
            titleText.setText(dateManagement.getTitleText());
            ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(MainActivity.this,R.layout.date,dateManagement.girdArray());
            calendarGridView.setAdapter(arrayAdapter);
        }
    };
    View.OnClickListener prev = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DateManagement dateManagement = new DateManagement();
            dateManagement.prevMonth();

            GridView calendarGridView = (GridView) findViewById(R.id.calendarGridView);
            TextView titleText = (TextView) findViewById(R.id.titleText);
            titleText.setText(dateManagement.getTitleText());
            ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(MainActivity.this,R.layout.date,dateManagement.girdArray());
            calendarGridView.setAdapter(arrayAdapter);
        }
    };
}