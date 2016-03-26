package com.example.swujw.schedule;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swuassistant.Constant;
import com.example.swuassistant.MainActivity;
import com.example.swuassistant.R;
import com.example.swujw.Login;
import com.example.swujw.TotalInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张孟尧 on 2016/3/10.
 */
public class ScheduleTableFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnTouchListener
{
    /*保存课程表的列表*/
    private static List<ScheduleItem> scheduleItemList = new ArrayList<>();
//    /*处理后的课程表的列表,直接用*/
//    private static List<ScheduleItem> scheduleItemListSort = new ArrayList<>();

    /*账户名*/
    private static String userName = "";
    /*密码*/
    private static String password = "";

    /*课程表布局*/
    private RelativeLayout relativeLayout;
    /*下拉刷新布局*/
    SwipeRefreshLayout swipeRefreshLayout;
    /*包含在SwipeRefreshLayout中的scrollow布局*/
    ScrollView scrollView;
    /*星期一的textView*/
    TextView day1TextView;
    /*第一节课的textView*/
    TextView class1TextView;
    /*保存所有课程的textview列表*/
    private static List<TextView> textViewList = new ArrayList<>();

    /*保存用户信息*/
    private static TotalInfo totalInfo = new TotalInfo();
    private static SharedPreferences sharedPreferences;
    View scheduleTableLayout;
    private static MainActivity mainActivity;
    private int week;
    private Boolean isLoad = false;
    private Boolean sthChanged = false;
    private Boolean isFirst = true;
    private Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                /*成功获取课表*/
                case Constant.SCHEDULE_OK:

                    setTable();
                    isLoad = true;
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

    public static final ScheduleTableFragment newInstance(int week)
    {
        ScheduleTableFragment scheduleTableFragment = new ScheduleTableFragment();
        Bundle bundle = new Bundle(1);
        bundle.putInt("week", week);
        scheduleTableFragment.setArguments(bundle);
        return scheduleTableFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        week = getArguments().getInt("week");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        scheduleTableLayout = inflater.inflate(R.layout.schedule_table_layout, container, false);

        day1TextView = (TextView) scheduleTableLayout.findViewById(R.id.z1);
        class1TextView = (TextView) scheduleTableLayout.findViewById(R.id.classs1);
        relativeLayout = (RelativeLayout) scheduleTableLayout.findViewById(R.id.class_table);
        swipeRefreshLayout = (SwipeRefreshLayout) scheduleTableLayout.findViewById(R.id.schedule_table_SwipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        scrollView = (ScrollView) scheduleTableLayout.findViewById(R.id.schedule_table_ScrollView);
        swipeRefreshLayout.setOnRefreshListener(this);
        scrollView.setOnTouchListener(this);
        mainActivity = (MainActivity) getActivity();
//init();
        new MyThread().start();
//new Yibu().execute();
        Log.d("creatview", String.valueOf(week));

        return scheduleTableLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDestroyView()
    {
        Log.d("Destroyview", String.valueOf(week));
        super.onDestroyView();
    }

    @Override
    public void onDestroy()
    {
        Log.d("Destroy", String.valueOf(week));

        super.onDestroy();
    }

    @Override
    public void onStart()
    {
        Log.d("start", String.valueOf(week));

        super.onStart();
    }

    @Override
    public void onResume()
    {
        Log.d("resume", String.valueOf(week));

        super.onResume();
    }

    @Override
    public void onStop()
    {
        Log.d("stop", String.valueOf(week));

        super.onStop();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("可见1", String.valueOf(week));

        if (isVisibleToUser && !isLoad)
        {
            Log.d("可见2", String.valueOf(week));

        }
    }

    private void init()
    {

        /*判断用户是否登录*/
        if (userName.isEmpty())
        {  /*获取用户名和密码*/
            userName = mainActivity.getUserName();
            password = mainActivity.getPassword();
        /*打开本地存储文件*/
            sharedPreferences = getActivity().getSharedPreferences("userInfo", getActivity().MODE_PRIVATE);
        /*加载课程表数据*/
            totalInfo.setScheduleDataJson(sharedPreferences.getString("scheduleDataJson", ""));
        }
        /*判断是否已经获得课程表的数据*/
        if (totalInfo.getScheduleDataJson().equals(""))
        {
                /*没有就去请求数据*/

            Message message = new Message();
                /*显示加载的圆圈*/
            message.what = Constant.SCHEDULE__LOADING;
            handler.sendMessage(message);
            getSchedule();
            return;
        }
        if (scheduleItemList.isEmpty())/*数据是否已经写入list*/
        {
            scheduleItemList = Schedule.getScheduleList(totalInfo);
        }
        if (!isLoad)
        {
            Message messages = new Message();
            messages.what = Constant.SCHEDULE_OK;
            handler.sendMessage(messages);

        }
    }


    private void getSchedule()
    {
        userName = mainActivity.getUserName();
        password = mainActivity.getPassword();
//                /*开启线程开始查询*/
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {

                Login login = new Login();
                Message message = new Message();

                if (login.doLogin(userName, password).contains("LoginSuccessed"))
                {
                    Schedule schedule = new Schedule(login.client);
                    /*判断是否课程表是否正常获得*/
                    if (schedule.setSchedule(totalInfo, "2015", "12").equals(Constant.CLIENT_ERROR))
                    {
                        message.what = Constant.ERROR;

                        handler.sendMessage(message);
                    } else
                    {
                        scheduleItemList = schedule.getScheduleList(totalInfo);
                        /*将获取的课程表json信息写入本地文件*/
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("scheduleDataJson", totalInfo.getScheduleDataJson());
                        editor.commit();
                        message.what = Constant.SCHEDULE_OK;
                        handler.sendMessage(message);
                    }
                } else
                {
                    message.what = Constant.LOGIN_FAILED;

                    handler.sendMessage(message);
                }
            }
        }).start();

    }

