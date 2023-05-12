package com.example.finishproject;

import android.app.DownloadManager;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherClass {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    WeatherServiceLinker wservice = retrofit.create(WeatherServiceLinker.class);




}
