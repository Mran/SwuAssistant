package com.swuos.allfragment.library.libsearchs.search.model.net;


import com.swuos.swuassistant.Constant;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by 张孟尧 on 2016/9/3.
 */
public class LibApi {

    private static LibSearch libSearch;
    private static LibSearchList libSearchList;
    private static LibBookDetail libBookDetail;
    private static BookLocation bookLocation;
    private static RxJavaCallAdapterFactory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    private static ScalarsConverterFactory scalarsConverterFactory = ScalarsConverterFactory.create();
    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

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
            .addInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC))
            .authenticator(new Authenticator() {
                @Override
                public Request authenticate(Route route, Response response) throws IOException {
                    String credential = Credentials.basic("opensource", "freedom");
//                    SALog.d("okhttp", "认证");
                    Request request = response.request().newBuilder().header("Authorization", credential).build();
                    return request;
                }
            })
            .connectTimeout(Constant.TIMEOUT, TimeUnit.MILLISECONDS )
            .readTimeout(Constant.TIMEOUT, TimeUnit.MILLISECONDS)
            .build();

    public static LibSearch getLibSearch() {
        if (libSearch == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.LIBRARYSEARCH)
                    .client(okHttpClient)
                    .addConverterFactory(scalarsConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
        libSearch=retrofit.create(LibSearch.class);
        }
        return libSearch;
    }

    public static LibSearchList getLibSearchList() {
        if (libSearchList == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.LIBRARYSEARCH)
                    .client(okHttpClient)
                    .addConverterFactory(scalarsConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            libSearchList=retrofit.create(LibSearchList.class);
        }
        return libSearchList;
    }

    public static LibBookDetail getLibBookDetail() {
        if (libBookDetail == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.LIBRARYSEARCH)
                    .client(okHttpClient)
                    .addConverterFactory(scalarsConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            libBookDetail=retrofit.create(LibBookDetail.class);
        }
        return libBookDetail;
    }
    public static BookLocation getBookLocation() {
        if (bookLocation == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.LIBRARYSEARCH)
                    .client(okHttpClient)
                    .addConverterFactory(scalarsConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            bookLocation=retrofit.create(BookLocation.class);
        }
        return bookLocation;
    }
}
