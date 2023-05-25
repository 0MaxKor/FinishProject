package com.example.finishproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NoteLobbyActivity extends AppCompatActivity {
    String weatherString;
    ListView lvNotes;
    ArrayList<String>noteArrayList;

    DBHelper dbHelper;
    Button btt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_lobby);
        btt = findViewById(R.id.btt);
        lvNotes=findViewById(R.id.lvNotes);
        noteArrayList = new ArrayList<>();
        int city_temp = getIntent().getIntExtra("c_temp",1);
        String city_name = getIntent().getStringExtra("c_name");
        String city_weather = getIntent().getStringExtra("c_desc");
        //Toast.makeText(this, city_name+", "+city_weather+", temperature = "+city_temp+" degrees.", Toast.LENGTH_SHORT).show();
        btt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String t =defWeather("Ivanovo","1ddd6aec17416c218d71d7b9e2cb3b0a","temperature");
                 String d =defWeather("Ivanovo","1ddd6aec17416c218d71d7b9e2cb3b0a","description");
                Toast.makeText(NoteLobbyActivity.this,  "t="+t+", desc: "+d, Toast.LENGTH_SHORT).show();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,noteArrayList);
dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_NAME, null,null,null,null,null,null);


//        String query="DELETE FROM " + DBHelper.TABLE_NAME + " WHERE " + DBHelper.NOTE_NAME + " LIKE '"+"WEATHER"+"'";
//        database.execSQL(query);
//         query="DELETE FROM " + DBHelper.TABLE_NAME + " WHERE " + DBHelper.NOTE_NAME + " LIKE '"+"TEMPER"+"'";
//         database.execSQL(query);
//         query="DELETE FROM " + DBHelper.TABLE_NAME + " WHERE " + DBHelper.NOTE_NAME + " LIKE '"+"CITYNAME"+"'";
//         database.execSQL(query);



      if (cursor.moveToFirst()){
          int nameIndex = cursor.getColumnIndex(DBHelper.NOTE_NAME);
          do {

              noteArrayList.add(noteArrayList.size(), cursor.getString(nameIndex));

          }while (cursor.moveToNext());

      }else
          cursor.close();



        dbHelper.close();
        lvNotes.setAdapter(adapter);
        lvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
             //   Toast.makeText(NoteLobbyActivity.this,noteArrayList.get(i).toString(), Toast.LENGTH_SHORT).show();




            }

        });
    }
    public void addAndDelete(View view){
        Intent u = new Intent(NoteLobbyActivity.this, MyNoteActivity.class);
        startActivity(u);
    }


    String translate(String text, String from, String to){

       String[] translated_text = new String[1];


        Retrofit transRet = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TranslateLinker translator = transRet.create(TranslateLinker.class);


        Call<Translator> trans = translator.translate("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw",text, from,to);
        trans.enqueue(new Callback<Translator>() {
            @Override
            public void onResponse(Call<Translator> call, Response<Translator> response) {
               translated_text[0] =  response.body().data.translations.get(0).translatedText;
            }

            @Override
            public void onFailure(Call<Translator> call, Throwable t) {

            }
        });

        return translated_text[0];
    }
    String defWeather(String city, String apikey, String what){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherServiceLinker ws = retrofit.create(WeatherServiceLinker.class);
        Call<Weather> call = ws.w(city,"like",apikey);
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Log.i("TTTAAA",response.body().list.get(0).weather.get(0).description);
                if(what.equals("temperature")){
                    weatherString = response.body().list.get(0).main.temp+"";
                }else if(what.equals("description")){
                    weatherString=response.body().list.get(0).weather.get(0).description;
                }

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

            }
        });
        return weatherString;
    }
}