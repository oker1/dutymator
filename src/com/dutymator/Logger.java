package com.dutymator;

import android.content.ContentValues;
import android.content.Context;
import android.util.Config;
import android.util.Log;
import com.dutymator.database.LogProvider;
import static com.dutymator.DutymatorApp.*;

/**
 * @author Zsolt Takacs <takacs.zsolt@ustream.tv>
 */
public class Logger
{
    public static void log(Context context, String message) {
        ContentValues values = new ContentValues();

        values.put("date", System.currentTimeMillis());
        values.put("message", message);

        context.getContentResolver().insert(LogProvider.CONTENT_URI, values);

        if (Config.LOGV) Log.v(TAG, message);
    }
}
