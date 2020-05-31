package com.m2comm.headache.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivityClauseViewBinding;

public class ClauseView extends AppCompatActivity implements View.OnClickListener {

    ActivityClauseViewBinding binding;

    private void regObj () {
        this.binding.successBt.setOnClickListener(this);
    }

    private void init() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clause_view);
        this.binding = DataBindingUtil.setContentView(this,R.layout.activity_clause_view);
        this.binding.setClause(this);
        this.init();
        this.regObj();
    }

    @Override
    public void onClick(View v) {

        switch ( v.getId() ) {
            case R.id.successBt:
                Intent intent = getIntent();
                intent.putExtra("success",true);
                setResult(JoinStep1Activity.CLAUSE_CODE,intent);
                finish();
                break;
        }
    }
}
