package com.swuos.swuassistant.LoginActivity.pressenler;

/**
 * Created by 张孟尧 on 2016/7/19.
 */
public interface ILoginPresenter {
    String doLogin(String userName, String password);

    String loginResult(String result);

    void storageInfo();

    void dealError(String response);
}
