package com.m2comm.headache.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.m2comm.headache.DTO.AlarmDTO;
import com.m2comm.headache.R;

import java.util.ArrayList;

public class AlarmListAdapter extends BaseAdapter {

    private Context context;
    private Activity activity;
    private ArrayList<AlarmDTO> arrayList;
    private LayoutInflater inflater;

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

        convertView = this.inflater.inflate(R.layout.alarm_item , parent , false);


        return convertView;
    }
}
