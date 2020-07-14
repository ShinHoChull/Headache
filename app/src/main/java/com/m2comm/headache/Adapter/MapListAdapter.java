package com.m2comm.headache.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.m2comm.headache.DTO.AddressDTO;
import com.m2comm.headache.DTO.AlarmDTO;
import com.m2comm.headache.R;
import com.m2comm.headache.module.Custom_SharedPreferences;
import com.m2comm.headache.views.AlarmListActivity;
import com.m2comm.headache.views.AlarmPicker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapListAdapter extends BaseAdapter {

    private Context context;
    private Activity activity;
    private ArrayList<AddressDTO> arrayList;
    private LayoutInflater inflater;
    private Custom_SharedPreferences csp;

    public MapListAdapter(Context context, Activity activity, ArrayList<AddressDTO> arrayList, LayoutInflater inflater) {
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
        convertView = this.inflater.inflate(R.layout.map_item, parent, false);

        final AddressDTO row = this.arrayList.get(position);
        TextView address = convertView.findViewById(R.id.address);
        address.setText(row.getAddress());

        return convertView;
    }

}
