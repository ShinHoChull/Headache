package com.m2comm.headache.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m2comm.headache.DTO.CalendarDTO;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.module.CirView;
import com.m2comm.headache.views.ContentStepActivity;
import com.m2comm.headache.views.DetaiViewActivity;

import java.util.ArrayList;

public class DetailCalendarAdapter extends BaseAdapter {

    private ArrayList<String> dayString;
    private Context c;
    private LayoutInflater inflater;
    private int width;
    private ArrayList<CalendarDTO> calendarDTOS;
    private String dateStr;

    public DetailCalendarAdapter(ArrayList<CalendarDTO> calendarDTOS, String dateStr, ArrayList<String> dayString, Context c, LayoutInflater inflater, int width) {
        this.calendarDTOS = calendarDTOS;
        this.dayString = dayString;
        this.c = c;
        this.dateStr = dateStr;
        this.inflater = inflater;
        this.width = width;
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

        convertView = this.inflater.inflate(R.layout.detail_calendar_item, parent, false);
        convertView.getLayoutParams().height = this.width;
        final CirView back1 = convertView.findViewById(R.id.back1); // 원
        final FrameLayout back2 = convertView.findViewById(R.id.back2);// 시작
        final LinearLayout back2_color = convertView.findViewById(R.id.back2_color); //시작
        final FrameLayout back3 = convertView.findViewById(R.id.back3); //진행
        final FrameLayout back4 = convertView.findViewById(R.id.back4);// 시작
        final LinearLayout back4_color = convertView.findViewById(R.id.back4_color); //시작
        final LinearLayout mens = convertView.findViewById(R.id.mens);
        final LinearLayout medicine = convertView.findViewById(R.id.medicine);
        final LinearLayout effect = convertView.findViewById(R.id.effect);
        TextView count = convertView.findViewById(R.id.calendar_count);
        TextView tv = convertView.findViewById(R.id.calendar_number);
        final FrameLayout textBackView = convertView.findViewById(R.id.textBackView);

        tv.setText(this.dayString.get(position));

        if (this.calendarDTOS != null) {

            String day = this.dayString.get(position);
            if ( !day.equals("") ) {
                day = this.plusZero(day);
            }

            String pushDate = this.dateStr + "-" + day;

            for (int i = 0, j = this.calendarDTOS.size(); i < j; i++) {

                final CalendarDTO row = this.calendarDTOS.get(i);

                if (row.getDate().equals(pushDate)) {

                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if ( row.getDiary_sid() > 0 ) {
                                String strFormatedDate = Global.formatChangeToStrDate(row.getDate());
                                Intent intent = new Intent(c , DetaiViewActivity.class);
                                intent.putExtra("calendar_desc",row.getMedicine_txt()+"~"+row.getMedicine_effect_txt());
                                intent.putExtra("diary_sid",row.getDiary_sid());
                                intent.putExtra("date", strFormatedDate);
                                c.startActivity(intent);
                            }
                        }
                    });

                    if (row.getChk_num() > 1) {
                        count.setVisibility(View.VISIBLE);
                        count.setText(String.valueOf(row.getChk_num()));
                    }

                    if (row.getImg_type() != 0) {
                        tv.setTextColor(Color.WHITE);
                    }

                    if ( row.getMens() > 0 ) {
                        mens.setVisibility(View.VISIBLE);
                    }
                    if ( row.getMedicine() >0 ) {
                        medicine.setVisibility(View.VISIBLE);
                    }
                    if ( row.getEffect() >0 ) {
                        effect.setVisibility(View.VISIBLE);
                    }

                    textBackView.post(new Runnable() {
                        @Override
                        public void run() {
                            final int h = textBackView.getHeight();
                            //textBackView.getLayoutParams().height = h;
                            back1.setColor(Global.getEffectColor(row.getAche_power()));

                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            params.width = h;
                            params.height = h;
                            params.gravity = Gravity.CENTER;
                            back1.setLayoutParams(params);
                            back1.setRadius(h);

                            if (row.getImg_type() == 1) {
                                back1.setVisibility(View.VISIBLE);
                            } else if (row.getImg_type() == 2) {
                                back1.setVisibility(View.VISIBLE);
                                back2.setVisibility(View.VISIBLE);
                                back2_color.setVisibility(View.VISIBLE);
                                back2_color.setBackgroundColor(Global.getEffectLineColor(row.getAche_power()));

                                FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
                                //params2.width = h;
                                params2.height = h;
                                params2.gravity = Gravity.CENTER;
                                back2.setLayoutParams(params2);

                            } else if (row.getImg_type() == 3) {
                                back1.setVisibility(View.GONE);
                                back3.setVisibility(View.VISIBLE);
                                back3.setBackgroundColor(Global.getEffectLineColor(row.getAche_power()));

                                FrameLayout.LayoutParams params3 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT);
                              //  params3.width = h;
                              //  params3.height = h;
                                params3.gravity = Gravity.CENTER;
                                back3.setLayoutParams(params3);

                            } else if (row.getImg_type() == 4) {
                                back1.setVisibility(View.VISIBLE);
                                back4.setVisibility(View.VISIBLE);
                                back4_color.setVisibility(View.VISIBLE);
                                back4_color.setBackgroundColor(Global.getEffectLineColor(row.getAche_power()));

                                FrameLayout.LayoutParams params4 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
                                //params4.width = h;
                                params4.height = h;
                                params4.gravity = Gravity.CENTER;
                                back4.setLayoutParams(params4);
                            }
                        }
                    });
                }
            }
        }

        return convertView;
    }
    private String plusZero(String val) {
        if ( Integer.parseInt(val) < 10 ) {
            val = "0"+val;
        }
        return val;
    }
}
