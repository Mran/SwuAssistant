package com.swuos.ALLFragment.swujw.schedule.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.swuos.ALLFragment.swujw.Login;
import com.swuos.ALLFragment.swujw.TotalInfos;
import com.swuos.ALLFragment.swujw.schedule.model.Schedule;
import com.swuos.ALLFragment.swujw.schedule.view.IScheduleView;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.R;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
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
        xqm = "2015";
        xnm = "12";
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
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Login login = new Login();
                String response = login.doLogin(username, password);
                if (response.contains("LoginSuccessed")) {
                    Schedule schedule = new Schedule(login.okhttpNet);
                    /*判断是否课程表是否正常获得*/
                    if (schedule.setSchedule(totalInfos, xnm, xqm).equals(Constant.CLIENT_ERROR)) {
                        subscriber.onError(new Throwable(mContext.getString(R.string.school_servier_boom)));
                    } else {
                        totalInfos.setScheduleItemList(schedule.getScheduleList(totalInfos));
                        /*将获取的课程表json信息写入本地文件*/
                        editor.putString("scheduleDataJson", totalInfos.getScheduleDataJson());
                        editor.commit();
                        subscriber.onNext("");
                    }
                } else if (response.contains("LoginFailure")) {
                    subscriber.onError(new Throwable(mContext.getString(R.string.no_user_or_password_error)));
                } else {
                    subscriber.onError(new Throwable(response));
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                iScheduleView.showDialog(false);
                iScheduleView.showError(e.getMessage());
            }

            @Override
            public void onNext(String s) {
                iScheduleView.showDialog(false);
                iScheduleView.showResult();
            }
        });
    }
}
