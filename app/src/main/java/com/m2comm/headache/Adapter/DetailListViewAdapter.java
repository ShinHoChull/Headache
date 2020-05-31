package com.m2comm.headache.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.m2comm.headache.DTO.DetailCalendarDTO;
import com.m2comm.headache.DTO.ListViewAdapter;
import com.m2comm.headache.R;

import java.util.ArrayList;


public class DetailListViewAdapter extends BaseAdapter {

    private ArrayList<DetailCalendarDTO> list;
    private Context context;
    private Activity activity;
    private LayoutInflater inflater;
    private ViewHolder viewHolder;


    public DetailListViewAdapter(ArrayList<DetailCalendarDTO> list, Context context, Activity activity, LayoutInflater inflater) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DetailCalendarDTO row = this.list.get(position);

        ImageView img = null , nextImg = null;
        TextView headerTitle  = null , desc = null;
        if (row.isHeader()) {
            convertView = this.inflater.inflate(R.layout.detail_calendar_header, null);
            img = convertView.findViewById(R.id.header_icon);
            headerTitle = convertView.findViewById(R.id.header_text);

        } else {
            convertView = this.inflater.inflate(R.layout.detail_calendar_content, null);
            img = convertView.findViewById(R.id.content_icon);
            desc = convertView.findViewById(R.id.desc);
            nextImg = convertView.findViewById(R.id.nextImg);
            nextImg.setColorFilter(Color.parseColor("#B1B2B1"));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
            convertView.setLayoutParams(layoutParams);
        }

        if ( row.isHeader() ) {
            headerTitle.setText(row.getStrDate());
        } else {
            desc.setText("· 강도 ( "+row.getState()+" / 10 )\n· "+row.getDes()+" ( 효과 있음 )");
        }

        return convertView;
    }

    class ViewHolder {
        public ImageView headerImg ;
        public TextView headerTitle;

    }

}