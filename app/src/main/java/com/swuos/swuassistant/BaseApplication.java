package com.swuos.swuassistant;

import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.swuos.util.NavigationViewAttr;

import im.fir.sdk.FIR;
import solid.ren.skinlibrary.base.SkinBaseApplication;
import solid.ren.skinlibrary.config.SkinConfig;

/**
 * Created by 张孟尧 on 2016/8/5.
 */
public class BaseApplication extends SkinBaseApplication {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Fresco.initialize(this);
        FIR.init(this);
        SkinConfig.setCanChangeStatusColor(true);
        SkinConfig.addSupportAttr("navigationViewMenu", new NavigationViewAttr());
    }

}
