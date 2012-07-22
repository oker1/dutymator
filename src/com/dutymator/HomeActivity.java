package com.dutymator;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import com.dutymator.database.EventAdapter;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Zsolt Takacs <zsolt@takacs.cc>
 */
public class HomeActivity extends ListActivity
{
    private EventAdapter eventAdapter;
    private CalendarReader calendarReader;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        calendarReader = new CalendarReader();

        int calendarId = getCalendarIdFromPreferences();

        eventAdapter = new EventAdapter(this, R.layout.list_event, new ArrayList<Event>());
        fillEventAdapterFromCalendar(calendarId);
        
        setListAdapter(eventAdapter);

        setupButtons();

        CookieSyncManager.createInstance(getApplicationContext());
    }

    private void setupButtons() {
        Button schedule = (Button) findViewById(R.id.schedule);

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Redirecter.scheduleBySettings(HomeActivity.this);
                Logger.log(HomeActivity.this, Log.INFO, "Redirecting scheduled.");
            }
        });

        Button stop = (Button) findViewById(R.id.stop);

        stop.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Redirecter.stop(HomeActivity.this);
                Logger.log(HomeActivity.this, Log.INFO, "Redirecting stopped.");
            }
        });
    }

    private int getCalendarIdFromPreferences()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            return Integer.parseInt(settings.getString(Preferences.CALENDAR_ID, "-1"));
        } catch (NumberFormatException e) {
            return -1;
        }

    }

    @Override
    public void onResume()
    {
        super.onResume();

        fillEventAdapterFromCalendar(getCalendarIdFromPreferences());
    }

    private void fillEventAdapterFromCalendar(int calendar)
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        eventAdapter.clear();

        try {
            ArrayList<Event> events = calendarReader.getEventsFromCalendar(
                    getApplicationContext(), calendar, new Date(settings.getLong(Preferences.ALL_DAY_FROM, 0)),
                    new Date(settings.getLong(Preferences.ALL_DAY_TO, 0))
            );

            for (Event event : events) {
                eventAdapter.add(event);
            }
        } catch (IntermittentException e) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent;
        switch (item.getItemId()) {
            case R.id.menu_login:
                myIntent = new Intent(HomeActivity.this, LoginActivity.class);
                HomeActivity.this.startActivityForResult(myIntent, 0);
                return true;
            case R.id.menu_settings:
                myIntent = new Intent(HomeActivity.this, SettingsActivity.class);
                HomeActivity.this.startActivity(myIntent);
                return true;
            case R.id.menu_log:
                myIntent = new Intent(HomeActivity.this, LogActivity.class);
                HomeActivity.this.startActivity(myIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode != RESULT_OK || data == null) {
                    return;
                }
                // Get the token.
                String token = data.getStringExtra("token");
                if (token != null) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
                    editor.putString(Preferences.SESSION_ID, token);
                    editor.commit();
                }
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
