package com.example.finishproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLClientInfoException;

public class DBsetting extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=1;
    public static final String  DATABASE_NAME="dbsettings";
    public static final String  TABLE_NAME="Settings";
    public static final String  NAME_SETTING="NameOfSet";



    public static final String  KEY_ID="_id";
    public static final String  KEY_CONTENTION="contention";



    public DBsetting(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME_SETTING + " TEXT, " +
                KEY_CONTENTION + " TEXT); ";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}
