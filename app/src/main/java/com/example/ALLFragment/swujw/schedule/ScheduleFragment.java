package com.example.ALLFragment.swujw.schedule;


import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;


import com.example.swuassistant.Constant;
import com.example.swuassistant.MainActivity;
import com.example.swuassistant.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张孟尧 on 2016/2/29.
 */
public class ScheduleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    /*viewpager*/
    private ViewPager sceduleViewPager;
    private View schedule_layout;
    /*viewpager的适配器*/
    private ScheduleViewpagerAdapter scheduleViewpagerAdapter;
    /*保存所有的单周课表fragment*/
    private List<Fragment> scheduleTabblefragmentList;

    /*toolbar*/
    private Toolbar toolbar;
    private MainActivity mainActivity;
    private TabLayout tabLayout;
    /*下拉刷新布局*/
    private SwipeRefreshLayout swipeRefreshLayout;
    private ScrollView scrollView;
    private Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                /*成功获取课表*/
                case Constant.SCHEDULE_OK:

                    swipeRefreshLayout.setRefreshing(false);

                    break;
                case Constant.LOGIN_FAILED:
                    Toast.makeText(getActivity(), "登录失败", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                case Constant.ERROR:
                    Toast.makeText(getActivity(), "查不出来是服务器的锅╮(╯▽╰)╭", Toast.LENGTH_SHORT).show();

                    swipeRefreshLayout.setRefreshing(false);
                    break;
                case Constant.SCHEDULE__LOADING:
//                    Toast.makeText(getActivity(), "正在加载课程表", Toast.LENGTH_SHORT).show();

                    swipeRefreshLayout.setRefreshing(true);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        scheduleTabblefragmentList = new ArrayList<Fragment>();
        for (int i = 0; i < 21; i++)
        {
            /*批量加载课程表*/
            /*参数i为周次*/
            scheduleTabblefragmentList.add(ScheduleTableFragment.newInstance(i));
        }
    }

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        schedule_layout = inflater.inflate(R.layout.schedule_layout, container, false);

        sceduleViewPager = (ViewPager) schedule_layout.findViewById(R.id.schedule_viewpager);
        setSceduleViewPager();
        mainActivity = (MainActivity) getActivity();
        toolbar = mainActivity.getToolbar();
        toolbar.setTitle(R.string.schedule_title);
        swipeRefreshLayout = (SwipeRefreshLayout) schedule_layout.findViewById(R.id.schedule_SwipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        tabLayout = (TabLayout) schedule_layout.findViewById(R.id.schedule_tablayout);
        tabLayout.setupWithViewPager(sceduleViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        return schedule_layout;
    }

    @Override
    public void onStart()
    {

        super.onStart();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        Log.d("ScheduleFm", "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        Log.d("ScheduleFm", "onViewCreated");
        super.onViewCreated(view, savedInstanceState);

    }

    private void setSceduleViewPager()
    {

        /*设置适配器*/
        scheduleViewpagerAdapter = new ScheduleViewpagerAdapter(getChildFragmentManager(), scheduleTabblefragmentList);
        sceduleViewPager.setAdapter(scheduleViewpagerAdapter);
        /*设置预加载页面数*/
        sceduleViewPager.setOffscreenPageLimit(1);
        /*第一次打开展示当前周的课表*/
        sceduleViewPager.setCurrentItem(CurrentWeek.getweek());
        /*页面改变监听*/
        sceduleViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                super.onPageSelected(position);


            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
                super.onPageScrollStateChanged(state);
            }


        });
        sceduleViewPager.setOnTouchListener(new View.OnTouchListener()
        {
            /*避免scrollow没在顶部就允许下拉刷新*/
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {

                switch (event.getAction())
                {
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
    public void onRefresh()
    {
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("fragmentRafresh");
    }


}