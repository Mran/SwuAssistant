package com.swuos.swuassistant.main.view;

import com.swuos.allfragment.swujw.TotalInfos;

/**
 * Created by 张孟尧 on 2016/7/20.
 */
public interface IMainview {
    void showQuitDialog();

    void showUpdata(String changelog, final String url);

    void setNavigationViewHeader(TotalInfos totalInfo);
}
