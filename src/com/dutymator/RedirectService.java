package com.dutymator;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

/**
 * @author Zsolt Takacs <takacs.zsolt@ustream.tv>
 */
public class RedirectService extends Service
{
    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.fromParts("tel", "**21*\\" + intent.getExtras().getString("number") + "\\#", ""));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(callIntent);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
