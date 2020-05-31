package com.m2comm.headache.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivitySettingMainBinding;

public class SettingMainActivity extends AppCompatActivity implements View.OnClickListener {

    BottomActivity bottomActivity;
    ActivitySettingMainBinding binding;

    private void regObj () {
        this.binding.backBt.setOnClickListener(this);
        this.binding.option1.setOnClickListener(this);
        this.binding.option2.setOnClickListener(this);
        this.binding.option3.setOnClickListener(this);
        this.binding.option4.setOnClickListener(this);
        this.binding.option5.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_main);

        this.binding = DataBindingUtil.setContentView(this,R.layout.activity_setting_main);
        this.binding.setSettingMain(this);

        this.init();
        this.regObj();
    }

    private void init () {
        this.bottomActivity = new BottomActivity(getLayoutInflater() , R.id.bottom , this , this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.backBt:
                finish();
                break;

            case R.id.option1:
                intent = new Intent( this , Setting1.class);
                startActivity(intent);
                break;

            case R.id.option2:
                intent = new Intent( this , Setting2.class);
                startActivity(intent);
                break;

            case R.id.option3:

                break;

            case R.id.option4:
                intent = new Intent( this , Setting4.class);
                startActivity(intent);
                break;

            case R.id.option5:
                break;

        }
    }
}
