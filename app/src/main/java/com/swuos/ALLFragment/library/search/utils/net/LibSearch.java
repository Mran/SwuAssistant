package com.swuos.ALLFragment.library.search.utils.net;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

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
    @POST("netwb/default.aspx")
    Observable<String> search(@Body Map<String,String> data);
}
