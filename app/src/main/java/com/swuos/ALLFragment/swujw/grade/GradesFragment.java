package com.swuos.ALLFragment.swujw.grade;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.swuos.ALLFragment.swujw.grade.model.GradeItem;
import com.swuos.ALLFragment.swujw.grade.persenter.GradePresenterCompl;
import com.swuos.ALLFragment.swujw.grade.persenter.IGradePersenter;
import com.swuos.ALLFragment.swujw.grade.view.GradesAdapter;
import com.swuos.ALLFragment.swujw.grade.view.IGradeview;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.R;
import com.twotoasters.jazzylistview.JazzyListView;
import com.twotoasters.jazzylistview.effects.CardsEffect;

import java.util.List;

/**
 * Created by 张孟尧 on 2016/2/29.
 */
public class GradesFragment extends Fragment implements IGradeview, AdapterView.OnItemSelectedListener, View.OnClickListener, AdapterView.OnItemClickListener {

    private static JazzyListView listView;
    /*等待窗口*/
    private static ProgressDialog progressDialogLoading;
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
    IGradePersenter iGradePersenter;
    private IntentFilter intentFilter;
    private LocalRecevier localRecevier;
    private LocalBroadcastManager localBroadcastManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        gradesLayout = inflater.inflate(R.layout.grades_layout, container, false);
        iGradePersenter = new GradePresenterCompl(getContext(), this);
        initView();
        return gradesLayout;
    }

    private void initView() {
        listView = (JazzyListView) gradesLayout.findViewById(R.id.grades_list);
        listView.setTransitionEffect(new CardsEffect());
        spinnerXnm = (Spinner) gradesLayout.findViewById(R.id.xnm);
        spinnerXqm = (Spinner) gradesLayout.findViewById(R.id.xqm);
        ArrayAdapter<CharSequence> arrayAdapterxnm = ArrayAdapter.createFromResource(getActivity(), R.array.xnm, R.layout.grades_spinner_layout);
        arrayAdapterxnm.setDropDownViewResource(R.layout.grades_spinnerdown_layout);
        spinnerXnm.setAdapter(arrayAdapterxnm);

        ArrayAdapter<CharSequence> arrayAdapterxqm = ArrayAdapter.createFromResource(getActivity(), R.array.xqm, R.layout.grades_spinner_layout);
        arrayAdapterxqm.setDropDownViewResource(R.layout.grades_spinnerdown_layout);
        spinnerXqm.setAdapter(arrayAdapterxqm);

         /*设置下拉列表的选择监听*/
        spinnerXnm.setOnItemSelectedListener(this);
        spinnerXqm.setOnItemSelectedListener(this);
        /*学年下拉列表的默认值*/
        spinnerXnm.setSelection(3, true);
        /*学期下拉列表的默认值*/
        spinnerXqm.setSelection(1, true);
        progressDialogLoading = new ProgressDialog(gradesLayout.getContext());

        buttonGradesInquire = (Button) gradesLayout.findViewById(R.id.grade_inquire);
        buttonGradesInquire.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*选择了xnm的下拉列表*/
        if (parent == spinnerXnm) {
            xnm = Constant.ALL_XNM[position];
        } else if (parent == spinnerXqm)/*选择了xqm的下拉列表*/ {
            xqm = Constant.ALL_XQM[position];
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.grade_inquire) {
            if (iGradePersenter.getUsername() != null && !iGradePersenter.getUsername().equals(""))
                iGradePersenter.getGrades(iGradePersenter.getUsername(), iGradePersenter.getPassword(), xqm, xnm);
            else
                Toast.makeText(getActivity(), R.string.not_logged_in, Toast.LENGTH_SHORT).show();
        }
    }

    private void setReceiver() {
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.swuos.Logined");
        localRecevier = new LocalRecevier();
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.registerReceiver(localRecevier, intentFilter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //        Toast.makeText(getActivity(), (CharSequence) gradeItemList.get(position).getKcmc(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showDialog(Boolean isShow) {
        if (isShow) {
            /* &#x8bbe;&#x7f6e;&#x7b49;&#x5f85;&#x7a97;&#x53e3;&#x6587;&#x5b57; */
            progressDialogLoading.setMessage("正在查询请稍后");
            /*设置不可取消*/
            progressDialogLoading.setCancelable(false);
            /*显示等待窗口*/
            progressDialogLoading.show();
        } else {
            progressDialogLoading.cancel();
        }
    }

    @Override
    public void showResult(List<GradeItem> gradeItemList) {
        if (adapter == null) {
            /*设置listview适配器*/
            adapter = new GradesAdapter(gradesLayout.getContext(), R.layout.grades_item, gradeItemList);
            listView.setAdapter(adapter);
        } else {/*如果已经设置过就更新*/
            adapter.clear();
            adapter.addAll(gradeItemList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showError(String error) {
        Snackbar.make(gradesLayout, error, Snackbar.LENGTH_SHORT);
    }
}


/*设置广播接收刷新消息*/
class LocalRecevier extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


    }
}

