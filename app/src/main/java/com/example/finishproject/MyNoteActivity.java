package com.example.finishproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyNoteActivity extends AppCompatActivity {
    DBHelper dbHelper;
    Button del;
    Button add;
    EditText etDate;
    EditText etName;
    EditText etContention;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_note);
        ArrayList<String>array_of_names = new ArrayList<>();



        del = findViewById(R.id.btnDel);
        add = findViewById(R.id.btnAdd);

        etDate  = findViewById(R.id.etDate);
        etName  = findViewById(R.id.etName);
        etContention  = findViewById(R.id.etContention);

        dbHelper = new DBHelper(this);

        ArrayAdapter<String> adapter = new  ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array_of_names);




        del.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                String name = etName.getText().toString();
                String date = etDate.getText().toString();
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                String query="DELETE FROM " + DBHelper.TABLE_NAME + " WHERE " + DBHelper.NOTE_NAME + " LIKE '"+name+"'";
                database.execSQL(query);

                dbHelper.close();
            }


        });
        add.setOnClickListener(viev-> {

            String name = etName.getText().toString();
            String date = etDate.getText().toString();
            String contention = etContention.getText().toString();
            if(name!=""||name!=" "||contention!=""||contention!=" "||name!=null||contention!=null){

                SQLiteDatabase database = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(DBHelper.NOTE_NAME, name);
                values.put(DBHelper.KEY_CONTENTION, contention);
                values.put(DBHelper.KEY_DATE, date);
                database.insert(DBHelper.TABLE_NAME,null, values);

                Toast.makeText(this, "Создана 1 заметка", Toast.LENGTH_SHORT).show();

                dbHelper.close();
            }else {
                Toast.makeText(this, "Заполните все поля ", Toast.LENGTH_SHORT).show();
                //-2183 40 270 -2192 49 243 -1993 61 7 5
        }

        });

}


}