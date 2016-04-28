package com.swuos.ALLFragment.swujw.grade;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.swuos.ALLFragment.swujw.Login;
import com.swuos.ALLFragment.swujw.TotalInfo;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张孟尧 on 2016/2/29.
 */
public class GradesFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener

{
    /*保存成绩的列表,用于listview*/
    private static List<GradeItem> gradeItemList = new ArrayList<>();
    /*listview*/
    private static ListView listView;
    /*账户名*/
    private static String userName;
    /*密码*/
    private static String password;

    /*等待窗口*/
    private static ProgressDialog progressDialogLoading;


    /*保存用户信息*/
    private static TotalInfo totalInfo = new TotalInfo();
    /*listview的适配器*/
    private static GradesAdapter adapter = null;
    /*选择学年的下拉列表*/
    private static Spinner spinnerXnm;
    /*选择学期的下拉列表*/
    private static Spinner spinnerXqm;
    /*用户当前选择的学期和学年*/
    private static String xnm;
    private static String xqm;
    private static Button buttonGradesInquire;
    View gradesLayout;
    private Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                /*成功获取成绩*/
                case Constant.GRADES_OK:
                    /*关闭登陆窗口*/
                    progressDialogLoading.cancel();

                    if (adapter == null)
                    {
                        /*设置listview适配器*/
                        adapter = new GradesAdapter(gradesLayout.getContext(), R.layout.grades_item, gradeItemList);
                        listView.setAdapter(adapter);
                    } else
                    {/*如果已经设置过就更新*/
                        adapter.clear();
                        adapter.addAll(gradeItemList);
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
        gradesLayout = inflater.inflate(R.layout.grades_layout, container, false);
        listView = (ListView) gradesLayout.findViewById(R.id.grades_list);
        spinnerXnm = (Spinner) gradesLayout.findViewById(R.id.xnm);
        spinnerXqm = (Spinner) gradesLayout.findViewById(R.id.xqm);
         /*设置下拉列表的选择监听*/
        spinnerXnm.setOnItemSelectedListener(this);
        spinnerXqm.setOnItemSelectedListener(this);
        /*学年下拉列表的默认值*/
        spinnerXnm.setSelection(3, true);
        /*学期下拉列表的默认值*/
        spinnerXqm.setSelection(1, true);
        progressDialogLoading = new ProgressDialog(gradesLayout.getContext());

//        MainActivity mainActivity = (MainActivity) getActivity();
        userName = totalInfo.getUserName();
        password = totalInfo.getPassword();
        buttonGradesInquire = (Button) gradesLayout.findViewById(R.id.grade_inquire);
        buttonGradesInquire.setOnClickListener(this);
        return gradesLayout;
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

    private void getGrades()
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
                ;
                Message message = new Message();

                if (login.doLogin(userName, password).contains("LoginSuccessed"))
                {
                    Grades grades = new Grades(login.okhttpNet);
                    grades.setGrades(totalInfo, xnm, xqm);
                    gradeItemList = grades.getGradesList(totalInfo);
                    message.what = Constant.GRADES_OK;
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
        if (v.getId() == R.id.grade_inquire)
        {
            getGrades();
        }
    }
}
