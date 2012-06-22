package com.dutymator.database;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.dutymator.Event;
import com.dutymator.R;

import java.util.ArrayList;

/**
 * @author Zsolt Takacs <takacs.zsolt@ustream.tv>
 */
public class EventAdapter extends ArrayAdapter<Event>
{

    private ArrayList<Event> items;

    public EventAdapter(Context context, int textViewResourceId, ArrayList<Event> items) {
            super(context, textViewResourceId, items);
            this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.list_event, null);
            }
            Event event = items.get(position);
            if (event != null) {
                java.text.DateFormat dateFormat = DateFormat.getDateFormat(getContext());
                java.text.DateFormat timeFormat = DateFormat.getTimeFormat(getContext());
                String date = dateFormat.format(event.begin) + " " + timeFormat.format(event.begin) + " - " +
                    dateFormat.format(event.end) + " " + timeFormat.format(event.end);

                TextView tt = (TextView) v.findViewById(R.id.title);
                TextView bt = (TextView) v.findViewById(R.id.date);
                tt.setText(event.title);
                bt.setText(date);
            }
            return v;
    }
}
