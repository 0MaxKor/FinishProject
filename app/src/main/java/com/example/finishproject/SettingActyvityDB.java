package com.example.finishproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SettingActyvityDB extends AppCompatActivity {
EditText city;
EditText lang;
Button btn_go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_actyvity_db);
        city=findViewById(R.id.etcity_forset);
        lang=findViewById(R.id.etlang_forset);



        btn_go = findViewById(R.id.ready);

        btn_go.setOnClickListener(viev->{

            DBHelper helper=new DBHelper(this);
            SQLiteDatabase database = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            ContentValues valuesc = new ContentValues();
            values.put(DBHelper.NOTE_NAME, "1357lang");
            valuesc.put(DBHelper.NOTE_NAME, "1357city");
            values.put(DBHelper.KEY_CONTENTION, lang.getText().toString());
            valuesc.put(DBHelper.KEY_CONTENTION, city.getText().toString());

            database.insert(DBHelper.TABLE_NAME,null,values);
            database.insert(DBHelper.TABLE_NAME,null,valuesc);
            Intent g = new Intent(SettingActyvityDB.this, MainActivity.class);
            startActivity(g);
        });


    }
}