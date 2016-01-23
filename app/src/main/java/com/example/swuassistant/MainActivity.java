package com.example.swuassistant;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.os.Handler;

import com.example.swujw.grade.GradeItem;
import com.example.swujw.grade.Grades;
import com.example.swujw.grade.GradesAdapter;
import com.example.swujw.Login;
import com.example.swujw.TotalInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
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
    /*等待窗口*/
    private static AlertDialog.Builder dialogsLoading;
    private static TableLayout showGradesLayout;
    /*登陆*/
    private static Login login = new Login();
    private static Grades grades = new Grades();
    private static TotalInfo totalInfo = new TotalInfo();
    /*listview的适配器*/
    private static GradesAdapter adapter = null;
    /*刷新菜单按钮状态,初始化为不显示*/
    private static int freshMenuStatus = Constant.DISSHOW;

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
                    /*显示成绩的布局*/
                    showGradesLayout.setVisibility(View.VISIBLE);
                    if (adapter == null)
                    {
                        /*设置listview适配器*/
                        adapter = new GradesAdapter(MainActivity.this, R.layout.grades_item, gradeItemList);
                        listView.setAdapter(adapter);
                    } else
                    /*如果已经设置过就更新*/
                        adapter.notifyDataSetChanged();
                    break;
                case Constant.UPDATA:


                    break;
                case Constant.MAIN:
                    /*处在主页面的时候隐藏成绩布局*/
                    showGradesLayout.setVisibility(View.GONE);
//                    freshMenuItem.setVisible(false);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        password = intent.getStringExtra("password");
        totalInfo.setName(intent.getStringExtra("name"));
        totalInfo.setSwuID(intent.getStringExtra("swuID"));
        listView = (ListView) findViewById(R.id.grades_list);
        progressDialogLoading = new ProgressDialog(MainActivity.this);
        dialogsLoading = new AlertDialog.Builder(MainActivity.this);
        showGradesLayout = (TableLayout) findViewById(R.id.show_gaades_layout);
        showGradesLayout.setVisibility(View.INVISIBLE);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView swuIDTextView = (TextView) view.findViewById(R.id.swuid);
        swuIDTextView.setText(totalInfo.getSwuID());
        nameTextView.setText(totalInfo.getName());
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(R.id.fresh);
        if (freshMenuStatus != Constant.SHOW)
        {
            menuItem.setVisible(false);

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        } else if (id == R.id.fresh)
        {
            progressDialogLoading.setTitle("查成绩");
            progressDialogLoading.setMessage("正在查询请稍后");
            progressDialogLoading.setCancelable(false);
            progressDialogLoading.show();
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {

                    login.doLogin(userName, password);
                    grades.setGrades(totalInfo);
                    gradeItemList = grades.getGradesList(totalInfo);
                    Message message = new Message();
                    message.what = Constant.GRADES_OK;
                    handler.sendMessage(message);
                }
            }).start();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_main)
        {
            freshMenuStatus = Constant.DISSHOW;
            getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
            invalidateOptionsMenu();
            Message message = new Message();
            message.what = Constant.MAIN;
            handler.sendMessage(message);
        } else if (id == R.id.nav_grades)
        {
            freshMenuStatus = Constant.SHOW;
            getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
            invalidateOptionsMenu();
//            onPrepareOptionsMenu();
            if (gradeItemList.size() == 0)
            {

                progressDialogLoading.setTitle("查成绩");
                progressDialogLoading.setMessage("正在查询请稍后");
                progressDialogLoading.setCancelable(false);
                progressDialogLoading.show();
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {

                        login.doLogin(userName, password);
                        totalInfo=login.getBasicInfo();
                        grades.setGrades(totalInfo);
                        gradeItemList = grades.getGradesList(totalInfo);
                        Message message = new Message();
                        message.what = Constant.GRADES_OK;
                        handler.sendMessage(message);
                    }
                }).start();
            } else
            {
                Message message = new Message();
                message.what = Constant.GRADES_OK;
                handler.sendMessage(message);
            }
        } else if (id == R.id.nav_class_table)
        {
            freshMenuStatus = Constant.DISSHOW;
            getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
            invalidateOptionsMenu();
            showGradesLayout.setVisibility(View.INVISIBLE);
            showGradesLayout.clearAnimation();
            dialogsLoading.setMessage("放假了查什么课表嘛");
            dialogsLoading.setCancelable(true);
            dialogsLoading.show();


//            listView.setVisibility(View.GONE);
        } else if (id == R.id.nav_share)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
