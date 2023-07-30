package bunkyo.exsample.calendar;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DB extends SQLiteOpenHelper{

        public DB(Context context){
            super(context,"DB_Calender",null,1);
        }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Schedule(DayOfWeek Integer,Time Integer,Name text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
