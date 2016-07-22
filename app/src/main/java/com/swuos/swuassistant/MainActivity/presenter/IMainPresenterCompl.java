package com.swuos.swuassistant.MainActivity.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.swuos.ALLFragment.swujw.TotalInfo;
import com.swuos.Service.ClassAlarmService;
import com.swuos.Service.WifiNotificationService;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.MainActivity.view.IMainview;
import com.xiaomi.market.sdk.XiaomiUpdateAgent;

/**
 * Created by 张孟尧 on 2016/7/20.
 */
public class IMainPresenterCompl implements IMainPresenter {
    IMainview iMainview;
    Context context;
    SharedPreferences sharedPreferences;

    public IMainPresenterCompl(IMainview iMainview, Context context) {
        this.iMainview = iMainview;
        this.context = context;
    }

    @Override
    public void startServier() {
        SharedPreferences settingSharedPreferences = context.getSharedPreferences("com.swuos.swuassistant_preferences", Context.MODE_PRIVATE);
        Boolean scheduleIsRemind = settingSharedPreferences.getBoolean("schedule_is_should be_remind", false);
        if (scheduleIsRemind) {
            Intent statrtClassAlarmIntent = new Intent(context, ClassAlarmService.class);
            context.startService(statrtClassAlarmIntent);
        }
        Boolean wifiNotification = settingSharedPreferences.getBoolean("wifi_notification_show", true);
        if (wifiNotification) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
            int wifistate = wifiManager.getWifiState();
            if (wifistate == WifiManager.WIFI_STATE_ENABLED) {
                Intent statrtWifiIntent = new Intent(context, WifiNotificationService.class);
                context.startService(statrtWifiIntent);
            }
        }
    }

    @Override
    public void initData(TotalInfo totalInfo) {
         /*打开保存用户信息的文件*/
        sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        totalInfo.setUserName(sharedPreferences.getString("userName", ""));
        totalInfo.setName(sharedPreferences.getString("name", ""));
        totalInfo.setPassword(sharedPreferences.getString("password", ""));
        totalInfo.setSwuID(sharedPreferences.getString("swuID", ""));
        totalInfo.setScheduleDataJson(sharedPreferences.getString("scheduleDataJson", ""));
    }

    @Override
    public void cleanData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    public void startUpdata() {
        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, Constant.REQUEST_CODE_ASK_CALL_PHONE);
            return;
        } else {
            XiaomiUpdateAgent.update(context);
            XiaomiUpdateAgent.arrange();
        }
    }
}
