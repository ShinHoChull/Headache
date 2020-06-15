package com.m2comm.headache.contentStepView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m2comm.headache.Adapter.Step4GridviewAdapter;
import com.m2comm.headache.Adapter.Step7GridviewAdapter;
import com.m2comm.headache.Adapter.Step8GridviewAdapter;
import com.m2comm.headache.DTO.Step8EtcDTO;
import com.m2comm.headache.DTO.Step8EtcDTO;
import com.m2comm.headache.DTO.Step8SaveDTO;
import com.m2comm.headache.R;
import com.m2comm.headache.views.ContentStepActivity;
import com.m2comm.headache.views.EtcInputActivity;

import java.util.ArrayList;

public class Step8 implements View.OnClickListener , AdapterView.OnItemClickListener{

    public final static int ETC8_INPUT = 888;

    private LayoutInflater inflater;
    private int ParentID;
    private LinearLayout parent , step8LinearView , step8BottomV , step5BottomV2 , step8Line , step8ParentV;
    private Context context;
    private Activity activity;

    private GridView gridView;
    private Step8GridviewAdapter adapter;
    View view;
    ContentStepActivity parentActivity;

    //step8
    TextView nextBt , backBt , yesBt , noBt , nextBt2 , backBt2;

    int nextStepNum = 9;
    int backStepNum = 7;

    //두통 알수있는지 예true or 아니요false
    private boolean isHeadache = false;
    Step8SaveDTO step8SaveDTO;

    public Step8(LayoutInflater inflater, int parentID, Context context, Activity activity, ContentStepActivity parentActivity , Step8SaveDTO step8SaveDTO) {
        this.inflater = inflater;
        ParentID = parentID;
        this.context = context;
        this.activity = activity;
        this.parentActivity = parentActivity;
        this.step8SaveDTO = step8SaveDTO;
        this.init();
        this.regObj();
        this.viewReset();
    }

    private void regObj () {
        this.nextBt.setOnClickListener(this);
        this.backBt.setOnClickListener(this);
        this.yesBt.setOnClickListener(this);
        this.noBt.setOnClickListener(this);
        this.nextBt2.setOnClickListener(this);
        this.backBt2.setOnClickListener(this);
    }

