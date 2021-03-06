package com.m2comm.headache.contentStepView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.m2comm.headache.Adapter.Step5GridviewAdapter;
import com.m2comm.headache.Adapter.Step7GridviewAdapter;
import com.m2comm.headache.DTO.Step4DTO;
import com.m2comm.headache.DTO.Step5EtcDTO;
import com.m2comm.headache.DTO.Step7EtcDTO;
import com.m2comm.headache.DTO.Step7SaveDTO;
import com.m2comm.headache.R;
import com.m2comm.headache.views.ContentStepActivity;
import com.m2comm.headache.views.EtcInputActivity;

import java.util.ArrayList;

public class Step7 implements View.OnClickListener , AdapterView.OnItemClickListener , AdapterView.OnItemLongClickListener{

    public final static int ETC7_INPUT = 777;

    private LayoutInflater inflater;
    private int ParentID;
    private LinearLayout parent , step7LinearView , step7BottomV , step5BottomV2;
    private Context context;
    private Activity activity;

    private GridView gridView;
    private Step7GridviewAdapter adapter;
    //private ArrayList<Step7EtcDTO> list;
    View view;
    ContentStepActivity parentActivity;

    //step6
    TextView nextBt , backBt , yesBt , noBt;

    int nextStepNum = 8;
    int backStepNum = 6;

    private LinearLayout step7Line , step7GridV;

    //두통 알수있는지 예true or 아니요false
    private String isHeadache = "";

    private Step7SaveDTO step7SaveDTO;

    public Step7(LayoutInflater inflater, int parentID, Context context, Activity activity , ContentStepActivity parentActivity , Step7SaveDTO step7SaveDTO) {
        this.inflater = inflater;
        ParentID = parentID;
        this.context = context;
        this.activity = activity;
        this.parentActivity = parentActivity;
        this.step7SaveDTO = step7SaveDTO;
        this.init();
        this.regObj();
        this.viewReset();
    }

    private void regObj () {
        this.nextBt.setOnClickListener(this);
        this.backBt.setOnClickListener(this);
        this.yesBt.setOnClickListener(this);
        this.noBt.setOnClickListener(this);

    }

