package com.swuos.ALLFragment.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
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

import com.appyvet.rangebar.RangeBar;
import com.swuos.ALLFragment.wifi.presenter.IWifiPresenetrCompl;
import com.swuos.ALLFragment.wifi.presenter.IWifiPresenter;
import com.swuos.ALLFragment.wifi.view.IWifiFragmentView;
import com.swuos.swuassistant.R;

/**
 * Created by 张孟尧 on 2016/4/27.
 */
public class WifiFragment extends Fragment implements IWifiFragmentView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, RangeBar.OnRangeBarChangeListener {
    private Button login_button;
    private Button logout_button;
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView wifiStateTextView;
    private TextView wifiUsername;
    private RangeBar rangeBar;

    private LocalRecevier localRecevier;
    private LocalBroadcastManager localBroadcastManager;
    private IWifiPresenter iWifiPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.wifis_contain_layout, container, false);
        iWifiPresenter = new IWifiPresenetrCompl(this, getContext());
        iWifiPresenter.initdata();
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
        rangeBar = (RangeBar) view.findViewById(R.id.rangebar);
        rangeBar.setOnRangeBarChangeListener(this);
        logout_button.setOnClickListener(this);
        login_button.setOnClickListener(this);
        wifiStateTextView.setOnClickListener(this);
        wifiUsername.setText("当前用户:" + iWifiPresenter.getUsername());

        rangeBar.setTickEnd(10);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        //wifi ssid状态获取
        WifiManager wifiManager = (WifiManager) getActivity().getSystemService(getActivity().WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String wifiSsid = wifiInfo.toString();
        switch (id) {
            case R.id.wifi_login_button:
                iWifiPresenter.login(iWifiPresenter.getUsername(), iWifiPresenter.getPassword(), wifiSsid);
                swipeRefreshLayout.setRefreshing(true);
                break;
            case R.id.wifi_logout_button:
                iWifiPresenter.logout(iWifiPresenter.getUsername(), iWifiPresenter.getPassword(), wifiSsid);
                swipeRefreshLayout.setRefreshing(true);
                break;
            case R.id.wifi_state:
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            default:
                break;
        }
    }


    @Override
    public void showResult(String result) {
        swipeRefreshLayout.setRefreshing(false);
        Snackbar.make(view, result, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void changeWifiState(String state) {
        wifiStateTextView.setText("WIFI已关闭");
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {

    }

    private void setReceiver() {
        //注册本地广播接收器
        IntentFilter intentFilter;
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

            wifiUsername.setText("当前用户:" + iWifiPresenter.getUsername());

        }
    }

}
