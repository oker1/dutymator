package com.dutymator;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Zsolt Takacs <zsolt@takacs.cc>
 */
public class Settings extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        CalendarReader reader = new CalendarReader();

        Map<String, String> calendars = reader.getCalendarIds(getApplicationContext());

        Spinner spinner = (Spinner) findViewById(R.id.calendar_spinner);

        List<String> list = new ArrayList<String>();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        for (String calendar : calendars.keySet()) {
            list.add(calendars.get(calendar));
        }

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(
            new CalendarSpinnerListener(new ArrayList<String>(calendars.keySet()), getSharedPreferences("dutymator", 0))
        );
    }
}