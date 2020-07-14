package com.m2comm.headache.module;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.m2comm.headache.DTO.AlarmDTO;
import com.m2comm.headache.R;
import com.m2comm.headache.views.Main2Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {

    Context context;
    private Custom_SharedPreferences csp;
    private ArrayList<AlarmDTO> arrayList;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        this.arrayList = new ArrayList<>();
        this.csp = new Custom_SharedPreferences(context);

        Bundle extra = intent.getExtras();
        if (extra != null) {
            int num = extra.getInt("alarmNum");
            String code = extra.getString("pushCode");
            Log.d("kkkk", "num=" + num);
            Log.d("pushCode", "code=" + code);

            if (code.equals("content")) {
                Intent popupIntent = new Intent(context, Main2Activity.class);
                popupIntent.putExtra("action", "diary");
                this.sendPush(context, popupIntent , "종료되지 않은 일기가 있습니다.");
                return;
            }

            if (!this.csp.getValue("alarmList", "").equals("")) {
                try {
                    JSONObject obj = new JSONObject(this.csp.getValue("alarmList", ""));
                    JSONArray arr = obj.getJSONArray("alarmList");
                    for (int i = 0, j = arr.length(); i < j; i++) {
                        JSONObject row = arr.getJSONObject(i);
                        this.arrayList.add(new AlarmDTO(row.getString("time"), row.getBoolean("isPush"), row.getInt("id")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    this.arrayList = new ArrayList<>();
                }
            }
            Log.d("pushSize=", this.arrayList.size() + "_");
            AlarmDTO row = this.arrayList.get(num);
            Log.d("isPush = ", row.isPush() + "__");

            if (row.isPush()) {
                this.sendPush(context, intent, "예방약을 복용할 시간입니다.");
            } else {
                this.cancelAlarm(context, num);
            }
        }
    }

    public void sendPush(Context context, Intent intent , String text) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(context.getResources().getString(R.string.app_name), context.getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(mChannel);
            builder = new NotificationCompat.Builder(context, mChannel.getId());
        } else {
            builder = new NotificationCompat.Builder(context);
        }

        builder.setAutoCancel(true)
                .setSmallIcon((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) ? R.mipmap.ic_launcher : R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setTicker(context.getResources().getText(R.string.app_name))
                .setContentTitle(context.getResources().getText(R.string.app_name))
                .setContentText("예방약을 복용할 시간입니다.")
                .setPriority(NotificationCompat.PRIORITY_MAX); //최대로 펼침

        builder.setContentIntent(pendingIntent);
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        notificationManager.notify(0, builder.build());
    }

    public void cancelAlarm(Context context, int alarmId) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);
        alarmManager.cancel(pendingIntent);
    }


}
