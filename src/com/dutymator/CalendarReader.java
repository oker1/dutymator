package com.dutymator;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.format.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zsolt Takacs <zsolt@takacs.cc>
 */
public class CalendarReader {
    public Map<String, String> getCalendarIds(Context context) {
        ContentResolver contentResolver = context.getContentResolver();

        final Cursor cursor = contentResolver.query(Uri.parse("content://com.android.calendar/calendars"),
                (new String[] { "_id", "displayName", "selected" }), null, null, null);

        HashMap<String, String> calendars = new HashMap<String, String>();

        while (cursor.moveToNext()) {

            final String _id = cursor.getString(0);
            final String displayName = cursor.getString(1);
            final Boolean selected = !cursor.getString(2).equals("0");

            calendars.put(_id, displayName);
        }

        return calendars;
    }

    public Map<String, String> getEventsFromCalendar(Context context, String calendarId) {
        ContentResolver contentResolver = context.getContentResolver();

        Uri.Builder builder = Uri.parse("content://com.android.calendar/instances/when").buildUpon();
        long now = new Date().getTime();
        ContentUris.appendId(builder, now - DateUtils.WEEK_IN_MILLIS);
        ContentUris.appendId(builder, now + DateUtils.WEEK_IN_MILLIS);

        Cursor eventCursor = contentResolver.query(builder.build(),
                new String[] { "_id", "title", "begin", "end", "allDay"}, "Calendars._id=" + calendarId,
                null, "startDay ASC, startMinute ASC");

        HashMap<String, String> events = new HashMap<String, String>();
        
        while (eventCursor.moveToNext()) {
            final String _id = eventCursor.getString(0);
            final String title = eventCursor.getString(1);

            events.put(_id, title);
        }
        
        return events;
    }
}
