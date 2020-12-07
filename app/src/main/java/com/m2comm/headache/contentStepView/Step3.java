package com.m2comm.headache.contentStepView;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.m2comm.headache.Adapter.Step4GridviewAdapter;
import com.m2comm.headache.DTO.Step2SaveDTO;
import com.m2comm.headache.DTO.Step3SaveDTO;
import com.m2comm.headache.DTO.Step4DTO;
import com.m2comm.headache.R;
import com.m2comm.headache.views.ContentStepActivity;

import java.util.ArrayList;

public class Step3 implements View.OnClickListener {

    private LayoutInflater inflater;
    private int ParentID;
    private LinearLayout parent , view2;
    private Context context;
    private Activity activity;
    private ContentStepActivity parentActivity;
    private View view;
    private ScrollView scrollView;

    //step3
    TextView nextBt , backBt;
    ImageView backGoBt;

    int nextStepNum = 4;
    int backStepNum = 2;

    FrameLayout bt1 , bt2 , bt3 , bt4 ,bt5 ,bt6 ,bt7 ,bt8 ,bt9 ,bt10 ,bt11 , bt12 , bt13 , bt14 ;

    Step3SaveDTO step3SaveDTO;

    boolean[] isCheckImgs = {
            false,false,false,false,
            false,false,false,false,
            false,false,false,false,
            false,false,false,false,
            false,false
    };

    int[] btIds = {
            R.id.bt1,
            R.id.bt2,
            R.id.bt3,
            R.id.bt4,
            R.id.bt5_1,
            R.id.bt6_1,
            R.id.bt5_2,
            R.id.bt6_2,
            R.id.bt7,
            R.id.bt8,
            R.id.bt9,
            R.id.bt10,
            R.id.bt11,
            R.id.bt12,
            R.id.bt13_1,
            R.id.bt13_2,
            R.id.bt14,
            R.id.bt15,
    };

    RelativeLayout r;

    public Step3(LayoutInflater inflater, int parentID, Context context, Activity activity , ContentStepActivity parentActivity , Step3SaveDTO step3SaveDTO) {
        this.inflater = inflater;
        ParentID = parentID;
        this.context = context;
        this.activity = activity;
        this.parentActivity = parentActivity;
        this.step3SaveDTO = step3SaveDTO;
        this.init();
    }

    private void regObj () {
        this.nextBt = this.view.findViewById(R.id.nextBt);
        this.nextBt.setOnClickListener(this);
        this.backBt = this.view.findViewById(R.id.backBt);
        this.backBt.setOnClickListener(this);

        for ( int i = 0 , j = this.btIds.length; i < j; i ++ ) {
            FrameLayout f = this.view.findViewById(this.btIds[i]);
            f.setTag(i);
            f.setOnClickListener(this);
            for ( int k = 0 , l = f.getChildCount(); k < l ; k++ ) {
                ImageView index = (ImageView) f.getChildAt(k);
                index.setVisibility(View.GONE);
            }
        }
        this.backGoBt = this.view.findViewById(R.id.backGoBt);
        this.backGoBt.setOnClickListener(this);

        this.view2 = this.view.findViewById(R.id.view2);
        this.scrollView = this.view.findViewById(R.id.scrollview);
    }

