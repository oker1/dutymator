package com.dutymator;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

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
        Numbers numbers = new Numbers();

        Event activeEvent = reader.getActiveEventFromCalendar(this, calendarId);

        String message;
        if (activeEvent != null) {
            String number = numbers.getNumberForName(activeEvent.title);

            if (number != null) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.fromParts("tel", "**21*\\" + number + "\\#", ""));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);

                message = "Redirected to " + number;
            } else {
                message = "No number for name " + activeEvent.title;
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
