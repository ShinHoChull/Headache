package com.m2comm.headache.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;

import com.m2comm.headache.Adapter.AlarmListAdapter;
import com.m2comm.headache.DTO.AlarmDTO;
import com.m2comm.headache.R;
import com.m2comm.headache.contentStepView.Step1;
import com.m2comm.headache.databinding.ActivityAlarmListBinding;
import com.m2comm.headache.module.AlarmReceiver;
import com.m2comm.headache.module.Custom_SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AlarmListActivity extends AppCompatActivity implements View.OnClickListener {

    public static int ALARM_NUM = 999;
    public static int ALARM_MODIFY_NUM = 1212;
    String TAG = "AlarmListActivity1";

    BottomActivity bottomActivity;
    private ActivityAlarmListBinding binding;
    private AlarmListAdapter adapter;
    private ArrayList<AlarmDTO> arrayList;
    private Custom_SharedPreferences csp;

    AlarmManager mAlarmManager;

    private void regObj () {
        this.binding.backBt.setOnClickListener(this);
        this.binding.alarmBt.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
        this.binding = DataBindingUtil.setContentView(this,R.layout.activity_alarm_list);
        this.binding.setAlarmList(this);

        this.init();
        this.regObj();


        //this.arrayList.add(new AlarmDTO(new Date() , week , true , 1));
//        this.adapter = new AlarmListAdapter( this , this ,this.arrayList, getLayoutInflater() );
//        this.binding.listview.setAdapter(this.adapter);
        this.reloadAlarm();
    }

    public void reloadAlarm() {
        if ( !this.csp.getValue("alarmList","").equals("") ) {
            this.arrayList.clear();
            try {
                JSONObject obj = new JSONObject(this.csp.getValue("alarmList",""));
                JSONArray arr = obj.getJSONArray("alarmList");
                for ( int i = 0 , j = arr.length() ; i < j; i++ ) {
                    JSONObject row = arr.getJSONObject(i);
                    this.arrayList.add(new AlarmDTO(row.getString("time"),row.getBoolean("isPush"),row.getInt("id")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.adapterChanger();
        }
    }

    private void init () {
        this.bottomActivity = new BottomActivity(getLayoutInflater() , R.id.bottom , this , this,-1);
        this.arrayList = new ArrayList<>();
        this.csp = new Custom_SharedPreferences(this);
        this.mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    }

    private void adapterChanger () {
        this.adapter = new AlarmListAdapter( this , this ,this.arrayList, getLayoutInflater() );
        this.binding.listview.setAdapter(this.adapter);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.backBt:
                finish();
                break;

            case R.id.alarmBt:
                Intent intent = new Intent(this , AlarmPicker.class);
                startActivityForResult(intent,ALARM_NUM);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            assert data != null;
            if ( requestCode == ALARM_NUM ) {
                String time = data.getStringExtra("time");

                int alarmId = 0;
                if ( this.arrayList.size() > 0 ) {
                    AlarmDTO row = this.arrayList.get(this.arrayList.size()-1);
                    alarmId = row.getAlarmId()+1;
                }

                Log.d("alarmID=",alarmId+"_");
                this.arrayList.add(new AlarmDTO(data.getStringExtra("time")  , true , alarmId));
                this.adapterChanger();

                JSONObject obj = new JSONObject();
                try {
                    JSONArray jArray = new JSONArray();
                    for (int i = 0 , j = this.arrayList.size(); i < j ; i++) {
                        JSONObject sObj = new JSONObject();
                        AlarmDTO row = this.arrayList.get(i);
                        sObj.put("id",row.getAlarmId());
                        sObj.put("time",row.getTime());
                        sObj.put("isPush",row.isPush());
                        jArray.put(sObj);
                    }
                    obj.put("alarmList",jArray);

                } catch (Exception e) {
                    Log.d("error",e.toString());
                }
                csp.put("alarmList",obj.toString());
                this.addAlarm(alarmId , data.getStringExtra("time"));

            } else if ( requestCode == ALARM_MODIFY_NUM ) {

                String time = data.getStringExtra("time");
                int num = data.getIntExtra("num",-1);
                JSONObject obj = new JSONObject();
                try {
                    JSONArray jArray = new JSONArray();
                    for (int i = 0 , j = this.arrayList.size(); i < j ; i++) {
                        JSONObject sObj = new JSONObject();
                        AlarmDTO row = this.arrayList.get(i);
                        sObj.put("id",row.getAlarmId());
                        if ( row.getAlarmId() == num ) {
                            sObj.put("time",time);
                        } else {
                            sObj.put("time",row.getTime());
                        }
                        sObj.put("isPush",row.isPush());
                        jArray.put(sObj);
                    }
                    obj.put("alarmList",jArray);

                } catch (Exception e) {
                    Log.d("error",e.toString());
                }
                csp.put("alarmList",obj.toString());
                this.reloadAlarm();
                this.cancelAlarm(num);
                this.addAlarm(num , time);
            }
        }
    }


    private void addAlarm ( int alarmId , String time) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        long triggerTime = 0;
        long intervalTime = 24 * 60 * 60 * 1000;// 24시간
        intent.putExtra("alarmNum",alarmId);
        intent.putExtra("pushCode","base");
        String[] timeCut = time.split(":");
        PendingIntent pending = PendingIntent.getBroadcast(this , alarmId , intent , 0);
        triggerTime = setTriggerTime(Integer.parseInt(timeCut[0]),Integer.parseInt(timeCut[1]));
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime, intervalTime, pending);
    }

    public void cancelAlarm (int alarmNum) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this , alarmNum , intent,0);
        mAlarmManager.cancel(pIntent);
    }

    private long setTriggerTime(int hour , int min)
    {
        // current Time
        long atime = System.currentTimeMillis();
        // timepicker
        Calendar curTime = Calendar.getInstance();
        curTime.set(Calendar.HOUR_OF_DAY, hour);
        curTime.set(Calendar.MINUTE, min);
        curTime.set(Calendar.SECOND, 0);
        curTime.set(Calendar.MILLISECOND, 0);
        long btime = curTime.getTimeInMillis();
        long triggerTime = btime;
        if (atime > btime)
            triggerTime += 1000 * 60 * 60 * 24;

        return triggerTime;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }


}
