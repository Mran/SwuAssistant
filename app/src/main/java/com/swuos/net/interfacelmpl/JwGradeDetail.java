package com.swuos.net.interfacelmpl;

import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by 张孟尧 on 2016/9/2.
 */
public interface JwGradeDetail {
    @POST("http://jw.swu.edu.cn/jwglxt/cjcx/cjcx_cxCjxq.html?gnmkdmKey=N305005")
    Observable<String> getSchedule(@Query("time") String time, @Query("sessionUserKey") String sessionUserkey, @QueryMap Map<String, String> data);
}
