package bunkyo.exsample.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
        setDateAdapter();
    }
    View.OnClickListener next = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DateManagement dateManagement = new DateManagement();

            dateManagement.LookingDate = dateManagement.nextMonth(dateManagement.LookingDate);
            setDateAdapter();
        }
    };
    View.OnClickListener prev = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DateManagement dateManagement = new DateManagement();
            dateManagement.LookingDate = dateManagement.prevMonth(dateManagement.LookingDate);
            setDateAdapter();

        }
    };
    public void setDateAdapter(){
        DateManagement dateManagement = new DateManagement();
        GridView calendarGridView = (GridView) findViewById(R.id.calendarGridView);
        TextView titleText = (TextView) findViewById(R.id.titleText);
        titleText.setText(dateManagement.getTitleText(dateManagement.LookingDate));
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(MainActivity.this,R.layout.date,dateManagement.girdArray(dateManagement.LookingDate));
        calendarGridView.setAdapter(arrayAdapter);
        calendarGridView.setOnClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

        });
    }

}