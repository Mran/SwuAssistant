package com.swuos.util.updata;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by 张孟尧 on 2015/12/5.
 */
public class GetAppVersion {

    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = null;

        try {
            PackageManager pm = context.getPackageManager();
            packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);

            return packageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return packageInfo;
    }
}
