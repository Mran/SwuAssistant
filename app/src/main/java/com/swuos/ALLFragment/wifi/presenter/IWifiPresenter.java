package com.swuos.allfragment.wifi.presenter;

/**
 * Created by 张孟尧 on 2016/7/23.
 */
public interface IWifiPresenter {
    void login(String username, String password);

    void logout(String username, String password);

    void timingLogout(String username,String password,int delaytime);

    String getUsername();

    String getPassword();

    void initdata();
}
