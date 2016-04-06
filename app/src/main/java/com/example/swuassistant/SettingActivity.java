package com.example.swuassistant;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.WindowManager;
import android.widget.Toolbar;

/**
 * Created by 张孟尧 on 2016/4/7.
 */
public class SettingActivity extends PreferenceActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.setting_toolbar);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            toolbar.setTitle("设置");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            toolbar.setTitleTextColor(Color.WHITE);
        }
        Drawable d=getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            toolbar.setNavigationIcon(d);
        }
        addPreferencesFromResource(R.xml.setting);
    }
}
