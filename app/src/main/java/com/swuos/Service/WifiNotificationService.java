package com.swuos.Service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.R;
import com.swuos.util.wifi.WifiExit;
import com.swuos.util.wifi.WifiLogin;

import io.github.zhitaocai.toastcompat.ToastCompat;

/**
 * Created by 张孟尧 on 2016/5/14.
 */
public class WifiNotificationService extends Service {
    private static String username;
    private static String password;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showNotification();
        return super.onStartCommand(intent, flags, startId);
    }

    private void showNotification() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.wifi_notification_layout);
        Intent intentLogin = new Intent(Constant.NOTIFICATION_LOGIN);
        intentLogin.setAction(Constant.NOTIFICATION_LOGIN);
        Intent intentlogout = new Intent(Constant.NOTIFICATION_LOGOUT);
        intentlogout.setAction(Constant.NOTIFICATION_LOGOUT);
        PendingIntent pendingIntentLogin = PendingIntent.getBroadcast(this, 0, intentLogin, 0);
        PendingIntent pendingIntentLogout = PendingIntent.getBroadcast(this, 0, intentlogout, 0);
        remoteViews.setOnClickPendingIntent(R.id.wifi_notification_login, pendingIntentLogin);
        remoteViews.setOnClickPendingIntent(R.id.wifi_notification_logout, pendingIntentLogout);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        Notification notification = mBuilder.setContent(remoteViews).setTicker("校园wifi").setOngoing(true).setSmallIcon(R.drawable.icon_notification).build();
        startForeground(1, notification);
    }

    public static class MwifiBroadcast extends BroadcastReceiver {
        private Context mcontext;

        @Override
        public void onReceive(Context context, Intent intent) {
            //wifi ssid状态获取
            WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            final String wifiSsid = wifiInfo.toString();
            String action = intent.getAction();
            SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", context.MODE_PRIVATE);
            username = sharedPreferences.getString("userName", "");
            password = sharedPreferences.getString("password", "");
            mcontext = context;
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
}

