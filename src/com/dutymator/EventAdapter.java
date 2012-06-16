package com.dutymator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
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
                String date;
                if (event.allDay) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy. MM. dd.");
                    date = format.format(event.begin);
                } else {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy. MM. dd. HH:mm");
                    date = format.format(event.begin) + " - " + format.format(event.end);
                }

                TextView tt = (TextView) v.findViewById(R.id.title);
                TextView bt = (TextView) v.findViewById(R.id.date);
                tt.setText(event.title);
                bt.setText(date);
            }
            return v;
    }
}
