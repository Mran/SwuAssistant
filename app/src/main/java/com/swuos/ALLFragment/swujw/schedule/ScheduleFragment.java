package com.swuos.ALLFragment.swujw.schedule;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.swuos.ALLFragment.swujw.Login;
import com.swuos.ALLFragment.swujw.TotalInfo;
import com.swuos.ALLFragment.swujw.schedule.util.CurrentWeek;
import com.swuos.ALLFragment.swujw.schedule.util.Schedule;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.MainActivity;
import com.swuos.swuassistant.R;
import com.swuos.util.SALog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张孟尧 on 2016/2/29.
 */
public class ScheduleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    /*viewpager*/
    private ViewPager sceduleViewPager;
    private View schedule_layout;
    /*viewpager的适配器*/
    private ScheduleViewpagerAdapter scheduleViewpagerAdapter;
    /*toolbar*/
    private Toolbar toolbar;
    private MainActivity mainActivity;
    private TabLayout tabLayout;
    /*下拉刷新布局*/
    private SwipeRefreshLayout swipeRefreshLayout;
    private ScrollView scrollView;
    private LocalBroadcastManager localBroadcastManager;
    private static SharedPreferences sharedPreferences;

    private List<Fragment> scheduleTabblefragmentList;    //保存所有的单周课表fragment
    private TotalInfo totalInfo = new TotalInfo();

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case Constant.SCHEDULE_OK: /*成功获取课表*/
                    /*获得课程表成功,发送广播要求所有页面加载课表*/
                    Intent intent = new Intent("com.swuos.ALLFragment.swujw.schedule.SCHEDULEDATECHANGE");
                    localBroadcastManager.sendBroadcast(intent);

                    break;
                case Constant.LOGIN_FAILED://登录失败
                    Toast.makeText(getActivity(), R.string.no_user_or_password_error, Toast
                            .LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                case Constant.SCHOOL_SERVER_BOOM:  //学校服务器爆炸
                    Toast.makeText(getActivity(), R.string.school_servier_boom, Toast
                            .LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                case Constant.SCHEDULE__LOADING:
                    swipeRefreshLayout.setRefreshing(true);
                    break;
                case Constant.SHOW:
                    Toast.makeText(getActivity(), (CharSequence) msg.obj, Toast
                            .LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scheduleTabblefragmentList = new ArrayList<Fragment>();
        for (int i = 0; i < 21; i++) {
            /*批量加载课程表*/
            /*参数i为周次*/
            scheduleTabblefragmentList.add(ScheduleTableFragment.newInstance(i));
        }
    }

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        schedule_layout = inflater.inflate(R.layout.schedule_layout, container, false);

        sceduleViewPager = (ViewPager) schedule_layout.findViewById(R.id.schedule_viewpager);

        setSceduleViewPager();

        mainActivity = (MainActivity) getActivity();
        toolbar = mainActivity.getToolbar();

        /*设置下拉刷新*/
        swipeRefreshLayout = (SwipeRefreshLayout) schedule_layout.findViewById(R.id
                .schedule_SwipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R
                .color.holo_red_light, android.R.color.holo_orange_light, android.R.color
                .holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        /*设置tablayout*/
        tabLayout = (TabLayout) schedule_layout.findViewById(R.id.schedule_tablayout);
        tabLayout.setupWithViewPager(sceduleViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        return schedule_layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        SALog.d("ScheduleFm", "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
         /*打开本地存储文件*/
        sharedPreferences = getActivity().getSharedPreferences("userInfo", getActivity().MODE_PRIVATE);
        initScheduleDate();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        SALog.d("ScheduleFm", "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (totalInfo.getScheduleDataJson() == null) {
                swipeRefreshLayout.setRefreshing(true);
                getSchedule(totalInfo.getSwuID(), totalInfo.getPassword());

            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (totalInfo.getScheduleDataJson().equals("")) {
                swipeRefreshLayout.setRefreshing(true);
                getSchedule(totalInfo.getSwuID(), totalInfo.getPassword());

            }

        }
    }

    private void setSceduleViewPager() {

        /*设置适配器*/
        scheduleViewpagerAdapter = new ScheduleViewpagerAdapter(
                getChildFragmentManager(),
                scheduleTabblefragmentList
        );
        sceduleViewPager.setAdapter(scheduleViewpagerAdapter);
        /*设置预加载页面数*/
        sceduleViewPager.setOffscreenPageLimit(1);
        /*第一次打开展示当前周的课表*/
        sceduleViewPager.setCurrentItem(CurrentWeek.getweek());
        /*页面改变监听*/
        sceduleViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }


        });
        sceduleViewPager.setOnTouchListener(new View.OnTouchListener() {
            /*避免scrollow没在顶部就允许下拉刷新*/
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        swipeRefreshLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        swipeRefreshLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });
    }


    @Override
    public void onRefresh() {
        if (totalInfo.getSwuID().equals("")) {
            Toast.makeText(getActivity(), R.string.not_logged_in, Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        } else {
            totalInfo.getScheduleItemList().clear();
            getSchedule(totalInfo.getSwuID(), totalInfo.getPassword());
        }

    }

    private void initScheduleDate() {

        if (!totalInfo.getSwuID().equals("")) {
            totalInfo.setScheduleDataJson(sharedPreferences.getString("scheduleDataJson", ""));
           /*判断为课程表从未被获取,开始获取课程表*/
            if (totalInfo.getScheduleDataJson().equals("")) {
                swipeRefreshLayout.setRefreshing(true);
                getSchedule(totalInfo.getUserName(), totalInfo.getPassword());
            } else if (totalInfo.getScheduleItemList().isEmpty()) {
                totalInfo.setScheduleItemList(Schedule.getScheduleList(totalInfo));

            }

        }

    }

    private void getSchedule(final String userName, final String password) {
        //                /*开启线程开始查询*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Message message = new Message();
                Login login = new Login();
                String response = login.doLogin(userName, password);
                if (response.contains("LoginSuccessed")) {
                    Schedule schedule = new Schedule(login.okhttpNet);
                    /*判断是否课程表是否正常获得*/
                    if (schedule.setSchedule(totalInfo, "2015", "12").equals(Constant
                            .CLIENT_ERROR)) {
                        message.what = Constant.SCHOOL_SERVER_BOOM;
                        handler.sendMessage(message);
                    } else {
                        //                        scheduleItemList = schedule.getScheduleList(totalInfo);
                        totalInfo.setScheduleItemList(schedule.getScheduleList(totalInfo));
                        /*将获取的课程表json信息写入本地文件*/
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("scheduleDataJson", totalInfo.getScheduleDataJson());
                        editor.commit();
                        message.what = Constant.SCHEDULE_OK;
                        handler.sendMessage(message);
                    }
                } else if (response.contains("LoginFailure")) {
                    message.what = Constant.LOGIN_FAILED;
                    handler.sendMessage(message);
                } else {
                    message.what = Constant.SHOW;
                    message.obj = response;
                    handler.sendMessage(message);
                }
            }
        }).start();

    }


}