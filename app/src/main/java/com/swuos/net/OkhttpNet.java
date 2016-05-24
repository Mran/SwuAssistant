package com.swuos.net;

import com.google.gson.JsonObject;
import com.swuos.swuassistant.Constant;
import com.swuos.util.SALog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Credentials;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by 张孟尧 on 2016/4/28.
 */
public class OkhttpNet implements Serializable {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
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
            .authenticator(new Authenticator() {
                @Override
                public Request authenticate(Route route, Response response) throws IOException {
                    String credential = Credentials.basic("opensource", "freedom");
                    SALog.d("okhttp", "认证");
                    Request request = response.request().newBuilder().header("Authorization", credential).build();
                    return request;
                }
            })
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
        } catch (ConnectException e) {
            e.printStackTrace();
            responses = Constant.NO_NET;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            responses = Constant.CLIENT_TIMEOUT;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (responses == null) {
            responses = Constant.NO_NET;
        }
        SALog.d("post", responses);
        return responses;
    }

    //返回get请求得到的输入流
    public InputStream doGetInputStream(String url) {
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        InputStream responses = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                responses = response.body().byteStream();
            }
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
        } catch (ConnectException e) {
            e.printStackTrace();
            responses = Constant.NO_NET;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            responses = Constant.CLIENT_TIMEOUT;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (responses == null) {
            responses = Constant.NO_NET;
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
        } catch (ConnectException e) {
            e.printStackTrace();
            responses = Constant.NO_NET;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            responses = Constant.CLIENT_TIMEOUT;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (responses == null) {
            responses = Constant.NO_NET;
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
        } catch (ConnectException e) {
            e.printStackTrace();
            responses = Constant.NO_NET;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            responses = Constant.CLIENT_TIMEOUT;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (responses == null) {
            responses = Constant.NO_NET;
        }
        SALog.d("post", responses);
        return responses;
    }

    public String doPost(String url, JsonObject jsonObject) {
        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());

        Request request = new Request.Builder().url(url).post(requestBody).build();
        Response response = null;
        String responses = null;
        try {
            response = okHttpClient.newCall(request).execute();
            SALog.d("okhttp", String.valueOf(response.code()));

            if (response.isSuccessful()) {
                //                SALog.d("okhttp", String.valueOf(response.code()));
                responses = response.body().string();
            } else
                responses = Constant.CLIENT_ERROR;
        } catch (ConnectException e) {
            e.printStackTrace();
            responses = Constant.NO_NET;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            responses = Constant.CLIENT_TIMEOUT;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (responses == null) {
            responses = Constant.NO_NET;
        }
        SALog.d("post", responses);
        return responses;
    }

    public String doPost(String url, RequestBody requestBody, Headers headers) {
        Request request;
        if (headers != null)
            request = new Request.Builder().headers(headers).url(url).post(requestBody).build();
        else
            request = new Request.Builder().url(url).post(requestBody).build();

        Response response = null;
        String responses = null;
        try {
            response = okHttpClient.newCall(request).execute();
            SALog.d("okhttp", String.valueOf(response.code()));

            if (response.isSuccessful()) {
                //                SALog.d("okhttp", String.valueOf(response.code()));
                responses = response.body().string();
            } else
                responses = Constant.CLIENT_ERROR;
        } catch (ConnectException e) {
            e.printStackTrace();
            responses = Constant.NO_NET;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            responses = Constant.CLIENT_TIMEOUT;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (responses == null) {
            responses = Constant.NO_NET;
        }
        SALog.d("post", responses);
        return responses;
    }

}
