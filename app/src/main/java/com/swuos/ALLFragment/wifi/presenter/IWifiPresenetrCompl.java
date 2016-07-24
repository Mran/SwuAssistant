package com.swuos.ALLFragment.wifi.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.swuos.ALLFragment.swujw.TotalInfos;
import com.swuos.ALLFragment.wifi.view.IWifiFragmentView;
import com.swuos.util.wifi.WifiExit;
import com.swuos.util.wifi.WifiLogin;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 张孟尧 on 2016/7/23.
 */
public class IWifiPresenetrCompl implements IWifiPresenter {
    IWifiFragmentView iWifiFragmentView;
    Context context;
    private TotalInfos totalInfo = TotalInfos.getInstance();
    private WifiStateBroad wifiStateBroad;
    String wifissid;
    public IWifiPresenetrCompl(IWifiFragmentView iWifiFragmentView, Context context) {
        this.iWifiFragmentView = iWifiFragmentView;
        this.context = context;
        wifiStateBroad = new WifiStateBroad();
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        wifissid= wifiInfo.getSSID();
    }

    @Override
    public void login(final String username, final String password) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(WifiLogin.login(username, password, wifissid));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        iWifiFragmentView.showResult(s);
                    }
                });
    }

    @Override
    public void logout(final String username, final String password) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(WifiExit.logout(username, password, wifissid));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        iWifiFragmentView.showResult(s);
                    }
                });
    }

    @Override
    public void timingLogout(final String username, final String password, final int delaytime) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(WifiExit.timingLogout(username, password, wifissid,delaytime));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        iWifiFragmentView.showResult(s);
                    }
                });
    }

    @Override
    public String getUsername() {
        return totalInfo.getUserName();
    }

    @Override
    public String getPassword() {
        return totalInfo.getPassword();
    }

    @Override
    public void initdata() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        context.registerReceiver(wifiStateBroad, filter);
    }

    class WifiStateBroad extends BroadcastReceiver {
        @Override


        public void onReceive(Context context, Intent intent) {

            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            wifissid= wifiInfo.getSSID();
            int wifiState = wifiManager.getWifiState();
            if (wifiState == WifiManager.WIFI_STATE_ENABLING) {
                iWifiFragmentView.changeWifiState("正在打开WIFI");
            } else if (wifiState == WifiManager.WIFI_STATE_ENABLED) {
                iWifiFragmentView.changeWifiState("WIFI未连接");

                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
//                        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//                        String wifissid = wifiInfo.getSSID();
                        iWifiFragmentView.changeWifiState(wifissid.replace("\"", ""));
                    }
                } else if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                    if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {

                        iWifiFragmentView.changeWifiState("正在连接 " + wifissid);
                    }
                }
            } else if (wifiState == WifiManager.WIFI_STATE_DISABLED) {
                iWifiFragmentView.changeWifiState("WIFI已关闭");
            }

        }
    }

}
