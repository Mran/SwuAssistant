package com.swuos.allfragment.swujw.schedule.presenter;

/**
 * Created by 张孟尧 on 2016/8/3.
 */
public interface ISchedulePresenter {
    String getUsername();

    String getPassword();

    String getXnm();

    void setXnm(String xnm);

    String getXqm();

    void setXqm(String xqm);

    void initData();

    void getSchedule(String username, String password, String xnm, String xqm);

}
