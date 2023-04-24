package com.example.finishproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyNoteActivity extends AppCompatActivity {
    DBHelper dbHelper;
    Button del;
    Button add;
    Button read;
    EditText etDate;
    EditText etName;
    EditText etContention;
    TextView savings;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_note);
        ArrayList<String>array_of_names = new ArrayList<>();

        savings = findViewById(R.id.tvsavings);


        del = findViewById(R.id.btnDel);
        add = findViewById(R.id.btnAdd);
        read = findViewById(R.id.btnRead);

        etDate  = findViewById(R.id.etDate);
        etName  = findViewById(R.id.etName);
        etContention  = findViewById(R.id.etContention);

        dbHelper = new DBHelper(this);
        list = findViewById(R.id.lvSavings);

        ArrayAdapter<String> adapter = new  ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array_of_names);




        del.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                String name = etName.getText().toString();
                String date = etDate.getText().toString();
                String contention = etContention.getText().toString();
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                String query="DELETE * FROM Notes WHERE NOTE_NAME = '"+name+"'";
                database.execSQL(query);

                dbHelper.close();
                for (int i = 0; i < list.getBottom()+1 ; i++) {
                    array_of_names.remove(name);
                }


                list.setAdapter(adapter);
            }

        });
        add.setOnClickListener(viev-> {

            String name = etName.getText().toString();
            String date = etDate.getText().toString();
            String contention = etContention.getText().toString();

            SQLiteDatabase database = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBHelper.NOTE_NAME, name);
            values.put(DBHelper.KEY_CONTENTION, contention);
            values.put(DBHelper.KEY_DATE, date);
            // TODO: 27.03.2023
            database.insert(DBHelper.TABLE_NAME,null, values);

            dbHelper.close();


        });
        read.setOnClickListener(view -> {


            String name = etName.getText().toString();
            String date = etDate.getText().toString();
            String contention = etContention.getText().toString();
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            Cursor cursor = database.query(DBHelper.TABLE_NAME, null,null,null,null,null,null);

            if(cursor.moveToFirst()){
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.NOTE_NAME);
                int contentionIndex = cursor.getColumnIndex(DBHelper.KEY_CONTENTION);
                int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
                String s;
                do {
                    String d =savings.getText().toString();
                    array_of_names.add(array_of_names.size(), cursor.getString(nameIndex));
                    d+= ("\n"+cursor.getString(nameIndex)+": "+"\n"+
                            cursor.getString(contentionIndex)+"\n"+"Добавлено: "+
                            cursor.getString(dateIndex)+"\n" +"_______________________");
                    s=d;

                }while (cursor.moveToNext());
                list.setAdapter(adapter);

                savings.setText(s);
            }else savings.setText("Ничего нет");
            cursor.close();



            dbHelper.close();
        });

    }
}