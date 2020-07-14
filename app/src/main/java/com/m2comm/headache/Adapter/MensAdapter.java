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
import com.m2comm.headache.DTO.MensDTO;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.module.Custom_SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MensAdapter extends BaseAdapter {

    private Context context;
    private Activity activity;
    private ArrayList<MensDTO> arrayList;
    private LayoutInflater inflater;
    private Custom_SharedPreferences csp;

    public MensAdapter(Context context, Activity activity, ArrayList<MensDTO> arrayList, LayoutInflater inflater) {
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
        convertView = this.inflater.inflate(R.layout.mens_item , parent , false);
        MensDTO r = this.arrayList.get(position);

        TextView timeTv = convertView.findViewById(R.id.time);
        timeTv.setText("· "+Global.inputDateToStr(r.getsDate()));



        return convertView;
    }




}
