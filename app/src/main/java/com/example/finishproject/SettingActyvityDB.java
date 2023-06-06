package com.example.finishproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.vcn.VcnConfig;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActyvityDB extends AppCompatActivity {
EditText city;
CheckBox lang_en;
CheckBox lang_ru;
Button btn_go;
boolean cango=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_actyvity_db);
        city=findViewById(R.id.etcity_forset);
        lang_en=findViewById(R.id.ch_en);
        lang_ru=findViewById(R.id.ch_ru);



        btn_go = findViewById(R.id.ready);

        btn_go.setOnClickListener(viev->{


String c = city.getText().toString();
if(lang_en.isChecked()&&lang_ru.isChecked()){
    Toast.makeText(this, "Выберете  1 язык", Toast.LENGTH_SHORT).show();
}else cango=true;

if(c==null||c==""||c==" "||c=="   "||c=="    "||c=="     "){
    cango=false;
    Toast.makeText(this, "Введите город", Toast.LENGTH_SHORT).show();
}else cango=true;

if(cango==true){
    DBsetting helper = new DBsetting(this);
    SQLiteDatabase database = helper.getWritableDatabase();
    ContentValues values = new ContentValues();

    if(lang_en.isChecked()){
        values.put(DBsetting.NAME_SETTING, "lang");
        values.put(DBsetting.KEY_CONTENTION, "en");
    }else if(lang_ru.isChecked()){
        values.put(DBsetting.NAME_SETTING, "lang");
        values.put(DBsetting.KEY_CONTENTION, "ru");
    }


    database.insert(DBsetting.TABLE_NAME   ,null,values);
    ContentValues cityval=new ContentValues();
    cityval.put(DBsetting.NAME_SETTING,"city");
    cityval.put(DBsetting.KEY_CONTENTION,city.getText().toString());
database.insert(DBsetting.TABLE_NAME,null,cityval);
    Intent y =new Intent(SettingActyvityDB.this, MainActivity.class);
    startActivity(y);
    helper.close();
}


        });


    }
}