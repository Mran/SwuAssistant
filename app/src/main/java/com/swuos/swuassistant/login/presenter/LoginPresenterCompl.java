package com.swuos.swuassistant.login.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import com.swuos.ALLFragment.swujw.Login;
import com.swuos.ALLFragment.swujw.TotalInfos;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.login.view.ILoginView;
import com.swuos.swuassistant.R;

/**
 * Created by 张孟尧 on 2016/7/19.
 */
public class LoginPresenterCompl implements ILoginPresenter {
    ILoginView iLoginView;
    Handler handler;
    Context context;
    /*保存登陆信息*/
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TotalInfos totalInfo;
    String result;

    public LoginPresenterCompl(ILoginView iLoginView, Context context) {
        this.iLoginView = iLoginView;
        this.context = context;
        handler = new Handler(Looper.getMainLooper());

    }


    @Override
    public String doLogin(final String userName, final String password) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String response;
                Login login = new Login();
                /*尝试登陆并获取登陆信息*/
                response = login.doLogin(userName, password);
                if (response.contains("Successed")) {
                    /*登陆成功获得名字和学号*/
                    totalInfo = login.getBasicInfo();
                    totalInfo.setUserName(userName);
                    totalInfo.setPassword(password);
                    result = "success";
                    storageInfo();
                } else {dealError(response);}
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iLoginView.onLoginResult(result);
                    }
                });
            }
        }).start();

        return null;
    }

    @Override
    public void dealError(String response) {

        if (response.contains("LoginFailure")) {
                        /*密码错误*/
            result = context.getResources().getString(R.string.no_user_or_password_error);

        } else if (response.contains(Constant.CLIENT_TIMEOUT)) {
                        /*登陆超时*/
            result = context.getResources().getString(R.string.login_timeout);

        } else if (response.contains(Constant.CLIENT_ERROR)) {
                        /*连接错误*/
            result = context.getResources().getString(R.string.connect_error);

        } else if (response.contains(Constant.NO_NET)) {
                        /*网络错误*/
            result = context.getResources().getString(R.string.net_error_please_check_net);

        }

    }


    @Override
    public void storageInfo() {
        sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("userName", totalInfo.getUserName());
        editor.putString("password", totalInfo.getPassword());
        editor.putString("name", totalInfo.getName());
        editor.putString("swuID", totalInfo.getSwuID());
        editor.commit();
    }

}
