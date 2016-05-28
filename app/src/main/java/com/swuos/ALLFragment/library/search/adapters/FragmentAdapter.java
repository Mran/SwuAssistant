package com.swuos.ALLFragment.library.search.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by youngkaaa on 2016/5/26.
 * Email:  645326280@qq.com
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        fragments=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
