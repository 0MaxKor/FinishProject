package com.example.finishproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class NoteBase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "NoteDatabase";
    public static final String DATABASE_ID = "_id";
    public static final String TABLE_NAME = "TableNotes";
    public static final String NAME_OF_NOTE = "NameOfNote";
    public static final String CONTENTION_OF_NOTE = "ContentionOfNote";
    public static final String IMPORTANT = "importance";
    public static final String DATE_OF_NOTE = "DateOfNote";


    public NoteBase(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                DATABASE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME_OF_NOTE + " TEXT, " +
                CONTENTION_OF_NOTE + " TEXT, " +
                IMPORTANT + " INTEGER, " +
                DATE_OF_NOTE + " TEXT); ";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
