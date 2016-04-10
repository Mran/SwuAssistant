package com.swuos.ALLFragment.swujw.schedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.swuos.ALLFragment.swujw.TotalInfo;
import com.swuos.ALLFragment.swujw.schedule.util.CurrentWeek;
import com.swuos.ALLFragment.swujw.schedule.util.ScheduleItem;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.MainActivity;
import com.swuos.swuassistant.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张孟尧 on 2016/3/10.
 */
public class ScheduleTableFragment extends Fragment implements View.OnTouchListener {

    /*课程表布局*/
    private RelativeLayout relativeLayout;

    private ScrollView scrollView;
    /*星期一的textView*/
    private TextView day1TextView;
    /*第一节课的textView*/
    private TextView class1TextView;
    /*保存所有课程的textview列表*/
    private List<TextView> textViewList = new ArrayList<>();
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
        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id
                .schedule_SwipeRefreshLayout);

        Log.d("creatview", String.valueOf(week));

        return scheduleTableLayout;
    }


    private void init() {
        if (totalInfo.getScheduleItemList().isEmpty()) {
            return;
        } else {
            setTable();
        }
    }


    //    private void getSchedule() {
    //        userName = totalInfo.getUserName();
    //        password = totalInfo.getPassword();
    //        //                /*开启线程开始查询*/
    //        new Thread(new Runnable() {
    //            @Override
    //            public void run() {
    //
    //                Login login = new Login();
    //                Message message = new Message();
    //
    //                if (login.doLogin(userName, password).contains("LoginSuccessed")) {
    //                    Schedule schedule = new Schedule(login.client);
    //                    /*判断是否课程表是否正常获得*/
    //                    if (schedule.setSchedule(totalInfo, "2015", "12").equals(Constant
    //                            .CLIENT_ERROR)) {
    //                        message.what = Constant.ERROR;
    //
    //                        //                        handler.sendMessage(message);
    //                    } else {
    //                        scheduleItemList = schedule.getScheduleList(totalInfo);
    //                        /*将获取的课程表json信息写入本地文件*/
    //                        SharedPreferences.Editor editor = sharedPreferences.edit();
    //                        editor.putString("scheduleDataJson", totalInfo.getScheduleDataJson());
    //                        editor.commit();
    //                        message.what = Constant.SCHEDULE_OK;
    //                        //                        handler.sendMessage(message);
    //                    }
    //                } else {
    //                    message.what = Constant.LOGIN_FAILED;
    //                }
    //            }
    //        }).start();
    //
    //    }

    public void setTable() {
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
            i++;
            /*判断该课本周是否有课*/
            if (!scheduleItem.getClassweek()[week]) {
                continue;
            }
            layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams
                    .WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                /*建一个新的textview*/
            TextView textView = new TextView(scheduleTableLayout.getContext());

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
                /*将新建的textview加入列表*/
            //            textViewList.add(textView);
                /*将新建的textview加入布局*/
            relativeLayout.addView(textView);

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

    class MyUpdate extends AsyncTask {
        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Object doInBackground(Object[] params) {
            return null;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p/>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param o The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(Object o) {
            init();
            super.onPostExecute(o);
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

    /*============================================分割线=================================================*/
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
    public void onDestroyView() {
        Log.d("Destroyview", String.valueOf(week));
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("Destroy", String.valueOf(week));

        super.onDestroy();
    }

    @Override
    public void onStart() {
        Log.d("start", String.valueOf(week));

        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("resume", String.valueOf(week));
        /*延后刷新*/
        if (late_Load) {
            late_Load = false;
            new MyThread().start();
            Log.d("resume", "随后刷新" + String.valueOf(week));
        }
        super.onResume();
    }

    @Override
    public void onStop() {
        Log.d("stop", String.valueOf(week));

        super.onStop();
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
        Log.d("onAttach", String.valueOf(week));

    }

    /**
     * Called when the fragment is no longer attached to its activity.  This
     * is called after {@link #onDestroy()}.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("onDetach", String.valueOf(week));

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

}
