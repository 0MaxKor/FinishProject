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
   NoteBase noteBase;
    CheckBox isimportant;

    String timeinm;

    Button add;
    EditText etName;
    EditText etContention;
    CalendarView calendarView;
    boolean iswrite=true;
     String spasialLang="ru";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_note);
        ArrayList<String>array_of_names = new ArrayList<>();
        isimportant=findViewById(R.id.ch_importantOrNot);
        calendarView = findViewById(R.id.cv_calendarView);

        add = findViewById(R.id.btnAdd);

        etName  = findViewById(R.id.etName);
        etContention  = findViewById(R.id.etContention);
//
noteBase = new NoteBase(this);

        ArrayAdapter<String> adapter = new  ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array_of_names);


defLang();;


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

timeinm = year + " "+month+" "+dayOfMonth;
            }
        });

        add.setOnClickListener(viev-> {
        iswrite=true;

            String name = etName.getText().toString();
            String contention = etContention.getText().toString();
            SQLiteDatabase database = noteBase.getWritableDatabase();
            
            Cursor cursor = database.query(NoteBase.TABLE_NAME,null,null,null,null,null,null);
            if(cursor.moveToFirst()){
                int nameIndex=cursor.getColumnIndex(NoteBase.NAME_OF_NOTE);
                do {
                    String dbname=cursor.getString(nameIndex);
                    if(dbname.equals(name)){
                        iswrite=false;
                        Toast.makeText(this, "Выберете другое имя", Toast.LENGTH_SHORT).show();
                    }
                }while (cursor.moveToNext());
            }
            
            if((name!=""&&name!=" "&&contention!=""&&contention!=" "&&name!=null&&contention!=null)&&iswrite==true){

                
                ContentValues values = new ContentValues();

                values.put(NoteBase.NAME_OF_NOTE, name);
                values.put(NoteBase.CONTENTION_OF_NOTE, contention);
                values.put(NoteBase.DATE_OF_NOTE, timeinm);
                if(isimportant.isChecked()){
                    values.put(NoteBase.IMPORTANT,1);
                }else{
                            values.put(NoteBase.IMPORTANT,0);
                }
                database.insert(NoteBase.TABLE_NAME,null, values);

                Toast.makeText(this, "Создана 1 заметка", Toast.LENGTH_SHORT).show();

                noteBase.close();
            }else {
                Toast.makeText(this, "Заполните все поля ", Toast.LENGTH_SHORT).show();
                //-2183 40 270 -2192 49 243 -1993 61 7 5
            }

        });

        if(spasialLang.equals("ru")){
            isimportant.setHint("Отметить как важное");
            add.setText("Добавить");
            etContention.setHint("Содержание");
            etName.setHint("Имя");
        }else{
            isimportant.setHint("Note as important");
            add.setText("Add");
            etContention.setHint("Contention");
            etName.setHint("Name");
        }

}


public void  defLang(){
    String c="ru";
    DBsetting helper= new DBsetting(this);
    SQLiteDatabase langDatabase = helper.getWritableDatabase();

    Cursor cursor = langDatabase.query(DBsetting.TABLE_NAME,null,null,null,null,null,null);
    int setIn=cursor.getColumnIndex(DBsetting.NAME_SETTING);
    int langIn = cursor.getColumnIndex(DBsetting.KEY_CONTENTION);
    if(cursor.moveToFirst()){
        if(cursor.getString(setIn).equals("lang")){
            c= cursor.getString(langIn);
            c= cursor.getString(langIn);
            c= cursor.getString(langIn);
            c= cursor.getString(langIn);
            c= cursor.getString(langIn);


        }while (cursor.moveToNext()) ;
    }
    spasialLang = c;
    spasialLang=c;
    spasialLang=c;
    spasialLang=c;


    spasialLang=c;
    spasialLang=c;
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
    public void back(View view){
        Intent y = new Intent(MyNoteActivity.this, NoteLobbyActivity.class);
        startActivity(y);

    }
}