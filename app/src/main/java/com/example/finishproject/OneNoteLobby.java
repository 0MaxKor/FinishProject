package com.example.finishproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class OneNoteLobby extends AppCompatActivity {
Button change;
Button delete;
TextView tvName;
TextView tvCont;
TextView tvDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_note_lobby);

        change=findViewById(R.id.btnChange_one_note);
        delete=findViewById(R.id.btnDelete_one_note);

        tvCont=findViewById(R.id.tv_cont_one_note);
        tvDate=findViewById(R.id.tv_date_one_note);
        tvName=findViewById(R.id.tv_name_one_note);

        String name=getIntent().getStringExtra("name");

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_NAME,null,null,null,null,null,null);
        String final_name="";
        String final_cont="";
        String final_date="";
        if(cursor.moveToFirst()){
            int nameIndex=cursor.getColumnIndex(DBHelper.NOTE_NAME);
            int contentionIndex=cursor.getColumnIndex(DBHelper.KEY_CONTENTION);
            int dateIndex=cursor.getColumnIndex(DBHelper.KEY_DATE);
            do{
                String readable_note_name = cursor.getString(nameIndex);
                if(readable_note_name.equals(name)){
                    final_name=name;
                    final_cont= cursor.getString(contentionIndex);
                    long readable_date = cursor.getLong(dateIndex);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(readable_date);
                    String year = calendar.get(Calendar.YEAR)+"";
                    String day = calendar.get(Calendar.DAY_OF_MONTH)+"";
                    String month = calendar.get(Calendar.MONTH)+"";
                    final_date=year+" "+month+" "+day;
                }
            }while (cursor.moveToNext());
        }

        tvName.setText(final_name);
        tvCont.setText(final_cont);
        tvDate.setText(final_date);


        change.setOnClickListener(view->{



        });
        delete.setOnClickListener(view->{



        });
    }
}