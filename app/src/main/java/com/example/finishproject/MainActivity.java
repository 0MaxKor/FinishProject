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
import android.widget.Toast;


import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

Button goto_notes;
TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
txt=findViewById(R.id.textidd);
        Gson d = new Gson();
        String w = "{\"message\":\"like\",\"cod\":\"200\",\"count\":5,\"list\"" +
                ":[{\"id\":555312,\"name\":\"Ivanovo\",\"coord\"" +
                ":{\"lat\":56.9942,\"lon\":40.9858},\"main\":{\"temp\":286.95,\"feels_like\"" +
                ":286.08,\"temp_min\":286.95,\"temp_max\":286.95,\"pressure\":1020,\"humidity\"" +
                ":65,\"sea_level\":1020,\"grnd_level\":1005},\"dt\":1683994145,\"wind\":{\"speed\"" +
                ":5.32,\"deg\":16},\"sys\":{\"country\":\"RU\"},\"rain\":null,\"snow\":null,\"clouds\"" +
                ":{\"all\":71},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\"" +
                ",\"icon\":\"04d\"}]},{\"id\":730852,\"name\":\"Ivanovo\",\"coord\":{\"lat\":43.7,\"lon\"" +
                ":25.9833},\"main\":{\"temp\":290.12,\"feels_like\":289.41,\"temp_min\":290.12,\"temp_max\"" +
                ":290.12,\"pressure\":1022,\"humidity\":59,\"sea_level\":1022,\"grnd_level\":1012},\"dt\"" +
                ":1683993923,\"wind\":{\"speed\":2.32,\"deg\":60},\"sys\":{\"country\":\"BG\"},\"rain\"" +
                ":null,\"snow\":null,\"clouds\":{\"all\":72},\"weather\":[{\"id\":803,\"main\":\"Clouds\"" +
                ",\"description\":\"broken clouds\",\"icon\":\"04d\"}]},{\"id\":730851,\"name\":\"Obshtina Ivanovo\",\"coord\":{\"lat\":43.7,\"lon\"" +
                ":25.9833},\"main\":{\"temp\":290.12,\"feels_like\":289.41,\"temp_min\":290.12,\"temp_max\":290.12,\"" +
                "pressure\":1022,\"humidity\":59,\"sea_level\":1022,\"grnd_level\":1012},\"dt\":1683993923,\"wind\"" +
                ":{\"speed\":2.32,\"deg\":60},\"sys\":{\"country\":\"BG\"},\"rain\":null,\"snow\":null,\"clouds\"" +
                ":{\"all\":72},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\"" +
                ",\"icon\":\"04d\"}]},{\"id\":789982,\"name\":\"Ivanovo\",\"coord\":{\"lat\":44.7392,\"lon\"" +
                ":20.6975},\"main\":{\"temp\":290.03,\"feels_like\":289.58,\"temp_min\":287.2,\"temp_max\"" +
                ":291.41,\"pressure\":1018,\"humidity\":69,\"sea_level\":1018,\"grnd_level\":1010},\"dt\"" +
                ":1683993926,\"wind\":{\"speed\":7.08,\"deg\":113},\"sys\":{\"country\":\"RS\"},\"rain\":null,\"" +
                "snow\":null,\"clouds\":{\"all\":100},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\"" +
                ":\"overcast clouds\",\"icon\":\"04d\"}]},{\"id\":6608392,\"name\":\"Ivanovo\",\"coord\":{\"lat\"" +
                ":56.6914,\"lon\":43.4161},\"main\":{\"temp\":284.96,\"feels_like\":284.36,\"temp_min\":284.96,\"" +
                "temp_max\":284.96,\"pressure\":1018,\"humidity\":83,\"sea_level\":1018,\"grnd_level\":1006},\"dt\"" +
                ":1683994482,\"wind\":{\"speed\":4.34,\"deg\":354},\"sys\":{\"country\":\"RU\"},\"rain\":null,\"snow\"" +
                ":null,\"clouds\":{\"all\":59},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\"" +
                ":\"broken clouds\",\"icon\":\"04d\"}]}]}";
        String ss ="{\"message\":\"like\",\"cod\":\"200\",\"count\":4,\"list\":[{\"id\":524901,\"name\":\"Moscow\",\"coord\":{\"lat\":55.7522,\"lon\":37.6156},\"main\":{\"temp\":292.19,\"feels_like\":290.96,\"temp_min\":291.69,\"temp_max\":292.9,\"pressure\":1022,\"humidity\":31,\"sea_level\":1022,\"grnd_level\":1005},\"dt\":1684159681,\"wind\":{\"speed\":1.18,\"deg\":135},\"sys\":{\"country\":\"RU\"},\"rain\":null,\"snow\":null,\"clouds\":{\"all\":7},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clearsky\",\"icon\":\"01d\"}]},{\"id\":5601538,\"name\":\"Moscow\",\"coord\":{\"lat\":46.7324,\"lon\":-117.0002},\"main\":{\"temp\":290.1,\"feels_like\":289.39,\"temp_min\":288.96,\"temp_max\":290.81,\"pressure\":1020,\"humidity\":59},\"dt\":1684160081,\"wind\":{\"speed\":4.12,\"deg\":100},\"sys\":{\"country\":\"US\"},\"rain\":null,\"snow\":null,\"clouds\":{\"all\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clearsky\",\"icon\":\"01d\"}]},{\"id\":5202009,\"name\":\"Moscow\",\"coord\":{\"lat\":41.3368,\"lon\":-75.5185},\"main\":{\"temp\":286.14,\"feels_like\":284.83,\"temp_min\":283.68,\"temp_max\":288.9,\"pressure\":1016,\"humidity\":51},\"dt\":1684159777,\"wind\":{\"speed\":2.06,\"deg\":250},\"sys\":{\"country\":\"US\"},\"rain\":null,\"snow\":null,\"clouds\":{\"all\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clearsky\",\"icon\":\"01d\"}]},{\"id\":524894,\"name\":\"Moscow\",\"coord\":{\"lat\":55.7617,\"lon\":37.6067},\"main\":{\"temp\":292.11,\"feels_like\":290.87,\"temp_min\":291.59,\"temp_max\":292.81,\"pressure\":1022,\"humidity\":31,\"sea_level\":1022,\"grnd_level\":1003},\"dt\":1684159511,\"wind\":{\"speed\":1.19,\"deg\":134},\"sys\":{\"country\":\"RU\"},\"rain\":null,\"snow\":null,\"clouds\":{\"all\":7},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clearsky\",\"icon\":\"01d\"}]}]}";



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherServiceLinker wservice = retrofit.create(WeatherServiceLinker.class);
       // Call<Weather> call = wservice.w("Ivanovo","1ddd6aec17416c218d71d7b9e2cb3b0a");
        Call<Weather> cq = wservice.w("Canberra","like","1ddd6aec17416c218d71d7b9e2cb3b0a");

            cq.enqueue(new Callback<Weather>() {
                @Override
                public void onResponse(Call<Weather> call, Response<Weather> response) {
                    Log.i("tagrr", "s");


                    Log.i("tagrr", (int)(response.body().list.get(0).main.temp-273)+"");


                }

                @Override
                public void onFailure(Call<Weather> call, Throwable t) {
                    Log.e("tagrr", t.getMessage());
                }
            });




        // http://api.openweathermap.org/data/2.5/find/?q=Ivanovo&type=like&APPID=1ddd6aec17416c218d71d7b9e2cb3b0a



        Weather wax = d.fromJson(w,Weather.class);

            Log.i("TTTTAAAAG",wax.list.get(0).name+"");




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goto_notes = findViewById(R.id.goto_Notes);
        goto_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NoteLobbyActivity.class);
                startActivity(i);
            }
        });

    }
}