package com.swuos.swuassistant.login.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;

import com.swuos.ALLFragment.swujw.TotalInfos;
import com.swuos.net.api.SwuApi;
import com.swuos.net.jsona.LoginJson;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.R;
import com.swuos.swuassistant.login.view.ILoginView;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 张孟尧 on 2016/7/19.
 */
public class LoginPresenterCompl implements ILoginPresenter {
    static String cookie;
    ILoginView iLoginView;
    Handler handler;
    Context context;
    /*保存登陆信息*/
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TotalInfos totalInfo = TotalInfos.getInstance();
    String result;

    public LoginPresenterCompl(ILoginView iLoginView, Context context) {
        this.iLoginView = iLoginView;
        this.context = context;
        handler = new Handler(Looper.getMainLooper());

    }

    @Override
    public String doLogin(final String userName, final String password) {

        //                new Thread(new Runnable() {
        //                    @Override
        //                    public void run() {
        //                        String response;
        //                        Login login = new Login();
        //                        /*尝试登陆并获取登陆信息*/
        //
        //                        LoginJson loginJson = login.doLogin(userName, password);
        //                        if (loginJson.getData().getGetUserInfoByUserNameResponse().getReturnX().isSuccess()) {
        //                            /*登陆成功获得名字和学号*/
        //                            totalInfo = login.getBasicInfo(loginJson);
        //                            totalInfo.setUserName(userName);
        //                            totalInfo.setPassword(password);
        //                            result = "success";
        //                            storageInfo();
        //                        } else {dealError(Constant.USERNAMEORPSWERRR);}
        //                        handler.post(new Runnable() {
        //                            @Override
        //                            public void run() {
        //                                iLoginView.onLoginResult(result);
        //                            }
        //                        });
        //                    }
        //                }).start();
        String swuLoginjsons = String.format("{\"serviceAddress\":\"https://uaaap.swu.edu.cn/cas/ws/acpInfoManagerWS\",\"serviceType\":\"soap\",\"serviceSource\":\"td\",\"paramDataFormat\":\"xml\",\"httpMethod\":\"POST\",\"soapInterface\":\"getUserInfoByUserName\",\"params\":{\"userName\":\"%s\",\"passwd\":\"%s\",\"clientId\":\"yzsfwmh\",\"clientSecret\":\"1qazz@WSX3edc$RFV\",\"url\":\"http://i.swu.edu.cn\"},\"cDataPath\":[],\"namespace\":\"\",\"xml_json\":\"\"}", userName, password);
        SwuApi.loginIswu().login(swuLoginjsons).flatMap(new Func1<LoginJson, Observable<String>>() {
            @Override
            public Observable<String> call(LoginJson loginJson) {
                if (loginJson.getData().getGetUserInfoByUserNameResponse().getReturnX().isSuccess()) {
                    String tgt = loginJson.getData().getGetUserInfoByUserNameResponse().getReturnX().getInfo().getAttributes().getTgt();
                    cookie = String.format("CASTGC=\"%s\"; rtx_rep=no", new String(Base64.decode(tgt, Base64.DEFAULT)));
                    totalInfo = getBasicInfo(loginJson);
                    totalInfo.setPassword(password);
                    result = "success";
                    storageInfo();
                    return SwuApi.loginJw(cookie).login();
                } else
                    return Observable.error(new Throwable(Constant.USERNAMEORPSWERRR));

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                iLoginView.onLoginResult("success");
            }

            @Override
            public void onError(Throwable e) {
                String error = e.getMessage();
                if (e instanceof UnknownHostException) {
                    error = Constant.CLIENT_ERROR;
                } else if (e instanceof SocketTimeoutException) {
                    error = Constant.CLIENT_TIMEOUT;
                }
                iLoginView.onLoginResult(error);
                Log.d("LoginPresenterCompl", "e:" + e);
            }

            @Override
            public void onNext(String s) {

            }
        });


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

    public TotalInfos getBasicInfo(LoginJson loginJson) {
        /*获得基本信息名字和学号*/
        LoginJson.DataBean.GetUserInfoByUserNameResponseBean.ReturnBean.InfoBean.AttributesBean returnBean = loginJson.getData().getGetUserInfoByUserNameResponse().getReturnX().getInfo().getAttributes();
        totalInfo.setName(new String(Base64.decode(returnBean.getACPNAME(), Base64.DEFAULT)));
        totalInfo.setSwuID(loginJson.getData().getGetUserInfoByUserNameResponse().getReturnX().getInfo().getId());
        totalInfo.setUserName(new String(Base64.decode(returnBean.getACPNICKNAME(), Base64.DEFAULT)));
        return totalInfo;
    }

}