    private void init () {
        this.parent = this.activity.findViewById(this.ParentID);
        this.parent.removeAllViews();
        this.view = inflater.inflate(R.layout.step8,this.parent,true);

        this.nextBt = this.view.findViewById(R.id.nextBt);
        this.backBt = this.view.findViewById(R.id.backBt);
        this.yesBt = this.view.findViewById(R.id.yesBt);
        this.noBt = this.view.findViewById(R.id.noBt);
        this.step8LinearView = this.view.findViewById(R.id.step8View1);
        this.step8BottomV = this.view.findViewById(R.id.step8BottomV);
        this.gridView = view.findViewById(R.id.step5_gridV);
        this.step8Line = view.findViewById(R.id.step8Line);
        this.step8ParentV = view.findViewById(R.id.step8ParentV);

        this.step5BottomV2 = this.view.findViewById(R.id.step5BottomV2);
        this.nextBt2 = this.view.findViewById(R.id.nextBt2);
        this.backBt2 = this.view.findViewById(R.id.backBt2);
        

        if ( this.step8SaveDTO == null ) {
            this.step8SaveDTO = new Step8SaveDTO("N","N","N","N","N",
                    "N","N","N","N",
                    "N","N","N","N",
                    "N","N","N",new ArrayList<Step8EtcDTO>());
            this.isHeadche(false);

            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default1,R.drawable.step8_type_click1,"스트레스",false,false,false,0 , "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default2,R.drawable.step8_type_click2,"피로",false,false,false,0 , "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default3,R.drawable.step8_type_click3,"수면부족",false,false,false,0 , "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default4,R.drawable.step8_type_click4,"낮잠 또는\n늦잠",false,false,false,0 , "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default5,R.drawable.step8_type_click5,"주말",false,false,false,0 , "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default6,R.drawable.step8_type_click6,"굶음",false,false,false,0 , "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default7,R.drawable.step8_type_click7,"과식",false,false,false,0 , "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default8,R.drawable.step8_type_click8,"체함",false,false,false,0 , "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default9,R.drawable.step8_type_click9,"빛",false,false,false,0 , "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default10,R.drawable.step8_type_click10,"소리",false,false,false,0 , "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default11,R.drawable.step8_type_click11,"냄새",false,false,false,0 , "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default12,R.drawable.step8_type_click12,"감기",false,false,false,0 , "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default13,R.drawable.step8_type_click13,"운동",false,false,false,0 , "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default14,R.drawable.step8_type_click14,"술",false,false,false,0 , "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default15,R.drawable.step8_type_click15,"월경",false,false,false,0 , "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step_type_etc,R.drawable.step_type_etc,"기타",false,true,false,0 , "N"));

        } else {

            this.isHeadche(this.step8SaveDTO.getAche_factor_yn().equals("Y"));
            this.step8SaveDTO.getArrayList().get(0).setClick(this.step8SaveDTO.getAche_factor1().equals("Y"));
            this.step8SaveDTO.getArrayList().get(1).setClick(this.step8SaveDTO.getAche_factor2().equals("Y"));
            this.step8SaveDTO.getArrayList().get(2).setClick(this.step8SaveDTO.getAche_factor3().equals("Y"));
            this.step8SaveDTO.getArrayList().get(3).setClick(this.step8SaveDTO.getAche_factor4().equals("Y"));
            this.step8SaveDTO.getArrayList().get(4).setClick(this.step8SaveDTO.getAche_factor5().equals("Y"));
            this.step8SaveDTO.getArrayList().get(5).setClick(this.step8SaveDTO.getAche_factor6().equals("Y"));
            this.step8SaveDTO.getArrayList().get(6).setClick(this.step8SaveDTO.getAche_factor7().equals("Y"));
            this.step8SaveDTO.getArrayList().get(7).setClick(this.step8SaveDTO.getAche_factor8().equals("Y"));
            this.step8SaveDTO.getArrayList().get(8).setClick(this.step8SaveDTO.getAche_factor9().equals("Y"));
            this.step8SaveDTO.getArrayList().get(9).setClick(this.step8SaveDTO.getAche_factor10().equals("Y"));
            this.step8SaveDTO.getArrayList().get(10).setClick(this.step8SaveDTO.getAche_factor11().equals("Y"));
            this.step8SaveDTO.getArrayList().get(11).setClick(this.step8SaveDTO.getAche_factor12().equals("Y"));
            this.step8SaveDTO.getArrayList().get(12).setClick(this.step8SaveDTO.getAche_factor13().equals("Y"));
            this.step8SaveDTO.getArrayList().get(13).setClick(this.step8SaveDTO.getAche_factor14().equals("Y"));
            this.step8SaveDTO.getArrayList().get(14).setClick(this.step8SaveDTO.getAche_factor15().equals("Y"));
        }

        this.adapter = new Step8GridviewAdapter( this.step8SaveDTO.getArrayList() , this.activity.getLayoutInflater());
        this.gridView.setAdapter(this.adapter);
        this.gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id ) {
        Step8EtcDTO row = this.step8SaveDTO.getArrayList().get(position);
        if ( row.getEtcBt() ) {
            getEtcActivity();
        } else {
            if ( row.getClick() ){
                if (position == 0) this.step8SaveDTO.setAche_factor1("N");
                else if (position == 1) this.step8SaveDTO.setAche_factor2("N");
                else if (position == 2) this.step8SaveDTO.setAche_factor3("N");
                else if (position == 3) this.step8SaveDTO.setAche_factor4("N");
                else if (position == 4) this.step8SaveDTO.setAche_factor5("N");
                else if (position == 5) this.step8SaveDTO.setAche_factor6("N");
                else if (position == 6) this.step8SaveDTO.setAche_factor7("N");
                else if (position == 7) this.step8SaveDTO.setAche_factor8("N");
                else if (position == 8) this.step8SaveDTO.setAche_factor9("N");
                else if (position == 9) this.step8SaveDTO.setAche_factor10("N");
                else if (position == 10) this.step8SaveDTO.setAche_factor11("N");
                else if (position == 11) this.step8SaveDTO.setAche_factor12("N");
                else if (position == 12) this.step8SaveDTO.setAche_factor13("N");
                else if (position == 13) this.step8SaveDTO.setAche_factor14("N");
                else if (position == 14) this.step8SaveDTO.setAche_factor15("N");
                row.setVal("N");
                row.setClick(false);
            } else {
                if (position == 0) this.step8SaveDTO.setAche_factor1("Y");
                else if (position == 1) this.step8SaveDTO.setAche_factor2("Y");
                else if (position == 2) this.step8SaveDTO.setAche_factor3("Y");
                else if (position == 3) this.step8SaveDTO.setAche_factor4("Y");
                else if (position == 4) this.step8SaveDTO.setAche_factor5("Y");
                else if (position == 5) this.step8SaveDTO.setAche_factor6("Y");
                else if (position == 6) this.step8SaveDTO.setAche_factor7("Y");
                else if (position == 7) this.step8SaveDTO.setAche_factor8("Y");
                else if (position == 8) this.step8SaveDTO.setAche_factor9("Y");
                else if (position == 9) this.step8SaveDTO.setAche_factor10("Y");
                else if (position == 10) this.step8SaveDTO.setAche_factor11("Y");
                else if (position == 11) this.step8SaveDTO.setAche_factor12("Y");
                else if (position == 12) this.step8SaveDTO.setAche_factor13("Y");
                else if (position == 13) this.step8SaveDTO.setAche_factor14("Y");
                else if (position == 14) this.step8SaveDTO.setAche_factor15("Y");
                row.setVal("Y");
                row.setClick(true);
            }
            this.parentActivity.save8(this.step8SaveDTO);
            reloadListView();
        }
    }

