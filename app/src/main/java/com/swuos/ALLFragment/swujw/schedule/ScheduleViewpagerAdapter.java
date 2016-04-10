package com.swuos.ALLFragment.swujw.schedule;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.swuos.swuassistant.Constant;

import java.util.List;


/**
 * Created by 张孟尧 on 2016/3/18.
 * scheuleviewpager的适配器
 */
public class ScheduleViewpagerAdapter extends FragmentStatePagerAdapter
{
    private List<Fragment> mscheduleTabblefragmentList;
    private FragmentManager fragmentManager;
    public ScheduleViewpagerAdapter(FragmentManager fm,List<Fragment> scheduleTabblefragmentList)
    {
        super(fm);
        mscheduleTabblefragmentList=scheduleTabblefragmentList;
        fragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position)
    {
        return mscheduleTabblefragmentList.get(position);
    }

    @Override
    public int getCount()
    {
        return mscheduleTabblefragmentList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        return super.instantiateItem(container, position);
    }


    @Override
    public CharSequence getPageTitle(int position)
    {
        return
                Constant.SCHEDULE_WEEK_TITLE[position];
    }
}
