package com.swuos.ALLFragment.swujw.schedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.swuos.ALLFragment.swujw.TotalInfo;
import com.swuos.ALLFragment.swujw.schedule.util.CurrentWeek;
import com.swuos.ALLFragment.swujw.schedule.util.ScheduleData;
import com.swuos.ALLFragment.swujw.schedule.util.ScheduleItem;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.MainActivity;
import com.swuos.swuassistant.R;
import com.swuos.util.SALog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张孟尧 on 2016/3/10.
 */
public class ScheduleTableFragment extends Fragment implements View.OnTouchListener, View.OnClickListener {

    /*课程表布局*/
    private RelativeLayout relativeLayout;

    private ScrollView scrollView;
    /*星期一的textView*/
    private TextView day1TextView;
    /*第一节课的textView*/
    private TextView class1TextView;
    /*保存所有课程的textview列表*/
    private List<ScheduleData.ScheduleDetail> textViewList = new ArrayList<>();
    private static TotalInfo totalInfo = new TotalInfo();
    View scheduleTableLayout;
    private static SwipeRefreshLayout swipeRefreshLayout;
    private static MainActivity mainActivity;
    private int week;
    private static int curretweek = -1;
    private Boolean late_Load = false;  //标记是否需要刷新
    private IntentFilter intentFilter;
    private LocalRecevier localRecevier;
    private LocalBroadcastManager localBroadcastManager;

    public static final ScheduleTableFragment newInstance(int week) {
        ScheduleTableFragment scheduleTableFragment = new ScheduleTableFragment();
        Bundle bundle = new Bundle(1);
        bundle.putInt("week", week);
        scheduleTableFragment.setArguments(bundle);
        return scheduleTableFragment;
    }

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        SALog.d("onAttach", String.valueOf(week));

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        week = getArguments().getInt("week");
        if (curretweek == -1) {
            curretweek = CurrentWeek.getweek();
            mainActivity = (MainActivity) getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        scheduleTableLayout = inflater.inflate(R.layout.schedule_table_layout, container, false);
        mainActivity = (MainActivity) getActivity();
        day1TextView = (TextView) scheduleTableLayout.findViewById(R.id.z1);
        class1TextView = (TextView) scheduleTableLayout.findViewById(R.id.classs1);
        relativeLayout = (RelativeLayout) scheduleTableLayout.findViewById(R.id.class_table);
        scrollView = (ScrollView) scheduleTableLayout.findViewById(R.id.schedule_table_ScrollView);
        scrollView.setOnTouchListener(this);
        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.schedule_SwipeRefreshLayout);

        SALog.d("creatview", String.valueOf(week));

        return scheduleTableLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.swuos.ALLFragment.swujw.schedule.SCHEDULEDATECHANGE");
        localRecevier = new LocalRecevier();
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.registerReceiver(localRecevier, intentFilter);
        new MyThread().start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        SALog.d("start", String.valueOf(week));
        super.onStart();
    }

    @Override
    public void onResume() {
        SALog.d("resume", String.valueOf(week));
        /*延后刷新*/
        if (late_Load) {
            late_Load = false;
            new MyThread().start();
            SALog.d("resume", "随后刷新" + String.valueOf(week));
        }
        super.onResume();
    }

    @Override
    public void onStop() {
        SALog.d("stop", String.valueOf(week));

        super.onStop();
    }

    @Override
    public void onDestroyView() {
        SALog.d("Destroyview", String.valueOf(week));
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        SALog.d("Destroy", String.valueOf(week));

        super.onDestroy();
    }

    /**
     * Called when the fragment is no longer attached to its activity.  This
     * is called after {@link #onDestroy()}.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        SALog.d("onDetach", String.valueOf(week));

    }

    private void init() {
        if (totalInfo.getScheduleItemList().isEmpty()) {
            return;
        } else {
            setTable();
        }
    }


    public void setTable() {
        textViewList.clear();
        relativeLayout.removeAllViews();
        /*得到一节课的高度*/
        int hight = class1TextView.getHeight();
        /*得到一天的宽度*/
        int width = day1TextView.getWidth();
        /*设置新的布局参数*/
        RelativeLayout.LayoutParams layoutParams;
        int i = 0;
        if (!isAdded()) {
            this.onDetach();
        }
        for (ScheduleItem scheduleItem : totalInfo.getScheduleItemList()) {

            /*判断该课本周是否有课*/
            if (!scheduleItem.getClassweek()[week]) {
                continue;
            }
            layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams
                    .WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                /*建一个新的textview*/
            //            TextView textView = new TextView(scheduleTableLayout.getContext());
            TextView textView = new TextView(getContext());
            if (week == 0) {
                textView.setText(scheduleItem.getTextShowAll());
            } else {
                textView.setText(scheduleItem.getTextShow());
            }

            textView.setTextColor(getResources().getColor(R.color.textcolorblack));
               /*设置高度,用节数乘以一节课的高度*/
            textView.setHeight(hight * scheduleItem.getClassCount());
                /*设置宽度*/
            textView.setWidth(width);
                /*设置距离上边的距离,用一节课的固定高度乘以开始的节次*/
            layoutParams.topMargin = hight * (scheduleItem.getStart() - 1);
            //                layoutParams.setMargins(width * (scheduleItem.getXqj()-1),hight * scheduleItem
            // .getEnd(),0,0);
                /*设置距离左边的距离,用固定宽度乘以该课的上课日*/
            layoutParams.leftMargin = width * (scheduleItem.getXqj() - 1);

            textView.setLayoutParams(layoutParams);
                /*设置背景色*/
            textView.setBackgroundResource(Constant.background[i % 6]);
            textView.setTextColor(getResources().getColor(R.color.white));
            TextPaint tp = textView.getPaint();
            tp.setFakeBoldText(true);
            textView.setPadding(10, 0, 10, 0);
            textView.setOnClickListener(this);
            textView.setId(i);
            ScheduleData.ScheduleDetail scheduleDetail = new ScheduleData.ScheduleDetail();
            scheduleDetail.setScheduleItem(scheduleItem);
            scheduleDetail.setTextView(textView);
            scheduleDetail.setColor(getResources().getColor(Constant.background[i % 6]));
                /*将新建的textview加入列表*/
            textViewList.add(scheduleDetail);
                /*将新建的textview加入布局*/
            relativeLayout.addView(textView);
            i++;
        }
        /*加载完成取消刷新动画*/
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                if (v.getScrollY() != 0) {
                    swipeRefreshLayout.setEnabled(false);
                } else {
                    swipeRefreshLayout.setEnabled(true);

                }

        }
        return false;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        SALog.d("scheduletable", String.valueOf(id));

        Intent intent = new Intent(getActivity(), SchedulDetialActivity.class);
        intent.putExtra("title", textViewList.get(id).getScheduleItem().getKcmc());
        intent.putExtra("xm", textViewList.get(id).getScheduleItem().getXm());
        intent.putExtra("cdmc", textViewList.get(id).getScheduleItem().getCdmc());
        intent.putExtra("jc", textViewList.get(id).getScheduleItem().getJc());
        intent.putExtra("color", textViewList.get(id).getColor());

        startActivity(intent);

    }


    class MyThread extends Thread {
        @Override
        public void run() {
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    init();
                }
            });
        }
    }


    /*设置广播接收刷新消息*/
    class LocalRecevier extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isAdded())
                new MyThread().start();
            else
                late_Load = true;
            //  new MyUpdate().execute();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

}
