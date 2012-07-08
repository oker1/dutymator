package com.dutymator;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;

/**
 * @author Zsolt Takacs <zsolt@takacs.cc>
 */
public class SettingsActivity extends PreferenceActivity
{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        CalendarReader reader = new CalendarReader();

        ListPreference calendarList = (ListPreference) findPreference("calendar_id");
        calendarList.setEntries(reader.getCalendarEntries(this));
        calendarList.setEntryValues(reader.getCalendarValues(this));
    }
}