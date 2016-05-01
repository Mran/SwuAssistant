package com.swuos.ALLFragment.wifi.util;

import com.swuos.net.OkhttpNet;
import com.swuos.swuassistant.Constant;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by 张孟尧 on 2016/5/1.
 */
public class SwuDormWifiLogin {
    private static OkhttpNet okhttpNet = new OkhttpNet();
    private static String result = null;

    public static String Login(final String userName,
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
        } else
            result = Constant.dormWifiLoginSomeTHError;

        return result;

    }

}
