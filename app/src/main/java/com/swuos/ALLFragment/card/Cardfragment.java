package com.swuos.ALLFragment.card;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.swuos.ALLFragment.library.FragmentAdapter;
import com.swuos.swuassistant.MainActivity;
import com.swuos.swuassistant.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by codekk on 2016/5/3.
 * Email:  645326280@qq.com
 */

public class Cardfragment extends Fragment implements ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragments;
    private List<String> titles;
    private EcardTools ecardTools;
    private CardContentFragmentEcard fragmentEcardInfo;
    private CardContentFragmentConsume fragmentConsumeInfo;
    private String id = "id";
    private String pd = "pd";
    private MaterialDialog materialDialog;
    public static ProgressDialog progressDialogLoading;
    public static boolean page2IsUpdate = false;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("kklog","Cardfragment onActivityCreated()");
        View rootView = inflater.inflate(R.layout.card_layout_main, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpagerCard);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayoutCard);
        ecardTools = new EcardTools();
        fragmentEcardInfo = CardContentFragmentEcard.onNewInstance(id, pd, ecardTools);
        fragmentConsumeInfo = CardContentFragmentConsume.onNewInstance(ecardTools);
        progressDialogLoading = new ProgressDialog(getContext());
        id = MainActivity.sharedPreferences.getString("swuID", "nothing");
        progressDialogLoading.setMessage("正在查询请稍后");
        progressDialogLoading.setCancelable(false);
        progressDialogLoading.show();
        return rootView;
    }

    private void showDialog() {
        materialDialog = new MaterialDialog.Builder(getActivity())
                .title("请输入")
                .positiveText("确定")
                .cancelable(false)
                .positiveColor(Color.parseColor("#48b360"))
                .customView(R.layout.card_dialog_view)
                .build();
        View customView = materialDialog.getCustomView();
        final EditText edittextPd = (EditText) customView.findViewById(R.id.editTextDialogCardPd);
        materialDialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = edittextPd.getText().toString().trim();
                materialDialog.dismiss();
                progressDialogLoading.show();
                fragmentEcardInfo.setPd(pd);
                fragmentEcardInfo.initHttpData();
            }
        });
        materialDialog.show();
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("kklog","Cardfragment onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        if (id.equals("nothing")) {
            Log.d("kklog","id.equals(nothing)");
            progressDialogLoading.dismiss();
            Toast.makeText(getContext(), "请先登录!!", Toast.LENGTH_SHORT).show();
            initFragments();
//            setVisiableGone();
        } else {
            Log.d("kklog","id.equals  else ");
            sharedPreferences = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            pd = sharedPreferences.getString(id, "nothing");
            if (pd.equals("nothing")) {   //指定用户没有
                Log.d("kklog","id.equals  else  if ");
                progressDialogLoading.dismiss();
                initFragments();
                showDialog();
            } else {
                Log.d("kklog","id.equals  else  else ");
                initFragments();
            }
        }
        fragmentAdapter = new FragmentAdapter(getFragmentManager(), fragments, titles);
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(1)));
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setTabsFromPagerAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(this);
    }

//    private void setVisiableGone() {
//        if(fragmentEcardInfo.recyclerView.getVisibility()==View.VISIBLE){
//            fragmentEcardInfo.recyclerView.setVisibility(View.GONE);
//        }
//
//        if(fragmentEcardInfo.swipeRefreshLayout.getVisibility()==View.VISIBLE){
//            fragmentEcardInfo.swipeRefreshLayout.setVisibility(View.GONE);
//        }
//    }

    private void initFragments() {
        fragmentEcardInfo = CardContentFragmentEcard.onNewInstance(id, pd, ecardTools);
        fragmentConsumeInfo = CardContentFragmentConsume.onNewInstance(ecardTools);
        fragments.add(fragmentEcardInfo);
        fragments.add(fragmentConsumeInfo);
        titles.add("卡信息");
        titles.add("消费信息");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 1 && !page2IsUpdate) {
            fragmentConsumeInfo.swipeRefreshLayout.setRefreshing(true);
            fragmentConsumeInfo.initHttpData();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {}
}
