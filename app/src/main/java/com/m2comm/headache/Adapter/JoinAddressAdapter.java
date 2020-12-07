package com.m2comm.headache.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.m2comm.headache.DTO.HospitalDTO;
import com.m2comm.headache.R;

import java.util.ArrayList;

public class JoinAddressAdapter extends BaseAdapter {

    private Context context;
    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<HospitalDTO> hospitalDTOArrayList;

    public JoinAddressAdapter(Context context, Activity activity, LayoutInflater inflater, ArrayList<HospitalDTO> hospitalDTOArrayList) {
        this.context = context;
        this.activity = activity;
        this.inflater = inflater;
        this.hospitalDTOArrayList = hospitalDTOArrayList;
    }

    @Override
    public int getCount() {
        return this.hospitalDTOArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.hospitalDTOArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HospitalDTO row = this.hospitalDTOArrayList.get(position);
        convertView = this.inflater.inflate(R.layout.hospital_item, parent, false);
        convertView.getLayoutParams().height = 90;
        TextView tv = convertView.findViewById(R.id.hospitalTv);
        tv.setText(row.getName());


        return convertView;
    }



}
