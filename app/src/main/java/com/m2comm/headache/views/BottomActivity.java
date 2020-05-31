package com.m2comm.headache.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m2comm.headache.R;


public class BottomActivity implements View.OnClickListener {

    private LayoutInflater inflater;
    private int ParentID;
    private LinearLayout parent;
    private Context context;
    private Activity activity;

    private int[] btImgs = {
            R.id.bt1Img,
            R.id.bt2Img,
            R.id.bt3Img,
            R.id.bt4Img,
            R.id.bt5Img
    };

    private int[] btTxts = {
            R.id.bt1Txt,
            R.id.bt2Txt,
            R.id.bt3Txt,
            R.id.bt4Txt,
            R.id.bt5Txt
    };

    private int[] defaultImg = {
            R.drawable.bottom_img_1,
            R.drawable.bottom_img_2,
            R.drawable.bottom_img_3,
            R.drawable.bottom_img_4,
            R.drawable.bottom_img_5
    };

    private int[] clickImg = {
            R.drawable.bottom_img_1_click,
            R.drawable.bottom_img_2_click,
            R.drawable.bottom_img_3_click,
            R.drawable.bottom_img_4_click,
            R.drawable.bottom_img_5_click
    };

    public BottomActivity(LayoutInflater inflater, int parentID, Context context, Activity activity) {
        this.inflater = inflater;
        ParentID = parentID;
        this.context = context;
        this.activity = activity;
        this.init();
    }

    private void init () {
        this.parent = this.activity.findViewById(this.ParentID);
        View view = inflater.inflate(R.layout.activity_bottom,this.parent,true);

        view.findViewById(R.id.bottomBt1).setOnClickListener(this);
        view.findViewById(R.id.bottomBt2).setOnClickListener(this);
        view.findViewById(R.id.bottomBt3).setOnClickListener(this);
        view.findViewById(R.id.bottomBt4).setOnClickListener(this);
        view.findViewById(R.id.bottomBt5).setOnClickListener(this);
    }

    private void changeColor ( int num ) {

        for ( int i = 0 , j = this.btImgs.length; i < j; i ++ ) {
            ImageView img = this.activity.findViewById( this.btImgs[i] );
            TextView txt = this.activity.findViewById( this.btTxts[i] );
            if ( i == num ) {
                img.setImageResource(this.clickImg[i]);
                txt.setTextColor(Color.parseColor("#000000"));
            } else {
                img.setImageResource(this.defaultImg[i]);
                txt.setTextColor(Color.parseColor("#959595"));
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch ( v.getId() ) {

            case R.id.bottomBt1:
             //   this.changeColor(0);
                intent = new Intent(this.activity , Main2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.activity.startActivity(intent);
                break;

            case R.id.bottomBt2:
             //   this.changeColor(1);
                intent = new Intent(this.activity , ContentStepActivity.class);
                this.activity.startActivity(intent);
                break;

            case R.id.bottomBt3:
                //this.changeColor(2);
                intent = new Intent(this.activity , DetailCalendarActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.activity.startActivity(intent);
                break;

            case R.id.bottomBt4:
                //this.changeColor(3);
                intent = new Intent(this.activity , AnalysisViewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.activity.startActivity(intent);
                break;

            case R.id.bottomBt5:
                //this.changeColor(4);
                intent = new Intent(this.activity , NewsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.activity.startActivity(intent);
                break;

        }

    }
}
