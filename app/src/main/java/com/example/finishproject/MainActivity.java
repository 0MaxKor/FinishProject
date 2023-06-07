package com.example.finishproject;




import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

Button goto_notes;
CalendarView calendarView;
String spacialString="";
TextView tv_city;
TextView tv_temp;
ImageView imgv;
String dbs="";
String spasialLang="ru";
String weatherCity;
TextView tvDate;
String language;
Button goto_settings;
String translateString="";
Retrofit transRet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
       StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goto_settings=findViewById(R.id.goto_Settings_J);
        imgv=findViewById(R.id.imgv);
calendarView=findViewById(R.id.cv_mainCalendar);
tvDate=findViewById(R.id.tvDate);
tv_city=findViewById(R.id.tvCity);
tv_temp=findViewById(R.id.tvTemp);

defLang();
defLang();


        DBsetting setHelper = new DBsetting(this);

        SQLiteDatabase setdatabase =  setHelper.getWritableDatabase();
//setdatabase.delete(DBsetting.TABLE_NAME,null,null);
//
//NoteBase noteBase = new NoteBase(this);
//
//        SQLiteDatabase sqLiteDatabaseq =  noteBase.getWritableDatabase();
//        setdatabase.delete(DBsetting.TABLE_NAME,null,null);
//        sqLiteDatabaseq.delete(NoteBase.TABLE_NAME,null,null);
        Cursor cursor =setdatabase.query(DBsetting.TABLE_NAME,null,null,null,null,null,null);
        int nameIndex = cursor.getColumnIndex(DBsetting.NAME_SETTING);
        if (cursor.moveToFirst()){

            do {
            dbs+= cursor.getString(nameIndex);


            }while (cursor.moveToNext());

        }else
            cursor.close();
if(dbs==""||dbs==null){
    Intent g = new Intent(MainActivity.this, SettingActyvityDB.class);
    startActivity(g);
}




        Log.i("DATABB",dbs);
        setHelper.close();
        goto_notes = findViewById(R.id.goto_Notes);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DBsetting dBsetting = new DBsetting(this);

        SQLiteDatabase sqLiteDatabase = dBsetting.getWritableDatabase();
        Cursor cur =sqLiteDatabase.query(DBsetting.TABLE_NAME,null,null,null,null,null,null);

        if (cursor.moveToFirst()){
            int setnameIndex = cursor.getColumnIndex(DBsetting.NAME_SETTING);
            int setcontIndex = cursor.getColumnIndex(DBsetting.KEY_CONTENTION);
            do {
                if(cursor.getString(setnameIndex).equals("lang")){
                    language=cursor.getString(setcontIndex);
                }if(cursor.getString(setnameIndex).equals("city")){
                    weatherCity=cursor.getString(setcontIndex);
                }


            }while (cursor.moveToNext());

        }else
            cursor.close();
        dBsetting.close();


        if(dbs==""||dbs==null){
            Intent g = new Intent(MainActivity.this, SettingActyvityDB.class);
            startActivity(g);
        }

        Log.i("WEATHERC",language+" "+weatherCity);


