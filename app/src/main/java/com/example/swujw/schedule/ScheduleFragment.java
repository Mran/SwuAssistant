package com.example.swujw.schedule;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.swuassistant.Constant;
import com.example.swuassistant.MainActivity;
import com.example.swuassistant.R;
import com.example.swujw.TotalInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张孟尧 on 2016/2/29.
 */
public class ScheduleFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener
{
    /*保存课程表的列表,用于listview*/
    private static List<ScheduleItem> scheduleItemList = new ArrayList<>();
    /*listview*/
    private static ListView listView;
    /*账户名*/
    private static String userName;
    /*密码*/
    private static String password;

    /*等待窗口*/
    private static ProgressDialog progressDialogLoading;

//    private static TableLayout showGradesLayout;

    /*保存用户信息*/
    private static TotalInfo totalInfo = new TotalInfo();
    /*listview的适配器*/
    private static ScheduleAdapter adapter = null;
    /*选择学年的下拉列表*/
    private static Spinner spinnerXnm;
    /*选择学期的下拉列表*/
    private static Spinner spinnerXqm;
    /*用户当前选择的学期和学年*/
    private static String xnm;
    private static String xqm;
    private static Button buttoncx;
    View scheduleLayout;
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

                    if (adapter == null)
                    {
                        /*设置listview适配器*/
                        adapter = new ScheduleAdapter(scheduleLayout.getContext(), R.layout.schedule_item, scheduleItemList);
                        listView.setAdapter(adapter);
                    } else
                    {/*如果已经设置过就更新*/
                        adapter.clear();
                        adapter.addAll(scheduleItemList);
                        adapter.notifyDataSetChanged();
                    }
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
        scheduleLayout = inflater.inflate(R.layout.schedule_layout, container, false);
        listView = (ListView) scheduleLayout.findViewById(R.id.schedule_list);
        spinnerXnm = (Spinner) scheduleLayout.findViewById(R.id.sxnm);
        spinnerXqm = (Spinner) scheduleLayout.findViewById(R.id.sxqm);
        buttoncx = (Button) scheduleLayout.findViewById(R.id.cx);
        buttoncx.setOnClickListener(this);
         /*设置下拉列表的选择监听*/
        spinnerXnm.setOnItemSelectedListener(this);
        spinnerXqm.setOnItemSelectedListener(this);
        /*学年下拉列表的默认值*/
        spinnerXnm.setSelection(3, true);
        /*学期下拉列表的默认值*/
        spinnerXqm.setSelection(2, true);
        progressDialogLoading = new ProgressDialog(scheduleLayout.getContext());
        MainActivity mainActivity = (MainActivity) getActivity();
        userName = mainActivity.getUserName();
        password = mainActivity.getPassword();
        getSchedule();
        return scheduleLayout;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        /*选择了xnm的下拉列表*/
        if (parent == spinnerXnm)
        {

            xnm = Constant.ALL_XNM[position];

        } else if (parent == spinnerXqm)/*选择了xqm的下拉列表*/
        {
            xqm = Constant.ALL_XQM[position];

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

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

                Schedule schedule = new Schedule();
                Message message = new Message();

                if (1 != 0)
                {
                            /*登陆成功,读取信息*/
//                            totalInfo = login.getBasicInfo();
                    schedule.setSchedule(totalInfo, xnm, xqm);
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

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.cx)
        {
            getSchedule();
        }
    }
}
