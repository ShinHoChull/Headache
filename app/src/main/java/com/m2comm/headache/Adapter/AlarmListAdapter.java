package com.m2comm.headache.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.m2comm.headache.DTO.AlarmDTO;
import com.m2comm.headache.R;
import com.m2comm.headache.module.Custom_SharedPreferences;
import com.m2comm.headache.views.AlarmListActivity;
import com.m2comm.headache.views.AlarmPicker;
import com.m2comm.headache.views.SubTimePicker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AlarmListAdapter extends BaseAdapter {

    private Context context;
    private Activity activity;
    private ArrayList<AlarmDTO> arrayList;
    private LayoutInflater inflater;
    private Custom_SharedPreferences csp;

    public AlarmListAdapter(Context context, Activity activity, ArrayList<AlarmDTO> arrayList, LayoutInflater inflater) {
        this.context = context;
        this.activity = activity;
        this.arrayList = arrayList;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        csp = new Custom_SharedPreferences(context);
        convertView = this.inflater.inflate(R.layout.alarm_item , parent , false);

        final AlarmDTO row = this.arrayList.get(position);
        final ImageView check = convertView.findViewById(R.id.checkBt);
        TextView time = convertView.findViewById(R.id.time);
        TextView am_pm = convertView.findViewById(R.id.am_pm);
        ImageView delBt = convertView.findViewById(R.id.alarmDel);
        ImageView modifyBt = convertView.findViewById(R.id.alarmModify);

        if ( row.isPush() ) {
            check.setImageResource(R.drawable.setting_cehck_on);
        } else {
            check.setImageResource(R.drawable.setting_cehck_off);
        }

        String[] timeCut = row.getTime().split(":");
        int hour = Integer.parseInt(timeCut[0]);
        if ( hour > 12 ) {
            am_pm.setText("오후");
        }
        if ( hour > 12 ) hour = hour - 12;

        time.setText(this.zeroPoint(String.valueOf(hour))+":"+this.zeroPoint(String.valueOf(timeCut[1])));

        modifyBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , AlarmPicker.class);
                intent.putExtra("num",row.getAlarmId());
                intent.putExtra("time",row.getTime());
                activity.startActivityForResult(intent,AlarmListActivity.ALARM_MODIFY_NUM);
            }
        });

        delBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(activity).setTitle("안내").setMessage("알림을 삭제하시겠습니까?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                JSONObject obj = new JSONObject();
                                try {
                                    JSONArray jArray = new JSONArray();
                                    for (int i = 0 , j = arrayList.size(); i < j ; i++) {

                                        JSONObject sObj = new JSONObject();
                                        AlarmDTO r = arrayList.get(i);
                                        if ( row.getAlarmId() == r.getAlarmId() ) {
                                            continue;
                                        }
                                        sObj.put("id",r.getAlarmId());
                                        sObj.put("time",r.getTime());
                                        sObj.put("isPush",r.isPush());
                                        jArray.put(sObj);
                                    }
                                    obj.put("alarmList",jArray);

                                } catch (Exception e) {
                                    Log.d("error=",e.toString());
                                }
                                csp.put("alarmList",obj.toString());
                                ((AlarmListActivity)context).reloadAlarm();
                                ((AlarmListActivity)context).cancelAlarm(row.getAlarmId());

                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( row.isPush() ) {
                    row.setPush(false);
                    check.setImageResource(R.drawable.setting_cehck_off);
                } else {
                    row.setPush(true);
                    check.setImageResource(R.drawable.setting_cehck_on);
                }

                JSONObject obj = new JSONObject();
                try {
                    JSONArray jArray = new JSONArray();
                    for (int i = 0 , j = arrayList.size(); i < j ; i++) {
                        JSONObject sObj = new JSONObject();
                        AlarmDTO row = arrayList.get(i);
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
            }
        });


        return convertView;
    }

    public String zeroPoint(String data) {
        data = data.trim();
        if (data.length() == 1) {
            data = "0" + data;
        }
        return data;
    }
}
