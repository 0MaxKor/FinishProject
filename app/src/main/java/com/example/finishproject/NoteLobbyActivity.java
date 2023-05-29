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
    String translateString;
    ListView lvNotes;
    ArrayList<String>noteArrayList;

    DBHelper dbHelper;
    Button btt;
    String spacialString="";

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
                Retrofit retrofit  = new Retrofit.Builder()
                        .baseUrl("http://api.openweathermap.org/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                defWeather(retrofit,"Ivanovo",2);

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


    public void translate(Retrofit transRet, String text, String from, String to){

        TranslateLinker translator = transRet.create(TranslateLinker.class);


        Call<Translator> trans = translator.translate("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw",text, from,to);
        trans.enqueue(new Callback<Translator>() {
            @Override
            public void onResponse(Call<Translator> call, Response<Translator> response) {
               translateString =  response.body().data.translations.get(0).translatedText;
            }

            @Override
            public void onFailure(Call<Translator> call, Throwable t) {

            }
        });
    }
    public void defWeather(Retrofit retrofit, String city,int cod) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String mas[]= new String[3];

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


    }
}