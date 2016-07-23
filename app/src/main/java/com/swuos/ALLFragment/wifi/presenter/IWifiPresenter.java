package com.swuos.ALLFragment.wifi.presenter;

/**
 * Created by 张孟尧 on 2016/7/23.
 */
public interface IWifiPresenter {
    void login(String username, String password, String wifissid);

    void logout(String username, String password, String wifissid);

    String getUsername();

    String getPassword();

    void initdata();
}