    private void init () {
        this.parent = this.activity.findViewById(this.ParentID);
        this.parent.removeAllViews();
        this.view = inflater.inflate(R.layout.step3,this.parent,true);

        this.regObj();

        if ( step3SaveDTO == null ) {
            step3SaveDTO = new Step3SaveDTO(
                    "N","N","N","N",
                    "N","N","N","N",
                    "N","N","N","N",
                    "N","N","N","N",
                    "N","N");

        } else {

            this.isCheckImgs[0] = !this.step3SaveDTO.getAche_location1().equals("Y");
            this.isCheckImgs[1] = !this.step3SaveDTO.getAche_location2().equals("Y");
            this.isCheckImgs[2] = !this.step3SaveDTO.getAche_location3().equals("Y");
            this.isCheckImgs[3] = !this.step3SaveDTO.getAche_location4().equals("Y");
            this.isCheckImgs[4] = !this.step3SaveDTO.getAche_location5().equals("Y");
            this.isCheckImgs[5] = !this.step3SaveDTO.getAche_location6().equals("Y");
            this.isCheckImgs[6] = !this.step3SaveDTO.getAche_location7().equals("Y");
            this.isCheckImgs[7] = !this.step3SaveDTO.getAche_location8().equals("Y");
            this.isCheckImgs[8] = !this.step3SaveDTO.getAche_location9().equals("Y");
            this.isCheckImgs[9] = !this.step3SaveDTO.getAche_location10().equals("Y");
            this.isCheckImgs[10] = !this.step3SaveDTO.getAche_location11().equals("Y");
            this.isCheckImgs[11] = !this.step3SaveDTO.getAche_location12().equals("Y");
            this.isCheckImgs[12] = !this.step3SaveDTO.getAche_location13().equals("Y");
            this.isCheckImgs[13] = !this.step3SaveDTO.getAche_location14().equals("Y");
            this.isCheckImgs[14] = !this.step3SaveDTO.getAche_location15().equals("Y");
            this.isCheckImgs[15] = !this.step3SaveDTO.getAche_location16().equals("Y");
            this.isCheckImgs[16] = !this.step3SaveDTO.getAche_location17().equals("Y");
            this.isCheckImgs[17] = !this.step3SaveDTO.getAche_location18().equals("Y");

            for ( int i = 0 , j = this.isCheckImgs.length; i < j; i++ ) {
                FrameLayout f = this.view.findViewById(this.btIds[i]);
                checkImg(f);
            }
        }

        this.scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if ( scrollView.getScrollY() >  view2.getHeight()) {
                    backGoBt.setVisibility(View.GONE);
                } else {
                    backGoBt.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void saveChecked() {
        this.step3SaveDTO.setAche_location1(this.isCheckImgs[0] ? "Y" : "N");
        this.step3SaveDTO.setAche_location2(this.isCheckImgs[1] ? "Y" : "N");
        this.step3SaveDTO.setAche_location3(this.isCheckImgs[2] ? "Y" : "N");
        this.step3SaveDTO.setAche_location4(this.isCheckImgs[3] ? "Y" : "N");
        this.step3SaveDTO.setAche_location5(this.isCheckImgs[4] ? "Y" : "N");
        this.step3SaveDTO.setAche_location6(this.isCheckImgs[5] ? "Y" : "N");
        this.step3SaveDTO.setAche_location7(this.isCheckImgs[6] ? "Y" : "N");
        this.step3SaveDTO.setAche_location8(this.isCheckImgs[7] ? "Y" : "N");
        this.step3SaveDTO.setAche_location9(this.isCheckImgs[8] ? "Y" : "N");
        this.step3SaveDTO.setAche_location10(this.isCheckImgs[9] ? "Y" : "N");
        this.step3SaveDTO.setAche_location11(this.isCheckImgs[10] ? "Y" : "N");
        this.step3SaveDTO.setAche_location12(this.isCheckImgs[11] ? "Y" : "N");
        this.step3SaveDTO.setAche_location13(this.isCheckImgs[12] ? "Y" : "N");
        this.step3SaveDTO.setAche_location14(this.isCheckImgs[13] ? "Y" : "N");
        this.step3SaveDTO.setAche_location15(this.isCheckImgs[14] ? "Y" : "N");
        this.step3SaveDTO.setAche_location16(this.isCheckImgs[15] ? "Y" : "N");
        this.step3SaveDTO.setAche_location17(this.isCheckImgs[16] ? "Y" : "N");
        this.step3SaveDTO.setAche_location18(this.isCheckImgs[17] ? "Y" : "N");

        this.parentActivity.save3(this.step3SaveDTO);
    }

    private void checkImg ( FrameLayout frameLayout ) {
        boolean isCheck = this.isCheckImgs[(int)frameLayout.getTag()];
        if ( isCheck ) {
            this.isCheckImgs[(int)frameLayout.getTag()] = false;
            for ( int k = 0 , l = frameLayout.getChildCount(); k < l ; k++ ) {
                ImageView index = (ImageView) frameLayout.getChildAt(k);
                index.setVisibility(View.GONE);
            }
        } else {
            this.isCheckImgs[(int)frameLayout.getTag()] = true;
            for ( int k = 0 , l = frameLayout.getChildCount(); k < l ; k++ ) {
                ImageView index = (ImageView) frameLayout.getChildAt(k);
                index.setVisibility(View.VISIBLE);
            }
        }

        this.saveChecked();

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

            case R.id.backGoBt:
                this.scrollView.smoothScrollTo(0, (int) this.view2.getY());
                this.backGoBt.setVisibility(View.GONE);
                break;

            case R.id.bt1:
            case R.id.bt2:
            case R.id.bt3:
            case R.id.bt4:
            case R.id.bt5_1:
            case R.id.bt5_2:
            case R.id.bt6_1:
            case R.id.bt6_2:
            case R.id.bt7:
            case R.id.bt8:
            case R.id.bt9:
            case R.id.bt10:
            case R.id.bt11:
            case R.id.bt12:
            case R.id.bt13_1:
            case R.id.bt13_2:
            case R.id.bt14:
            case R.id.bt15:
                this.checkImg((FrameLayout) v);
                break;
        }
    }
}
