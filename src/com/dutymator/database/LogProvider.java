package com.dutymator.database;

import android.content.*;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * @author Zsolt Takacs <takacs.zsolt@ustream.tv>
 */
public class LogProvider extends ContentProvider
{
    private static final String AUTHORITY = "com.dutymator.database.LogProvider";
    public static final int LOGS = 100;
    public static final int LOG_ID = 110;

    private static final String BASE_PATH = "log";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_PATH);

    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/log";
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/log";

    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, LOGS);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", LOG_ID);
    }

    private DatabaseHelper database;

    @Override
    public boolean onCreate() {
        database = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables("log");

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case LOG_ID:
                queryBuilder.appendWhere("_id" + "="
                        + uri.getLastPathSegment());
                break;
            case LOGS:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        Cursor cursor = queryBuilder.query(database.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri)
    {
        switch (sURIMatcher.match(uri)) {
            case LOG_ID:
                return CONTENT_ITEM_TYPE;
            case LOGS:
                return CONTENT_TYPE;
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues)
    {
        long insertId = database.getWritableDatabase().insertOrThrow("log", null, contentValues);

        if (insertId > 0) {
            Uri rowUri = ContentUris.withAppendedId(uri, insertId);
            getContext().getContentResolver().notifyChange(rowUri, null);
            return rowUri;
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        
        int rowsAffected;
        switch (sURIMatcher.match(uri)) {
            case LOG_ID:
                String[] args = { uri.getLastPathSegment() };
                rowsAffected = sqlDB.delete("log", "_id = ?", args);
                break;
            case LOGS:
                rowsAffected = sqlDB.delete("log", "", null);
                break;
            default:
                throw new IllegalArgumentException("Unknown or Invalid URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsAffected;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings)
    {
        return 0;
    }
}
