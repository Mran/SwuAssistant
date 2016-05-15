package com.swuos.util.wifi;

import com.swuos.net.OkhttpNet;
import com.swuos.swuassistant.Constant;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by 张孟尧 on 2016/5/2.
 */
public class WifiLogin {
    private static OkhttpNet okhttpNet = new OkhttpNet();
    private static String result = null;

    public static String login(String username, String password, String wifissid) {
        if (wifissid.contains("swu-wifi-dorm")) {
            return swuDormWifiLogin(username, password);
        } else if (wifissid.contains("swu-wifi")) {
            return swuWifiLogin(username, password);
        } else
            return Constant.noWifi;
    }


    private static String swuWifiLogin(String username, String password) {
        final RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .add("if_login", "")
                .add("B2", "").build();

        String responses = okhttpNet.doPost(Constant.urlSwuWifi, "gb2312", requestBody);

        if (responses.contains(Constant.swuWifiLoginSuccessed)) {
            result = Constant.swuWifiLoginSuccessed;
        } else if (responses.contains(Constant.swuWifiLoginNameOrPasswordError)) {
            result = Constant.swuWifiLoginNameOrPasswordError;
        } else if (responses.contains(Constant.swuWifiLoginAnotherDeviceLogined)) {
            result = Constant.swuWifiLoginAnotherDeviceLogined;
        } else if (responses.contains(Constant.CLIENT_TIMEOUT)) {
            result = Constant.CLIENT_TIMEOUT;
        } else if (responses.contains(Constant.NO_NET)) {
            result = Constant.NO_NET;
        } else
            result = Constant.swuWifiLoginSomeThError;

        return result;
    }


    private static String swuDormWifiLogin(final String userName,
            final String password) {
        final RequestBody requestBody = new FormBody.Builder()
                .add("username", userName)
                .add("password", password)
                .add("loginTime", String.valueOf(System.currentTimeMillis()))
                .build();

        String responses = okhttpNet.doPost(Constant.urlSwuDormWifi, requestBody);
        if (responses.contains(Constant.dormWifiLoginSuccessed)) {
            result = Constant.dormWifiLoginSuccessed;
        } else if (responses.contains(Constant.dormWifiAnotherDeviceLoginedE)) {
            result = Constant.dormWifiAnotherDeviceLoginedC;
        } else if (responses.contains(Constant.dormWifiLoginReject)) {
            result = Constant.dormWifiLoginReject;
        } else if (responses.contains(Constant.dormWifiLoginShortE)) {
            result = Constant.dormWifiLoginShortC;
        } else if (responses.contains(Constant.dormWifiLoginEmptyE)) {
            result = Constant.dormWifiLoginEmptyC;
        } else if (responses.contains(Constant.dormWifiLoginPasswordWrongE)) {
            result = Constant.dormWifiLoginPasswordWrongC;
        } else if (responses.contains(Constant.dormWifiLoginNotExistE)) {
            result = Constant.dormWifiLoginNotExistC;
        } else if (responses.contains(Constant.CLIENT_TIMEOUT)) {
            result = Constant.CLIENT_TIMEOUT;
        } else if (responses.contains(Constant.NO_NET)) {
            result = Constant.NO_NET;
        } else
            result = Constant.dormWifiLoginSomeTHError;

        return result;

    }

}

