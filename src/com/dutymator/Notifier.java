package com.dutymator;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

/**
 * @author Zsolt Takacs <zsolt@takacs.cc>
 */
public class Notifier {
    public static void notifyRedirect(Context context, String message)
    {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Service.NOTIFICATION_SERVICE);
        int icon = R.drawable.ic_notification_email;
        Notification notification = new Notification(icon, message, System.currentTimeMillis());

        Intent notificationIntent = new Intent(context, HomeActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(
            context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT
        );

        notification.setLatestEventInfo(context, context.getString(R.string.app_name), message, contentIntent);
        notification.flags = Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(1, notification);
    }
}
