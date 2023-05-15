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


import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

Button goto_notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

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

        Weather wiv = d.fromJson(w,Weather.class);

            Log.i("TTTTAAAAG",wiv.list.get(0).weatherr.description.toString());



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