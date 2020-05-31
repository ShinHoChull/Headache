package com.m2comm.headache.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivityJoinStepSuccessBinding;

public class JoinStepSuccess extends AppCompatActivity implements View.OnClickListener {

    ActivityJoinStepSuccessBinding binding;

    private void regObj () {
        this.binding.joinSuccessBt.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_step_success);
        this.binding = DataBindingUtil.setContentView(this,R.layout.activity_join_step_success);
        this.binding.setJoinSuccess(this);
        this.init();
        this.regObj();
    }

    private void init () {

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.joinSuccessBt:
                intent = new Intent(this , LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

        }
    }
}
