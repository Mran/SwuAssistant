package com.example.ALLFragment.setting;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.swuassistant.R;

/**
 * Created by 张孟尧 on 2016/4/7.
 */
public class SettingFragment extends PreferenceFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
    }
}
