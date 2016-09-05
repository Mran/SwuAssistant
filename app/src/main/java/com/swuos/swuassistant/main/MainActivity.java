package com.swuos.swuassistant.main;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.swuos.allfragment.FragmentControl;
import com.swuos.allfragment.library.libsearchs.search.SearchActity;
import com.swuos.allfragment.library.search.views.SearchAtyImp;
import com.swuos.allfragment.swujw.TotalInfos;
import com.swuos.swuassistant.BaseActivity;
import com.swuos.swuassistant.BaseApplication;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.R;
import com.swuos.swuassistant.about.AboutActivity;
import com.swuos.swuassistant.login.LoginActivity;
import com.swuos.swuassistant.main.presenter.IMainPresenter;
import com.swuos.swuassistant.main.presenter.IMainPresenterCompl;
import com.swuos.swuassistant.main.view.IMainview;
import com.swuos.swuassistant.setting.SettingActivity;
import com.swuos.util.SALog;
import com.swuos.util.tools.Tools;

import solid.ren.skinlibrary.loader.SkinManager;


public class MainActivity extends BaseActivity implements IMainview, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static TotalInfos totalInfo = TotalInfos.getInstance();
    private static int fragmentPosition = R.id.nav_wifi;
    TextView nameTextView;
    TextView swuIDTextView;
    IMainPresenter iMainPresenter;
    View headerView;
    NavigationView navigationView;
    private FragmentControl fragmentControl;
    private LocalBroadcastManager localBroadcastManager;
    private boolean isFragmentLibSelected = false;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        iMainPresenter = new IMainPresenterCompl(this, this);
        iMainPresenter.initData(totalInfo);
        iMainPresenter.startServier();
        initView();
        initChangeskin();
        fragmentControl = new FragmentControl(getSupportFragmentManager());
        fragmentControl.fragmentStateCheck(savedInstanceState, getSupportFragmentManager(), fragmentPosition);
        SALog.d("Mainactivity", "OnCreatview");
        SALog.d("Mainactivity", Tools.getSystemProperty("ro.miui.ui.version.name"));
        iMainPresenter.startUpdata();

    }

    /*获得某个活动的回复信息*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.LOGIN_RESULT_CODE:
                if (resultCode == Constant.LOGIN_RESULT_CODE) {
                    setNavigationViewHeader(totalInfo);
                    localBroadcastManager = LocalBroadcastManager.getInstance(this);
                    Intent intent = new Intent("com.swuos.Logined");
                    localBroadcastManager.sendBroadcast(intent);
                }
                break;
        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string
                .navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);

        /*显示退出按钮*/
        ImageButton imageButtonLoginOut = (ImageButton) headerView.findViewById(R.id.logout);
        imageButtonLoginOut.setOnClickListener(MainActivity.this);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        /*侧边栏显示姓名学号*/
        nameTextView = (TextView) headerView.findViewById(R.id.name);
        swuIDTextView = (TextView) headerView.findViewById(R.id.swuid);
        setNavigationViewHeader(totalInfo);
    }

    private void initChangeskin() {
        dynamicAddView(toolbar, "background", R.color.colorPrimary);
        dynamicAddView(headerView, "background", R.color.colorPrimary);
        dynamicAddView(navigationView, "navigationViewMenu", R.color.colorPrimary);
    }

    @Override
    public void setNavigationViewHeader(TotalInfos totalInfo) {
        if (totalInfo.getUserName().equals("")) {
            nameTextView.setOnClickListener(this);
            Toast.makeText(this, R.string.not_logged_in, Toast.LENGTH_SHORT).show();
        } else {
            /*对侧边栏的姓名和学号进行配置*/
            swuIDTextView.setText(totalInfo.getSwuID());
            nameTextView.setText(totalInfo.getName());
            nameTextView.setClickable(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isFragmentLibSelected) {
            menu.findItem(R.id.search).setVisible(true);
        } else {
            menu.findItem(R.id.search).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
            case R.menu.main:
                SALog.d("MainActivity", "click_main");
                break;
            case R.id.search:
//                startActivity(new Intent(this, SearchAtyImp.class));
                startActivity(new Intent(this, SearchActity.class));
                break;
        }

        return true;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_grades:
                fragmentControl.fragmentSelection(id);
                toolbar.setTitle(R.string.grades_title);
                fragmentPosition = id;
                isFragmentLibSelected = false;
                break;
            case R.id.nav_schedule:
                fragmentControl.fragmentSelection(id);
                toolbar.setTitle(R.string.schedule_title);
                fragmentPosition = id;
                isFragmentLibSelected = false;
                break;
            case R.id.nav_library:
                fragmentControl.fragmentSelection(id);
                toolbar.setTitle(R.string.library_title);
                fragmentPosition = id;
                isFragmentLibSelected = true;
                break;
            case R.id.nav_ecard:
                fragmentControl.fragmentSelection(id);
                toolbar.setTitle(R.string.ecard_title);
                fragmentPosition = id;
                isFragmentLibSelected = false;
                break;
            case R.id.nav_wifi:
                fragmentControl.fragmentSelection(id);
                toolbar.setTitle(R.string.wifi);
                fragmentPosition = id;
                isFragmentLibSelected = false;
                break;
            case R.id.about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.about_activity_in, 0);
                isFragmentLibSelected = false;
                return true;
            case R.id.change_skin:
                pickSkin();
                break;
            default:
                break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        invalidateOptionsMenu();
        return true;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void pickSkin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View pickSkinView = LayoutInflater.from(this).inflate(R.layout.pick_skin_layout, null);
        Button blueButton = (Button) pickSkinView.findViewById(R.id.button_blue);
        Button greenButton = (Button) pickSkinView.findViewById(R.id.button_green);
        Button blackButton = (Button) pickSkinView.findViewById(R.id.button_black);
        Button yellowButton = (Button) pickSkinView.findViewById(R.id.button_yellow);
        Button cyanButton = (Button) pickSkinView.findViewById(R.id.button_cyan);
        blueButton.setOnClickListener(this);
        greenButton.setOnClickListener(this);
        blackButton.setOnClickListener(this);
        yellowButton.setOnClickListener(this);
        cyanButton.setOnClickListener(this);

        builder.setView(pickSkinView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*点击退出按钮*/
            case R.id.logout:
                showQuitDialog();
                break;
            case R.id.name:
                //开启登陆活动,并要求获得回复信息
                startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), Constant.LOGIN_RESULT_CODE);
                break;
            case R.id.button_blue:
                SkinManager.getInstance().restoreDefaultTheme();
                break;
            case R.id.button_black:
                SkinManager.getInstance().loadSkin("black.skin", null);
                break;
            case R.id.button_yellow:
                SkinManager.getInstance().loadSkin("yellow.skin", null);
                break;
            case R.id.button_cyan:
                SkinManager.getInstance().loadSkin("cyan.skin", null);
                break;
            case R.id.button_green:
                SkinManager.getInstance().loadSkin("green.skin", null);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }


    @Override
    public void showQuitDialog() {
        /*退出确认框*/
        final AlertDialog.Builder dialogsQuit;
        dialogsQuit = new AlertDialog.Builder(this);

        dialogsQuit.setMessage("确认退出");
        dialogsQuit.setNegativeButton(
                "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        dialogsQuit.setPositiveButton(
                "确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    /*确认退出,清除保存的用户信息,并退出应用*/
                        iMainPresenter.cleanData();
                        System.exit(0);
                    }
                });
            /*显示警告框*/
        dialogsQuit.show();
    }

    @Override
    public void showUpdata(String changelog, final String url) {
        final AlertDialog.Builder dialogsQuit;
        dialogsQuit = new AlertDialog.Builder(this);
        dialogsQuit.setTitle("发现新版本");
        dialogsQuit.setMessage(changelog);
        dialogsQuit.setCancelable(false);
        dialogsQuit.setPositiveButton("马上更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse(url));
                BaseApplication.getContext().startActivity(intent);
            }
        });
        dialogsQuit.show();
    }
}
