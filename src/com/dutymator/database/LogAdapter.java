package com.dutymator.database;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.dutymator.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Zsolt Takacs <zsolt@takacs.cc>
 */
public class LogAdapter extends SimpleCursorAdapter {
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

        TextView date = (TextView) view.findViewById(R.id.date);

        SimpleDateFormat format = new SimpleDateFormat("yyyy. MM. dd. HH:mm:ss");

        date.setText(format.format(new Date(cursor.getLong(cursor.getColumnIndex("date")))));
    }

    public LogAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
    }
}
