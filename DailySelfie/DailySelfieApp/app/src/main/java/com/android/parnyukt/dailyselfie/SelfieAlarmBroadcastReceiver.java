package com.android.parnyukt.dailyselfie;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
/**
 * Created by tatyana on 22.08.15.
 */
public class SelfieAlarmBroadcastReceiver extends BroadcastReceiver {

    private static final int MY_NOTIFICATION_ID = 1;
    private static final int TWO_MINUTES = 30 * 1000;

    public void setupAlarm(Context context) {

        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = getAlarmIntent(context);

        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + (TWO_MINUTES),
                TWO_MINUTES,
                alarmIntent);
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = getAlarmIntent(context);

        alarmMgr.cancel(alarmIntent);
    }

    private PendingIntent getAlarmIntent(Context context) {
        Intent intent = new Intent(context, SelfieAlarmBroadcastReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        return alarmIntent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("#### alarm received!!");

        Intent mNotificationIntent = new Intent(context,
                MainActivity.class);

        PendingIntent mContentIntent = PendingIntent.getActivity(context, 0,
                mNotificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

        long[] mVibratePattern = { 0, 200, 200, 300 };

        // Define the Notification's expanded message and Intent:
        Notification.Builder notificationBuilder = new Notification.Builder(
                context)
                .setTicker("Time for another selfie")
                .setSmallIcon(R.drawable.ic_camera)
                .setAutoCancel(true)
                .setContentTitle("Daily Selfie")
                .setContentText("Time for another selfie")
                .setContentIntent(mContentIntent)
                .setVibrate(mVibratePattern);

        // Pass the Notification to the NotificationManager:
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MY_NOTIFICATION_ID,
                notificationBuilder.build());
    }

}