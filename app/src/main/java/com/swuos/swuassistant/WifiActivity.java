package com.swuos.swuassistant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.swuos.ALLFragment.swujw.TotalInfo;
import com.swuos.util.wifi.WifiExit;
import com.swuos.util.wifi.WifiLogin;

/**
 * Created by 张孟尧 on 2016/4/27.
 */
public class WifiActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private Button login_button;
    private Button logout_button;
    private String username;
    private String password;
    private TotalInfo totalInfo = new TotalInfo();
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView wifiStateTextView;
    private WifiStateBroad wifiStateBroad;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.SHOW_RESPONSE:
                    Snackbar.make(view, (String) msg.obj, Snackbar.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifis_layout);
        view = (View) findViewById(R.id.wifi_contain);
        /*设置toolbar*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle(R.string.wifi);
        toolbar.setTitleTextColor(Color.WHITE);
        Drawable d = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationIcon(d);

        initview();
        initdata();
    }

    private void initview() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.wifi_SwipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R
                .color.holo_red_light, android.R.color.holo_orange_light, android.R.color
                .holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        login_button = (Button) findViewById(R.id.wifi_login_button);
        logout_button = (Button) findViewById(R.id.wifi_logout_button);
        wifiStateTextView = (TextView) findViewById(R.id.wifi_state);
        logout_button.setOnClickListener(this);
        login_button.setOnClickListener(this);
        wifiStateTextView.setOnClickListener(this);
    }

    private void initdata() {
        wifiStateBroad = new WifiStateBroad();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        this.registerReceiver(wifiStateBroad, filter);
        username = totalInfo.getUserName();
        password = totalInfo.getPassword();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("settingActivity", "onOptionsItemSelected");
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        //wifi ssid状态获取
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String wifiSsid = wifiInfo.toString();
        switch (id) {
            case R.id.wifi_login_button:
                swipeRefreshLayout.setRefreshing(true);
                login(wifiSsid);
                break;
            case R.id.wifi_logout_button:
                swipeRefreshLayout.setRefreshing(true);
                logout(wifiSsid);
                break;
            case R.id.wifi_state:
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            default:
                break;
        }
    }

    private void login(String wifissid) {
        new Mytask().execute(wifissid, "login");
    }

    private void logout(String wifissid) {

        new Mytask().execute(wifissid, "logout");
    }


    @Override
    public void onRefresh() {

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
            swipeRefreshLayout.setRefreshing(false);

            Snackbar.make(view, s, Snackbar.LENGTH_SHORT).show();
        }
    }

    class WifiStateBroad extends BroadcastReceiver {
        @Override


        public void onReceive(Context context, Intent intent) {
            System.out.println(intent.getAction());

            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            //            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int wifiState = wifiManager.getWifiState();
            if (wifiState == WifiManager.WIFI_STATE_ENABLING) {
                wifiStateTextView.setText("正在打开WIFI");
            } else if (wifiState == WifiManager.WIFI_STATE_ENABLED) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();


                String wifissid = wifiInfo.getSSID();
                wifiStateTextView.setText(wifissid.replace("\"", ""));
            } else if (wifiState == WifiManager.WIFI_STATE_DISABLED) {
                wifiStateTextView.setText("WIFI已关闭");
            }

        }
    }

    public int getStrength(Context context) {

        return 0;
    }


}
