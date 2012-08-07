package com.dutymator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author Zsolt Takacs <zsolt@takacs.cc>
 */
public class StartAtBootServiceReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Logger.log(context, Log.INFO, "Booted up!");
            Redirecter.startRedirecting(context);
        }
    }
}
