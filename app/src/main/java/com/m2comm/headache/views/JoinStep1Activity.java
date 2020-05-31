package com.m2comm.headache.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.m2comm.headache.R;
import com.m2comm.headache.contentStepView.Step1;
import com.m2comm.headache.databinding.ActivityJoinStep1Binding;

import java.util.ArrayList;

public class JoinStep1Activity extends AppCompatActivity implements View.OnClickListener {

    ActivityJoinStep1Binding binding;
    private boolean ALL_CHECK = false;
    public static int CLAUSE_CODE = 999;

    boolean[] isChecks = {
      false,false,false,false,
    };
    ArrayList<ImageView> imageViews;


    private void regObj () {
        this.binding.backBt.setOnClickListener(this);
        this.binding.nextBt.setOnClickListener(this);
        this.binding.allCheck.setOnClickListener(this);
        this.binding.check1.setOnClickListener(this);
        this.binding.check2.setOnClickListener(this);
        this.binding.check3.setOnClickListener(this);
        this.binding.check4.setOnClickListener(this);
        this.binding.allCheck.setOnClickListener(this);
        this.binding.view1.setOnClickListener(this);
        this.binding.view2.setOnClickListener(this);
        this.binding.view3.setOnClickListener(this);
        this.binding.view4.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_step1);
        this.binding = DataBindingUtil.setContentView(this,R.layout.activity_join_step1);
        this.binding.setJoinStep1(this);
        this.init();
        this.regObj();
    }

    private void init () {
        //Check BOX
        this.imageViews = new ArrayList<>();
        this.imageViews.add(this.binding.check1);
        this.imageViews.add(this.binding.check2);
        this.imageViews.add(this.binding.check3);
        this.imageViews.add(this.binding.check4);

        for( int i = 0, j = this.imageViews.size() ; i < j; i++ ) {
            this.imageViews.get(i).setTag(i);
        }
    }

    private void checked ( int num ) {
        ImageView img = this.imageViews.get(num);
        if ( this.isChecks[num] ) {
            this.isChecks[num] = false;
            img.setImageResource(R.drawable.login_check_off);
        } else {
            this.isChecks[num] = true;
            img.setImageResource(R.drawable.login_check_on);
        }
    }

    private void allCheck() {

        for ( int i = 0 , j = this.imageViews.size(); i < j; i ++) {
            ImageView img = this.imageViews.get(i);

            if ( this.ALL_CHECK ) {
                this.isChecks[i] = false;
                this.binding.allCheck.setImageResource(R.drawable.login_check_off);
                img.setImageResource(R.drawable.login_check_off);
            } else {
                this.isChecks[i] = true;
                this.binding.allCheck.setImageResource(R.drawable.login_check_on);
                img.setImageResource(R.drawable.login_check_on);
            }
        }

        this.ALL_CHECK = !this.ALL_CHECK;
        Log.d("allCheck",this.ALL_CHECK+"");
    }

    private void viewClause(int getClauseNum) {
        Intent intent = new Intent( this , ClauseView.class );
        intent.putExtra("code",getClauseNum);
        startActivityForResult(intent,CLAUSE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if ( requestCode == JoinStep1Activity.CLAUSE_CODE ) {

            }

        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.backBt:
                finish();
                break;

            case R.id.nextBt:

                for ( int i = 0 , j = this.isChecks.length; i < j; i++ ) {
                    if ( !this.isChecks[i] ) {
                        Toast.makeText(this , "필수 이용약관을 동의 해주세요.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                intent = new Intent(this , JoinStep2Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                break;

            case R.id.check1:
            case R.id.check2:
            case R.id.check3:
            case R.id.check4:
                ImageView img = (ImageView)v;
                this.checked((int)img.getTag());
                break;

            case R.id.all_check:
                this.allCheck();
                break;

            case R.id.view1:
                this.viewClause(0);
                break;

            case R.id.view2:
                this.viewClause(1);
                break;

            case R.id.view3:
                this.viewClause(2);
                break;

            case R.id.view4:
                this.viewClause(3);
                break;

        }
    }
}
