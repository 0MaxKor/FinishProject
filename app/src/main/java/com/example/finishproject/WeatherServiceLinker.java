package com.example.finishproject;


import android.graphics.Canvas;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherServiceLinker {


    @GET ("/data/2.5/find/q/&type=like&{APPID}")
    Call<WeatherClass> w (@Query("q")String q, @Path("APPID") String APPID);

}
