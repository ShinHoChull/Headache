package com.m2comm.headache.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.m2comm.headache.Global;
import com.m2comm.headache.R;

public class EtcInputActivity extends AppCompatActivity implements View.OnClickListener {

    TextView cancelBt, successBt;
    EditText input1;

    private void regObj() {
        this.cancelBt = findViewById(R.id.cancelBt);
        this.cancelBt.setOnClickListener(this);
        this.successBt = findViewById(R.id.successBt);
        this.successBt.setOnClickListener(this);
        this.input1 = findViewById(R.id.input1);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etc_input);
        this.regObj();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.cancelBt:
                finish();
                break;

            case R.id.successBt:
                if ( this.input1.getText().toString().equals("") ) {
                    Toast.makeText(this, "의견을 입력해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra("input1",this.input1.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                break;

        }
    }
}
