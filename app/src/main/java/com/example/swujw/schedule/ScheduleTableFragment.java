package com.example.swujw.schedule;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
public class ScheduleTableFragment extends Fragment
{
    /*保存课程表的列表,用于listview*/
    private static List<ScheduleItem> scheduleItemList = new ArrayList<>();

    /*账户名*/
    private static String userName;
    /*密码*/
    private static String password;

    /*等待窗口*/
    private static ProgressDialog progressDialogLoading;
    /*成绩表布局*/
    RelativeLayout relativeLayout;
    /*星期一的textView*/
    TextView day1TextView;
    /*第一节课的textView*/
    TextView class1TextView;
    /*保存所有课程的textview列表*/
    List<TextView> textViewList = new ArrayList<>();

    /*保存用户信息*/
    private static TotalInfo totalInfo = new TotalInfo();
    View scheduleTableLayout;
    private Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                /*成功获取课表*/
                case Constant.SCHEDULE_OK:
                    /*关闭登陆窗口*/
                    progressDialogLoading.cancel();
                    setTable();

                    break;
                case Constant.LOGIN_FAILED:
                    progressDialogLoading.setMessage(Constant.NO_NET);
                    progressDialogLoading.setCancelable(true);

                    break;

                default:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        scheduleTableLayout = inflater.inflate(R.layout.schedule_table_layout, container, false);
        progressDialogLoading = new ProgressDialog(scheduleTableLayout.getContext());
        MainActivity mainActivity = (MainActivity) getActivity();
        userName = mainActivity.getUserName();
        password = mainActivity.getPassword();
        day1TextView = (TextView) scheduleTableLayout.findViewById(R.id.z1);
        class1TextView = (TextView) scheduleTableLayout.findViewById(R.id.classs1);
        relativeLayout = (RelativeLayout) scheduleTableLayout.findViewById(R.id.class_table);
        /*获取成绩*/
        getSchedule();
        return scheduleTableLayout;
    }


    private void getSchedule()
    {

                /*设置等待窗口文字*/
        progressDialogLoading.setMessage("正在查询请稍后");
                /*设置不可取消*/
        progressDialogLoading.setCancelable(false);
                /*显示等待窗口*/
        progressDialogLoading.show();
                /*开启线程开始查询*/
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {

                Login login = new Login();
                login.doLogin(userName, password);

                Message message = new Message();

                if (login.doLogin(userName, password).contains("LoginSuccessed"))
                {
                    Schedule schedule = new Schedule(login.client);
                    schedule.setSchedule(totalInfo, "2015", "12");
                    scheduleItemList = schedule.getScheduleList(totalInfo);
                    message.what = Constant.SCHEDULE_OK;
                    handler.sendMessage(message);
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
        ScheduleItem scheduleItem;
        /*得到一节课的高度*/
        int hight = class1TextView.getHeight();
        /*得到一天的宽度*/
        int width = day1TextView.getWidth();
        /*背景颜色*/
        int[] background = {R.color.colorclass1, R.color.colorclass2, R.color.colorclass3, R.color.colorclass4, R.color.colorclass5, R.color.colorclass6};
        for (int i = 0; i < scheduleItemList.size(); i++)
        {
            /*设置新的布局参数*/
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            /*获取一个课程*/
            scheduleItem = scheduleItemList.get(i);
            /*判断这个课程是否已经添加到textview*/
            TextView textView = IsClassExist(scheduleItem.getKcmc(), scheduleItem.getJc());

            if (textView == null)
            {
                /*建一个新的textview*/
                textView = new TextView(scheduleTableLayout.getContext());

                textView.setText(scheduleItem.getKcmc() + "\n" + scheduleItem.getCdmc() + "\n" + scheduleItem.getJc() + " " + scheduleItem.getZcd() + "\n");
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
            } else
            {
                /*如果该课程已经存在.添加新的上课周*/
                textView.setText(textView.getText() + scheduleItem.getZcd() + "\n");
            }
        }
    }

    private TextView IsClassExist(String className, String jc)
    {
        if (textViewList != null)
        {
            for (int i = 0; i < textViewList.size(); i++)
            {
                TextView textView1 = textViewList.get(i);
                /*通过检查已存在的textView是否包含相同的课程名和上课节次*/
                if (textView1.getText().toString().contains(className) && textView1.getText().toString().contains(jc))
                {
                    /*存在就返回这个已存在的textView*/
                    return textView1;
                } else
                {
                    continue;
                }
            }
        }
        return null;
    }

}
