package com.m2comm.headache.DTO;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m2comm.headache.DTO.ListDTO;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    ArrayList<ListDTO> array;
    Context context;
    Activity activity;
    LayoutInflater inflater;
    ViewHolder viewHolder;
    Global g;

    public ListViewAdapter(ArrayList<ListDTO> array, Context context, Activity activity) {
        this.array = array;
        this.context = context;
        this.activity = activity;
        this.inflater = LayoutInflater.from(context);
        this.g = new Global(context);
    }

    @Override
    public int getCount() {
        return this.array.size();
    }

    @Override
    public Object getItem(int position) {
        return this.array.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if ( convertView == null ) {

            this.viewHolder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.listview_item , null);
            this.viewHolder.name = convertView.findViewById(R.id.name);
            this.viewHolder.addr = convertView.findViewById(R.id.addr);
            this.viewHolder.num = convertView.findViewById(R.id.num);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,300 );
            convertView.setLayoutParams(layoutParams);
            convertView.setTag(this.viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ListDTO row = this.array.get(position);

        this.viewHolder.name.setText(row.name + "("+row.age+")");
        this.viewHolder.addr.setText(row.add);
        this.viewHolder.num.setText(String.valueOf(row.num));

        return convertView;
    }

    class ViewHolder {
        public TextView name , addr , num ;
    }



}

