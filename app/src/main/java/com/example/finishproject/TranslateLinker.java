package com.example.finishproject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TranslateLinker {

    @GET("/language/translate/v2/")
    Call<Translator> translate (@Query("key") String key,
                                @Query("q")String q,
                                @Query("source")String source,
                                @Query("target")String target);
}
