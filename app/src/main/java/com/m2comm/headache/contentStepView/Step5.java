package com.m2comm.headache.contentStepView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m2comm.headache.Adapter.Step4GridviewAdapter;
import com.m2comm.headache.Adapter.Step5GridviewAdapter;
import com.m2comm.headache.DTO.Step4DTO;
import com.m2comm.headache.DTO.Step5EtcDTO;
import com.m2comm.headache.DTO.Step5SaveDTO;
import com.m2comm.headache.R;
import com.m2comm.headache.views.ContentStepActivity;
import com.m2comm.headache.views.EtcInputActivity;

import java.util.ArrayList;

public class Step5 implements View.OnClickListener , AdapterView.OnItemClickListener{

    public final static int ETC5_INPUT = 555;

    private LayoutInflater inflater;
    private int ParentID;
    private LinearLayout parent , step5LinearView , step5BottomV ,step5BottomV2 ,  timeSettingV , step5Line ,step5gridVParent;
    private Context context;
    private Activity activity;
    private EditText hour , min;
    private GridView gridView;
    private Step5GridviewAdapter adapter;
    //private ArrayList<Step5EtcDTO> list;
    ContentStepActivity parentActivity;
    View view;

    //두통 알수있는지 예true or 아니요false
    private boolean isHeadache = false;

    //step5
    TextView nextBt , backBt , yesBt , noBt ,nextBt2 , backBt2;

    int nextStepNum = 6;
    int backStepNum = 4;

    Step5SaveDTO step5SaveDTO;

    public Step5(LayoutInflater inflater, int parentID, Context context, Activity activity , ContentStepActivity parentActivity , Step5SaveDTO step5SaveDTO) {
        this.inflater = inflater;
        ParentID = parentID;
        this.context = context;
        this.activity = activity;
        this.parentActivity = parentActivity;
        this.step5SaveDTO = step5SaveDTO;
        this.init();
        this.regObj();
        this.viewReset();
    }

    private void regObj () {
        this.nextBt.setOnClickListener(this);
        this.backBt.setOnClickListener(this);

        this.nextBt2.setOnClickListener(this);
        this.backBt2.setOnClickListener(this);

        this.yesBt.setOnClickListener(this);
        this.noBt.setOnClickListener(this);


    }

