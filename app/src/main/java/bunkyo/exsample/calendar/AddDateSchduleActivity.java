package bunkyo.exsample.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;

public class AddDateSchduleActivity extends AppCompatActivity {
    //spinner用配列
    private final String[] DayOfWeekSpinner={"月","火","水","木","金","土","日"};
    private final String[] HoursSpinner = {"1時間目","2時間目","3時間目","4時間目","5時間目","6時間目","7時間目"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //リスナーとか追加
        setContentView(R.layout.add_date_schedul);
        findViewById(R.id.CalenderButton).setOnClickListener(calenderButton);
        findViewById(R.id.ScheduleButton).setOnClickListener(scheduleButton);
        findViewById(R.id.TaskButton).setOnClickListener(taskButton);
        Spinner dayOfWeekSpinner = findViewById(R.id.spinner);
        Spinner hoursSpinner = findViewById(R.id.spinner2);
        Button submitSchedule = findViewById(R.id.SubmidSchedule);

        //スピナーに配列を送る
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, DayOfWeekSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayOfWeekSpinner.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, HoursSpinner);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hoursSpinner.setAdapter(adapter1);

        //決定ボタンがクリックされた時の処理
        //スピナーの選択されているデータを取るためにOnCreateの中にOnClickを記述
        submitSchedule.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String dayOfWeekSpinnerSelected = dayOfWeekSpinner.getSelectedItem().toString();
                String hoursSpinnerSelected = hoursSpinner.getSelectedItem().toString();
                if (!hoursSpinnerSelected.isEmpty() && !dayOfWeekSpinnerSelected.isEmpty()){
                    //選択されているときDBに追加
                    //calenderに画面を遷移
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);

                }else{
                    //選択されていないとき
                    Toast.makeText(AddDateSchduleActivity.this,"曜日、時間を指定してください",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //画面下部のボタン3つの処理
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
        }
    };
    View.OnClickListener taskButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),AddTaskActivity.class);
            startActivity(intent);
        }
    };
}