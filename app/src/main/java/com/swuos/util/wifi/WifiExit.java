package com.swuos.util.wifi;


import com.google.gson.JsonObject;
import com.swuos.net.OkhttpNet;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.R;

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

    public static String timingLogout(String username, String password, String wifissid, int delaytime) {
        if (wifissid.contains("swu-wifi")) {
            final JsonObject postjson = new JsonObject();
            postjson.addProperty("username", username);
            postjson.addProperty("password", password);
            postjson.addProperty("date", System.currentTimeMillis() + delaytime * 1000 * 60);
            String respones = okhttpNet.doPost(Constant.urlQuitnet, postjson);
            if (respones.contains(Constant.TIMING_USER_ERROR)) {
                result = Constant.swuWifiLogoutPasswordError;
            } else if (respones.contains(Constant.TIMING_SUCCESS)) {
                result = Constant.TIMING_SUCCESS;
            } else if (respones.contains(Constant.TIMING_USER_NOLOGIN)) {
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
