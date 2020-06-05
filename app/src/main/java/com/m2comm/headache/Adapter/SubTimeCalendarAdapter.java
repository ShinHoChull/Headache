package com.m2comm.headache.Adapter;

import android.app.Activity;
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

import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.module.CalendarModule;
import com.m2comm.headache.module.Custom_SharedPreferences;

import java.util.ArrayList;
import java.util.Date;

public class SubTimeCalendarAdapter extends BaseAdapter {

    private ArrayList<String> dayString;
    private Context c;
    private LayoutInflater inflater;
    private int width;
    Date startDate , endDate; //현재 년도랑 월
    private String currentlyDateStr;
    private CalendarModule cmm;
    private boolean isEnd = false;

    public SubTimeCalendarAdapter( boolean isEnd,Date startDate , Date endDate ,ArrayList<String> dayString,String currentlyDateStr, Context c, LayoutInflater inflater , int width,Activity activity) {
        this.dayString = dayString;
        this.c = c;
        this.inflater = inflater;
        this.width = width;
        this.startDate = startDate;
        this.endDate = endDate;
        this.currentlyDateStr = currentlyDateStr;
        this.cmm = new CalendarModule(c,activity);
        this.isEnd = isEnd;
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

        convertView = this.inflater.inflate(R.layout.subtime_calendar_item , parent , false);
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
        FrameLayout back4 = convertView.findViewById(R.id.back4); //Start Click(원형) 방향 View

        TextView tv = convertView.findViewById(R.id.calendar_number);

        String day = this.dayString.get(position);
        if ( day != null && !day.equals("") ) {
            tv.setText(day);
            //day currently visible
            Date date = Global.getStrToDate(currentlyDateStr+"-"+this.dayString.get(position));
            Date now_date = Global.getStrToDate(this.cmm.getStrRealDate());

            //현재날짜보다 후의 날짜는 색깔을 바꿔준고 아무런 이벤트 작업을 하지 않는다.
            if ( this.isEnd && date.getTime() < startDate.getTime() ) {
                tv.setTextColor(Color.parseColor("#BDBDBD"));
            } else if ( date.getTime() > now_date.getTime()  ) {
                tv.setTextColor(Color.parseColor("#BDBDBD"));
            } else {
                //첫번째 클릭한 시간
                if ( this.startDate != null ) {
                    //StartDate
                    assert date != null;
                    if (date.getTime() == this.startDate.getTime() && this.endDate != null) {
                        back2.setVisibility(View.VISIBLE);
                    } else if ( date.getTime() == this.startDate.getTime() ) {
                        back4.setVisibility(View.VISIBLE);
                    } else if ( this.endDate != null && date.getTime() == this.endDate.getTime()) {
                        back3.setVisibility(View.VISIBLE);
                    } else if (this.endDate != null && ( startDate.getTime() < date.getTime() && date.getTime() < endDate.getTime() ) ) {
                        Log.d("dayString",this.dayString.get(position));
                        tv.setTextColor(Color.parseColor("#21afc1"));
                        back1.setVisibility(View.VISIBLE);
                    } else {
                        back4.setVisibility(View.GONE);
                    }
                }
            }


        }


        return convertView;
    }
}
