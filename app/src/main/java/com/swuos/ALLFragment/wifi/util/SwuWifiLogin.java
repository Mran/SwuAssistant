
package com.swuos.ALLFragment.wifi.util;


import com.swuos.net.OkhttpNet;
import com.swuos.swuassistant.Constant;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by 张孟尧 on 2015/5/1.
 */
public class SwuWifiLogin {
    private static OkhttpNet okhttpNet = new OkhttpNet();
    private static String result = null;

    public static String login(String username, String password) {
        final RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .add("if_login", "")
                .add("B2", "").build();

        String responses = okhttpNet.doPost(Constant.urlSwuWifi, requestBody);

        if (responses.contains(Constant.swuWifiLoginSuccessed)) {
            result = Constant.swuWifiLoginSuccessed;
        } else if (responses.contains(Constant.swuWifiLoginNameOrPasswordError)) {
            result = Constant.swuWifiLoginNameOrPasswordError;
        } else if (responses.contains(Constant.swuWifiLoginAnotherDeviceLogined)) {
            result = Constant.swuWifiLoginAnotherDeviceLogined;
        } else
            result = Constant.swuWifiLoginSomeThError;

        return result;
    }
}