    private void setTable()
    {
        relativeLayout.removeAllViews();
        ScheduleItem scheduleItem;
        /*得到一节课的高度*/
        int hight = class1TextView.getHeight();
        /*得到一天的宽度*/
        int width = day1TextView.getWidth();
        /*背景颜色*/
        int[] background = {R.color.colorclass1, R.color.colorclass2, R.color.colorclass3, R.color.colorclass4, R.color.colorclass5, R.color.colorclass6};
 /*设置新的布局参数*/
        RelativeLayout.LayoutParams layoutParams;
        for (int i = 0; i < scheduleItemList.size(); i++)
        {
            /*获取一个课程*/
            scheduleItem = scheduleItemList.get(i);
            /*判断该课本周是否有课*/
            if (!scheduleItem.getClassweek()[week])
            {continue;

            }
                layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                /*建一个新的textview*/
                TextView textView = new TextView(scheduleTableLayout.getContext());
                textView.setText(scheduleItem.getTextShow());
                textView.setTextColor(getResources().getColor(R.color.textcolorblack));
               /*设置高度,用节数乘以一节课的高度*/
                textView.setHeight(hight * scheduleItem.getClassCount());
                /*设置宽度*/
                textView.setWidth(width);
                /*设置距离上边的距离,用一节课的固定高度乘以开始的节次*/
                layoutParams.topMargin = hight * (scheduleItem.getStart() - 1);
//                layoutParams.setMargins(width * (scheduleItem.getXqj()-1),hight * scheduleItem.getEnd(),0,0);
                /*设置距离左边的距离,用固定宽度乘以该课的上课日*/
                layoutParams.leftMargin = width * (scheduleItem.getXqj() - 1);

                textView.setLayoutParams(layoutParams);
                /*设置背景色*/
                textView.setBackgroundResource(background[i % 6]);
                /*将新建的textview加入列表*/
                textViewList.add(textView);
                /*将新建的textview加入布局*/
                relativeLayout.addView(textView);
            }



    }

    @Override
    public void onRefresh()
    {
        /*下拉时查询课表*/

        scheduleItemList.clear();
        textViewList.clear();
        getSchedule();
    }

    /*避免scrollow没在顶部就允许下拉刷新*/
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                if (v.getScrollY() != 0)
                {
                    swipeRefreshLayout.setEnabled(false);
                } else
                {
                    swipeRefreshLayout.setEnabled(true);
                }
                break;
        }
        return false;
    }
    class MyThread extends Thread
    {
        @Override
        public void run() {
            mainActivity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    init();
                }
            });
        }
    }
    class Yibu extends AsyncTask<String, String, String>
    {
        @Override
        protected String doInBackground(String... params) {
            init();

            return null;
        }
        @Override
        protected void onPostExecute(String result) {


        }
    }
}
