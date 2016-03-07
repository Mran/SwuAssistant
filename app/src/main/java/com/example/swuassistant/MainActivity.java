package com.example.swuassistant;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.Handler;

import com.example.charge.ChargeFragment;
import com.example.find_lost.FindLostFragment;
import com.example.library.LibraryFragrment;
import com.example.main.MainPageFragment;
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

    /*保存用户信息*/
    private static TotalInfo totalInfo = new TotalInfo();

    /*刷新菜单按钮状态,初始化为不显示*/
    private static int freshMenuStatus = Constant.DISSHOW;

    /*用户信息的本地储存文件*/
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    /*主界面布局*/
    private static MainPageFragment mainPageFragment;
    /*课程表界面布局*/
    private static ScheduleFragment scheduleFragment;
    /*成绩界面布局*/
    private static GradesFragment gradesFragment;
    /*学习资料界面布局*/
    private static StudyMaterialsFragment studyMaterialsFragment;
    /*图书馆界面布局*/
    private static LibraryFragrment libraryFragrment;
    /*水电费界面布局*/
    private static ChargeFragment chargeFragment;
    /*失物找寻界面布局*/
    private static FindLostFragment findLostFragment;
    /*对fragmengt进行管理*/
    private FragmentManager fragmentManager;
    private Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {

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

        /*打开保存用户信息的文件*/
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        fragmentManager = getFragmentManager();

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
        fragmentSelection(R.id.nav_main);

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
            fragmentSelection(id);
        } else if (id == R.id.nav_grades)
        {
            fragmentSelection(id);
        } else if (id == R.id.nav_schedule)
        {
            fragmentSelection(id);
        } else if (id == R.id.nav_study_materials)
        {
            fragmentSelection(id);
        } else if (id == R.id.nav_library)
        {
            fragmentSelection(id);
        } else if (id == R.id.nav_charge)
        {
            fragmentSelection(id);
        } else if (id == R.id.nav_find_lost)
        {
            fragmentSelection(id);
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

    private void fragmentSelection(int id)
    {
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (id)
        {
            case R.id.nav_main:
                /*设置刷新按钮不可见*/
                freshMenuStatus = Constant.DISSHOW;
                getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
                invalidateOptionsMenu();

                if (mainPageFragment == null)
                {
                    // 如果mainPageFragment为空，则创建一个并添加到界面上
                    mainPageFragment = new MainPageFragment();
                    transaction.add(R.id.content, mainPageFragment);
                } else
                {
                    // 如果mainPageFragment不为空，则直接将它显示出来
                    transaction.show(mainPageFragment);
                }
                break;
            case R.id.nav_grades:
                /*设置刷新按钮可见*/
                freshMenuStatus = Constant.SHOW;
                getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
                invalidateOptionsMenu();

                if (gradesFragment == null)
                {
                    // 如果GradesFragment为空，则创建一个并添加到界面上
                    gradesFragment = new GradesFragment();
                    transaction.add(R.id.content, gradesFragment);
                } else
                {
                    // 如果GradesFragment不为空，则直接将它显示出来
                    transaction.show(gradesFragment);
                }

                break;
            case R.id.nav_schedule:
                /*设置刷新按钮可见*/
                freshMenuStatus = Constant.SHOW;
                getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
                invalidateOptionsMenu();

                if (scheduleFragment == null)
                {
                    // 如果scheduleFragment为空，则创建一个并添加到界面上
                    scheduleFragment = new ScheduleFragment();
                    transaction.add(R.id.content, scheduleFragment);
                } else
                {
                    // 如果scheduleFragment不为空，则直接将它显示出来
                    transaction.show(scheduleFragment);
                }
                break;
            case R.id.nav_charge:
                /*设置刷新按钮可见*/
                freshMenuStatus = Constant.SHOW;
                getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
                invalidateOptionsMenu();

                if (chargeFragment == null)
                {
                    // 如果chargeFragment为空，则创建一个并添加到界面上
                    chargeFragment = new ChargeFragment();
                    transaction.add(R.id.content, chargeFragment);
                } else
                {
                    // 如果chargeFragment不为空，则直接将它显示出来
                    transaction.show(chargeFragment);
                }
                break;
            case R.id.nav_study_materials:
                /*设置刷新按钮可见*/
                freshMenuStatus = Constant.SHOW;
                getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
                invalidateOptionsMenu();

                if (studyMaterialsFragment == null)
                {
                    // 如果studyMaterialsFragment为空，则创建一个并添加到界面上
                    studyMaterialsFragment = new StudyMaterialsFragment();
                    transaction.add(R.id.content, studyMaterialsFragment);
                } else
                {
                    // 如果studyMaterialsFragment不为空，则直接将它显示出来
                    transaction.show(studyMaterialsFragment);
                }
                break;
            case R.id.nav_library:
                /*设置刷新按钮可见*/
                freshMenuStatus = Constant.SHOW;
                getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
                invalidateOptionsMenu();

                if (libraryFragrment == null)
                {
                    // 如果libraryFragrment为空，则创建一个并添加到界面上
                    libraryFragrment = new LibraryFragrment();
                    transaction.add(R.id.content, libraryFragrment);
                } else
                {
                    // 如果libraryFragrment不为空，则直接将它显示出来
                    transaction.show(libraryFragrment);
                }
                break;
            case R.id.nav_find_lost:
                /*设置刷新按钮可见*/
                freshMenuStatus = Constant.SHOW;
                getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
                invalidateOptionsMenu();

                if (findLostFragment == null)
                {
                    // 如果findLostFragment为空，则创建一个并添加到界面上
                    findLostFragment = new FindLostFragment();
                    transaction.add(R.id.content, findLostFragment);
                } else
                {
                    // 如果findLostFragment不为空，则直接将它显示出来
                    transaction.show(findLostFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction fragmentTransaction)
    {
        if (mainPageFragment != null)
        {
            fragmentTransaction.hide(mainPageFragment);
        }
        if (gradesFragment != null)
        {
            fragmentTransaction.hide(gradesFragment);
        }
        if (scheduleFragment != null)
        {
            fragmentTransaction.hide(scheduleFragment);
        }
        if (studyMaterialsFragment != null)
        {
            fragmentTransaction.hide(studyMaterialsFragment);
        }
        if (libraryFragrment != null)
        {
            fragmentTransaction.hide(libraryFragrment);
        }
        if (chargeFragment != null)
        {
            fragmentTransaction.hide(chargeFragment);
        }
        if (findLostFragment != null)
        {
            fragmentTransaction.hide(findLostFragment);
        }
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPassword()
    {
        return password;
    }
}
