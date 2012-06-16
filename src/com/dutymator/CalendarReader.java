package com.dutymator;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.format.DateUtils;

import java.util.ArrayList;
import java.util.Collections;
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
                (new String[] { "_id", "displayName" }), null, null, null);

        HashMap<String, String> calendars = new HashMap<String, String>();

        if (cursor != null) {
            while (cursor.moveToNext()) {

                final String _id = cursor.getString(0);
                final String displayName = cursor.getString(1);

                calendars.put(_id, displayName);
            }
        }

        return calendars;
    }

    public ArrayList<Event> getEventsFromCalendar(Context context, int calendarId) {
        ContentResolver contentResolver = context.getContentResolver();

        Uri.Builder builder = Uri.parse("content://com.android.calendar/instances/when").buildUpon();
        long now = new Date().getTime();
        ContentUris.appendId(builder, now);
        ContentUris.appendId(builder, now + DateUtils.WEEK_IN_MILLIS);

        Cursor eventCursor = contentResolver.query(builder.build(),
                new String[] { "_id", "title", "begin", "end", "allDay", "eventTimezone"},
                "Calendars._id = " + calendarId, null, "begin ASC");

        ArrayList<Event> events = new ArrayList<Event>();

        if (eventCursor != null) {
            while (eventCursor.moveToNext()) {
                Event event = new Event();
                event._id = eventCursor.getString(0);
                event.title = eventCursor.getString(1);
                event.begin = new Date(eventCursor.getLong(2));
                event.end = new Date(eventCursor.getLong(3));
                event.allDay = eventCursor.getInt(4) != 0;
                event.beginTimestamp = eventCursor.getLong(2);

                events.add(event);
            }

            Collections.sort(events);
        }
        
        return events;
    }

    public String[] getCalendarEntries(Context context) {
        Map<String, String> calendars = getCalendarIds(context);

        String[] entries = new String[calendars.size()];

        calendars.values().toArray(entries);

        return entries;
    }

    public String[] getCalendarValues(Context context) {
        Map<String, String> calendars = getCalendarIds(context);

        String[] entries = new String[calendars.size()];

        calendars.keySet().toArray(entries);

        return entries;
    }
}
