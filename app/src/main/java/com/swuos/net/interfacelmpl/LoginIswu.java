package com.swuos.net.interfacelmpl;


import com.swuos.net.jsona.LoginJson;

import retrofit2.http.*;
import rx.Observable;


/**
 * Created by 张孟尧 on 2016/8/30.
 */
public interface LoginIswu {
    @Headers({"X-Requested-With:XMLHttpRequest", "Content-Type:application/x-www-form-urlencoded; charset=UTF-8"})
    @POST("http://i.swu.edu.cn/remote/service/process")
    Observable<LoginJson> login(@Query("serviceInfo") String swuLoginJson);
}
