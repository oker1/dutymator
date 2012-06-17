package com.dutymator;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Zsolt Takacs <takacs.zsolt@ustream.tv>
 */
public class Redirecter
{
    public static void schedule(Context context) {
        AlarmManager mgr = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
        Intent redirectIntent = new Intent(context, RedirectService.class);
        redirectIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pi = PendingIntent.getService(context, 0, redirectIntent, 0);

        Calendar time = Calendar.getInstance();

        time.setTimeInMillis(System.currentTimeMillis());

        time.add(Calendar.SECOND, 60);

        mgr.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pi);

        SimpleDateFormat format = new SimpleDateFormat("yyyy. MM. dd. HH:mm");

        String message = "Scheduled next check: " + format.format(time.getTime());
        Logger.log(context, message);
    }
}
