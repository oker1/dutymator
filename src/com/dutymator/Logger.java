package com.dutymator;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Config;
import android.util.Log;
import com.dutymator.database.LogProvider;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;

import java.io.IOException;

import static com.dutymator.DutymatorApp.TAG;

/**
 * @author Zsolt Takacs <takacs.zsolt@ustream.tv>
 */
public class Logger
{
    public static void log(Context context, int level, String message) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final boolean verbose = preferences.getBoolean(Preferences.VERBOSE_LOG, false);
        if ((level == Log.VERBOSE && verbose) || level == Log.INFO) {
            ContentValues values = new ContentValues();

            values.put("date", System.currentTimeMillis());
            values.put("message", message);

            context.getContentResolver().insert(LogProvider.CONTENT_URI, values);

            if (Config.LOGV) Log.v(TAG, message);

            final String session = preferences.getString(Preferences.SESSION_ID, "");
            final String apiUrl = preferences.getString(Preferences.API_URL, "") + "/json/log";
            final String time = Long.toString(System.currentTimeMillis() / 1000);

            new HttpLogTask().execute(time, message, session, apiUrl);
        }
    }
}
