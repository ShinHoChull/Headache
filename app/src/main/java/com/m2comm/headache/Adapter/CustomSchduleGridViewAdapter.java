package com.m2comm.headache.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;


import com.m2comm.headache.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CustomSchduleGridViewAdapter extends BaseAdapter {

    private int x, y, w, h;

    private Context c;
    private LayoutInflater inflater;

    private ArrayList<String> list; //date ArrayList

    private Calendar mCal;
    private Calendar realmCal , lineCal;
    private Date date , lineDate;

    public CustomSchduleGridViewAdapter(Context c, LayoutInflater inflater, Calendar mCal, Date date, ArrayList<String> list ,  Calendar lineCal) {
        this.c = c;
        this.inflater = inflater;
        this.mCal = mCal;
        this.realmCal = mCal;
        this.list = list;
        this.date = date;
        this.lineCal = lineCal;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.schdule_grid_item, parent, false);
            holder = new ViewHolder();
            holder.tvItemGridView = (TextView) convertView.findViewById(R.id.tv_item_gridview);
            holder.cirV= (FrameLayout) convertView.findViewById(R.id.schedule_startView);
            holder.choice_start_view = convertView.findViewById(R.id.choice_start_day);
            holder.line = convertView.findViewById(R.id.schedule_choice_line);
            holder.check_view = convertView.findViewById(R.id.check_view);
            holder.startSubView = convertView.findViewById(R.id.startSubView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvItemGridView.setText("" + getItem(position));

        //해당 날짜 텍스트 컬러,배경 변경
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd",Locale.KOREA);
        String dayString = getItem(position);
        if (!dayString.equals("")) {
            dayString = String.valueOf(mCal.get(Calendar.YEAR)) + "." + this.zeroPoint(String.valueOf(mCal.get(Calendar.MONTH) + 1)) + "." + this.zeroPoint(dayString);
        } else {
            return convertView;
        }

        Date nDate = null;
        try {
            nDate = dateFormat.parse(dayString);

            //new Calendar
            Calendar newCal = Calendar.getInstance();
            //오늘 날짜 표시해야 해서 새로운 인스턴스를 생성해준다..
            Integer today = newCal.get(Calendar.DAY_OF_MONTH);
            String sToday = String.valueOf(today);

            //이번주 구하기
            Integer nowWeekState = newCal.get(Calendar.WEEK_OF_MONTH);
            Integer oldWeekState = realmCal.get(Calendar.WEEK_OF_MONTH);
            if ( newCal.get(Calendar.YEAR) == realmCal.get(Calendar.YEAR) && newCal.get(Calendar.MONTH) == realmCal.get(Calendar.MONTH) && nowWeekState == oldWeekState) {
                //같은 주차면 뷰의 백그라운드 색상을 변경
               // convertView.setBackgroundColor(Color.rgb(244,244,244));
            }

            this.realmCal.setTime(nDate);
            switch (realmCal.get(Calendar.DAY_OF_WEEK)) {
                case 1:
                  //  holder.tvItemGridView.setTextColor(this.c.getResources().getColor(R.color.schadule_sun_color));
                    break;
                case 7:
                 //   holder.tvItemGridView.setTextColor(this.c.getResources().getColor(R.color.schadule_sat_color));
                    break;
                default:
                 //   holder.tvItemGridView.setTextColor(this.c.getResources().getColor(R.color.main_color_black));
            }

            if (newCal.get(Calendar.YEAR) == realmCal.get(Calendar.YEAR) && newCal.get(Calendar.MONTH) ==
                    realmCal.get(Calendar.MONTH) && sToday.equals(getItem(position))) {
                //오늘 day 텍스트 컬러 변경
                //convertView.setBackgroundColor(Color.rgb(221, 221, 221));
                //holder.tvItemGridView.setTextColor(this.c.getResources().getColor(R.color.main_color_skyblue));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return convertView;

    }

    private class ViewHolder {

        TextView tvItemGridView;
        TextView choice_start_view;
        FrameLayout cirV;
        LinearLayout line;
        LinearLayout check_view;
        LinearLayout startSubView;

    }

    public String zeroPoint(String data) {
        data = data.trim();
        if (data.length() == 1) {
            data = "0" + data;
        }
        return data;
    }

}
