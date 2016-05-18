package com.swuos.swuassistant;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 张孟尧 on 2016/5/17.
 */
public class AboutActivity extends AppCompatActivity {
    private View view;
    private TextView zmy;
    private TextView yk;
    private TextView csd;
    private TextView swuos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);
        view = (View) findViewById(R.id.wifi_contain);
        /*设置toolbar*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("关于我们");
        toolbar.setTitleTextColor(Color.WHITE);
        Drawable d = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationIcon(d);
        initview();
    }

    private void initview() {
        zmy = (TextView) findViewById(R.id.developer_zmy);
        zmy.setMovementMethod(LinkMovementMethod.getInstance());
        yk = (TextView) findViewById(R.id.developer_yk);
        yk.setMovementMethod(LinkMovementMethod.getInstance());
        csd = (TextView) findViewById(R.id.developer_csd);
        csd.setMovementMethod(LinkMovementMethod.getInstance());
        swuos = (TextView) findViewById(R.id.swuos);
        swuos.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("settingActivity", "onOptionsItemSelected");
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.about_actity_out, 0);
    }
}
