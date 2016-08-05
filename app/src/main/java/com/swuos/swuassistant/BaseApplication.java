package com.swuos.swuassistant;

import com.swuos.util.NavigationViewAttr;

import solid.ren.skinlibrary.base.SkinBaseApplication;
import solid.ren.skinlibrary.config.SkinConfig;

/**
 * Created by 张孟尧 on 2016/8/5.
 */
public class BaseApplication extends SkinBaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinConfig.setCanChangeStatusColor(true);
        SkinConfig.addSupportAttr("navigationViewMenu", new NavigationViewAttr());
    }
}
