package com.m2comm.headache.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.m2comm.headache.Adapter.SubTimeCalendarAdapter;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivityAlarmPickerBinding;
import com.m2comm.headache.databinding.ActivitySubTimePickerBinding;
import com.m2comm.headache.module.CalendarMonthModule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AlarmPicker extends AppCompatActivity implements View.OnClickListener {

    private final int VIEW_PAGER = 0;
    private final int VIEW_TIME_PICKER = 1;

    ActivityAlarmPickerBinding binding;
    int dateIndex = -1;
    //timepicker
    String time = "";
    private Activity activity;

    private void regObj() {
        this.binding.successBt.setOnClickListener(this);
        this.binding.cancelBt.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_picker);
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_alarm_picker);
        this.binding.setAlarmPicker(this);
        this.init();
        this.regObj();
    }

    private void init() {
        this.binding.timepicker.setIs24HourView(false);
        this.binding.timepicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                time =hourOfDay + ":" + minute;
            }
        });
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.time = String.valueOf(calendar.get(Calendar.HOUR)) +":"+ String.valueOf(calendar.get(Calendar.MINUTE));
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.successBt:
                Intent intent = new Intent();
                intent.putExtra("time",this.time);
                setResult(RESULT_OK,intent);
                finish();
                break;

            case R.id.cancelBt:
                finish();
                break;
        }
    }
}
