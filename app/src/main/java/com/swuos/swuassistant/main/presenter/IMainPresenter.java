package com.swuos.swuassistant.main.presenter;

import com.swuos.allfragment.swujw.TotalInfos;

/**
 * Created by 张孟尧 on 2016/7/20.
 */
public interface IMainPresenter {
    void startServier();

    void initData(TotalInfos totalInfo);

    void cleanData();

    void startUpdata();
}

