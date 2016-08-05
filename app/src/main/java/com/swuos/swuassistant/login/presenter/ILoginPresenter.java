package com.swuos.swuassistant.login.presenter;

/**
 * Created by 张孟尧 on 2016/7/19.
 */
public interface ILoginPresenter {
    String doLogin(String userName, String password);


    void storageInfo();

    void dealError(String response);
}
