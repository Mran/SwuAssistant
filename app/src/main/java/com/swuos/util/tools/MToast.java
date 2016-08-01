package com.swuos.util.tools;

import android.content.Context;
import android.widget.Toast;

import io.github.zhitaocai.toastcompat.MIUIToast;

/**
 * Created by 张孟尧 on 2016/8/1.
 */
public class MToast {

    public static void show(Context context, CharSequence text, int duration) {
        if ("V7".contains(Tools.getSystemProperty("ro.miui.ui.version.name"))) {
            MIUIToast.makeText(context, (String) text,duration).show();
        }
        else {
            Toast.makeText(context,text,duration).show();
        }
    }
}
