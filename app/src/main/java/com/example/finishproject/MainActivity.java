package com.example.finishproject;




import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import java.util.Arrays;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

Button goto_notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goto_notes = findViewById(R.id.goto_Notes);
        goto_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MyNoteActivity.class);
                startActivity(i);
            }
        });

    }
}

//    @Override
//    public void onClick(View view) {
//
//        String name = etName.getText().toString();
//                String date = etDate.getText().toString();
//                String contention = etContention.getText().toString();
//                SQLiteDatabase database = dbHelper.getWritableDatabase();
//                ContentValues values = new ContentValues();
//
//        switch (view.getId()){
//            case  R.id.btnAdd:
//
//                values.put(DBHelper.NOTE_NAME, name);
//               values.put(DBHelper.KEY_CONTENTION, contention);
//                values.put(DBHelper.KEY_DATE, date);
//                  database.insert(DBHelper.TABLE_NAME,null, values);
//            break;
//            case  R.id.btnDel:
//                database.delete(DBHelper.TABLE_NAME,null,null);
//                break;
//            case R.id.btnRead:
//                Cursor cursor = database.query(DBHelper.TABLE_NAME, null,null,null,null,null,null);
//
//                if(cursor.moveToFirst()){
//                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
//                    int nameIndex = cursor.getColumnIndex(DBHelper.NOTE_NAME);
//                    int contentionIndex = cursor.getColumnIndex(DBHelper.KEY_CONTENTION);
//                    int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
//                    do {
//                        savings.setText("ID = "+cursor.getInt(idIndex)+", || NAME = "
//                                +cursor.getString(nameIndex)+", || CONTENTION = "
//                                +cursor.getString(contentionIndex)+", || DATE =  "
//                                +cursor.getString(dateIndex));
//
//                    }while (cursor.moveToNext());
//
//                }else savings.setText("Ничего нет");
//       cursor.close();
//                break;
//        }dbHelper.close();

