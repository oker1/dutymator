package com.dutymator;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;

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

        Logger.log(this, "Calendar id: " + calendarId);

        eventAdapter = new EventAdapter(this, R.layout.list_event, new ArrayList<Event>());
        fillEventAdapterFromCalendar(calendarId);
        
        setListAdapter(eventAdapter);

        Button schedule = (Button) findViewById(R.id.schedule);

        schedule.setOnClickListener(new ScheduleButtonListener(this));
    }

    private int getCalendarIdFromPreferences()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        return Integer.parseInt(settings.getString(Preferences.CALENDAR_ID, "-1"));
    }

    @Override
    public void onResume()
    {
        super.onResume();

        fillEventAdapterFromCalendar(getCalendarIdFromPreferences());
    }

    private void fillEventAdapterFromCalendar(int calendar)
    {
        eventAdapter.clear();

        ArrayList<Event> events = calendarReader.getEventsFromCalendar(getApplicationContext(), calendar);

        for (Event event : events) {
            eventAdapter.add(event);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent;
        switch (item.getItemId()) {
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
}
