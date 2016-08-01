package com.swuos.ALLFragment.swujw.grade;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.swuos.ALLFragment.swujw.grade.model.GradeItem;
import com.swuos.ALLFragment.swujw.grade.persenter.GradePresenterCompl;
import com.swuos.ALLFragment.swujw.grade.persenter.IGradePersenter;
import com.swuos.ALLFragment.swujw.grade.view.GradeDetaiAdapter;
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
public class GradesFragment extends Fragment implements IGradeview, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static JazzyListView listView;
    /*listview的适配器*/
    private static GradesAdapter adapter = null;
    /*选择学年的下拉列表*/
    private static Spinner spinnerXnm;
    /*选择学期的下拉列表*/
    private static Spinner spinnerXqm;
    private static SwipeRefreshLayout swipeRefreshLayout;


    View gradesLayout;
    IGradePersenter iGradePersenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        gradesLayout = inflater.inflate(R.layout.grades_layout, container, false);
        iGradePersenter = new GradePresenterCompl(getContext(), this);
        iGradePersenter.initData();
        initView();
        return gradesLayout;
    }


    private void initView() {
        listView = (JazzyListView) gradesLayout.findViewById(R.id.grades_list);
        listView.setTransitionEffect(new CardsEffect());
        spinnerXnm = (Spinner) gradesLayout.findViewById(R.id.xnm);
        spinnerXqm = (Spinner) gradesLayout.findViewById(R.id.xqm);
        swipeRefreshLayout = (SwipeRefreshLayout) gradesLayout.findViewById(R.id.grade_swiperefresh);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R
                .color.holo_red_light, android.R.color.holo_orange_light, android.R.color
                .holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
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
        spinnerXnm.setSelection(iGradePersenter.getLastxnmPosition(), true);
        /*学期下拉列表的默认值*/
        spinnerXqm.setSelection(iGradePersenter.getLastxqmPosition(), true);

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*选择了xnm的下拉列表*/
        if (parent == spinnerXnm) {
            iGradePersenter.setXnm(Constant.ALL_XNM[position]);
            iGradePersenter.setXnmPosition(position);
        } else if (parent == spinnerXqm)/*选择了xqm的下拉列表*/ {
            iGradePersenter.setXqm(Constant.ALL_XQM[position]);
            iGradePersenter.setXqmPosition(position);
        }
        if (iGradePersenter.getUsername() != null && !iGradePersenter.getUsername().equals("")) {
            if (iGradePersenter.getXqm()!=null&&iGradePersenter.getXnm()!= null) {
                iGradePersenter.saveUserLastCLick(iGradePersenter.getXnmPosition(), iGradePersenter.getXqmPosition());
                iGradePersenter.getGrades(iGradePersenter.getUsername(), iGradePersenter.getPassword(), iGradePersenter.getXqm(), iGradePersenter.getXnm(), false);
            }
        } else
            Toast.makeText(getActivity(), R.string.not_logged_in, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position < adapter.getCount() - 2) {
            iGradePersenter.getGradeDetial(iGradePersenter.getUsername(), iGradePersenter.getPassword(), iGradePersenter.getXqm(), iGradePersenter.getXnm(), position);
        }
    }

    @Override
    public void showDialog(Boolean isShow) {
        if (isShow) {
            swipeRefreshLayout.setRefreshing(true);
        } else {

            swipeRefreshLayout.setRefreshing(false);
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
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showGradeDetial(GradeItem gradeItem) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.grade_detail_layout, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        ListView gradeDetailListview = (ListView) view.findViewById(R.id.grade_detail_list);
        GradeDetaiAdapter gradeDetaiAdapter = new GradeDetaiAdapter(gradesLayout.getContext(), R.layout.grade_detail_item, gradeItem.getDetial());
        gradeDetailListview.setAdapter(gradeDetaiAdapter);
        alertDialog.setTitle(gradeItem.getKcmc());
        alertDialog.setView(view);
        AlertDialog adl = alertDialog.create();
        adl.show();
    }

    @Override
    public void onRefresh() {
        if (iGradePersenter.getUsername() != null && !iGradePersenter.getUsername().equals("")) {
            iGradePersenter.saveUserLastCLick(iGradePersenter.getXnmPosition(), iGradePersenter.getXqmPosition());
            iGradePersenter.getGrades(iGradePersenter.getUsername(), iGradePersenter.getPassword(), iGradePersenter.getXqm(), iGradePersenter.getXnm(), true);
        } else
            Toast.makeText(getActivity(), R.string.not_logged_in, Toast.LENGTH_SHORT).show();
    }
}