    private void init () {
        this.parent = this.activity.findViewById(this.ParentID);
        this.parent.removeAllViews();
        this.view = inflater.inflate(R.layout.step5,this.parent,true);
        this.gridView = view.findViewById(R.id.step5_gridV);
        this.hour = this.view.findViewById(R.id.hourInput);
        this.min = this.view.findViewById(R.id.minuteInput);
        this.step5LinearView = this.view.findViewById(R.id.step5View1);
        this.step5BottomV = this.view.findViewById(R.id.step5BottomV);
        this.nextBt = this.view.findViewById(R.id.nextBt);
        this.backBt = this.view.findViewById(R.id.backBt);
        this.step5BottomV2 = this.view.findViewById(R.id.step5BottomV2);
        this.nextBt2 = this.view.findViewById(R.id.nextBt2);
        this.backBt2 = this.view.findViewById(R.id.backBt2);

        this.yesBt = this.view.findViewById(R.id.yesBt);
        this.noBt = this.view.findViewById(R.id.noBt);
        this.timeSettingV = this.view.findViewById(R.id.timeSettingV);
        this.step5Line = this.view.findViewById(R.id.step5Line);
        this.step5gridVParent = this.view.findViewById(R.id.step5gridVParent);

        if ( this.step5SaveDTO == null ) {
//            this.step5SaveDTO = new Step5SaveDTO("N",0,0,
//                    "N","N","N","N",
//                    "N","N","N","N",new ArrayList<Step5EtcDTO>());
//            this.isHeadche(false);
//            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default1,R.drawable.step5_type_click1,"아플 것 같은\n느낌",false,false,false,0 , "N"));
//            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default2,R.drawable.step5_type_click2,"뒷목통증\n뻐근함/당김",false,false,false,0 , "N"));
//            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default3,R.drawable.step5_type_click3,"하품",false,false,false,0 , "N"));
//            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default4,R.drawable.step5_type_click4,"피로",false,false,false,0 , "N"));
//            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default5,R.drawable.step5_type_click5,"집중력저하",false,false,false,0 , "N"));
//            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default6,R.drawable.step5_type_click6,"기분변화",false,false,false,0 , "N"));
//            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default7,R.drawable.step5_type_click7,"식욕변화",false,false,false,0 , "N"));
//            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default8,R.drawable.step5_type_click8,"빛/소리/\n냄새에 과민",false,false,false,0 , "N"));
//            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step_type_etc,R.drawable.step_type_etc,"기타",false,true, false,0 , "N"));
        } else {
            this.isHeadche(this.step5SaveDTO.getAche_realize_yn().equals("Y"));
            if ( this.step5SaveDTO.getAche_realize_hour() == 0 ) {
                this.hour.setText("");
            } else {
                this.hour.setText(String.valueOf(this.step5SaveDTO.getAche_realize_hour()));
            }

            if ( this.step5SaveDTO.getAche_realize_minute() == 0 ) {
                this.min.setText("");
            } else {
                this.min.setText(String.valueOf(this.step5SaveDTO.getAche_realize_minute()));
            }

            this.step5SaveDTO.getArrayList().get(0).setClick(this.step5SaveDTO.getAche_realize1().equals("Y"));
            this.step5SaveDTO.getArrayList().get(1).setClick(this.step5SaveDTO.getAche_realize2().equals("Y"));
            this.step5SaveDTO.getArrayList().get(2).setClick(this.step5SaveDTO.getAche_realize3().equals("Y"));
            this.step5SaveDTO.getArrayList().get(3).setClick(this.step5SaveDTO.getAche_realize4().equals("Y"));
            this.step5SaveDTO.getArrayList().get(4).setClick(this.step5SaveDTO.getAche_realize5().equals("Y"));
            this.step5SaveDTO.getArrayList().get(5).setClick(this.step5SaveDTO.getAche_realize6().equals("Y"));
            this.step5SaveDTO.getArrayList().get(6).setClick(this.step5SaveDTO.getAche_realize7().equals("Y"));
            this.step5SaveDTO.getArrayList().get(7).setClick(this.step5SaveDTO.getAche_realize8().equals("Y"));
        }

        this.adapter = new Step5GridviewAdapter( this.step5SaveDTO.getArrayList() , this.activity.getLayoutInflater());
        this.gridView.setAdapter(this.adapter);
        this.gridView.setOnItemClickListener(this);

        this.hour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    step5SaveDTO.setAche_realize_hour(Integer.parseInt(s.toString()));
                    parentActivity.save5(step5SaveDTO);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        this.min.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    step5SaveDTO.setAche_realize_minute(Integer.parseInt(s.toString()));
                    parentActivity.save5(step5SaveDTO);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
        Step5EtcDTO row = this.step5SaveDTO.getArrayList().get(position);
        if ( row.getEtcBt() ) {
            getEtcActivity();
        } else {
            if ( row.getClick() ){
                if (position == 0) this.step5SaveDTO.setAche_realize1("N");
                else if (position == 1) this.step5SaveDTO.setAche_realize2("N");
                else if (position == 2) this.step5SaveDTO.setAche_realize3("N");
                else if (position == 3) this.step5SaveDTO.setAche_realize4("N");
                else if (position == 4) this.step5SaveDTO.setAche_realize5("N");
                else if (position == 5) this.step5SaveDTO.setAche_realize6("N");
                else if (position == 6) this.step5SaveDTO.setAche_realize7("N");
                else if (position == 7) this.step5SaveDTO.setAche_realize8("N");
                row.setVal("N");
                row.setClick(false);
            } else {
                if (position == 0) this.step5SaveDTO.setAche_realize1("Y");
                else if (position == 1) this.step5SaveDTO.setAche_realize2("Y");
                else if (position == 2) this.step5SaveDTO.setAche_realize3("Y");
                else if (position == 3) this.step5SaveDTO.setAche_realize4("Y");
                else if (position == 4) this.step5SaveDTO.setAche_realize5("Y");
                else if (position == 5) this.step5SaveDTO.setAche_realize6("Y");
                else if (position == 6) this.step5SaveDTO.setAche_realize7("Y");
                else if (position == 7) this.step5SaveDTO.setAche_realize8("Y");
                row.setVal("Y");
                row.setClick(true);
            }
            this.parentActivity.save5(this.step5SaveDTO);
            reloadListView();
        }
    }

