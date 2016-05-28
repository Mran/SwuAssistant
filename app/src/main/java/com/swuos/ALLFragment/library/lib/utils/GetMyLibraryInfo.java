package com.swuos.ALLFragment.library.lib.utils;



import com.swuos.net.OkhttpNet;
import com.swuos.swuassistant.Constant;

import okhttp3.RequestBody;

/**
 * Created by ASUS on 2016/3/11.
 */
public class GetMyLibraryInfo {
    private static OkhttpNet okhttpNet;

    public static void Init() {

        okhttpNet = new OkhttpNet();
    }

    //登录到图书馆主页
    public static String libraryLogin(RequestBody requestBody) {

        return okhttpNet.doPost(Constant.loginMyLibrary, requestBody);
    }

    //跳转到我的图书馆页面主页
    public static String libraryBorrowInfo() {

        return okhttpNet.doGet(Constant.libraryBorrowInfo);
    }

    //跳转到我的书架
    public static String ToMyBookShelf() {

        return okhttpNet.doGet(Constant.libraryBorrowUri);
    }

    public static String getMyBorrowHistory() {

        return okhttpNet.doGet(Constant.libraryBorrowHistoryUri);
    }
}
