package com.swuos.swuassistant;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.swuos.ALLFragment.swujw.TotalInfo;
import com.swuos.ALLFragment.wifi.util.SwuDormWifiLogin;
import com.swuos.ALLFragment.wifi.util.SwuWifiLogin;
import com.swuos.ALLFragment.wifi.util.WifiExit;

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
        logout_button.setOnClickListener(this);
        login_button.setOnClickListener(this);
    }

    private void initdata() {
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
            default:
                break;
        }
    }

    private void login(String wifissid) {
        if (wifissid.contains("swu-wifi-dorm")) {
            new Mytask().execute(1);
        } else if (wifissid.contains("swu-wifi")) {

            new Mytask().execute(2);
        } else {

            new Mytask().execute(4);

        }
    }

    private void logout(String wifissid) {

        if (wifissid.contains("swu-wifi")) {
            new Mytask().execute(3);
        } else {
            new Mytask().execute(4);

        }
    }


    @Override
    public void onRefresh() {

    }

    class Mytask extends AsyncTask<Integer, Integer, String> {


        @Override
        protected String doInBackground(Integer... params) {
            int position = params[0];
            if (position == 1) {
                return SwuDormWifiLogin.Login(username, password);
            } else if (position == 2) {
                return SwuWifiLogin.login(username, password);
            } else if (position == 3) {return WifiExit.Logout(username, password);} else
                return Constant.noWifi;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            swipeRefreshLayout.setRefreshing(false);

            Snackbar.make(view, s, Snackbar.LENGTH_SHORT).show();
        }
    }

}
