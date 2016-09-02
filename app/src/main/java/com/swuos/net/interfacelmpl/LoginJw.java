package com.swuos.net.interfacelmpl;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import rx.Observable;

/**
 * Created by 张孟尧 on 2016/8/30.
 */
public interface LoginJw {
    @Headers({"X-Requested-With:XMLHttpRequest", "Content-Type:application/x-www-form-urlencoded; charset=UTF-8"})
    @GET("https://uaaap.swu.edu.cn/cas/login?service=http%3A%2F%2Fjw.swu.edu.cn%2Fssoserver%2Flogin%3Fywxt%3Djw")
    Observable<String> login();
}
