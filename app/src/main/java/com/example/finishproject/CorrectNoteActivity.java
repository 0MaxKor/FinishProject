package com.example.finishproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CorrectNoteActivity extends AppCompatActivity {
    EditText etname;
    EditText etcont;
    CalendarView calendarView;
    CheckBox checkBox;
    String date="";
    Button btt_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct_note);
        etcont = findViewById(R.id.etContention_cor);
        etname = findViewById(R.id.etName_cor);
        checkBox = findViewById(R.id.ch_importantOrNot_cor);
        calendarView = findViewById(R.id.cv_calendarView_cor);
        btt_add = findViewById(R.id.btnChange_cor);
        String name = getIntent().getStringExtra("name1");
        String cont = "";

        NoteBase noteBase = new NoteBase(this);
        SQLiteDatabase database = noteBase.getWritableDatabase();

        Cursor cursor = database.query(NoteBase.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(NoteBase.NAME_OF_NOTE);
            int contIndex = cursor.getColumnIndex(NoteBase.CONTENTION_OF_NOTE);
            do {
                if (cursor.getString(nameIndex).equals(name)) {
                    cont = cursor.getString(contIndex);
                }
            } while (cursor.moveToNext());

        }
        etname.setText(name);
        etcont.setText(cont);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                date = year + " " + month + " " + dayOfMonth;
            }
        });

        noteBase.close();
        btt_add.setOnClickListener(view -> {
            ContentValues values = new ContentValues();

            NoteBase noteBase1 = new NoteBase(this);
            SQLiteDatabase database1 = noteBase1.getWritableDatabase();
            String query = "DELETE FROM "+NoteBase.TABLE_NAME+" WHERE "+NoteBase.NAME_OF_NOTE+" LIKE '"+name+"'";
            database1.execSQL(query);
            values.put(NoteBase.NAME_OF_NOTE, etname.getText().toString());
            values.put(NoteBase.CONTENTION_OF_NOTE, etcont.getText().toString());
            values.put(NoteBase.DATE_OF_NOTE, date);
            if (checkBox.isChecked()) {
                values.put(NoteBase.IMPORTANT, 1);
            } else {
                values.put(NoteBase.IMPORTANT, 0);
            }
            database1.insert(NoteBase.TABLE_NAME, null, values);
            noteBase1.close();

            Intent y = new Intent(CorrectNoteActivity.this, NoteLobbyActivity.class);
            startActivity(y);


        });

    }

}