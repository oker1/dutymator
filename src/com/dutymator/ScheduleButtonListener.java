package com.dutymator;

import android.content.Context;
import android.view.View;
import android.widget.Button;

/**
 * @author Zsolt Takacs <takacs.zsolt@ustream.tv>
 */
public class ScheduleButtonListener implements Button.OnClickListener
{
    private Context context;

    public ScheduleButtonListener(Context context)
    {
        this.context = context;
    }

    @Override
    public void onClick(View view)
    {
        Redirecter.schedule(context);
    }
}
