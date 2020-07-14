package com.m2comm.headache.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivitySetting1Binding;
import com.m2comm.headache.module.Custom_SharedPreferences;

public class Setting1 extends AppCompatActivity implements View.OnClickListener {

    BottomActivity bottomActivity;
    ActivitySetting1Binding binding;
    private Custom_SharedPreferences csp;

    private void regObj () {
        this.binding.backBt.setOnClickListener(this);
        this.binding.logoutBt.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting1);

        this.binding = DataBindingUtil.setContentView(this,R.layout.activity_setting1);
        this.binding.setSetting1(this);

        this.init();
        this.regObj();
    }

    private void init () {
        this.bottomActivity = new BottomActivity(getLayoutInflater() , R.id.bottom , this , this,-1);
        this.csp = new Custom_SharedPreferences(this);
        this.binding.id.setText(this.csp.getValue("user_id",""));
        this.binding.year.setText(this.csp.getValue("birth_year",""));
        this.binding.jender.setText(this.csp.getValue("sex","").equals("M") ? "남자":"여자");
        if ( this.csp.getValue("sex","").equals("M") ) {
            this.binding.meanV.setVisibility(View.GONE);
            this.binding.meanVLine.setVisibility(View.GONE);
        } else {
            if ( this.csp.getValue("mens","").equals("N") ) {
                this.binding.mean.setText("폐경");
            } else {
                this.binding.mean.setText("폐경안함");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.backBt:
                finish();
                break;
            case R.id.logoutBt:
                csp.put("isLogin",false);
                csp.put("user_sid","");
                csp.put("user_id","");
                csp.put("sex","");
                csp.put("birth_year","");
                csp.put("mens","");
                Intent intent = new Intent(this , LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}
