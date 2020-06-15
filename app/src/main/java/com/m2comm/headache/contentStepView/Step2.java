package com.m2comm.headache.contentStepView;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m2comm.headache.DTO.Step1SaveDTO;
import com.m2comm.headache.DTO.Step2SaveDTO;
import com.m2comm.headache.R;
import com.m2comm.headache.views.ContentStepActivity;

public class Step2 implements View.OnClickListener, View.OnTouchListener {

    private LayoutInflater inflater;
    private int ParentID;
    private LinearLayout parent;
    private Context context;
    private Activity activity;
    ContentStepActivity parentActivity;
    private View view;

    private Step2SaveDTO step2SaveDTO;
    //step2
    TextView nextBt, backBt;

    int nextStepNum = 3;
    int backStepNum = 1;

    LinearLayout cursor;
    FrameLayout cursor_frame, no10, no9, no8, no7, no6, no5, no4, no3, no2, no1;
    int cursorFrameHeight = 0;

    int[] cursorID = {
            R.id.no1,
            R.id.no2,
            R.id.no3,
            R.id.no4,
            R.id.no5,
            R.id.no6,
            R.id.no7,
            R.id.no8,
            R.id.no9,
            R.id.no10
    };

    ImageView icon;
    TextView icon_txt;



    public Step2(LayoutInflater inflater, int parentID, Context context, Activity activity, ContentStepActivity parentActivity ,Step2SaveDTO step2SaveDTO) {
        this.inflater = inflater;
        ParentID = parentID;
        this.context = context;
        this.activity = activity;
        this.parentActivity = parentActivity;
        this.step2SaveDTO = step2SaveDTO;
        this.init();
    }

    private void regObj() {
        this.nextBt.setOnClickListener(this);
        this.backBt.setOnClickListener(this);
//        this.no9 = this.view.findViewById(R.id.no9);
//        this.no9.setOnTouchListener(this);

        for (int i =0, j = cursorID.length ; i < j; i++) {
            FrameLayout bt = this.view.findViewById(cursorID[i]);
            bt.setTag(i);
            bt.setOnTouchListener(this);
        }
    }

    private void init() {
        this.parent = this.activity.findViewById(this.ParentID);
        this.parent.removeAllViews();

        this.view = inflater.inflate(R.layout.step2, this.parent, true);
        this.nextBt = this.view.findViewById(R.id.nextBt);
        this.backBt = this.view.findViewById(R.id.backBt);
        this.cursor = this.view.findViewById(R.id.cursor);
        this.cursor_frame = this.view.findViewById(R.id.cursor_frame);
        this.icon = this.view.findViewById(R.id.icon);
        this.icon_txt = this.view.findViewById(R.id.icon_text);

        this.regObj();

        this.cursor_frame.post(new Runnable() {
            @Override
            public void run() {
                cursorFrameHeight = cursor_frame.getHeight();
                cursorHeight();
            }
        });

    }

    private void cursorHeight() {

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height = (int) (cursorFrameHeight / 8.5);
        cursor.setLayoutParams(layoutParams);

        if ( step2SaveDTO == null ) {
            step2SaveDTO = new Step2SaveDTO(1);
        }

        getHeight(step2SaveDTO.getAche_power()-1);

//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , 30);
//        this.cursor.setLayoutParams(params);

    }

    private void setIconAndText(int num) {
        if ( num <= 3 ) {
            this.icon.setImageResource(R.drawable.step2_1);
            this.icon_txt.setText("약한 통증");
        } else if ( num <= 6 ) {
            this.icon.setImageResource(R.drawable.step2_2);
            this.icon_txt.setText("보통 통증");
        } else if ( num <= 9 ) {
            this.icon.setImageResource(R.drawable.step2_3);
            this.icon_txt.setText("심한 통증");
        } else {
            this.icon.setImageResource(R.drawable.step2_4);
            this.icon_txt.setText("상상할 수 있는\n가장 심한 통증");
        }
    }

    private void getHeight(int num) {
        this.step2SaveDTO.setAche_power(num+1);
        this.parentActivity.save2(this.step2SaveDTO);

        int margin = (cursorFrameHeight / 70);
        int stateH = (cursorFrameHeight / 10);
        if (num == 9) {
            cursor.setY(margin);
        } else if ( num == 8 || num == 7 ){
            cursor.setY(stateH * (10 - ( num + 1 )) + margin);
        } else if (num == 6 || num == 5 || num == 4) {
            cursor.setY(stateH * (10 - ( num + 1 )));
        } else if (num == 0) {
            cursor.setY((float) (cursorFrameHeight - stateH*1.25));
        } else {
            cursor.setY((float) ((cursorFrameHeight - ((cursorFrameHeight / 10) * (num+1))))-margin);
        }

        setIconAndText(num+1);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.nextBt:
                this.parentActivity.positionView(this.nextStepNum);
                break;

            case R.id.backBt:
                this.parentActivity.positionView(this.backStepNum);
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN ) {
            getHeight((int)v.getTag());
        }

        return false;
    }
}
