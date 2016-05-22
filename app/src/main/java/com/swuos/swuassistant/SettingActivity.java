package com.swuos.swuassistant;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.swuos.ALLFragment.setting.SettingFragment;
import com.swuos.util.SALog;

/**
 * Created by 张孟尧 on 2016/4/8.
 */
public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明导航栏
        //        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.setting_layout);
        /*设置toolbar*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle(R.string.action_settings);
        toolbar.setTitleTextColor(Color.WHITE);
        Drawable d = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationIcon(d);
        /*打开preferenceFragment*/
        SettingFragment settingFragment=new SettingFragment();
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.setting_fragment,settingFragment, getString(R.string.action_settings));
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SALog.d("settingActivity", "onOptionsItemSelected");
       onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
