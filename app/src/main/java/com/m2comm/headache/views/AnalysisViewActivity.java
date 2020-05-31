package com.m2comm.headache.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivityAnalysisViewBinding;

public class AnalysisViewActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityAnalysisViewBinding binding;
    BottomActivity bottomActivity;

    private void regObj () {
        this.binding.backBt.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_view);
        this.binding = DataBindingUtil.setContentView(this,R.layout.activity_analysis_view);
        this.binding.setAnaysis(this);
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
