package com.m2comm.headache.contentStepView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m2comm.headache.DTO.Step6SaveDTO;
import com.m2comm.headache.R;
import com.m2comm.headache.views.ContentStepActivity;

public class Step6 implements View.OnClickListener {

    private LayoutInflater inflater;
    private int ParentID;
    private LinearLayout parent;
    private Context context;
    private Activity activity;
    private View view;
    ContentStepActivity parentActivity;
    //step6
    TextView nextBt , backBt , yesBt , noBt;

    int nextStepNum = 7;
    int backStepNum = 5;

    int[] checkBoxIds = {
            R.id.checkbox1,
            R.id.checkbox2,
            R.id.checkbox3,
            R.id.checkbox4,
            R.id.checkbox5,
            R.id.checkbox6,
            R.id.checkbox7,
    };

    int[] checkImgIds = {
            R.id.check1,
            R.id.check2,
            R.id.check3,
            R.id.check4,
            R.id.check5,
            R.id.check6,
            R.id.check7,
    };

    String[] check = {
        "N","N","N","N","N","N","N"
    };

    //두통 알수있는지 예true or 아니요false
    private boolean isHeadache = false;

    private Step6SaveDTO step6SaveDTO;

    public Step6(LayoutInflater inflater, int parentID, Context context, Activity activity , ContentStepActivity parentActivity , Step6SaveDTO step6SaveDTO) {
        this.inflater = inflater;
        ParentID = parentID;
        this.context = context;
        this.activity = activity;
        this.parentActivity = parentActivity;
        this.step6SaveDTO = step6SaveDTO;
        this.init();
        this.regObj();
    }

    private void regObj () {
        this.nextBt.setOnClickListener(this);
        this.backBt.setOnClickListener(this);
        this.yesBt.setOnClickListener(this);
        this.noBt.setOnClickListener(this);
    }

    private void init () {
        this.parent = this.activity.findViewById(this.ParentID);
        this.parent.removeAllViews();
        this.view = inflater.inflate(R.layout.step6,this.parent,true);

        this.backBt = this.view.findViewById(R.id.backBt);
        this.nextBt = this.view.findViewById(R.id.nextBt);
        this.yesBt = this.view.findViewById(R.id.yesBt);
        this.noBt = this.view.findViewById(R.id.noBt);

        for ( int i = 0 , j = this.checkBoxIds.length; i < j; i++ ) {
            LinearLayout l = this.view.findViewById(this.checkBoxIds[i]);
            l.setTag(i);
            l.setOnClickListener(this);
        }

        if ( this.step6SaveDTO == null ) {
            this.step6SaveDTO = new Step6SaveDTO("N","N","N",
                    "N","N","N","N","N");
            this.isHeadche(false);
        } else {
            this.isHeadche(this.step6SaveDTO.getAche_sign_yn().equals("Y"));
            this.check[0] = this.step6SaveDTO.getAche_sign1().equals("Y")? "N":"Y";
            this.check[1] = this.step6SaveDTO.getAche_sign2().equals("Y")? "N":"Y";
            this.check[2] = this.step6SaveDTO.getAche_sign3().equals("Y")? "N":"Y";
            this.check[3] = this.step6SaveDTO.getAche_sign4().equals("Y")? "N":"Y";
            this.check[4] = this.step6SaveDTO.getAche_sign5().equals("Y")? "N":"Y";
            this.check[5] = this.step6SaveDTO.getAche_sign6().equals("Y")? "N":"Y";
            this.check[6] = this.step6SaveDTO.getAche_sign7().equals("Y")? "N":"Y";

            for ( int i = 0 , j = this.check.length; i < j ; i ++ ) {
                this.checkedChange(i);
            }
        }

    }

    private void isHeadche(boolean isHeadache) {
        this.isHeadache = isHeadache;
        if ( isHeadache ) {
            this.yesBt.setBackgroundResource(R.drawable.step5_select_board);
            this.yesBt.setTextColor(Color.parseColor("#1EA2B6"));
            this.noBt.setTextColor(Color.parseColor("#C2C2C2"));
            this.noBt.setBackgroundColor(Color.TRANSPARENT);
        } else {
            this.yesBt.setBackgroundColor(Color.TRANSPARENT);
            this.yesBt.setTextColor(Color.parseColor("#C2C2C2"));
            this.noBt.setBackgroundResource(R.drawable.step5_no_select_board);
            this.noBt.setTextColor(Color.parseColor("#1EA2B6"));
        }
    }

    private void checkedChange (int num) {
        ImageView img = this.view.findViewById(this.checkImgIds[num]);
        if (check[num].equals("N")) {
            check[num] = "Y";
            img.setImageResource(R.drawable.login_check_on);
        } else {
            check[num] = "N";
            img.setImageResource(R.drawable.login_check_off);
        }
        if (num == 0)this.step6SaveDTO.setAche_sign1(check[num]);
        else if (num == 1)this.step6SaveDTO.setAche_sign2(check[num]);
        else if (num == 2)this.step6SaveDTO.setAche_sign3(check[num]);
        else if (num == 3)this.step6SaveDTO.setAche_sign4(check[num]);
        else if (num == 4)this.step6SaveDTO.setAche_sign5(check[num]);
        else if (num == 5)this.step6SaveDTO.setAche_sign6(check[num]);
        else if (num == 6)this.step6SaveDTO.setAche_sign7(check[num]);

        this.parentActivity.save6(this.step6SaveDTO);
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

            case R.id.yesBt:
                this.isHeadche(true);
                break;
            case R.id.noBt:
                this.isHeadche(false);
                break;

            case R.id.checkbox1:
            case R.id.checkbox2:
            case R.id.checkbox3:
            case R.id.checkbox4:
            case R.id.checkbox5:
            case R.id.checkbox6:
            case R.id.checkbox7:
                LinearLayout l = (LinearLayout)v;
                this.checkedChange((int)l.getTag());
                break;

        }
    }


}
