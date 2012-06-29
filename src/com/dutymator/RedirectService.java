package com.dutymator;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.IBinder;
import android.preference.PreferenceManager;

import java.util.Date;

/**
 * @author Zsolt Takacs <takacs.zsolt@ustream.tv>
 */
public class RedirectService extends Service
{

    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        int calendarId = Integer.parseInt(settings.getString(Preferences.CALENDAR_ID, "-1"));

        CalendarReader reader = new CalendarReader();
        ContactsReader contactsReader = new ContactsReader();

        Event activeEvent = reader.getActiveEventFromCalendar(
            this, calendarId, new Date(settings.getLong(Preferences.ALL_DAY_FROM, 0)), new Date(settings.getLong(Preferences.ALL_DAY_TO, 0))
        );

        String message;
        if (activeEvent != null) {
            String number = contactsReader.getNumber(this, activeEvent.title);

            if (!activeEvent.title.equals(Redirecter.lastRedirectedContact)) {
                if (number != null) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.fromParts("tel", "**21*\\" + number + "\\#", ""));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (!intent.getBooleanExtra(Preferences.DRY_RUN, false)) {
                        startActivity(callIntent);
                    }

                    Redirecter.lastRedirectedContact = activeEvent.title;
                    message = "Redirected to " + activeEvent.title + "(" + number + ")";
                } else {
                    message = "No number for name " + activeEvent.title;
                }
            } else {
                message = "Already redirected to " + activeEvent.title + "(" + number + ")";
            }
        } else {
            message = "No active event found!";
        }

        //Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        Logger.log(this, message);

        Redirecter.schedule(this);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
