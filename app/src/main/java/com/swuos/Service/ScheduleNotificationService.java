package com.swuos.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.swuos.ALLFragment.swujw.TotalInfo;
import com.swuos.ALLFragment.swujw.schedule.util.ScheduleItem;
import com.swuos.swuassistant.R;
import com.swuos.util.SALog;

/**
 * Created by 张孟尧 on 2016/4/9.
 */
public class ScheduleNotificationService extends Service {
    @Nullable
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
         /*打开本地存储文件*/
        //        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        /*加载课程表数据*/
        TotalInfo totalInfo = new TotalInfo();
        //        totalInfo.setScheduleDataJson(sharedPreferences.getString("scheduleDataJson", ""));
        if (totalInfo.getScheduleItemList().isEmpty()) {
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }
        //        Toast.makeText(this,"上课通知",Toast.LENGTH_SHORT).show();
        SALog.d("ClassAlam", "接到通知");

        showNotify(totalInfo.getScheduleItemList().get(totalInfo.getPosition()));
        Intent intent1 = new Intent(this, ClassAlarmService.class);
        startService(intent1);
        return super.onStartCommand(intent, flags, startId);
    }

    private void showNotify(ScheduleItem scheduleItem) {

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle(scheduleItem.getKcmc()).setContentText(scheduleItem.getCdmc() + "  " + scheduleItem.getJc()).setTicker("要上课了").setSmallIcon(R.drawable.ic_action_home).setDefaults(Notification.DEFAULT_LIGHTS).setAutoCancel(true);
        Notification notification = mBuilder.build();
        //        notification.defaults=Notification.DEFAULT_LIGHTS;
        notificationManager.notify(100, notification);
    }
}
