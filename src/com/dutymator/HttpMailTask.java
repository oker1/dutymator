package com.dutymator;

import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

/**
 * @author Zsolt Takacs <zsolt@takacs.cc>
 */
public class HttpMailTask extends AsyncTask<String, Void, Boolean>
{
    @Override
    protected Boolean doInBackground(String... strings) {
        BasicHttpParams params = new BasicHttpParams();
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", PlainSocketFactory.getSocketFactory(), 443));
        ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
        DefaultHttpClient httpClient = new DefaultHttpClient(cm, params);

        try {
            URL url = new URL(strings[2]);

            HttpPut put = new HttpPut(url.toString());

            CookieStore cookieStore = new BasicCookieStore();
            BasicClientCookie cookie = new BasicClientCookie("PHPSESSID", strings[1]);

            cookie.setDomain(url.getHost());
            cookie.setPath("/");

            cookieStore.addCookie(cookie);
            httpClient.setCookieStore(cookieStore);

            JSONObject json = new JSONObject();
            json.put("message", strings[0]);

            put.setEntity(new StringEntity(json.toString(), "UTF8"));

            HttpResponse response = httpClient.execute(put);
        } catch (IOException e) {
        } catch (RuntimeException e) {
        } catch (JSONException e) {
        }

        return true;
    }
}
