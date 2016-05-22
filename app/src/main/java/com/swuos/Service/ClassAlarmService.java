package com.swuos.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;

import com.swuos.ALLFragment.swujw.TotalInfo;
import com.swuos.ALLFragment.swujw.schedule.util.CurrentWeek;
import com.swuos.ALLFragment.swujw.schedule.util.Schedule;
import com.swuos.ALLFragment.swujw.schedule.util.ScheduleItem;
import com.swuos.swuassistant.Constant;
import com.swuos.util.SALog;

import java.util.Calendar;
import java.util.List;

/**
 * Created by 张孟尧 on 2016/4/4.
 */
public class ClassAlarmService extends Service {
    /*保存课程表的列表*/
    private static List<ScheduleItem> scheduleItemList;
    /*保存用户信息*/
    private static TotalInfo totalInfo = new TotalInfo();
    private static SharedPreferences sharedPreferences;
    private static int curretweek = -1;
    private Boolean mwait = false;
    private AlarmManager manager;
    private int remindtime;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SharedPreferences settingSharedPreferences = getSharedPreferences("com.swuos.swuassistant_preferences", MODE_PRIVATE);
        String remind = settingSharedPreferences.getString("headway_before_class", "15");
        remindtime = Integer.valueOf(remind);
        if (totalInfo.getScheduleItemList().isEmpty()) {
            if (totalInfo.getScheduleDataJson() == null) { /*打开本地存储文件*/
                sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
            /*加载课程表数据*/
                totalInfo.setScheduleDataJson(sharedPreferences.getString("scheduleDataJson", ""));
            }
            if (totalInfo.getScheduleDataJson().equals("")) {
                stopSelf();
                return super.onStartCommand(intent, flags, startId);
            }
            totalInfo.setScheduleItemList(Schedule.getScheduleList(totalInfo));

        }
        curretweek = CurrentWeek.getweek();
        if (mwait) {
            mwait = false;
            manager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent1 = new Intent(this, ClassAlarmService.class);
            PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent1, 0);
            SALog.d("ClassAlam", "上发送通知");
            /*已经发送了本次的通知,截止上课前就不再通知*/
            manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + (remindtime + 1) * Constant.ONE_MIN_TIME, pendingIntent);

            return super.onStartCommand(intent, flags, startId);
        }
        setNotification();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean stopService(Intent name) {
        Intent intent1 = new Intent(this, ClassAlarmService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent1, 0);
        manager.cancel(pendingIntent);
        SALog.d("ClassAlam", "停止闹钟");
        return super.stopService(name);

    }

    private int getDayOfWeek(Calendar calendar) {
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == 1)
    {
        return 6;
    } else
            return day - 2;
    }

    private void setNotification() {
        ScheduleItem scheduleItem;
        Calendar calendar = Calendar.getInstance();
        /*获取当前在本周已经过的毫秒数*/
        long curTime = getDayOfWeek(calendar) * Constant.ONE_DAY_TIME + calendar.get(Calendar.HOUR_OF_DAY) * Constant.ONE_HOUR_TIME + calendar.get(Calendar.MINUTE) * Constant.ONE_MIN_TIME + calendar.get(Calendar.SECOND) * 1000;
        scheduleItemList = totalInfo.getScheduleItemList();
        for (int i = 0; i < scheduleItemList.size(); i++) {
            scheduleItem = scheduleItemList.get(i);
            /*判断该课本周是否有课*/
            if (!scheduleItem.getClassweek()[curretweek] || (scheduleItem.getStartTime() - curTime < 0)) {
                continue;
            } else {
                /*距离上课提醒剩余的时间*/
                int timeToclass = (int) (scheduleItem.getStartTime() - curTime - remindtime * Constant.ONE_MIN_TIME);
                manager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent1 = new Intent(this, ScheduleNotificationService.class);
                PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent1, 0);
                SALog.d("ClassAlam", "发送通知" + String.valueOf(timeToclass / Constant.ONE_MIN_TIME));
                totalInfo.setPosition(i);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + timeToclass, pendingIntent);
                }
                mwait = true;
                break;
            }
        }
        /*mwait为false说明这周没课了,将闹铃设到下一周早7点*/
        if (!mwait)
        {
            /*本周剩余时间加上下周一早上7点的时间*/
            int next = (int) (Constant.ONE_WEEK_TIME - curTime + 7 * Constant.ONE_HOUR_TIME);
            manager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent1 = new Intent(this, ClassAlarmService.class);
            PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent1, 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + next, pendingIntent);
            }
        }

    }
}
