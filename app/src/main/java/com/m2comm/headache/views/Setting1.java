package com.m2comm.headache.views;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivitySetting1Binding;

public class Setting1 extends AppCompatActivity implements View.OnClickListener {

    BottomActivity bottomActivity;
    ActivitySetting1Binding binding;

    private void regObj () {
        this.binding.backBt.setOnClickListener(this);
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
        this.bottomActivity = new BottomActivity(getLayoutInflater() , R.id.bottom , this , this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.backBt:
                finish();
                break;
        }
    }
}
