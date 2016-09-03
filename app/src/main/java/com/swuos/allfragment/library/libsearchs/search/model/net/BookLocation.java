package com.swuos.allfragment.library.libsearchs.search.model.net;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by 张孟尧 on 2016/9/4.
 */
public interface BookLocation {
    @GET
    Observable<String> bookLocation(@Url String s);
}
