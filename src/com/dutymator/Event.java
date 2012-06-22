package com.dutymator;

import java.util.Date;

/**
 * @author Zsolt Takacs <takacs.zsolt@ustream.tv>
 */
public class Event implements Comparable<Event>
{
    public String _id;

    public String title;

    public Date begin;

    public Date end;

    public boolean allDay;

    public long beginTimestamp;

    public long endTimestamp;

    @Override
    public int compareTo(Event event)
    {
        if (this.beginTimestamp == event.beginTimestamp) {
            return 0;
        } else if (this.beginTimestamp < event.beginTimestamp) {
            return -1;
        } else {
            return 1;
        }
    }
}
