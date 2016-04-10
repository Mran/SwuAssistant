package com.swuos.ALLFragment.setting;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.swuos.Service.ClassAlarmService;
import com.swuos.swuassistant.R;

/**
 * Created by 张孟尧 on 2016/4/7.
 */
/* 本fragment不受fragment管理*/
public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
    private CheckBoxPreference checkBoxPreference;
    private ListPreference listPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        checkBoxPreference = (CheckBoxPreference) findPreference("schedule_is_should be_remind");
        listPreference = (ListPreference) findPreference("headway_before_class");
        listPreference.setOnPreferenceChangeListener(this);

    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if ("schedule_is_should be_remind".equals(preference.getKey())) {
            if (checkBoxPreference.isChecked()) {
                Intent statrtIntent = new Intent(getActivity(), ClassAlarmService.class);
                getActivity().startService(statrtIntent);
                Log.d("setting", "开启服务");
            } else {
                Intent statrtIntent = new Intent(getActivity(), ClassAlarmService.class);
                getActivity().stopService(statrtIntent);
                Log.d("setting", "停止服务");
            }
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        if (preference == listPreference) {
            Intent statrtIntent = new Intent(getActivity(), ClassAlarmService.class);
            getActivity().stopService(statrtIntent);
            getActivity().startService(statrtIntent);
            Log.d("setting", "设定时间改变,重启服务");
        }
        return true;
    }

}
