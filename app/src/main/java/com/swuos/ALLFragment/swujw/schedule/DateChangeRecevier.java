package com.swuos.ALLFragment.swujw.schedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by 张孟尧 on 2016/3/30.
 */
public class DateChangeRecevier extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Toast.makeText(context, "收到刷新请求", Toast.LENGTH_SHORT).show();
    }
}
