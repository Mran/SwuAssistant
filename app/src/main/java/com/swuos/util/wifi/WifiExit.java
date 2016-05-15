package com.swuos.util.wifi;


import com.swuos.net.OkhttpNet;
import com.swuos.swuassistant.Constant;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class WifiExit {
    private static OkhttpNet okhttpNet = new OkhttpNet();
    private static String result = null;

    public static String logout(String username, String password, String wifissid) {
        if (wifissid.contains("swu-wifi")) {
            final RequestBody requestBody = new FormBody.Builder()
                    .add("username", username)
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
            else
                result = respones;
        } else
            result = Constant.noWifi;
        return result;
    }


}
