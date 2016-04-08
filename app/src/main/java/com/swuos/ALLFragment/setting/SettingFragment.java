package com.swuos.ALLFragment.setting;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.swuos.swuassistant.R;

/**
 * Created by 张孟尧 on 2016/4/7.
 */
/* 本fragment不受fragment管理*/
public class SettingFragment extends PreferenceFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
    }
}
