package com.swuos.allfragment.swujw.schedule.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.google.gson.Gson;
import com.swuos.allfragment.swujw.TotalInfos;
import com.swuos.allfragment.swujw.schedule.model.Schedule;
import com.swuos.allfragment.swujw.schedule.model.ScheduleData;
import com.swuos.allfragment.swujw.schedule.view.IScheduleView;
import com.swuos.allfragment.swujw.net.api.SwuApi;
import com.swuos.allfragment.swujw.net.jsona.LoginJson;
import com.swuos.swuassistant.Constant;
import com.swuos.util.SALog;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 张孟尧 on 2016/8/3.
 */
public class SchedulePresenterCompl implements ISchedulePresenter {
    Context mContext;
    IScheduleView iScheduleView;
    TotalInfos totalInfos;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String xnm;
    String xqm;


    public SchedulePresenterCompl(Context mContext, IScheduleView iScheduleView) {
        this.mContext = mContext;
        this.iScheduleView = iScheduleView;
        this.totalInfos = TotalInfos.getInstance();
        sharedPreferences = mContext.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    @Override
    public String getUsername() {
        return totalInfos.getUserName();
    }

    @Override
    public String getPassword() {
        return totalInfos.getPassword();
    }

    @Override
    public String getXnm() {
        return xnm;
    }

    @Override
    public void setXnm(String xnm) {
        this.xnm = xnm;
    }

    @Override
    public String getXqm() {
        return xqm;
    }

    @Override
    public void setXqm(String xqm) {
        this.xqm = xqm;
    }

    @Override
    public void initData() {
        xqm = "2016";
        xnm = "3";
        if (!totalInfos.getSwuID().equals("")) {
            totalInfos.setScheduleDataJson(sharedPreferences.getString("scheduleDataJson", ""));
           /*判断为课程表从未被获取,开始获取课程表*/
            if (totalInfos.getScheduleDataJson().equals("")) {
                getSchedule(totalInfos.getUserName(), totalInfos.getPassword(), xnm, xqm);
            } else if (totalInfos.getScheduleItemList().isEmpty()) {
                totalInfos.setScheduleItemList(Schedule.getScheduleList(totalInfos));
            }

        }
    }

    @Override
    public void getSchedule(final String username, final String password, final String xnm, final String xqm) {
        iScheduleView.showDialog(true);
        //        Observable.create(new Observable.OnSubscribe<String>() {
        //            @Override
        //            public void call(Subscriber<? super String> subscriber) {
        //                Login login = new Login();
        //                LoginJson loginJson = login.doLogin(username, password);
        //                if (loginJson.getData().getGetUserInfoByUserNameResponse().getReturnX().isSuccess()) {
        //                    Schedule schedule = new Schedule(login.okhttpNet);
        //                    /*判断是否课程表是否正常获得*/
        //                    if (schedule.setSchedule(totalInfos, xnm, xqm).equals(Constant.CLIENT_ERROR)) {
        //                        subscriber.onError(new Throwable(mContext.getString(R.string.school_servier_boom)));
        //                    } else {
        //                        totalInfos.setScheduleItemList(schedule.getScheduleList(totalInfos));
        //                        /*将获取的课程表json信息写入本地文件*/
        //                        editor.putString("scheduleDataJson", totalInfos.getScheduleDataJson());
        //                        editor.commit();
        //                        subscriber.onNext("");
        //                    }
        //                } else if (loginJson.getData().getGetUserInfoByUserNameResponse().getReturnX().isSuccess()) {
        //                    subscriber.onError(new Throwable(mContext.getString(R.string.no_user_or_password_error)));
        //                }
        //            }
        //        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
        //            @Override
        //            public void onCompleted() {
        //            }
        //
        //            @Override
        //            public void onError(Throwable e) {
        //                iScheduleView.showDialog(false);
        //                iScheduleView.showError(e.getMessage());
        //            }
        //
        //            @Override
        //            public void onNext(String s) {
        //                iScheduleView.showDialog(false);
        //                iScheduleView.showResult();
        //            }
        //        });

        SwuApi.jwSchedule().getSchedule(totalInfos.getSwuID(), xnm, xqm).flatMap(new Func1<String, Observable<?>>() {
            @Override
            public Observable<?> call(String s) {
                if (s.contains("登录超时"))
                    return Observable.error(new Throwable("登录超时"));
                else {
                    totalInfos.setScheduleDataJson(s);
                    Gson gson = new Gson();
                    totalInfos.setScheduleData(gson.fromJson(totalInfos.getScheduleDataJson(), ScheduleData.class));
                    totalInfos.setScheduleItemList(Schedule.getScheduleList(totalInfos));
                        /*将获取的课程表json信息写入本地文件*/
                    editor.putString("scheduleDataJson", totalInfos.getScheduleDataJson());
                    editor.commit();
                    return Observable.just(true);
                }
            }
        }).retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observable) {
                return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {
                        if (throwable.getMessage().contains("登录超时")) {
                            String swuLoginjsons = String.format("{\"serviceAddress\":\"https://uaaap.swu.edu.cn/cas/ws/acpInfoManagerWS\",\"serviceType\":\"soap\",\"serviceSource\":\"td\",\"paramDataFormat\":\"xml\",\"httpMethod\":\"POST\",\"soapInterface\":\"getUserInfoByUserName\",\"params\":{\"userName\":\"%s\",\"passwd\":\"%s\",\"clientId\":\"yzsfwmh\",\"clientSecret\":\"1qazz@WSX3edc$RFV\",\"url\":\"http://i.swu.edu.cn\"},\"cDataPath\":[],\"namespace\":\"\",\"xml_json\":\"\"}", username, password);
                            return SwuApi.loginIswu().login(swuLoginjsons).flatMap(new Func1<LoginJson, Observable<?>>() {
                                @Override
                                public Observable<?> call(LoginJson loginJson) {
                                    String tgt = loginJson.getData().getGetUserInfoByUserNameResponse().getReturnX().getInfo().getAttributes().getTgt();
                                    String cookie = String.format("CASTGC=\"%s\"; rtx_rep=no", new String(Base64.decode(tgt, Base64.DEFAULT)));
                                    return SwuApi.loginJw(cookie).login();
                                }
                            });
                        } else {
                            return Observable.error(throwable);
                        }
                    }
                });
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                               @Override
                               public void onCompleted() {
                                   iScheduleView.showDialog(false);
                               }

                               @Override
                               public void onError(Throwable e) {
                                   SALog.d("SchedulePresenterCompl", "e:" + e.getMessage());
                                   String error = e.getMessage();
                                   iScheduleView.showDialog(false);
                                   if (e instanceof UnknownHostException) {
                                       error = Constant.CLIENT_ERROR;
                                   } else if (e instanceof SocketTimeoutException) {
                                       error = Constant.CLIENT_TIMEOUT;
                                   }
                                   iScheduleView.showError(error);
                               }

                               @Override
                               public void onNext(Object s) {
                                   iScheduleView.showDialog(false);
                                   iScheduleView.showResult();
                               }
                           }

                );
    }
}
