package com.swuos.util.tools;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by youngkaaa on 2016/5/29.
 * Email:  645326280@qq.com
 */
public class Tools {

    //关闭软键盘
    public static void closeSoftKeyBoard(Activity activity){
        View peekDecorView=activity.getWindow().peekDecorView();
        if(peekDecorView!=null){
            InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(peekDecorView.getWindowToken(), 0);
        }
    }

}
