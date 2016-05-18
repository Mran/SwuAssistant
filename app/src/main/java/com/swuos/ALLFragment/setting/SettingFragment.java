package com.swuos.ALLFragment.setting;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.swuos.Service.ClassAlarmService;
import com.swuos.Service.WifiNotificationService;
import com.swuos.swuassistant.R;
import com.swuos.util.SALog;

/**
 * Created by 张孟尧 on 2016/4/7.
 */
/* 本fragment不受fragment管理*/
public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
    private CheckBoxPreference scheduleCheckBoxPreference;
    private ListPreference listPreference;
    private CheckBoxPreference wifiNotificationCheckBoxPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        scheduleCheckBoxPreference = (CheckBoxPreference) findPreference("schedule_is_should be_remind");
        listPreference = (ListPreference) findPreference("headway_before_class");
        wifiNotificationCheckBoxPreference = (CheckBoxPreference) findPreference("wifi_notification_show");
        listPreference.setOnPreferenceChangeListener(this);

    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if ("schedule_is_should be_remind".equals(preference.getKey())) {
            if (scheduleCheckBoxPreference.isChecked()) {
                Intent statrtIntent = new Intent(getActivity(), ClassAlarmService.class);
                getActivity().startService(statrtIntent);
                SALog.d("setting", "开启课程提示服务");
            } else {
                Intent statrtIntent = new Intent(getActivity(), ClassAlarmService.class);
                getActivity().stopService(statrtIntent);
                SALog.d("setting", "停止课程提示服务");
            }
        } else if ("wifi_notification_show".equals(preference.getKey())) {
            if (wifiNotificationCheckBoxPreference.isChecked()) {
                WifiManager wifiManager = (WifiManager) getActivity().getSystemService(getActivity().WIFI_SERVICE);
                int wifistate = wifiManager.getWifiState();
                if (wifistate == WifiManager.WIFI_STATE_ENABLED) {
                    Intent statrtWifiIntent = new Intent(getActivity(), WifiNotificationService.class);
                    getActivity().startService(statrtWifiIntent);
                    SALog.d("setting", "开始前台服务");

                }
            } else {
                Intent stopIntent = new Intent(getActivity(), WifiNotificationService.class);
                getActivity().stopService(stopIntent);
                SALog.d("setting", "停止前台服务");
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
            SALog.d("setting", "设定时间改变,重启服务");
        }
        return true;
    }

}
