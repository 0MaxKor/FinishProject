package com.example.finishproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NoteLobbyActivity extends AppCompatActivity {
    String translateString;
    ListView lvNotes;
    Spinner spinner;

    String action;
    int countImportant=0;
    ArrayList<String>noteArrayList;
    String[] actions = {"Дате","Важности","Только важные","Только второстепенные","Все заметки"};

    NoteBase noteBase;
    String spacialString="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_lobby);
        spinner=findViewById(R.id.spinner);
        lvNotes=findViewById(R.id.lvNotes);
        noteArrayList = new ArrayList<>();
        int city_temp = getIntent().getIntExtra("c_temp",1);
        String city_name = getIntent().getStringExtra("c_name");
        String city_weather = getIntent().getStringExtra("c_desc");
        Toast.makeText(this, city_name+", "+city_weather+", temperature = "+city_temp+" degrees.", Toast.LENGTH_SHORT).show();




        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,noteArrayList);
noteBase= new NoteBase(this);
        SQLiteDatabase database = noteBase.getWritableDatabase();


        ArrayAdapter<String>actAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,actions);
        actAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(actAdapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                action = (String)parent.getItemAtPosition(position);
                Toast.makeText(NoteLobbyActivity.this, action, Toast.LENGTH_SHORT).show();
                refresh(lvNotes,action);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);

        Cursor cursor = database.query(NoteBase.TABLE_NAME, null,null,null,null,null,null);
      if (cursor.moveToFirst()){
          int nameIndex = cursor.getColumnIndex(NoteBase.NAME_OF_NOTE);
          int importantIndex = cursor.getColumnIndex(NoteBase.IMPORTANT);
          do {
if(cursor.getInt(importantIndex)==1){

    noteArrayList.add(cursor.getString(nameIndex));
    countImportant++;
}

          }while (cursor.moveToNext());

      }else
          cursor.close();


        Cursor cursor1 = database.query(NoteBase.TABLE_NAME, null,null,null,null,null,null);
        if (cursor1.moveToFirst()){
            int nameIndex = cursor1.getColumnIndex(NoteBase.NAME_OF_NOTE);
            int importantIndex = cursor1.getColumnIndex(NoteBase.IMPORTANT);
            do {
                if(cursor1.getInt(importantIndex)==0){

                    noteArrayList.add(cursor1.getString(nameIndex));

                }

            }while (cursor1.moveToNext());

        }else
            cursor1.close();

        noteBase.close();
        lvNotes.setAdapter(adapter);

        for (int i = 0; i < countImportant; i++) {
            Log.i("errtt",lvNotes.getChildCount()+"");
            Log.i("errtt",lvNotes.getChildCount()+"");

        }

        lvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

             //   Toast.makeText(NoteLobbyActivity.this,noteArrayList.get(i).toString(), Toast.LENGTH_SHORT).show();