    public void addListView (String etc) {
        this.step5SaveDTO.getArrayList().remove(this.step5SaveDTO.getArrayList().size()-1);
        this.step5SaveDTO.getArrayList().add( new Step5EtcDTO(R.drawable.step_type_etc_add, R.drawable.step_type_etc_add, etc,true,false, true,0,"Y") );
        this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step_type_etc,R.drawable.step_type_etc,"기타",false,true, false,0 , "N"));
        this.parentActivity.save5(this.step5SaveDTO);
        reloadListView();
    }

    private void reloadListView() {
        viewReset();
        this.adapter = new Step5GridviewAdapter( this.step5SaveDTO.getArrayList() , this.activity.getLayoutInflater() );
        this.gridView.setAdapter( this.adapter );
        this.adapter.notifyDataSetChanged();
    }

    private void getEtcActivity () {
        Intent intent = new Intent(this.activity , EtcInputActivity.class);
        this.activity.startActivityForResult(intent,ETC5_INPUT);
    }

    private void viewReset() {
        step5BottomV.post(new Runnable() {
            @Override
            public void run() {
                final int titleHeight = step5BottomV.getHeight();
                step5LinearView.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("gridViewHeight=",((int)Math.ceil((double)step5SaveDTO.getArrayList().size() / 4) )+"_");
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.height = (int) (titleHeight + (400 * Math.ceil((double)step5SaveDTO.getArrayList().size() / 4)));
                        step5LinearView.setLayoutParams(layoutParams);
                    }
                });
            }
        });
    }

    private void isHeadche(boolean isHeadache) {
        this.isHeadache = isHeadache;
        if ( isHeadache ) {
            this.step5SaveDTO.setAche_realize_yn("Y");
            this.parentActivity.save5(this.step5SaveDTO);

            this.yesBt.setBackgroundResource(R.drawable.step5_select_board);
            this.yesBt.setTextColor(Color.parseColor("#1EA2B6"));
            this.noBt.setTextColor(Color.parseColor("#C2C2C2"));;
            this.noBt.setBackgroundColor(Color.TRANSPARENT);

            this.timeSettingV.setVisibility(View.VISIBLE);
            this.step5Line.setVisibility(View.VISIBLE);
            this.step5gridVParent.setVisibility(View.VISIBLE);
            this.step5BottomV2.setVisibility(View.GONE);
            this.step5BottomV.setVisibility(View.VISIBLE);

        } else {
            this.step5SaveDTO.setAche_realize_yn("N");
            this.step5SaveDTO.setAche_realize_hour(0);
            this.step5SaveDTO.setAche_realize_minute(0);
            this.parentActivity.save5(this.step5SaveDTO);

            this.yesBt.setBackgroundColor(Color.TRANSPARENT);
            this.yesBt.setTextColor(Color.parseColor("#C2C2C2"));
            this.noBt.setBackgroundResource(R.drawable.step5_no_select_board);
            this.noBt.setTextColor(Color.parseColor("#1EA2B6"));

            this.timeSettingV.setVisibility(View.INVISIBLE);
            this.step5Line.setVisibility(View.GONE);
            this.step5gridVParent.setVisibility(View.GONE);
            this.step5BottomV2.setVisibility(View.VISIBLE);
            this.step5BottomV.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextBt2:
            case R.id.nextBt:
                this.parentActivity.positionView(this.nextStepNum);
                break;

            case R.id.backBt2:
            case R.id.backBt:
                this.parentActivity.positionView(this.backStepNum);
                break;

            case R.id.yesBt:
                this.isHeadche(true);
                break;
            case R.id.noBt:
                this.isHeadche(false);
                break;
        }
    }

}
