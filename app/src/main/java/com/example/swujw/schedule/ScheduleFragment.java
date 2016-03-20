package com.example.swujw.schedule;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.swuassistant.MainActivity;
import com.example.swuassistant.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张孟尧 on 2016/2/29.
 */
public class ScheduleFragment extends Fragment implements View.OnLongClickListener
{
    /*viewpager*/
    private ViewPager sceduleViewPager;
    private View schedule_layout;
    /*viewpager的适配器*/
    private ScheduleViewpagerAdapter scheduleViewpagerAdapter;
    /*保存所有的单周课表fragment*/
    private List<Fragment> scheduleTabblefragmentList;
    /*在toolbar显示第几周*/
    private TextView weekTextViewtoolbar;
    /*toolbar*/
    private Toolbar toolbar;
    MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        schedule_layout = inflater.inflate(R.layout.schedule_layout, container, false);
//        weekTextView = (TextView) schedule_layout.findViewById(R.id.week);
        mainActivity = (MainActivity) getActivity();
        toolbar = mainActivity.getToolbar();
        weekTextViewtoolbar = (TextView) toolbar.findViewById(R.id.toolbarTextView);
        weekTextViewtoolbar.setOnLongClickListener(this);
        /*加载viewpager*/
        setSceduleViewPager();
        return schedule_layout;
    }

    @Override
    public void onHiddenChanged(boolean hidden)
    {
        super.onHiddenChanged(hidden);
        if (!hidden)
        {
            /*当前viewpager显示设置显示第几周*/
            weekTextViewtoolbar.setVisibility(View.VISIBLE);
            weekTextViewtoolbar.setText("第" + String.valueOf(CurrentWeek.getweek()) + "周");
        } else weekTextViewtoolbar.setVisibility(View.INVISIBLE);
    }

    private void setSceduleViewPager()
    {
        scheduleTabblefragmentList = new ArrayList<Fragment>();
        for (int i = 0; i < 20; i++)
        {
            /*批量加载课程表*/
            /*参数i为周次*/
            scheduleTabblefragmentList.add(ScheduleTableFragment.newInstance(i));
        }
        /*设置适配器*/
        scheduleViewpagerAdapter = new ScheduleViewpagerAdapter(getChildFragmentManager(), scheduleTabblefragmentList);
        sceduleViewPager = (ViewPager) schedule_layout.findViewById(R.id.schedule_viewpager);
        sceduleViewPager.setAdapter(scheduleViewpagerAdapter);
        /*设置预加载页面数*/
        sceduleViewPager.setOffscreenPageLimit(2);
        /*第一次打开展示当前周的课表*/
        sceduleViewPager.setCurrentItem(CurrentWeek.getweek() - 1);
        /*页面改变监听*/
        sceduleViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                super.onPageSelected(position);
/*页面改变时,改变toolbar上的周次显示*/
                weekTextViewtoolbar.setText("第" + String.valueOf(position + 1) + "周");

            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
                super.onPageScrollStateChanged(state);
            }

        });
    }

    @Override
    public boolean onLongClick(View v)
    {
        int id = v.getId();
        switch (id)
        {
            case R.id.toolbarTextView:
                sceduleViewPager.setCurrentItem(CurrentWeek.getweek() - 1);
                break;
            default:
                break;
        }
        return false;
    }
}