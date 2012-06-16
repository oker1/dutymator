package com.dutymator;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import com.dutymator.database.LogProvider;

/**
 * @author Zsolt Takacs <takacs.zsolt@ustream.tv>
 */
public class LogActivity extends ListActivity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log);

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(LogProvider.CONTENT_URI, null, null, null, null);

        startManagingCursor(cursor);

        String[] from = { "date", "message" };
        int[] to = { R.id.date, R.id.message };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_log, cursor, from, to);

        setListAdapter(adapter);
    }
}