package com.dutymator;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.dutymator.service.RedirectService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Zsolt Takacs <takacs.zsolt@ustream.tv>
 */
public class Redirecter
{
    private static PendingIntent lastIntent;

    public static String lastRedirectedContact;

    public static void scheduleBySettings(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        int seconds = Integer.parseInt(settings.getString(Preferences.SCHEDULING_INTERVAL, "300"));

        scheduleInSeconds(context, seconds);
    }

    public static void scheduleInSeconds(Context context, int seconds) {
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
        Logger.log(context, Log.VERBOSE, message);
    }

    public static void stop(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);

        alarmManager.cancel(lastIntent);

        Redirecter.lastRedirectedContact = null;

        Logger.log(context, Log.VERBOSE, "Redirecting cancelled!");
    }

    public static void startRedirecting(Context context) {
        scheduleInSeconds(context, 5);
        Logger.log(context, Log.INFO, "Redirecting scheduled.");
        Notifier.notifyRedirectStarted(context);
    }

    public static void stopRedirecting(Context context) {
        stop(context);
        Logger.log(context, Log.INFO, "Redirecting stopped.");
        Notifier.notifyRedirectStopped(context);
    }
}
