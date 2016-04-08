package com.swuos.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.swuos.ALLFragment.swujw.TotalInfo;
import com.swuos.ALLFragment.swujw.schedule.util.CurrentWeek;
import com.swuos.ALLFragment.swujw.schedule.util.Schedule;
import com.swuos.ALLFragment.swujw.schedule.util.ScheduleItem;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.MainActivity;
import com.swuos.swuassistant.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 张孟尧 on 2016/4/4.
 */
public class ClassAlarmService extends Service
{
    /*保存课程表的列表*/
    private static List<ScheduleItem> scheduleItemList = new ArrayList<>();
    /*保存用户信息*/
    private static TotalInfo totalInfo = new TotalInfo();
    private static SharedPreferences sharedPreferences;
    private static int curretweek = -1;
    private MainActivity mainActivity;
    private Timer timer = new Timer();

    private TimerTask timerTask;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        /*打开本地存储文件*/
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        /*加载课程表数据*/
        totalInfo.setScheduleDataJson(sharedPreferences.getString("scheduleDataJson", ""));
        curretweek = CurrentWeek.getweek();
       if (totalInfo.getScheduleDataJson().equals(""))
       {
           stopSelf();
           return super.onStartCommand(intent, flags, startId);
       }
        scheduleItemList = Schedule.getScheduleList(totalInfo);
        setNotification();
        timer = new Timer();
        timer.schedule(new MtimerTask(), 0, Constant.DEFAULHEADWAY);
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    private void setNotification()
    {
        ScheduleItem scheduleItem;
        int position = 0;
        for (int i = 0; i < scheduleItemList.size(); i++)
        {
            /*获取一个课程*/
            scheduleItem = scheduleItemList.get(i);
            /*判断该课本周是否有课*/
            if ((scheduleItem.getXqj() != (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1)) || !scheduleItem.getClassweek()[curretweek])
            {
                continue;
            }
            if (isApproachClass(scheduleItem,15))
            {
                showNotify(scheduleItem);
                timer.cancel();
                timer = null;
                timer = new Timer();
                timer.schedule(new MtimerTask(), Constant.ONE_CLASS_TIME, Constant.DEFAULHEADWAY);
                break;
            }

        }

    }

    private boolean isApproachClass(ScheduleItem scheduleItem, long headwayMin)
    {
        long time = scheduleItem.getStartTime() - (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) * 60 + Calendar.getInstance().get(Calendar.MINUTE));
        if ((time <= headwayMin) && (time >= 0))
        {
            Log.d("ClassAlamok", String.valueOf(time) + "---" + String.valueOf(scheduleItem.getStartTime()));
            return true;
        } else
            Log.d("ClassAlamnot", String.valueOf(time) + "---" + String.valueOf(scheduleItem.getStartTime()));
        return false;
    }

    private class MtimerTask extends TimerTask
    {
        protected MtimerTask()
        {
            super();
        }

        @Override
        public void run()
        {
            Log.d("ClassAlam", "测试一次");
setNotification();
        }
    }

    private void showNotify(ScheduleItem scheduleItem)
    {

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle(scheduleItem.getKcmc()).setContentText(scheduleItem.getCdmc() + "  " + scheduleItem.getJc()).setTicker("要上课了").setSmallIcon(R.drawable.ic_action_home).setDefaults(Notification.DEFAULT_LIGHTS).setAutoCancel(true);
        Notification notification = mBuilder.build();
//        notification.defaults=Notification.DEFAULT_LIGHTS;
        notificationManager.notify(100, notification);
        Log.d("ClassAlam", "通知");
    }
}