    public void addListView (String etc) {
        this.step8SaveDTO.getArrayList().remove(this.step8SaveDTO.getArrayList().size()-1);
        this.step8SaveDTO.getArrayList().add( new Step8EtcDTO(R.drawable.step_type_etc_add, R.drawable.step_type_etc_add, etc,true,false, true,0,"Y") );
        this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step_type_etc,R.drawable.step_type_etc,"기타",false,true,false,0 , "N"));
        reloadListView();
    }

    private void reloadListView() {
        viewReset();
        this.adapter = new Step8GridviewAdapter( this.step8SaveDTO.getArrayList() , this.activity.getLayoutInflater() );
        this.gridView.setAdapter( this.adapter );
        this.adapter.notifyDataSetChanged();
    }

    private void getEtcActivity () {
        Intent intent = new Intent(this.activity , EtcInputActivity.class);
        this.activity.startActivityForResult(intent,ETC8_INPUT);
    }

    private void viewReset() {
        step8BottomV.post(new Runnable() {
            @Override
            public void run() {
                final int titleHeight = step8BottomV.getHeight();
                step8LinearView.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("gridViewHeight=",((int)Math.ceil((double)step8SaveDTO.getArrayList().size() / 4) )+"_");
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.height = (int) (titleHeight + (400 * Math.ceil((double)step8SaveDTO.getArrayList().size() / 4)));
                        step8LinearView.setLayoutParams(layoutParams);
                    }
                });
            }
        });

    }

    private void isHeadche(boolean isHeadache) {
        this.isHeadache = isHeadache;
        if ( isHeadache ) {
            this.step8SaveDTO.setAche_factor_yn("Y");
            this.yesBt.setBackgroundResource(R.drawable.step5_select_board);
            this.yesBt.setTextColor(Color.parseColor("#1EA2B6"));
            this.noBt.setTextColor(Color.parseColor("#C2C2C2"));;
            this.noBt.setBackgroundColor(Color.TRANSPARENT);
            this.step8Line.setVisibility(View.VISIBLE);
            this.step8ParentV.setVisibility(View.VISIBLE);

            this.step5BottomV2.setVisibility(View.GONE);
            this.step8BottomV.setVisibility(View.VISIBLE);
        } else {
            this.step8SaveDTO.setAche_factor_yn("N");
            this.yesBt.setBackgroundColor(Color.TRANSPARENT);
            this.yesBt.setTextColor(Color.parseColor("#C2C2C2"));
            this.noBt.setBackgroundResource(R.drawable.step5_no_select_board);
            this.noBt.setTextColor(Color.parseColor("#1EA2B6"));
            this.step8Line.setVisibility(View.GONE);
            this.step8ParentV.setVisibility(View.GONE);
            this.step5BottomV2.setVisibility(View.VISIBLE);
            this.step8BottomV.setVisibility(View.INVISIBLE);
        }
        this.parentActivity.save8(this.step8SaveDTO);
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