    private void init () {
        this.parent = this.activity.findViewById(this.ParentID);
        this.parent.removeAllViews();
        this.view = inflater.inflate(R.layout.step7,this.parent,true);
        this.gridView = view.findViewById(R.id.step5_gridV);
        this.nextBt = this.view.findViewById(R.id.nextBt);
        this.backBt = this.view.findViewById(R.id.backBt);
        this.yesBt = this.view.findViewById(R.id.yesBt);
        this.noBt = this.view.findViewById(R.id.noBt);
        this.step7LinearView = this.view.findViewById(R.id.step7View1);
        this.step7BottomV = this.view.findViewById(R.id.step7BottomV);
        this.step7Line = this.view.findViewById(R.id.step7Line);
        this.step7GridV = this.view.findViewById(R.id.step7GridV);
        this.step5BottomV2 = this.view.findViewById(R.id.step5BottomV2);



        //this.list = new ArrayList<>();


        if ( this.step7SaveDTO == null ) {

//            this.step7SaveDTO = new Step7SaveDTO("N","N","N","N","N",
//                    "N","N","N","N",
//                    "N","N","N","N",new ArrayList<Step7EtcDTO>());
//            this.isHeadche(false);
//            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default1,R.drawable.step7_type_click1,"소화가 안됨",false,false,false,0 , "N"));
//            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default2,R.drawable.step7_type_click2,"울렁거림",false,false,false,0 , "N"));
//            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default3,R.drawable.step7_type_click3,"구토",false,false,false,0 , "N"));
//            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default4,R.drawable.step7_type_click4,"어지럼증",false,false,false,0 , "N"));
//            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default5,R.drawable.step7_type_click5,"움직임에\n의해 악화",false,false,false,0 , "N"));
//            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default6,R.drawable.step7_type_click6,"빛에 예민",false,false,false,0 , "N"));
//            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default7,R.drawable.step7_type_click7,"소리에 예민",false,false,false,0 , "N"));
//            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default8,R.drawable.step7_type_click8,"냄새에 예민",false,false,false,0 , "N"));
//            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default9,R.drawable.step7_type_click9,"뒷목통증/\n뻐근함/당김",false,false,false,0 , "N"));
//            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default10,R.drawable.step7_type_click10,"어깨통증",false,false,false,0 , "N"));
//            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default11,R.drawable.step7_type_click11,"눈물/눈충혈",false,false,false,0 , "N"));
//            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default12,R.drawable.step7_type_click12,"콧물/코막힘",false,false,false,0 , "N"));
//            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step_type_etc,R.drawable.step_type_etc,"기타",false,true,false,0 , "N"));

        } else {
           // this.isHeadche(this.step7SaveDTO.getAche_with_yn());
            this.step7SaveDTO.getStep7EtcDTOS().get(0).setClick(this.step7SaveDTO.getAche_with1().equals("Y"));
            this.step7SaveDTO.getStep7EtcDTOS().get(1).setClick(this.step7SaveDTO.getAche_with2().equals("Y"));
            this.step7SaveDTO.getStep7EtcDTOS().get(2).setClick(this.step7SaveDTO.getAche_with3().equals("Y"));
            this.step7SaveDTO.getStep7EtcDTOS().get(3).setClick(this.step7SaveDTO.getAche_with4().equals("Y"));
            this.step7SaveDTO.getStep7EtcDTOS().get(4).setClick(this.step7SaveDTO.getAche_with5().equals("Y"));
            this.step7SaveDTO.getStep7EtcDTOS().get(5).setClick(this.step7SaveDTO.getAche_with6().equals("Y"));
            this.step7SaveDTO.getStep7EtcDTOS().get(6).setClick(this.step7SaveDTO.getAche_with7().equals("Y"));
            this.step7SaveDTO.getStep7EtcDTOS().get(7).setClick(this.step7SaveDTO.getAche_with8().equals("Y"));
            this.step7SaveDTO.getStep7EtcDTOS().get(8).setClick(this.step7SaveDTO.getAche_with9().equals("Y"));
            this.step7SaveDTO.getStep7EtcDTOS().get(9).setClick(this.step7SaveDTO.getAche_with10().equals("Y"));
            this.step7SaveDTO.getStep7EtcDTOS().get(10).setClick(this.step7SaveDTO.getAche_with11().equals("Y"));
        }

        this.adapter = new Step7GridviewAdapter( this.step7SaveDTO.getStep7EtcDTOS() , this.activity.getLayoutInflater());
        this.gridView.setAdapter(this.adapter);
        this.gridView.setOnItemClickListener(this);
        this.gridView.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        Step7EtcDTO row = this.step7SaveDTO.getStep7EtcDTOS().get(position);
        if (row.getEtcBt() || !row.getEtc()) return true;

        new AlertDialog.Builder(activity).setTitle("안내").setMessage("해당 항목을 삭제 하시겠습니까?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        parentActivity.removeETC(step7SaveDTO.getStep7EtcDTOS().get(position).getKey());
                        step7SaveDTO.getStep7EtcDTOS().remove(position);
                        reloadListView();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id ) {

        //if (this.step7SaveDTO.getAche_with_yn().equals("N") ) return;  아니오 눌렀을때 클릭안되게 하는 코드

        Step7EtcDTO row = this.step7SaveDTO.getStep7EtcDTOS().get(position);
        if ( row.getEtcBt() ) {
            getEtcActivity();
        } else {
            if ( row.getClick() ) {
                if (position == 0) this.step7SaveDTO.setAche_with1("N");
                else if (position == 1) this.step7SaveDTO.setAche_with2("N");
                else if (position == 2) this.step7SaveDTO.setAche_with3("N");
                else if (position == 3) this.step7SaveDTO.setAche_with4("N");
                else if (position == 4) this.step7SaveDTO.setAche_with5("N");
                else if (position == 5) this.step7SaveDTO.setAche_with6("N");
                else if (position == 6) this.step7SaveDTO.setAche_with7("N");
                else if (position == 7) this.step7SaveDTO.setAche_with8("N");
                else if (position == 8) this.step7SaveDTO.setAche_with9("N");
                else if (position == 9) this.step7SaveDTO.setAche_with10("N");
                else if (position == 10) this.step7SaveDTO.setAche_with11("N");
                //else if (position == 11) this.step7SaveDTO.setAche_with12("N");
                row.setVal("N");
                row.setClick(false);
            } else {
                if (position == 0) this.step7SaveDTO.setAche_with1("Y");
                else if (position == 1) this.step7SaveDTO.setAche_with2("Y");
                else if (position == 2) this.step7SaveDTO.setAche_with3("Y");
                else if (position == 3) this.step7SaveDTO.setAche_with4("Y");
                else if (position == 4) this.step7SaveDTO.setAche_with5("Y");
                else if (position == 5) this.step7SaveDTO.setAche_with6("Y");
                else if (position == 6) this.step7SaveDTO.setAche_with7("Y");
                else if (position == 7) this.step7SaveDTO.setAche_with8("Y");
                else if (position == 8) this.step7SaveDTO.setAche_with9("Y");
                else if (position == 9) this.step7SaveDTO.setAche_with10("Y");
                else if (position == 10) this.step7SaveDTO.setAche_with11("Y");
                //else if (position == 11) this.step7SaveDTO.setAche_with12("N");
                row.setVal("Y");
                row.setClick(true);
            }
            this.parentActivity.save7(this.step7SaveDTO);
            reloadListView();
        }
    }

    private boolean checkState() {

        if (this.step7SaveDTO.getAche_with1().equals("Y"))return true;
        else if (this.step7SaveDTO.getAche_with2().equals("Y"))return true;
        else if (this.step7SaveDTO.getAche_with3().equals("Y"))return true;
        else if (this.step7SaveDTO.getAche_with4().equals("Y"))return true;
        else if (this.step7SaveDTO.getAche_with5().equals("Y"))return true;
        else if (this.step7SaveDTO.getAche_with6().equals("Y"))return true;
        else if (this.step7SaveDTO.getAche_with7().equals("Y"))return true;
        else if (this.step7SaveDTO.getAche_with8().equals("Y"))return true;
        else if (this.step7SaveDTO.getAche_with9().equals("Y"))return true;
        else if (this.step7SaveDTO.getAche_with10().equals("Y"))return true;
        else if (this.step7SaveDTO.getAche_with11().equals("Y"))return true;

        for ( int i = 0 , j = this.step7SaveDTO.getStep7EtcDTOS().size(); i < j; i++ ) {
            Step7EtcDTO row = this.step7SaveDTO.getStep7EtcDTOS().get(i);
            if ( row.getVal().equals("Y") ) return true;
        }

        return false;
    }

    public void addListView (String etc) {
        this.step7SaveDTO.getStep7EtcDTOS().remove(this.step7SaveDTO.getStep7EtcDTOS().size()-1);
        this.step7SaveDTO.getStep7EtcDTOS().add( new Step7EtcDTO(R.drawable.step_type_etc_add, R.drawable.step_type_etc_add, etc,true,false, true,0,"Y") );
        this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step_type_etc,R.drawable.step_type_etc,"기타",false,true,false,0 , "N"));
        reloadListView();
    }

    private void reloadListView() {
        viewReset();
        if ( this.checkState() ) {
            isHeadche("Y");
        } else {
            isHeadche("");
        }
        this.adapter = new Step7GridviewAdapter( this.step7SaveDTO.getStep7EtcDTOS() , this.activity.getLayoutInflater() );
        this.gridView.setAdapter( this.adapter );
        this.adapter.notifyDataSetChanged();
    }

    private void getEtcActivity () {
        Intent intent = new Intent(this.activity , EtcInputActivity.class);
        this.activity.startActivityForResult(intent,ETC7_INPUT);
    }

    private void viewReset() {
        step7BottomV.post(new Runnable() {
            @Override
            public void run() {
                final int titleHeight = step7BottomV.getHeight();
                step7LinearView.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("gridViewHeight=",((int)Math.ceil((double)step7SaveDTO.getStep7EtcDTOS().size() / 4) )+"_");
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.height = (int) (titleHeight + (450 * Math.ceil((double)step7SaveDTO.getStep7EtcDTOS().size() / 4)));
                        step7LinearView.setLayoutParams(layoutParams);
                    }
                });
            }
        });
    }

    private void step7Reset() {

        this.step7SaveDTO.setAche_with1("N");
        this.step7SaveDTO.setAche_with2("N");
        this.step7SaveDTO.setAche_with3("N");
        this.step7SaveDTO.setAche_with4("N");
        this.step7SaveDTO.setAche_with5("N");
        this.step7SaveDTO.setAche_with6("N");
        this.step7SaveDTO.setAche_with7("N");
        this.step7SaveDTO.setAche_with8("N");
        this.step7SaveDTO.setAche_with9("N");
        this.step7SaveDTO.setAche_with10("N");
        this.step7SaveDTO.setAche_with11("N");

        for ( int i = 0 , j = this.step7SaveDTO.getStep7EtcDTOS().size(); i < j; i++ ) {
            Step7EtcDTO row = this.step7SaveDTO.getStep7EtcDTOS().get(i);
            row.setClick(false);
            row.setVal("N");
        }
    }

    private void isHeadche(String isHeadache) {
        this.isHeadache = isHeadache;
        if ( isHeadache.equals("Y") ) {
         //   this.step7SaveDTO.setAche_with_yn("Y");
            this.yesBt.setBackgroundResource(R.drawable.step5_select_board);
            this.yesBt.setTextColor(Color.parseColor("#1EA2B6"));
            this.noBt.setTextColor(Color.parseColor("#C2C2C2"));
            this.noBt.setBackgroundColor(Color.TRANSPARENT);

//            this.step7Line.setVisibility(View.VISIBLE);
//            this.step7GridV.setVisibility(View.VISIBLE);
            //this.step5BottomV2.setVisibility(View.GONE);
//            this.step7BottomV.setVisibility(View.VISIBLE);
        } else if ( isHeadache.equals("N") ){
        //    this.step7SaveDTO.setAche_with_yn("N");
            this.yesBt.setBackgroundColor(Color.TRANSPARENT);
            this.yesBt.setTextColor(Color.parseColor("#C2C2C2"));
            this.noBt.setBackgroundResource(R.drawable.step5_no_select_board);
            this.noBt.setTextColor(Color.parseColor("#1EA2B6"));

//            this.step7Line.setVisibility(View.GONE);
//            this.step7GridV.setVisibility(View.GONE);
//            this.step5BottomV2.setVisibility(View.VISIBLE);
//            this.step7BottomV.setVisibility(View.INVISIBLE);
        } else {
            this.yesBt.setBackgroundColor(Color.TRANSPARENT);
            this.noBt.setBackgroundColor(Color.TRANSPARENT);
            this.yesBt.setTextColor(Color.parseColor("#C2C2C2"));
            this.noBt.setTextColor(Color.parseColor("#C2C2C2"));
        }
        this.parentActivity.save7(this.step7SaveDTO);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.nextBt:
                this.parentActivity.positionView(this.nextStepNum);
                break;

            case R.id.backBt:
                this.parentActivity.positionView(this.backStepNum);
                break;

            case R.id.yesBt:
                this.isHeadche("Y");
                break;
            case R.id.noBt:
                this.isHeadche("N");
                step7Reset();
                this.parentActivity.positionView(this.nextStepNum);
                break;

        }
    }


}
