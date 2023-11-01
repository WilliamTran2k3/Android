package com.example.bai2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "EventsManageDB";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "event";
    public static final String KEY_NAME = "Name";
    public static final String KEY_PLACE = "Place";
    public static final String KEY_DATETIME = "DateTime";
    public static final String KEY_ENABLED = "Enabled";
    public static final String[] COLUMNS = {"_id", KEY_NAME, KEY_PLACE, KEY_DATETIME, KEY_ENABLED};

    private static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
            KEY_NAME+" TEXT, "+
            KEY_PLACE + " TEXT, " +
            KEY_DATETIME + " TEXT, " +
            KEY_ENABLED + " TEXT);";

    public MyDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
