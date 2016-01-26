package com.example.swuassistant;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import com.example.swujw.grade.GradeItem;
import com.example.swujw.grade.Grades;
import com.example.swujw.grade.GradesAdapter;
import com.example.swujw.Login;
import com.example.swujw.TotalInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener, View.OnClickListener
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

    private static TableLayout showGradesLayout;
    /*登陆*/
    private static Login login = new Login();
    private static Grades grades = new Grades();
    /*保存用户信息*/
    private static TotalInfo totalInfo = new TotalInfo();
    /*listview的适配器*/
    private static GradesAdapter adapter = null;
    /*刷新菜单按钮状态,初始化为不显示*/
    private static int freshMenuStatus = Constant.DISSHOW;
    /*选择学年的下拉列表*/
    private static Spinner spinnerXnm;
    /*选择学期的下拉列表*/
    private static Spinner spinnerXqm;
    /*用户当前选择的学期和学年*/
    private static String xnm;
    private static String xqm;
    /*用户信息的本地储存文件*/
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;


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
        spinnerXnm = (Spinner) findViewById(R.id.xnm);
        spinnerXqm = (Spinner) findViewById(R.id.xqm);
        /*打开保存用户信息的文件*/
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        progressDialogLoading = new ProgressDialog(MainActivity.this);
        showGradesLayout = (TableLayout) findViewById(R.id.show_gaades_layout);
        showGradesLayout.setVisibility(View.INVISIBLE);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = navigationView.inflateHeaderView(R.layout.nav_header_main);
        /*侧边栏显示姓名学号*/
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView swuIDTextView = (TextView) view.findViewById(R.id.swuid);
        /*显示退出按钮*/
        ImageButton imageButtonLoginOut = (ImageButton) view.findViewById(R.id.login_out);
        imageButtonLoginOut.setOnClickListener(MainActivity.this);
        /*对侧边栏的姓名和学号进行配置*/
        swuIDTextView.setText(totalInfo.getSwuID());
        nameTextView.setText(totalInfo.getName());

        navigationView.setNavigationItemSelectedListener(this);
        /*设置下拉列表的选择监听*/
        spinnerXnm.setOnItemSelectedListener(MainActivity.this);
        spinnerXqm.setOnItemSelectedListener(MainActivity.this);
        /*学年下拉列表的默认值*/
        spinnerXnm.setSelection(3, true);
        /*学期下拉列表的默认值*/
        spinnerXqm.setSelection(1, true);


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

            progressDialogLoading.setMessage("正在查询请稍后");
            progressDialogLoading.setCancelable(false);
            progressDialogLoading.show();
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {

                    String response = login.doLogin(userName, password);
                    Message message = new Message();
                    if (response.contains("Successed"))
                    {
                        totalInfo = login.getBasicInfo();
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
            /*设置刷新按钮可见*/
            freshMenuStatus = Constant.SHOW;
            getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
            invalidateOptionsMenu();
            /*如果是第一次获得成绩,即列表为空*/
            if (gradeItemList.size() == 0)
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

                        String response = login.doLogin(userName, password);
                        Message message = new Message();

                        if (response.contains("Successed"))
                        {
                            /*登陆成功,读取信息*/
//                            totalInfo = login.getBasicInfo();
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
             /*等待窗口*/
            final AlertDialog.Builder dialogsLoading;
            dialogsLoading = new AlertDialog.Builder(MainActivity.this);
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
        /*阻止活动被销毁*/
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
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

    @Override
    public void onClick(View v)
    {
        /*点击退出按钮*/
        if (v.getId() == R.id.login_out)
        {
            /*退出确认框*/
            final AlertDialog.Builder dialogsQuit;
            dialogsQuit = new AlertDialog.Builder(MainActivity.this);

            dialogsQuit.setMessage("确认退出");
            dialogsQuit.setNegativeButton("取消", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                }
            });
            dialogsQuit.setPositiveButton("确认", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    /*确认退出,清除保存的用户信息,并退出应用*/
                    editor.clear();
                    editor.commit();
                    finish();
                }
            });
            /*显示警告框*/
            dialogsQuit.show();

        }
    }
}
