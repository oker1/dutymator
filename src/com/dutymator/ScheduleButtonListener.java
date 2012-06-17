package com.dutymator;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Zsolt Takacs <takacs.zsolt@ustream.tv>
 */
public class ScheduleButtonListener implements Button.OnClickListener
{
    private Context homeActivity;

    public ScheduleButtonListener(HomeActivity home) {
        this.homeActivity = home;
    }

    @Override
    public void onClick(View view)
    {
        String number = "+3630";

        AlarmManager mgr = (AlarmManager) homeActivity.getSystemService(HomeActivity.ALARM_SERVICE);
        Intent redirectIntent = new Intent(homeActivity, RedirectService.class);
        redirectIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        redirectIntent.putExtra("number", number);
        PendingIntent pi = PendingIntent.getService(homeActivity, 0, redirectIntent, 0);

        Calendar time = Calendar.getInstance();

        time.setTimeInMillis(System.currentTimeMillis());

        time.add(Calendar.SECOND, 3);

        mgr.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pi);

        SimpleDateFormat format = new SimpleDateFormat("yyyy. MM. dd. HH:mm");

        Toast.makeText(homeActivity, "Redirect scheduled at " + format.format(time.getTime()), Toast.LENGTH_LONG).show();
        Logger.log(homeActivity, "Next redirect at " + format.format(time.getTime()) + " to number " + number);
    }
}
