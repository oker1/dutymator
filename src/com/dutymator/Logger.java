package com.dutymator;

import android.content.ContentValues;
import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Config;
import android.util.Log;
import com.dutymator.database.LogProvider;

import static com.dutymator.DutymatorApp.TAG;

/**
 * @author Zsolt Takacs <takacs.zsolt@ustream.tv>
 */
public class Logger
{
    public static void log(Context context, int level, String message) {
        final boolean verbose = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(Preferences.VERBOSE_LOG, false);
        if ((level == Log.VERBOSE && verbose) || level == Log.INFO) {
            ContentValues values = new ContentValues();

            values.put("date", System.currentTimeMillis());
            values.put("message", message);

            context.getContentResolver().insert(LogProvider.CONTENT_URI, values);

            if (Config.LOGV) Log.v(TAG, message);
        }
    }
}
