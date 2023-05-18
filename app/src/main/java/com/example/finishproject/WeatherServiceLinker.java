package com.example.finishproject;


import android.graphics.Canvas;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherServiceLinker {


    @GET ("/data/2.5/find/")
    Call<Weather> w (@Query("q")String q,@Query("type")String type, @Query("APPID") String APPID);

    // http://api.openweathermap.org/data/2.5/find/?q=Ivanovo&type=like&APPID=1ddd6aec17416c218d71d7b9e2cb3b0a

}
