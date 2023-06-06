package com.example.finishproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyNoteActivity extends AppCompatActivity {
    DBHelper dbHelper;
    CheckBox isimportant;

    long timeinm;

    Button add;
    EditText etName;
    EditText etContention;
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_note);
        ArrayList<String>array_of_names = new ArrayList<>();
        isimportant=findViewById(R.id.ch_importantOrNot);
        calendarView = findViewById(R.id.cv_calendarView);
 setDate(calendarView,2024,6,6);

        add = findViewById(R.id.btnAdd);

        etName  = findViewById(R.id.etName);
        etContention  = findViewById(R.id.etContention);

        dbHelper = new DBHelper(this);

        ArrayAdapter<String> adapter = new  ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array_of_names);


        //SQLiteDatabase database = dbHelper.getWritableDatabase();
//                String query="DELETE FROM " + DBHelper.TABLE_NAME + " WHERE " + DBHelper.NOTE_NAME + " LIKE '"+name+"'";
//                database.execSQL(query);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR,year);
                c.set(Calendar.MONTH,month);
                c.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                timeinm=c.getTimeInMillis();
            }
        });

        add.setOnClickListener(viev-> {



            String name = etName.getText().toString();
            String contention = etContention.getText().toString();
            if(name!=""||name!=" "||contention!=""||contention!=" "||name!=null||contention!=null){

                SQLiteDatabase database = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put(DBHelper.NOTE_NAME, name);
                values.put(DBHelper.KEY_CONTENTION, contention);
                values.put(DBHelper.KEY_DATE, timeinm);
                if(isimportant.isChecked()){
                    values.put(DBHelper.IS_IMPORTANT,1);
                }else{
                    values.put(DBHelper.IS_IMPORTANT,0);
                }
                database.insert(DBHelper.TABLE_NAME,null, values);

                Toast.makeText(this, "Создана 1 заметка", Toast.LENGTH_SHORT).show();

                dbHelper.close();
            }else {
                Toast.makeText(this, "Заполните все поля ", Toast.LENGTH_SHORT).show();
                //-2183 40 270 -2192 49 243 -1993 61 7 5
            }

        });

}
void setDate(CalendarView calendarView, int year,int month,int day){
    Calendar calendar=Calendar.getInstance();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH,month);
    calendar.set(Calendar.DAY_OF_MONTH,day);
    long time = calendar.getTimeInMillis();
    calendarView.setDate(time);
}
    void setColorDate(CalendarView calendarView, Drawable drawable, long timeInMillis){
        Date date = new Date(timeInMillis);
    calendarView.setBackgroundDrawable(drawable);

    }
}