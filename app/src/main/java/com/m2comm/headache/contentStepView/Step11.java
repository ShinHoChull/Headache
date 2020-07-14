package com.m2comm.headache.contentStepView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.m2comm.headache.DTO.Step11SaveDTO;
import com.m2comm.headache.DTO.Step1SaveDTO;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.module.Custom_SharedPreferences;
import com.m2comm.headache.views.ContentStepActivity;
import com.m2comm.headache.views.SubTimePicker;

public class Step11 implements View.OnClickListener {

    public final static int BT1 = 11;
    public final static int BT2 = 22;

    private LayoutInflater inflater;
    private int ParentID;
    private LinearLayout parent;
    private Context context;
    private Activity activity;
    private Custom_SharedPreferences csp;

    View view;
    ContentStepActivity parentActivity;

    //step10
    TextView nextBt , backBt;

    //달력보기 버튼 1,2
    TextView calendarBt1 , calendarBt2 ;
    //날짜
    TextView startDateTxt , startTimeTxt , endDateTxt , endTimeTxt;


    int nextStepNum = 12;
    int backStepNum = 10;


    //getTime
    //long startDateTimeLong = 0;
    //long endDateTimeLong = 0;

    Step11SaveDTO step11SaveDTO;

    public Step11(LayoutInflater inflater, int parentID, Context context, Activity activity ,ContentStepActivity parentActivity , Step11SaveDTO step11SaveDTO) {
        this.inflater = inflater;
        ParentID = parentID;
        this.context = context;
        this.activity = activity;
        this.step11SaveDTO = step11SaveDTO;
        this.parentActivity = parentActivity;
        this.init();
        this.regObj();
    }

    private void regObj () {
        this.nextBt.setOnClickListener(this);
        this.backBt.setOnClickListener(this);

        this.calendarBt1.setOnClickListener(this);
        this.calendarBt2.setOnClickListener(this);
    }

    private void init () {
        this.parent = this.activity.findViewById(this.ParentID);
        this.parent.removeAllViews();
        this.view = inflater.inflate(R.layout.step11,this.parent,true);
        this.nextBt = this.view.findViewById(R.id.nextBt);
        this.backBt = this.view.findViewById(R.id.backBt);
        this.calendarBt1 = this.view.findViewById(R.id.calendarBt1);
        this.calendarBt2 = this.view.findViewById(R.id.calendarBt2);
        this.startDateTxt = this.view.findViewById(R.id.startDate);
        this.startTimeTxt = this.view.findViewById(R.id.startTime);
        this.endDateTxt = this.view.findViewById(R.id.endDate);
        this.endTimeTxt = this.view.findViewById(R.id.endTime);
        this.csp = new Custom_SharedPreferences(this.context);

        if ( this.step11SaveDTO == null ) {
            this.step11SaveDTO = new Step11SaveDTO(0L , 0L );
        } else {
            this.startTimeSetting(this.step11SaveDTO.getMens_sdate());
            this.endTimeSetting(this.step11SaveDTO.getMens_edate());
        }
    }

    public void startTimeSetting (long startDateTime) {
        this.step11SaveDTO.setMens_sdate(startDateTime);
        this.parentActivity.save11(this.step11SaveDTO);
        //this.startDateTimeLong = startDateTime;
        String date = Global.inputDateTimeToStr(startDateTime);
        if ( startDateTime == 0 ) {
            date = " - ";
        }

        String[] cut = date.split("-");

        this.startDateTxt.setText(cut[0]);
        this.startTimeTxt.setText(cut[1]);
    }

    public void endTimeSetting (long endDateTime) {
        this.step11SaveDTO.setMens_edate(endDateTime);
        this.parentActivity.save11(this.step11SaveDTO);
        //this.endDateTimeLong = endDateTime;
        String date = Global.inputDateTimeToStr(endDateTime);

        if ( endDateTime == 0 ) {
            date = " - ";
        }

        String[] cut = date.split("-");

        this.endDateTxt.setText(cut[0]);
        this.endTimeTxt.setText(cut[1]);
    }


    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()) {
            case R.id.nextBt:
                this.parentActivity.positionView(this.nextStepNum);
                break;

            case R.id.backBt:
                this.parentActivity.positionView(this.backStepNum);
                break;

            case R.id.calendarBt1:
                intent = new Intent(this.activity , SubTimePicker.class);
                intent.putExtra("startDateLong",this.step11SaveDTO.getMens_sdate());
                intent.putExtra("isStep11",true);
                this.activity.startActivityForResult(intent , BT1);
                break;

            case R.id.calendarBt2:
                if ( this.step11SaveDTO.getMens_sdate() == 0 ) {
                    Toast.makeText(this.context , "월경 시작시간을 선택해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                intent = new Intent(this.activity , SubTimePicker.class);
                intent.putExtra("isEnd",true);
                intent.putExtra("startDateLong",this.step11SaveDTO.getMens_sdate());
                intent.putExtra("endDateLong",this.step11SaveDTO.getMens_edate());
                intent.putExtra("isStep11",true);
                this.activity.startActivityForResult(intent , BT2);
                break;

        }
    }


}
