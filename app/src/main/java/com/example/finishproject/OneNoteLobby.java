package com.example.finishproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class OneNoteLobby extends AppCompatActivity {
Button change;
Button delete;
TextView tvName;
TextView tvCont;
TextView lanName;
TextView lanCont;
TextView lanAdd;
TextView tvDate;
String final_name="";
    String final_cont="";
    String final_date="";
     String spasialLang="ru";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_note_lobby);

        change=findViewById(R.id.btnChange_one_note);
        delete=findViewById(R.id.btnDelete_one_note);
lanAdd=findViewById(R.id.tvadded);
lanCont=findViewById(R.id.tvcont);
lanName=findViewById(R.id.tvname);
        tvCont=findViewById(R.id.tv_cont_one_note);
        tvDate=findViewById(R.id.tv_date_one_note);
        tvName=findViewById(R.id.tv_name_one_note);

        String name=getIntent().getStringExtra("name");

        NoteBase noteBase = new NoteBase(this);
        SQLiteDatabase database = noteBase.getWritableDatabase();
        Cursor cursor = database.query(NoteBase.TABLE_NAME,null,null,null,null,null,null);

        if(cursor.moveToFirst()){
            int nameIndex=cursor.getColumnIndex(NoteBase.NAME_OF_NOTE);
            int contentionIndex=cursor.getColumnIndex(NoteBase.CONTENTION_OF_NOTE);
            int dateIndex=cursor.getColumnIndex(NoteBase.DATE_OF_NOTE);
            do{
                String readable_note_name = cursor.getString(nameIndex);
                if(readable_note_name.equals(name)){
                    final_name=name;
                    final_cont= cursor.getString(contentionIndex);
                    final_date = cursor.getString(dateIndex);
//                    long readable_date = cursor.getLong(dateIndex);
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.setTimeInMillis(readable_date);
//                    String year = calendar.get(Calendar.YEAR)+"";
//                    String day = calendar.get(Calendar.DAY_OF_MONTH)+"";
//                    String month = calendar.get(Calendar.MONTH)+"";
//                    final_date=year+" "+month+" "+day;
                }
            }while (cursor.moveToNext());
        }
defLang();;
        tvName.setText(final_name);
        tvCont.setText(final_cont);
        tvDate.setText(final_date);
noteBase.close();

        change.setOnClickListener(view->{

            Intent s = new Intent(OneNoteLobby.this,CorrectNoteActivity.class);
            s.putExtra("name1",name);
            startActivity(s);


        });
        delete.setOnClickListener(view->{
            NoteBase noteBase1 = new NoteBase(this);
            SQLiteDatabase database1 = noteBase1.getWritableDatabase();
         String query = "DELETE FROM "+NoteBase.TABLE_NAME+" WHERE "+NoteBase.NAME_OF_NOTE+" LIKE '"+final_name+"'";
         database1.execSQL(query);
         noteBase1.close();

         Intent u = new Intent(OneNoteLobby.this, NoteLobbyActivity.class);
         startActivity(u);



        });
        if(spasialLang.equals("ru")){
            lanName.setText("Имя:");
            lanCont.setText("Содержание:");
            lanAdd.setText("Добавлено на:");
            change.setText("Изменить");
            delete.setText("Удалить");
        }else{
            lanName.setText("Name:");
            lanCont.setText("Contention:");
            lanAdd.setText("Added to:");
            change.setText("Correct");
            delete.setText("delete");
        }
    }
    public void  defLang(){
        String c="ru";
        DBsetting helper= new DBsetting(this);
        SQLiteDatabase langDatabase = helper.getWritableDatabase();

        Cursor cursor = langDatabase.query(DBsetting.TABLE_NAME,null,null,null,null,null,null);
        int setIn=cursor.getColumnIndex(DBsetting.NAME_SETTING);
        int langIn = cursor.getColumnIndex(DBsetting.KEY_CONTENTION);
        if(cursor.moveToFirst()){
            if(cursor.getString(setIn).equals("lang")){
                c= cursor.getString(langIn);
                c= cursor.getString(langIn);
                c= cursor.getString(langIn);
                c= cursor.getString(langIn);
                c= cursor.getString(langIn);


            }while (cursor.moveToNext()) ;
        }
        spasialLang=c;
        spasialLang=c;
        spasialLang=c;
        spasialLang=c;
        spasialLang=c;
        spasialLang=c;
    }
}
