package com.example.finishproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class NoteLobbyActivity extends AppCompatActivity {
    ListView lvNotes;
    ArrayList<String>noteArrayList;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_lobby);
        lvNotes=findViewById(R.id.lvNotes);
        noteArrayList = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,noteArrayList);
dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_NAME, null,null,null,null,null,null);







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
                Toast.makeText(NoteLobbyActivity.this,noteArrayList.get(i).toString(), Toast.LENGTH_SHORT).show();
            }
        });

}   }