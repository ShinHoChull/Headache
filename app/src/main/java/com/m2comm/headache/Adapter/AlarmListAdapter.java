package com.m2comm.headache.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.m2comm.headache.DTO.AlarmDTO;
import com.m2comm.headache.R;
import com.m2comm.headache.module.Custom_SharedPreferences;

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
        time.setText(this.zeroPoint(timeCut[0])+":"+timeCut[1]);

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
