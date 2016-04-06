package com.example.swuassistant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ALLFragmentControl.FragmentControl;
import com.example.Service.ClassAlarm;
import com.example.charge.ChargeFragment;
import com.example.find_lost.FindLostFragment;

import com.example.library.LibraryFragment;
import com.example.main_page.MainPageFragment;
import com.example.study_materials.StudyMaterialsFragment;
import com.example.swujw.TotalInfo;
import com.example.swujw.grade.GradesFragment;
import com.example.swujw.schedule.ScheduleFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener
{
    /*账户名*/
    private static String userName;
    /*密码*/
    private static String password;
    TextView nameTextView;
    TextView swuIDTextView;
    /*保存用户信息*/
    private static TotalInfo totalInfo = new TotalInfo();
//
//    /*刷新菜单按钮状态,初始化为不显示*/
//    private static int freshMenuStatus = Constant.DISSHOW;

    /*用户信息的本地储存文件*/
    public static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
//    /*主界面布局*/
//    private static MainPageFragment mainPageFragment;
//    /*课程表界面布局*/
//    private static ScheduleFragment scheduleFragment;
////    private static ScheduleTableFragment scheduleTableFragment;
//
//    /*成绩界面布局*/
//    private static GradesFragment gradesFragment;
//    /*学习资料界面布局*/
//    private static StudyMaterialsFragment studyMaterialsFragment;
//    /*图书馆界面布局*/
//    private static LibraryFragment libraryFragment;
//    /*水电费界面布局*/
//    private static ChargeFragment chargeFragment;
//    /*失物找寻界面布局*/
//    private static FindLostFragment findLostFragment;
    private FragmentControl fragmentControl;
    private static int fragmentPosition = R.id.nav_main;
    /*对fragmengt进行管理*/
    private FragmentManager fragmentManager;
    // 开启一个Fragment事务
    private static FragmentTransaction transaction;
    private Toolbar toolbar;
    private Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

 /*打开保存用户信息的文件*/
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userName = sharedPreferences.getString("userName", "");
        password = sharedPreferences.getString("password", "");
        editor.commit();
        fragmentManager = getSupportFragmentManager();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = navigationView.inflateHeaderView(R.layout.nav_header_main);
        /*侧边栏显示姓名学号*/
        nameTextView = (TextView) view.findViewById(R.id.name);
        swuIDTextView = (TextView) view.findViewById(R.id.swuid);
        /*显示退出按钮*/
        ImageButton imageButtonLoginOut = (ImageButton) view.findViewById(R.id.login_out);
        imageButtonLoginOut.setOnClickListener(MainActivity.this);
        if (userName.equals(""))
        {
            nameTextView.setOnClickListener(this);
            Toast.makeText(this, "您还没有登录呦", Toast.LENGTH_SHORT).show();
        } else
        {
            totalInfo.setName(sharedPreferences.getString("name", ""));
            totalInfo.setSwuID(sharedPreferences.getString("swuID", ""));
            /*对侧边栏的姓名和学号进行配置*/
            swuIDTextView.setText(totalInfo.getSwuID());
            nameTextView.setText(totalInfo.getName());
        }
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        fragmentControl=new FragmentControl(getSupportFragmentManager());
        fragmentControl.fragmentStateCheck(savedInstanceState, getSupportFragmentManager(), fragmentPosition);
        Log.d("Mainactivity", "OnCreatview");
        Intent statrtIntent = new Intent(this, ClassAlarm.class);
        startService(statrtIntent);
    }


    /*获得某个活动的回复信息*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case 1:
                if (resultCode == RESULT_OK)
                {
                    userName = data.getStringExtra("userName");
                    password = data.getStringExtra("password");
                    totalInfo.setName(data.getStringExtra("name"));
                    totalInfo.setSwuID(data.getStringExtra("swuID"));
                      /*对侧边栏的姓名和学号进行配置*/
                    swuIDTextView.setText(totalInfo.getSwuID());
                    nameTextView.setText(totalInfo.getName());
                    nameTextView.setClickable(false);
                }
                break;

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
            toolbar.setTitle(R.string.main_page_title);
            fragmentControl.fragmentSelection(id);
            fragmentPosition=id;
        } else if (id == R.id.nav_grades)
        {
            fragmentControl.fragmentSelection(id);
            toolbar.setTitle(R.string.grades_title);
            fragmentPosition=id;
        } else if (id == R.id.nav_schedule)
        {
            fragmentControl.fragmentSelection(id);
//            toolbar.setTitle(R.string.schedule_title);
            fragmentPosition=id;

        } else if (id == R.id.nav_study_materials)
        {
            fragmentControl.fragmentSelection(id);
            toolbar.setTitle(R.string.study_materials_title);
            fragmentPosition=id;
        } else if (id == R.id.nav_library)
        {
            fragmentControl.fragmentSelection(id);
            toolbar.setTitle(R.string.library_title);
            fragmentPosition=id;
        } else if (id == R.id.nav_charge)
        {
            fragmentControl.fragmentSelection(id);
            toolbar.setTitle(R.string.charge_title);
            fragmentPosition=id;
        } else if (id == R.id.nav_find_lost)
        {
            fragmentControl.fragmentSelection(id);
            toolbar.setTitle(R.string.find_lost_title);
            fragmentPosition=id;
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
    public void onClick(View v)
    {
        switch (v.getId())
        {
            /*点击退出按钮*/
            case R.id.login_out:

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
                        System.exit(0);
                    }
                });
            /*显示警告框*/
                dialogsQuit.show();
                break;
            case R.id.name:
//             开启登陆活动,并要求获得回复信息
                startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), 1);

        }
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
    protected void onResume()
    {
        super.onResume();
        Log.d("Mainactivity", "onResume");

    }

    @Override
    protected void onResumeFragments()
    {
        super.onResumeFragments();
        Log.d("Mainactivity", "onResumeFragments");

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d("Mainactivity", "onStart6");

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d("Mainactivity", "destory");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Log.d("Mainactivity", "onSaveInstanceState");

    }
    public String getUserName()
    {
        return userName;
    }

    public String getPassword()
    {
        return password;
    }

    public Toolbar getToolbar()
    {
        return toolbar;
    }


}
