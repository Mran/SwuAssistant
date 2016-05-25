package com.swuos.ALLFragment.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.swuos.ALLFragment.swujw.TotalInfo;
import com.swuos.swuassistant.R;
import com.swuos.util.wifi.WifiExit;
import com.swuos.util.wifi.WifiLogin;

/**
 * Created by 张孟尧 on 2016/4/27.
 */
public class WifiFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private Button login_button;
    private Button logout_button;
    private String username;
    private String password;
    private TotalInfo totalInfo = new TotalInfo();
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView wifiStateTextView;
    private WifiStateBroad wifiStateBroad;
    private TextView wifiUsername;
    private IntentFilter intentFilter;
    private LocalRecevier localRecevier;
    private LocalBroadcastManager localBroadcastManager;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.wifis_contain_layout, container, false);

        initdata();
        initview();
        setReceiver();
        return view;
    }

    private void initview() {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.wifi_SwipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R
                .color.holo_red_light, android.R.color.holo_orange_light, android.R.color
                .holo_green_light);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setOnRefreshListener(this);
        login_button = (Button) view.findViewById(R.id.wifi_login_button);
        logout_button = (Button) view.findViewById(R.id.wifi_logout_button);
        wifiStateTextView = (TextView) view.findViewById(R.id.wifi_state);
        wifiUsername = (TextView) view.findViewById(R.id.wifi_username);
        logout_button.setOnClickListener(this);
        login_button.setOnClickListener(this);
        wifiStateTextView.setOnClickListener(this);
        wifiUsername.setText("当前用户:" + username);
    }

    private void initdata() {
        wifiStateBroad = new WifiStateBroad();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        getActivity().registerReceiver(wifiStateBroad, filter);
        username = totalInfo.getUserName();
        password = totalInfo.getPassword();
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();

        //wifi ssid状态获取
        WifiManager wifiManager = (WifiManager) getActivity().getSystemService(getActivity().WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String wifiSsid = wifiInfo.toString();
        //        int ip=wifiInfo.getIpAddress();
        switch (id) {
            case R.id.wifi_login_button:
                login(wifiSsid);
                swipeRefreshLayout.setRefreshing(true);
                break;
            case R.id.wifi_logout_button:
                logout(wifiSsid);
                swipeRefreshLayout.setRefreshing(true);
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

            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            //            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int wifiState = wifiManager.getWifiState();
            if (wifiState == WifiManager.WIFI_STATE_ENABLING) {
                wifiStateTextView.setText("正在打开WIFI");
            } else if (wifiState == WifiManager.WIFI_STATE_ENABLED) {
                wifiStateTextView.setText("WIFI未连接");

                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(getActivity().CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                        String wifissid = wifiInfo.getSSID();
                        wifiStateTextView.setText(wifissid.replace("\"", ""));
                    }
                } else if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                    if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                        String wifissid = wifiInfo.getSSID();
                        wifiStateTextView.setText("正在连接 " + wifissid);
                    }

                }
            } else if (wifiState == WifiManager.WIFI_STATE_DISABLED) {
                wifiStateTextView.setText("WIFI已关闭");
            }

        }
    }

    private void setReceiver() {
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.swuos.Logined");
        localRecevier = new LocalRecevier();
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.registerReceiver(localRecevier, intentFilter);
    }

    /*设置广播接收刷新消息*/
    class LocalRecevier extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            username = totalInfo.getUserName();
            password = totalInfo.getPassword();
            wifiUsername.setText("当前用户:" + username);

        }
    }

}
