package com.example.finishproject;




import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Collection;
import java.util.Objects;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

Button goto_notes;
String spacialString="";
Button btn_temp;
EditText et_city;
TextView tv_temp;
String translateString="";
int c=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
       StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//if(DBHelper.DATABASE_VERSION==1 && c==0){
//    Intent t = new Intent(MainActivity.this,SettingActyvityDB.class);
//    startActivity(t);
//}
c++;
        btn_temp=findViewById(R.id.btntemp);
        tv_temp=findViewById(R.id.tvtemp);
         et_city = findViewById(R.id.etcity);
        goto_notes = findViewById(R.id.goto_Notes);




        DBHelper helper = new DBHelper(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();




        Retrofit transRet = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();






        btn_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Call<Weather> cq = wservice.w(et_city.getText().toString(),"like","1ddd6aec17416c218d71d7b9e2cb3b0a");
//                cq.enqueue(new Callback<Weather>() {
//                    @Override
//                    public void onResponse(Call<Weather> call, Response<Weather> response) {
//
//                    city_weather=response.body().list.get(0).weather.get(0).main;
//                    city_name=response.body().list.get(0).name;
//                    city_temperature=(int)((response.body().list.get(0).main.temp)-273);
//
//
//                        Toast.makeText(MainActivity.this, city_name+", t="+(int)((response.body().list.get(0).main.temp)-273)+", "+city_weather, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<Weather> call, Throwable t) {
//                        Log.e("tagrr", t.getMessage());
//                    }
//                });

defWeather(retrofit,"Ivanovo",2);
translate(transRet,spacialString, "en", "");
Log.i("WEATHER",translateString);

            }
        });
        goto_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NoteLobbyActivity.class);
                startActivity(i);
            }
        });
        // http://api.openweathermap.org/data/2.5/find/?q=Ivanovo&type=like&APPID=1ddd6aec17416c218d71d7b9e2cb3b0a

    }


    public void defWeather(Retrofit retrofit, String city,int cod) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        WeatherServiceLinker ws = retrofit.create(WeatherServiceLinker.class);
        Call<Weather> call = ws.w(city, "like", "1ddd6aec17416c218d71d7b9e2cb3b0a");
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if(cod==0){
                    spacialString=response.body().list.get(0).name;
                }else if(cod==1){
                    spacialString=(response.body().list.get(0).main.temp-273)+"";
                }else if(cod==2){
                    spacialString=response.body().list.get(0).weather.get(0).main;
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


    }public void translate(Retrofit transRet, String text, String from, String to){

        TranslateLinker translator = transRet.create(TranslateLinker.class);


        Call<Translator> trans = translator.translate("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw",text, from,to);
        trans.enqueue(new Callback<Translator>() {
            @Override
            public void onResponse(Call<Translator> call, Response<Translator> response) {
                translateString =  response.body().data.translations.get(0).translatedText;
                Log.i("trans", "resp");
                Log.i("TRANS", translateString);

            }

            @Override
            public void onFailure(Call<Translator> call, Throwable t) {
                Log.i("trans", "fail");
            }
        });
    }

}