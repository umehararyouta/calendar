package bunkyo.exsample.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    //変数定義
    TextView titleText = (TextView) findViewById(R.id.titleText);
    Button revButton = (Button) findViewById(R.id.prevButton);
    Button nextButton = (Button) findViewById(R.id.nextButton);
    GridView calendarGridView = (GridView) findViewById(R.id.calendarGridView);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DateManagement datemanagement = new DateManagement();
        titleText.setText(datemanagement.getTitleText());
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,R.layout.activity_main,datemanagement.girdArray());
        calendarGridView.setAdapter(arrayAdapter);

    }
}