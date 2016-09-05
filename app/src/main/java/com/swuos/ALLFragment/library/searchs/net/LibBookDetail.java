package com.swuos.ALLFragment.library.searchs.net;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 张孟尧 on 2016/9/3.
 */
public interface LibBookDetail {
    /**
     * Search observable.
     *
     * @param id the 书在搜索结果页的次序,从0开始
     * @return the observable
     */
    @POST("netweb/BaseView.aspx")
    Observable<String> search(@Query("ID") String id);
}
