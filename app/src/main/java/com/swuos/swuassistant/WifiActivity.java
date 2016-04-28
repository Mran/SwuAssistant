package com.swuos.swuassistant;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

/**
 * Created by 张孟尧 on 2016/4/27.
 */
public class WifiActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifis_layout);
        /*设置toolbar*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle(R.string.wifi);
        toolbar.setTitleTextColor(Color.WHITE);
        Drawable d = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationIcon(d);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("settingActivity", "onOptionsItemSelected");
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /*阻止活动被销毁*/
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
