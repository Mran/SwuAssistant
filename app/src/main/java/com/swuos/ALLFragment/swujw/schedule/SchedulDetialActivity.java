package com.swuos.ALLFragment.swujw.schedule;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.swuos.swuassistant.R;
import com.swuos.util.SALog;

/**
 * Created by 张孟尧 on 2016/5/19.
 */
public class SchedulDetialActivity extends AppCompatActivity {
    private TextView classTitleTextview;
    private TextView classTimeTextview;
    private TextView classTeacherTextview;
    private TextView classLocationTextview;
    private String classTitle;
    private String classTime;
    private String classTeacher;
    private String classLocation;
    private int colors;
    private static View mStatusBarView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.schedule_detial_layout);

        initdate();
        /*设置toolbar*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(colors);
        setSupportActionBar(toolbar);
        this.setTitle("");
        Drawable d = getResources().getDrawable(android.support.design.R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationIcon(d);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(colors);
        }
        initview();


    }

    private void initdate() {
        Intent intent = getIntent();
        classTitle = intent.getStringExtra("title");
        classTime = intent.getStringExtra("jc");
        classTeacher = intent.getStringExtra("xm");
        classLocation = intent.getStringExtra("cdmc");
        colors = intent.getIntExtra("color", R.color.colorPrimary);

    }

    private void initview() {
        classTitleTextview = (TextView) findViewById(R.id.schedule_detail_class_title);
        classTimeTextview = (TextView) findViewById(R.id.schedule_detail_time);
        classTeacherTextview = (TextView) findViewById(R.id.schedule_detail_teacher);
        classLocationTextview = (TextView) findViewById(R.id.schedule_detail_location);
        classTitleTextview.setText(classTitle);
        classTimeTextview.setText(classTime);
        classTeacherTextview.setText(classTeacher);
        classLocationTextview.setText(classLocation);
        classTitleTextview.setBackgroundColor(colors);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SALog.d("settingActivity", "onOptionsItemSelected");
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
