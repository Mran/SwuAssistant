package com.swuos.allfragment.library.libsearchs.search.model.net;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 张孟尧 on 2016/9/3.
 */
public interface LibSearchList {
    /**
     * Search list observable.
     *
     * @param page the 搜索结果的页码
     * @return the observable
     */
    @GET("netweb/ScarchList.aspx ")
    Observable<String> searchList(@Query("Page") String page);
}
