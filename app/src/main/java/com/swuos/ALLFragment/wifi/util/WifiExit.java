package com.swuos.ALLFragment.wifi.util;


import com.swuos.net.OkhttpNet;
import com.swuos.swuassistant.Constant;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class WifiExit {
    private static OkhttpNet okhttpNet = new OkhttpNet();
    private static String result = null;

    public static String Logout(final String userName,
            final String password) {
        final RequestBody requestBody = new FormBody.Builder()
                .add("username", userName)
                .add("password", password)
                .add("B1", "").build();

        String respones = okhttpNet.doPost(Constant.urlWifiLogout, "Gb2312", requestBody);
        if (respones.contains(Constant.swuWifiLogoutPasswordError)) {
            result = Constant.swuWifiLogoutPasswordError;
        } else if (respones.contains(Constant.swuWifiLogoutSuccessed)) {
            result = Constant.swuWifiLogoutSuccessed;
        } else if (respones.contains(Constant.swuWifiLogoutNoLogined)) {
            result = Constant.swuWifiLogoutNoLogined;
        } else if (respones.contains(Constant.CLIENT_TIMEOUT))
            result = Constant.CLIENT_TIMEOUT;

        return result;
    }


}
