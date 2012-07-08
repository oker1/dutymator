package com.dutymator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author Zsolt Takacs <zsolt@takacs.cc>
 */
public class LoginActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        WebView myWebView = (WebView) findViewById(R.id.login_webview);
        myWebView.loadUrl(preferences.getString(Preferences.API_URL, "") + "/login");

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                CookieSyncManager.getInstance().sync();
                // Get the cookie from cookie jar.
                String cookie = CookieManager.getInstance().getCookie(url);
                if (cookie == null) {
                    return;
                }
                // Cookie is a string like NAME=VALUE [; NAME=VALUE]
                String[] pairs = cookie.split(";");
                for (int i = 0; i < pairs.length; ++i) {
                    String[] parts = pairs[i].split("=", 2);
                    // If token is found, return it to the calling activity.
                    if (parts.length == 2 &&
                            parts[0].equalsIgnoreCase("PHPSESSID")) {
                        Intent result = new Intent();
                        result.putExtra("token", parts[1]);
                        setResult(RESULT_OK, result);
                        finish();
                    }
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        CookieSyncManager.getInstance().stopSync();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CookieSyncManager.getInstance().startSync();
    }
}
