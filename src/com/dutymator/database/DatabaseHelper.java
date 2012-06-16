package com.dutymator.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Zsolt Takacs <takacs.zsolt@ustream.tv>
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String NAME = "dutymator";
    public static final int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("create table log (_id integer primary key autoincrement, date integer, message text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int from, int to)
    {
    }
}
