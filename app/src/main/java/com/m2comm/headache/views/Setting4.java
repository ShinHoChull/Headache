package com.m2comm.headache.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivitySetting4Binding;

public class Setting4 extends AppCompatActivity implements View.OnClickListener {

    BottomActivity bottomActivity;
    ActivitySetting4Binding binding;
    private boolean isCheck = true;

    private void regObj () {
        this.binding.backBt.setOnClickListener(this);
        this.binding.option2.setOnClickListener(this);
        this.binding.setting3CheckBt.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting4);

        this.binding = DataBindingUtil.setContentView(this,R.layout.activity_setting4);
        this.binding.setSetting4(this);

        this.init();
        this.regObj();
    }

    private void isCheck() {
        if ( this.isCheck ) {
            this.binding.setting3CheckBt.setImageResource(R.drawable.setting_cehck_off);
            this.isCheck = false;
        } else {
            this.binding.setting3CheckBt.setImageResource(R.drawable.setting_cehck_on);
            this.isCheck = true;
        }
    }

    private void init () {
        this.bottomActivity = new BottomActivity(getLayoutInflater() , R.id.bottom , this , this,-1);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.backBt:
                finish();
                break;

            case R.id.option2:
                intent = new Intent(this , AlarmListActivity.class);
                startActivity(intent);
                break;

            case R.id.setting3CheckBt:
                this.isCheck();
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}
