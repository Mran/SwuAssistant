package com.swuos.net;

import com.swuos.swuassistant.Constant;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 张孟尧 on 2016/4/28.
 */
public class OkhttpNet {
    private static CookieJar cookieJar = new CookieJar() {
        List<Cookie> cookies;

        @Override
        public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
            cookies = list;
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl httpUrl) {
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
    };

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .readTimeout(Constant.TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(Constant.TIMEOUT, TimeUnit.MILLISECONDS)
            .build();

    public String doGet(String url) {
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        String responses = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                responses = response.body().string();
            } else
                responses = Constant.CLIENT_ERROR;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responses;
    }

    public String doPost(String url, RequestBody requestBody) {
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Response response = null;
        String responses = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                responses = response.body().string();
            } else
                responses = Constant.CLIENT_ERROR;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responses;
    }
}
