package com.dutymator;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Zsolt Takacs <takacs.zsolt@ustream.tv>
 */
public class Redirecter
{
    private static PendingIntent lastIntent;

    public static String lastRedirectedContact;

    public static void schedule(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        int seconds = Integer.parseInt(settings.getString(Preferences.SCHEDULING_INTERVAL, "300"));

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
        Intent redirectIntent = new Intent(context, RedirectService.class);
        redirectIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getService(context, 0, redirectIntent, 0);

        Calendar time = Calendar.getInstance();

        time.setTimeInMillis(System.currentTimeMillis());

        time.add(Calendar.SECOND, seconds);

        lastIntent = pendingIntent;
        alarmManager.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pendingIntent);

        SimpleDateFormat format = new SimpleDateFormat("yyyy. MM. dd. HH:mm:ss");

        String message = "Scheduled next check: " + format.format(time.getTime());
        Logger.log(context, message);
    }

    public static void stop(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);

        alarmManager.cancel(lastIntent);

        Logger.log(context, "Redirecting cancelled!");
    }
}
