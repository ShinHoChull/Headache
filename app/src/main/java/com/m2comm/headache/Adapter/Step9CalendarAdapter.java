package com.m2comm.headache.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m2comm.headache.DTO.Step9Dates;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;

import java.util.ArrayList;
import java.util.Date;

public class Step9CalendarAdapter extends BaseAdapter {

    private ArrayList<String> dayString;
    private Context c;
    private LayoutInflater inflater;
    private int width;
    Date innerDate; //현재 년도랑 월
    private String currentlyDateStr;
    private ArrayList<Step9Dates> dateList;

    Long startDateLong , endDateLong;

    public Step9CalendarAdapter(Long startDateLong , Long endDateLong, ArrayList<Step9Dates> dateList, ArrayList<String> dayString, String currentlyDateStr, Context c, LayoutInflater inflater, int width) {
        this.dayString = dayString;
        this.c = c;
        this.inflater = inflater;
        this.width = width;
        this.dateList = dateList;
        this.currentlyDateStr = currentlyDateStr;
        this.startDateLong = startDateLong;
        this.endDateLong = endDateLong;
    }

    @Override
    public int getCount() {
        return this.dayString.size();
    }

    @Override
    public Object getItem(int position) {
        return this.dayString.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = this.inflater.inflate(R.layout.subtime_calendar_item, parent, false);
        convertView.getLayoutParams().height = this.width;

        LinearLayout check_cir1 = convertView.findViewById(R.id.cir);
        check_cir1.getLayoutParams().width = this.width;
        LinearLayout check_cir2 = convertView.findViewById(R.id.cir2);
        check_cir2.getLayoutParams().width = this.width;
        LinearLayout check_cir3 = convertView.findViewById(R.id.cir3);
        check_cir3.getLayoutParams().width = this.width;

        FrameLayout back1 = convertView.findViewById(R.id.back1); //Start End 사이에 포함하는 View
        FrameLayout back2 = convertView.findViewById(R.id.back2); //Start (오른쪽방향) View
        FrameLayout back3 = convertView.findViewById(R.id.back3); //End (왼쪽) 방향 View
        FrameLayout back4 = convertView.findViewById(R.id.back4); //Start Click(원형) 방향X View

        TextView tv = convertView.findViewById(R.id.calendar_number);

        String day = this.dayString.get(position);
        if (day != null && !day.equals("")) {
            tv.setText(day);

            //day currently visible
            Date date = Global.getStrToDate(currentlyDateStr + "-" + this.dayString.get(position));

            String step1StartDate = Global.getTimeToStr(startDateLong);

            if ( date.getTime() < Global.getStrToDate(step1StartDate).getTime()  ||  date.getTime() > endDateLong) {
                tv.setTextColor(Color.parseColor("#BDBDBD"));
            } else {
                for (int i = 0 , j = this.dateList.size(); i < j ; i++) {
                    Step9Dates row = this.dateList.get(i);
                    if (date.getTime() == Global.getStrToDate(row.getDate()).getTime()) {
                        tv.setTextColor(Color.parseColor("#21afc1"));
                        back4.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
        return convertView;
    }
}
