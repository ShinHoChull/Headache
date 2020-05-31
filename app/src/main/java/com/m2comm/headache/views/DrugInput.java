package com.m2comm.headache.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.m2comm.headache.DTO.Step9Dates;
import com.m2comm.headache.R;
import com.m2comm.headache.contentStepView.Step1;
import com.m2comm.headache.contentStepView.Step10;
import com.m2comm.headache.contentStepView.Step11;
import com.m2comm.headache.contentStepView.Step4;
import com.m2comm.headache.contentStepView.Step5;
import com.m2comm.headache.contentStepView.Step7;
import com.m2comm.headache.contentStepView.Step8;
import com.m2comm.headache.contentStepView.Step9;

import java.util.ArrayList;

public class DrugInput extends AppCompatActivity implements View.OnClickListener {

    TextView cancelBt, successBt;
    EditText input1;
    LinearLayout parentV;
    Long startDateLong , endDateLong;


    private void regObj() {
        this.cancelBt.setOnClickListener(this);
        this.successBt.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_input);
        this.cancelBt = findViewById(R.id.cancelBt);
        this.successBt = findViewById(R.id.successBt);
        this.input1 = findViewById(R.id.input1);
        this.parentV = findViewById(R.id.parentV);
        this.regObj();

        Intent intent = getIntent();
        this.startDateLong = intent.getLongExtra("startDateLong",0L);
        this.endDateLong = intent.getLongExtra("endDateLong",0L);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            assert data != null;
            if ( requestCode == Step9.ETC9_INPUT2 ) {
                ArrayList<Step9Dates> dateArry = (ArrayList<Step9Dates>) data.getSerializableExtra("dates");
                Intent intent = new Intent();
                intent.putExtra("input1",this.input1.getText().toString());
                intent.putExtra("dates",dateArry);
                setResult(RESULT_OK, intent);
                finish();
            }
        }  else if ( resultCode == RESULT_CANCELED ) {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.cancelBt:
                finish();
                break;

            case R.id.successBt:
                if ( this.input1.getText().toString().equals("") ) {
                    Toast.makeText(this, "약물을 입력해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(this, Step9DatePicker.class);
                intent.putExtra("startDateLong",this.startDateLong);
                intent.putExtra("endDateLong",this.endDateLong);
                startActivityForResult(intent, Step9.ETC9_INPUT2);
                this.parentV.setVisibility(View.INVISIBLE);

                break;

        }
    }

}
