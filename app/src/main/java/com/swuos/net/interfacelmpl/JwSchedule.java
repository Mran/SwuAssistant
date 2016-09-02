package com.swuos.net.interfacelmpl;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 张孟尧 on 2016/9/2.
 */
public interface JwSchedule {
    @POST("jwglxt/kbcx/xskbcx_cxXsKb.html?gnmkdmKey=N253508")
    @FormUrlEncoded
    Observable<String> getSchedule(@Query("sessionUserKey") String sessionUserkey, @Field("xnm") String xnm, @Field("xqm") String xqm);

}