transRet = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        if(weatherCity!=null)
       Log .i("anss",defWeather(retrofit,weatherCity,2));

        goto_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e= new Intent(MainActivity.this, SettingMainActivity.class);
                startActivity(e);
            }
        });
        goto_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NoteLobbyActivity.class);
                startActivity(i);
            }
        });
        if(spasialLang.equals("ru")){
            goto_notes.setText("Заметки");
            goto_settings.setText("Настройки");

        }else {
            goto_notes.setText("Notes");
            goto_settings.setText("Settings");
        }
        dataC(calendarView,tvDate);
        // http://api.openweathermap.org/data/2.5/find/?q=Ivanovo&type=like&APPID=1ddd6aec17416c218d71d7b9e2cb3b0a
    }





    public String defWeather(Retrofit retrofit, String city,int cod) {
   String[]weathers = new String[3];
        WeatherServiceLinker ws = retrofit.create(WeatherServiceLinker.class);
        Call<Weather> call = ws.w(city, "like", "1ddd6aec17416c218d71d7b9e2cb3b0a");
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {







if(spasialLang.equals("ru")){
    translate(transRet,response.body().list.get(0).name,"en","ru",tv_city);


    if(response.body().list.get(0).weather.get(0).main.equals("Clear")) {
        tv_temp.setText("t="+(int)(response.body().list.get(0).main.temp-273)+", Ясно");
    }else if(response.body().list.get(0).weather.get(0).main.equals("Rain")) {
        tv_temp.setText("t="+(int)(response.body().list.get(0).main.temp-273)+", Дождь");
    } else if(response.body().list.get(0).weather.get(0).main.equals("Snow")){
        tv_temp.setText("t="+(int)(response.body().list.get(0).main.temp-273)+", Снег");
    }else if(response.body().list.get(0).weather.get(0).main.equals("Clouds")){
        tv_temp.setText("t="+(int)(response.body().list.get(0).main.temp-273)+", Облачно");
    }
} else if (spasialLang.equals("en")) {
    tv_temp.setText("t="+(int)(response.body().list.get(0).main.temp-273)+", "+response.body().list.get(0).weather.get(0).main);

    tv_city.setText(response.body().list.get(0).name);
}


                if(response.body().list.get(0).weather.get(0).main.equals("Clouds")){
                        imgv.setImageResource(R.drawable.clouds);

                    }else if(response.body().list.get(0).weather.get(0).main.equals("Clear")){
                        imgv.setImageResource(R.drawable.sunny);
                    } else if (response.body().list.get(0).weather.get(0).main.equals("Rain")) {
                        imgv.setImageResource(R.drawable.rain);
                    }else if(response.body().list.get(0).weather.get(0).main.equals("Snow")){
                        imgv.setImageResource(R.drawable.snow);
                    }


                Log.i("ANSS","Resp");
                Log.i("ANSS",response.body().list.get(0).name+"   ---   "+(response.body().list.get(0).main.temp-273)+"   ---   "+response.body().list.get(0).weather.get(0).main);
                Log.i("ANSS", spacialString);


            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.i("ANSS","Fail");
            }
        });

return spacialString;
    }public void translate(Retrofit transret, String text, String from, String to, TextView tv){

        TranslateLinker translator = transret.create(TranslateLinker.class);


        Call<Translator> trans = translator.translate("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw",text, from,to);
        trans.enqueue(new Callback<Translator>() {
            @Override
            public void onResponse(Call<Translator> call, Response<Translator> response) {
                translateString =  response.body().data.translations.get(0).translatedText;
                tv.setText(response.body().data.translations.get(0).translatedText);
                Log.i("trans", "resp");
                Log.i("TRANS", translateString);

            }

            @Override
            public void onFailure(Call<Translator> call, Throwable t) {
                Log.i("trans", "fail");
            }
        });


    }
    void dataC(CalendarView cv, TextView tv){
        long date = cv.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        String year,month="",day;
        int year1,month1,day1;
        year1 = calendar.get(Calendar.YEAR);
        month1 = calendar.get(Calendar.MONTH);
        day1 = calendar.get(Calendar.DAY_OF_MONTH);

        if(month1==0){
            month="января";
        } else if (month1==1) {
            month="февраля";
        }else if (month1==2) {
            month="марта";
        }else if (month1==3) {
            month="апреля";
        }else if (month1==4) {
            month="мая";
        }else if (month1==5) {
            month="июня";
        }else if (month1==6) {
            month="июля";
        }else if (month1==7) {
            month="августа";
        }else if (month1==8) {
            month="сентября";
        }else if (month1==9) {
            month="октября";
        }else if (month1==10) {
            month="ноября";
        }else if (month1==11) {
            month="декабря";
        }
        day = day1+"-e";
        year=year1+"";
        tv.setText(day+" "+month+" "+year);
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
        spasialLang=c;
        spasialLang=c;
        spasialLang=c;
        spasialLang=c;
        spasialLang=c;
        spasialLang=c;
    }

}