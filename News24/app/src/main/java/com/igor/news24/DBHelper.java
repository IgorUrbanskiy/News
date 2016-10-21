package com.igor.news24;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Igor on 21.09.2016.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "newsDB";
    public static final String TABLE_NEWS = "news";

    public static final String KEY_TITLE = "title";
    public static final String KEY_DATE = "date";
    public static final String KEY_LINK = "link";
    public static final String KEY_DESCRIPTION = "description";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("MyLogs", "OncreateDatabase");
        db.execSQL("create table " + TABLE_NEWS + " (" + KEY_TITLE + " text," + KEY_DATE
                + " text, " + KEY_LINK + " text," + KEY_DESCRIPTION + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("MyLogs", "onUpgradeDatabase");
        db.execSQL("drop table if exists " + TABLE_NEWS);
        onCreate(db);
    }
}
