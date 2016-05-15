package com.swuos.widget.wifi;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.view.View;
import android.widget.RemoteViews;

import com.swuos.ALLFragment.swujw.TotalInfo;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.R;
import com.swuos.util.wifi.WifiExit;
import com.swuos.util.wifi.WifiLogin;

/**
 * Implementation of App Widget functionality.
 */
public class wifi_widgets extends AppWidgetProvider {

    private String username;
    private String password;

    private TotalInfo totalInfo;
    private Context context;
    private static RemoteViews views;
    private static AppWidgetManager mappWidgetManager;
    private static int[] mappWidgetIds;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
            int appWidgetId) {

        // Construct the RemoteViews object
        views = new RemoteViews(context.getPackageName(), R.layout.wifi_widgets_layout);
        Intent intentLogin = new Intent(Constant.WIDGET_LOGIN);
        Intent intentlogout = new Intent(Constant.WIDGET_LOGOUT);
        Intent intentloginfo = new Intent(Constant.WIDGET_LOGINFO);
        intentLogin.setAction(Constant.WIDGET_LOGIN);
        intentlogout.setAction(Constant.WIDGET_LOGOUT);
        intentloginfo.setAction(Constant.WIDGET_LOGINFO);
        PendingIntent pendingIntentLogin = PendingIntent.getBroadcast(context, 0, intentLogin, 0);
        PendingIntent pendingIntentLogout = PendingIntent.getBroadcast(context, 0, intentlogout, 0);
        PendingIntent pendingIntentLoginfo = PendingIntent.getBroadcast(context, 0, intentloginfo, 0);
        views.setOnClickPendingIntent(R.id.wifi_widget_login, pendingIntentLogin);
        views.setOnClickPendingIntent(R.id.wifi_widget_logout, pendingIntentLogout);
        views.setOnClickPendingIntent(R.id.wifi_log_info, pendingIntentLoginfo);
        views.setViewVisibility(R.id.frameLayout1, View.INVISIBLE);
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

        if (action.equals(Constant.WIDGET_LOGIN)) {
            views.setTextViewText(R.id.wifi_log_info, "正在登录");
            views.setViewVisibility(R.id.frameLayout1, View.VISIBLE);
            views.setViewVisibility(R.id.frameLayout2, View.INVISIBLE);

            new Mytask().execute(wifiSsid, "login");
        } else if (action.equals(Constant.WIDGET_LOGOUT)) {
            views.setTextViewText(R.id.wifi_log_info, "正在退出");
            views.setViewVisibility(R.id.frameLayout1, View.VISIBLE);
            views.setViewVisibility(R.id.frameLayout2, View.INVISIBLE);
            new Mytask().execute(wifiSsid, "logout");
        } else if (action.equals(Constant.WIDGET_LOGINFO)) {

            views.setViewVisibility(R.id.frameLayout1, View.INVISIBLE);
            views.setViewVisibility(R.id.frameLayout2, View.VISIBLE);
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, wifi_widgets.class);

        //通知AppWidgetProvider更新
        appWidgetManager.updateAppWidget(componentName, views);
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
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.wifi_widgets_layout);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context, wifi_widgets.class);
            views.setTextViewText(R.id.wifi_log_info, s);
            //通知AppWidgetProvider更新
            appWidgetManager.updateAppWidget(componentName, views);
        }
    }

}