Intent p = new Intent(NoteLobbyActivity.this, OneNoteLobby.class);
p.putExtra("name",noteArrayList.get(i).toString());
startActivity(p);



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
    void refresh(ListView listView,String a){
        ArrayList<String>arrayList=new ArrayList<>();

        if(a.equals("Дате")){
            Map<Integer,String>map = new HashMap<>();
            map.get("w");

            ArrayList<Integer>keys=new ArrayList<>();



            NoteBase noteHelper = new NoteBase(this);
            SQLiteDatabase database = noteHelper.getWritableDatabase();


            Cursor cursor1 = database.query(NoteBase.TABLE_NAME, null,null,null,null,null,null);
            if (cursor1.moveToFirst()){
                int nameIndex = cursor1.getColumnIndex(NoteBase.NAME_OF_NOTE);
                int dateIngex = cursor1.getColumnIndex(NoteBase.DATE_OF_NOTE);
                do {
                    int key;
                    int year,month,date;
                    year = getDate(cursor1.getString(dateIngex),"year");
                    month = getDate(cursor1.getString(dateIngex),"month");
                    date = getDate(cursor1.getString(dateIngex),"day");
                    key = 200*year+month*30+date;

                    map.put(key,cursor1.getString(nameIndex));
                    keys.add(key);


                }while (cursor1.moveToNext());

            }else
                cursor1.close();

   noteHelper.close();
int[]keys1 = new int[keys.size()];
            for (int i =0;i<keys.size();i++){
               keys1[i]=keys.get(i);
            }
           Arrays.sort(keys1);

            for (Integer i:
                 keys1) {
                arrayList.add(map.get(i));
            }


        }else if(a.equals("Важности")){
            NoteBase noteHelper = new NoteBase(this);
            SQLiteDatabase database = noteHelper.getWritableDatabase();
            Cursor cursor = database.query(NoteBase.TABLE_NAME, null,null,null,null,null,null);
            if (cursor.moveToFirst()){
                int nameIndex = cursor.getColumnIndex(NoteBase.NAME_OF_NOTE);
                int importantIndex = cursor.getColumnIndex(NoteBase.IMPORTANT);
                do {
                    if(cursor.getInt(importantIndex)==1){

                        arrayList.add(cursor.getString(nameIndex));
                    }

                }while (cursor.moveToNext());

            }else
                cursor.close();


            Cursor cursor1 = database.query(NoteBase.TABLE_NAME, null,null,null,null,null,null);
            if (cursor1.moveToFirst()){
                int nameIndex = cursor1.getColumnIndex(NoteBase.NAME_OF_NOTE);
                int importantIndex = cursor1.getColumnIndex(NoteBase.IMPORTANT);
                do {
                    if(cursor1.getInt(importantIndex)==0){

                        arrayList.add(cursor1.getString(nameIndex));

                    }

                }while (cursor1.moveToNext());

            }else
                cursor1.close();
            noteHelper.close();
        }else if(a.equals("Только важные")){
            NoteBase noteHelper = new NoteBase(this);
            SQLiteDatabase database = noteHelper.getWritableDatabase();
            Cursor cursor = database.query(NoteBase.TABLE_NAME, null,null,null,null,null,null);
            if (cursor.moveToFirst()){
                int nameIndex = cursor.getColumnIndex(NoteBase.NAME_OF_NOTE);
                int importantIndex = cursor.getColumnIndex(NoteBase.IMPORTANT);
                do {
                    if(cursor.getInt(importantIndex)==1){

                        arrayList.add(cursor.getString(nameIndex));
                    }

                }while (cursor.moveToNext());

            }else
                cursor.close();
            noteHelper.close();
        }else if(a.equals("Только второстепенные")){
            NoteBase noteHelper = new NoteBase(this);
            SQLiteDatabase database = noteHelper.getWritableDatabase();
            Cursor cursor = database.query(NoteBase.TABLE_NAME, null,null,null,null,null,null);
            if (cursor.moveToFirst()){
                int nameIndex = cursor.getColumnIndex(NoteBase.NAME_OF_NOTE);
                int importantIndex = cursor.getColumnIndex(NoteBase.IMPORTANT);
                do {
                    if(cursor.getInt(importantIndex)==0){

                        arrayList.add(cursor.getString(nameIndex));
                    }

                }while (cursor.moveToNext());

            }else
                cursor.close();
            noteHelper.close();
        }else if(a.equals("Все заметки")){
            NoteBase noteHelper = new NoteBase(this);
            SQLiteDatabase database = noteHelper.getWritableDatabase();
            Cursor cursor = database.query(NoteBase.TABLE_NAME, null,null,null,null,null,null);
            if (cursor.moveToFirst()){
                int nameIndex = cursor.getColumnIndex(NoteBase.NAME_OF_NOTE);
                int importantIndex = cursor.getColumnIndex(NoteBase.IMPORTANT);
                do {


                        arrayList.add(cursor.getString(nameIndex));


                }while (cursor.moveToNext());

            }else
                cursor.close();
            noteHelper.close();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);
    }

    public int getDate(String date, String what) {
int r=0;

        int y=0,m=0,d=0;
        int sign1=0,sign2=0;
        for (int i = 0; i < date.length(); i++) {
            char f = date.charAt(i);
            if(f==' '){
                sign1=i;
                break;
            }
        }
        char[]chars=new char[sign1];
        for (int i = 0; i < sign1; i++) {
            chars[i]=date.charAt(i);
        }
        String s = new String(chars);
        y=Integer.parseInt(s);


        char[]chars1=new char[date.length()-sign1-1];
        String date1;
        for (int i = sign1+1; i < date.length(); i++) {
            chars1[i-sign1-1]=date.charAt(i);
        }
        date1 = new String(chars1);


        for (int i = 0; i < date1.length(); i++) {
            char f = date1.charAt(i);
            if(f==' '){
                sign1=i;
                break;
            }
        }
        char[]chars2 = new char[sign1];
        for (int i = 0; i < sign1; i++) {
            chars2[i]=date1.charAt(i);
        }
        String mon=new String(chars2);
       m=Integer.parseInt(mon);



       char[]chars3=new char[date1.length()-sign1-1];
        for (int i = sign1+1; i < date1.length(); i++) {
            chars3[i-sign1-1]=date1.charAt(i);
        }
        String s3 = new String(chars3);
        d=Integer.parseInt(s3);
        if(what.equals("year")){
            r=y;

        }else if(what.equals("month")){
            r=m;
        }else if(what.equals("day")){
r=d;
        }else return 0;
    return r;
    }
   public void backa(View v){
        Intent o = new Intent(NoteLobbyActivity.this, MainActivity.class);
        startActivity(o);
    }

}