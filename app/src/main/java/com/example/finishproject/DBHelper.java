package com.example.finishproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=1;
    public static final String  DATABASE_NAME="dbNotes";
    public static final String  TABLE_NAME="Notes";
    public static final String  NOTE_NAME="NoteName";



    public static final String  KEY_ID="_id";
    public static final String  KEY_CONTENTION="contention";
    public static final String  KEY_DATE="date";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOTE_NAME + " TEXT, " +
                KEY_DATE + " TEXT, " +
                KEY_CONTENTION + " TEXT); ";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


}
