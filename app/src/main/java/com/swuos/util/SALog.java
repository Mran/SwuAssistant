package com.swuos.util;

import android.util.Log;

/**
 * Created by 张孟尧 on 2016/4/19.
 */
public class SALog {
    private static final boolean positon = true;

    public static void d(String tag, String msg) {
        if (positon)
            Log.d(tag, msg);
    }
}
