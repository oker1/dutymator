package com.dutymator;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

/**
 * @author Zsolt Takacs <zsolt@takacs.cc>
 */
public class CalendarSpinnerListener implements AdapterView.OnItemSelectedListener {
    private List<String> ids;
    private SharedPreferences settings;

    CalendarSpinnerListener(List<String> ids, SharedPreferences settings) {
        this.ids = ids;
        this.settings = settings;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("calendar", Integer.parseInt(ids.get(pos)));
        editor.commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
