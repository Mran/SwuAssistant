package com.swuos.ALLFragment.swujw.schedule.util;

import android.widget.TextView;

/**
 * Created by 张孟尧 on 2016/5/19.
 */
public class ScheduleDetail {
    private TextView textView;
    private int id;
    private int color;
    private ScheduleItem scheduleItem;

    public int getId() {
        return id;
    }

    public ScheduleItem getScheduleItem() {
        return scheduleItem;
    }

    public TextView getTextView() {
        return textView;
    }

    public int getColor() {
        return color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setScheduleItem(ScheduleItem scheduleItem) {
        this.scheduleItem = scheduleItem;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
