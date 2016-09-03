package com.swuos.allfragment.library.libsearchs.search.model.net;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

import java.util.Map;

/**
 * Created by 张孟尧 on 2016/9/3.
 */
public interface LibSearch {
    /**
     * Search observable.
     *
     * @param data the 请求体.
     * @return the observable
     */
    @Headers({"Content-Type: application/x-www-form-urlencoded; charset=UTF-8","User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36"})
    @POST("netweb/default.aspx")
    @FormUrlEncoded
    Observable<String> search(@FieldMap Map<String, String> data);
}
