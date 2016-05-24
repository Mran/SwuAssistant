package com.swuos.ALLFragment.library.util;

import com.swuos.net.OkhttpNet;
import com.swuos.swuassistant.Constant;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.RequestBody;

/**
 * Created by 张孟尧 on 2016/5/24.
 */
public class LibSearch {
    public String bookSearch(String bookName) {
        RequestBody requestBody = new FormBody.Builder()
                .add("title", "")
                .add("su", "")
                .add("flword", "")
                .add("viewtype", "")
                .add("corename", "")
                .add("oneSearchWord", "")
                .add("onefq", "")
                .add("searchModel", "front")
                .add("searchType", "oneSearch")
                .add("tag", "search")
                .add("subtag", "simsearch")
                .add("searchFieldType", "text")
                .add("field", "title")
                .add("gcbook", "no")
                .add("aliasname", "全部馆藏")
                .add("q", bookName).build();
        OkhttpNet okhttpNet = new OkhttpNet();
        Headers header = new Headers.Builder()
                .add("Content-Type", "application/x-www-form-urlencoded")
                .build();
        return okhttpNet.doPost(Constant.librarySearch, requestBody, header);
    }

    public String bookDetail(int boodId) {
        String url = Constant.libraryBookDetail + boodId;
        OkhttpNet okhttpNet = new OkhttpNet();
        return okhttpNet.doGet(url);
    }
}
