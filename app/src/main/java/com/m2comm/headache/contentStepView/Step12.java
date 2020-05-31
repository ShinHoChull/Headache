package com.m2comm.headache.contentStepView;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.m2comm.headache.DTO.Step12SaveDTO;
import com.m2comm.headache.R;
import com.m2comm.headache.views.ContentStepActivity;

public class Step12  implements View.OnClickListener {

    private LayoutInflater inflater;
    private int ParentID;
    private LinearLayout parent;
    private Context context;
    private Activity activity;

    private EditText editText;

    ContentStepActivity parentActivity;
    View view;

    //step10
    TextView  backBt;
    LinearLayout saveBt;

    int nextStepNum = 13;
    int backStepNum = 11;

    Step12SaveDTO step12SaveDTO;

    public Step12(LayoutInflater inflater, int parentID, Context context, Activity activity, ContentStepActivity parentActivity , Step12SaveDTO step12SaveDTO) {
        this.inflater = inflater;
        ParentID = parentID;
        this.context = context;
        this.activity = activity;
        this.step12SaveDTO = step12SaveDTO;
        this.parentActivity = parentActivity;
        this.init();
        this.regObj();
    }

    private void regObj () {
        this.saveBt.setOnClickListener(this);
        this.backBt.setOnClickListener(this);
    }


    private void init () {
        this.parent = this.activity.findViewById(this.ParentID);
        this.parent.removeAllViews();
        this.view = inflater.inflate(R.layout.step12,this.parent,true);
        this.saveBt = this.view.findViewById(R.id.saveBt);
        this.backBt = this.view.findViewById(R.id.backBt);
        this.editText = this.view.findViewById(R.id.step12_desc);

        if (this.step12SaveDTO == null) {
            this.step12SaveDTO = new Step12SaveDTO("");
        } else {
            this.editText.setText(this.step12SaveDTO.getDesc());
        }

        this.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    step12SaveDTO.setDesc(s.toString());
                    parentActivity.save12(step12SaveDTO);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveBt:
                //Toast.makeText(activity , "SAVE",Toast.LENGTH_SHORT).show();
                this.parentActivity.sendData();
                break;

            case R.id.backBt:
                this.parentActivity.positionView(this.backStepNum);
                break;
        }
    }


}
