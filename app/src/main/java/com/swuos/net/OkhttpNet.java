package com.swuos.net;

import com.swuos.swuassistant.Constant;
import com.swuos.util.SALog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    //采用utf-8编码
    public String doGet(String url) {
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        String responses = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().byteStream(), "GBK"));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                //                    将收到的内容转化为字符串
                responses = stringBuilder.toString();

            } else
                responses = Constant.CLIENT_ERROR;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            responses = Constant.CLIENT_TIMEOUT;
        } catch (IOException e) {
            e.printStackTrace();
        }
        SALog.d("post", responses);
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
            responses = Constant.CLIENT_TIMEOUT;
        } catch (IOException e) {
            e.printStackTrace();
        }
        SALog.d("post", responses);
        return responses;
    }

    //设置指定编码
    public String doGet(String url, String parse) {
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        String responses = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().byteStream(), parse));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                responses = stringBuilder.toString();

            } else
                responses = Constant.CLIENT_ERROR;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            responses = Constant.CLIENT_TIMEOUT;
        } catch (IOException e) {
            e.printStackTrace();
        }
        SALog.d("post", responses);
        return responses;
    }

    public String doPost(String url, String parse, RequestBody requestBody) {
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Response response = null;
        String responses = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().byteStream(), parse));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                responses = stringBuilder.toString();
            } else
                responses = Constant.CLIENT_ERROR;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            responses = Constant.CLIENT_TIMEOUT;
        } catch (IOException e) {
            e.printStackTrace();
        }
        SALog.d("post", responses);
        return responses;
    }


}
