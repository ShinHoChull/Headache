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

import androidx.databinding.DataBindingUtil;

import com.m2comm.headache.DTO.Step1SaveDTO;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.module.CalendarModule;
import com.m2comm.headache.views.ContentStepActivity;
import com.m2comm.headache.views.SubTimePicker;

public class Step1 implements View.OnClickListener {

    public final static int BT1 = 111;
    public final static int BT2 = 222;

    private LayoutInflater inflater;
    private int ParentID;
    private LinearLayout parent;
    private Context context;
    private Activity activity;
    private ContentStepActivity parentActivity;
    private View view;

    /*step1*/
    int nextStepNum = 2;
    LinearLayout nextBt;

    //달력보기 버튼 1,2
    TextView calendarBt1 , calendarBt2 ;
    //날짜
    TextView startDateTxt , startTimeTxt , endDateTxt , endTimeTxt;

    //getTime
    long startDateTimeLong = 0;
    long endDateTimeLong = 0;

    public Step1SaveDTO step1SaveDTO;

    private CalendarModule cm;


    public Step1(LayoutInflater inflater, int parentID, Context context, Activity activity , ContentStepActivity parentActivity , Step1SaveDTO step1SaveDTO) {
        this.inflater = inflater;
        ParentID = parentID;
        this.context = context;
        this.activity = activity;
        this.parentActivity = parentActivity;
        this.step1SaveDTO = step1SaveDTO;
        this.init();
    }

    private void regObj () {
        this.nextBt.setOnClickListener(this);
        this.calendarBt1.setOnClickListener(this);
        this.calendarBt2.setOnClickListener(this);
    }

    private void init () {
        this.parent = this.activity.findViewById(this.ParentID);
        this.parent.removeAllViews();

        this.view = inflater.inflate(R.layout.step1,this.parent,true);
        this.nextBt = this.view.findViewById(R.id.nextBt);
        this.calendarBt1 = this.view.findViewById(R.id.calendarBt1);
        this.calendarBt2 = this.view.findViewById(R.id.calendarBt2);
        this.startDateTxt = this.view.findViewById(R.id.startDate);
        this.startTimeTxt = this.view.findViewById(R.id.startTime);
        this.endDateTxt = this.view.findViewById(R.id.endDate);
        this.endTimeTxt = this.view.findViewById(R.id.endTime);
        this.cm = new CalendarModule(this.context , this.activity);
        this.regObj();

        if ( this.step1SaveDTO == null ) {
            //새 일기 작성시에는 오늘날짜는 넣어줍니다.
            this.step1SaveDTO = new Step1SaveDTO(Global.getStrToDateTime(this.cm.getStrRealDateTime()).getTime() , 0L ,"");
            this.startTimeSetting(this.step1SaveDTO.getSdate());
        } else {
            this.startTimeSetting(this.step1SaveDTO.getSdate());
            this.endTimeSetting(this.step1SaveDTO.geteDate());
        }
    }

    public void startTimeSetting (long startDateTime) {
        //값 저장후 반드시 Save를 시켜준다.
        this.step1SaveDTO.setSdate(startDateTime);
        this.parentActivity.save1(this.step1SaveDTO);

        //this.startDateTimeLong = startDateTime;
        String date = Global.inputDateTimeToStr(this.step1SaveDTO.getSdate());
        if ( startDateTime == 0 ) {
            date = " - ";
        }
        String[] cut = date.split("-");

        this.startDateTxt.setText(cut[0]);
        this.startTimeTxt.setText(cut[1]);

    }

    public void endTimeSetting (long endDateTime) {

        this.step1SaveDTO.seteDate(endDateTime);
        this.parentActivity.save1(this.step1SaveDTO);

        //this.endDateTimeLong = endDateTime;
        String date = Global.inputDateTimeToStr(this.step1SaveDTO.geteDate());
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

            case R.id.calendarBt1:
                endTimeSetting(0);
                intent = new Intent(this.activity , SubTimePicker.class);
                intent.putExtra("startDateLong",this.step1SaveDTO.getSdate());
                this.activity.startActivityForResult(intent , BT1);
                break;

            case R.id.calendarBt2:
                if ( this.step1SaveDTO.getSdate() == 0 ) {
                    Toast.makeText(this.context , "두통 시작시간을 선택해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }

                intent = new Intent(this.activity , SubTimePicker.class);
                intent.putExtra("isEnd",true);
                intent.putExtra("startDateLong",this.step1SaveDTO.getSdate());
                intent.putExtra("endDateLong",this.step1SaveDTO.geteDate());
                this.activity.startActivityForResult(intent , BT2);
                break;
        }
    }



}
