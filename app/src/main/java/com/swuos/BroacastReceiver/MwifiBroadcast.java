package com.swuos.BroacastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.swuos.Service.WifiNotificationService;
import com.swuos.swuassistant.Constant;
import com.swuos.util.SALog;
import com.swuos.util.wifi.WifiExit;
import com.swuos.util.wifi.WifiLogin;

import io.github.zhitaocai.toastcompat.ToastCompat;

/**
 * Created by 张孟尧 on 2016/5/16.
 */
public class MwifiBroadcast extends BroadcastReceiver {
    private Context mcontext;
    private String username;
    private String password;

    @Override
    public void onReceive(Context context, Intent intent) {
        mcontext = context;
        //wifi ssid状态获取
        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        final String wifiSsid = wifiInfo.toString();

        String action = intent.getAction();
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", context.MODE_PRIVATE);
        username = sharedPreferences.getString("userName", "");
        password = sharedPreferences.getString("password", "");

        if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            int wifistate = wifiManager.getWifiState();

            if (wifistate == WifiManager.WIFI_STATE_DISABLED || wifistate == WifiManager.WIFI_STATE_DISABLING) {
                Intent stopIntent = new Intent(context, WifiNotificationService.class);
                context.stopService(stopIntent);
                SALog.d("setting", "关闭前台服务");
                Log.d("wifi", "WIFI关闭");
            }

            if (wifistate == WifiManager.WIFI_STATE_ENABLED) {
                SharedPreferences settingSharedPreferences = context.getSharedPreferences("com.swuos.swuassistant_preferences", context.MODE_PRIVATE);
                Boolean wifiNotification = settingSharedPreferences.getBoolean("wifi_notification_show", true);
                if (wifiNotification) {
                    Intent statrtIntent = new Intent(context, WifiNotificationService.class);
                    context.startService(statrtIntent);
                    SALog.d("setting", "开启前台服务");
                    Log.d("wifi", "WIFI开启");
                }
            }
        }

        if (action.equals(Constant.NOTIFICATION_LOGIN)) {
            ToastCompat.makeText(context, "正在登录", Toast.LENGTH_SHORT).show();
            new Mytask().execute(wifiSsid, "login");
        } else if (action.equals(Constant.NOTIFICATION_LOGOUT)) {
            ToastCompat.makeText(context, "正在登出", Toast.LENGTH_SHORT).show();

            new Mytask().execute(wifiSsid, "logout");
        }

    }

    class Mytask extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {
            String wifissid = params[0];
            String todo = params[1];
            if (todo.equals("logout")) {
                return WifiExit.logout(username, password, wifissid);
            } else
                return WifiLogin.login(username, password, wifissid);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ToastCompat.makeText(mcontext, s, Toast.LENGTH_SHORT).show();

        }
    }
}