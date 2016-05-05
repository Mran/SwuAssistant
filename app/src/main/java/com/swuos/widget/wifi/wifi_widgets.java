package com.swuos.widget.wifi;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.swuos.ALLFragment.swujw.TotalInfo;
import com.swuos.swuassistant.R;
import com.swuos.util.wifi.WifiExit;
import com.swuos.util.wifi.WifiLogin;

/**
 * Implementation of App Widget functionality.
 */
public class wifi_widgets extends AppWidgetProvider {
    private Button loginButton;
    private Button logoutButton;
    private String username;
    private String password;
    public static final String WIDGET_LOGIN = "wifi_login";
    public static final String WIDGET_LOGOUT = "wifi_logout";
    private TotalInfo totalInfo;
    private Context context;
    private static RemoteViews views;
    private static AppWidgetManager mappWidgetManager;
    private static int[] mappWidgetIds;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
            int appWidgetId) {

        // Construct the RemoteViews object
        views = new RemoteViews(context.getPackageName(), R.layout.wifi_widgets_layout);
        Intent intentLogin = new Intent(WIDGET_LOGIN);
        intentLogin.setAction(WIDGET_LOGIN);
        Intent intentlogout = new Intent(WIDGET_LOGOUT);
        intentlogout.setAction(WIDGET_LOGOUT);
        PendingIntent pendingIntentLogin = PendingIntent.getBroadcast(context, 0, intentLogin, 0);
        PendingIntent pendingIntentLogout = PendingIntent.getBroadcast(context, 0, intentlogout, 0);
        views.setOnClickPendingIntent(R.id.wifi_widget_login, pendingIntentLogin);
        views.setOnClickPendingIntent(R.id.wifi_widget_logout, pendingIntentLogout);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        mappWidgetManager = appWidgetManager;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        mappWidgetIds = appWidgetIds;
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }


        // Instruct the widget manager to update the widget
        //        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
           /*打开保存用户信息的文件*/


    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        //wifi ssid状态获取
        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        final String wifiSsid = wifiInfo.toString();
        String action = intent.getAction();
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", context.MODE_PRIVATE);
        username = sharedPreferences.getString("userName", "");
        password = sharedPreferences.getString("password", "");
        this.context = context;
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.wifi_widgets_layout);
        if (action.equals(WIDGET_LOGIN)) {
            views.setProgressBar(R.id.wifi_progressbar, 100, 0, false);
            mappWidgetManager.updateAppWidget(mappWidgetIds, views);
            new Mytask().execute(wifiSsid, "login");
        } else if (action.equals(WIDGET_LOGOUT)) {
            Toast.makeText(context, "正在退出", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        }
    }

}

