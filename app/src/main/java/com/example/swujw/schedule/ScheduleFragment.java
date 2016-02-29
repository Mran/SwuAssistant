package com.example.swujw.schedule;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swuassistant.R;

/**
 * Created by 张孟尧 on 2016/2/29.
 */
public class ScheduleFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View scheduleLyout = inflater.inflate(R.layout.schedule_layout, container, false);
        return scheduleLyout;
    }
}
