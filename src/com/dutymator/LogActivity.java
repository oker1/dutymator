package com.dutymator;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;
import com.dutymator.database.LogAdapter;
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

        SimpleCursorAdapter adapter = new LogAdapter(this, R.layout.list_log, cursor, from, to);

        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                getContentResolver().delete(LogProvider.CONTENT_URI, null, null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}