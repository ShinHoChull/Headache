package com.m2comm.headache.contentStepView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m2comm.headache.Adapter.Step10GridviewAdapter;
import com.m2comm.headache.Adapter.Step4GridviewAdapter;
import com.m2comm.headache.Adapter.Step9GridviewAdapter;
import com.m2comm.headache.DTO.Step10Dates;
import com.m2comm.headache.DTO.Step10EtcDTO;
import com.m2comm.headache.DTO.Step10SaveDTO;
import com.m2comm.headache.DTO.Step1SaveDTO;
import com.m2comm.headache.DTO.Step4DTO;
import com.m2comm.headache.DTO.Step9Dates;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.views.ContentStepActivity;
import com.m2comm.headache.views.EtcInputActivity;
import com.m2comm.headache.views.Step9DatePicker;

import java.util.ArrayList;

public class Step10 implements View.OnClickListener , AdapterView.OnItemClickListener {

    public final static int ETC10_INPUT = 1010;
    public final static int ETC10_ARRAY = 1011;

    private LayoutInflater inflater;
    private int ParentID;
    private LinearLayout parent , step10BottomV , step10Title;
    private Context context;
    private Activity activity;

    private GridView gridView;
    private Step10GridviewAdapter adapter;
    View view;
    ContentStepActivity parentActivity;

    //step10
    TextView nextBt , backBt;

    int nextStepNum = 11;
    int backStepNum = 9;

    Step10SaveDTO step10SaveDTO;
    private Step1SaveDTO step1SaveDTO; //고정 날짜를 알기위해서 넘겨줬다.

    public Step10(LayoutInflater inflater, int parentID, Context context, Activity activity , ContentStepActivity parentActivity , Step10SaveDTO step10SaveDTO , Step1SaveDTO step1SaveDTO) {
        this.inflater = inflater;
        ParentID = parentID;
        this.context = context;
        this.activity = activity;
        this.parentActivity = parentActivity;
        this.step10SaveDTO = step10SaveDTO;
        this.step1SaveDTO = step1SaveDTO;
        this.init();
        this.regObj();
        this.viewReset();
    }

    private void regObj () {
        this.nextBt.setOnClickListener(this);
        this.backBt.setOnClickListener(this);
    }

    private void init () {
        this.parent = this.activity.findViewById(this.ParentID);
        this.parent.removeAllViews();
        this.view = inflater.inflate(R.layout.step10,this.parent,true);

        this.nextBt = this.view.findViewById(R.id.nextBt);
        this.backBt = this.view.findViewById(R.id.backBt);
        this.step10Title = this.view.findViewById(R.id.step10Title);
        this.step10BottomV = this.view.findViewById(R.id.step10BottomV);
        this.gridView = view.findViewById(R.id.step10_gridV);

        if ( this.step10SaveDTO == null ) {
            this.step10SaveDTO = new Step10SaveDTO("N","N","N","N"
                    ,"N",new ArrayList<Step9Dates>() , new ArrayList<Step10EtcDTO>());
            
            this.step10SaveDTO.getArrayList().add(new Step10EtcDTO(R.drawable.step10_type_default1,R.drawable.step10_type_click1,"결근/결석",false,false,false,0 , "N"));
            this.step10SaveDTO.getArrayList().add(new Step10EtcDTO(R.drawable.step10_type_default2,R.drawable.step10_type_click2,"업무/학습\n능률저하",false,false,false,0 , "N"));
            this.step10SaveDTO.getArrayList().add(new Step10EtcDTO(R.drawable.step10_type_default3,R.drawable.step10_type_click3,"가사활동\n못함",false,false,false,0 , "N"));
            this.step10SaveDTO.getArrayList().add(new Step10EtcDTO(R.drawable.step10_type_default4,R.drawable.step10_type_click4,"가사활동\n능률 저하",false,false,false,0 , "N"));
            this.step10SaveDTO.getArrayList().add(new Step10EtcDTO(R.drawable.step10_type_default5,R.drawable.step10_type_click5,"여가활동\n불참",false,false,false,0 , "N"));
            this.step10SaveDTO.getArrayList().add(new Step10EtcDTO(R.drawable.step_type_etc,R.drawable.step_type_etc,"기타",false,true,false,0 , "N"));

        } else {
            this.step10SaveDTO.getArrayList().get(0).setClick(this.step10SaveDTO.getAche_effect1().equals("Y"));
            this.step10SaveDTO.getArrayList().get(1).setClick(this.step10SaveDTO.getAche_effect2().equals("Y"));
            this.step10SaveDTO.getArrayList().get(2).setClick(this.step10SaveDTO.getAche_effect3().equals("Y"));
            this.step10SaveDTO.getArrayList().get(3).setClick(this.step10SaveDTO.getAche_effect4().equals("Y"));
            this.step10SaveDTO.getArrayList().get(4).setClick(this.step10SaveDTO.getAche_effect5().equals("Y"));
        }

        this.adapter = new Step10GridviewAdapter( this.step10SaveDTO.getArrayList() , this.activity.getLayoutInflater());
        this.gridView.setAdapter(this.adapter);
        this.gridView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id ) {
        Step10EtcDTO row = this.step10SaveDTO.getArrayList().get(position);
        if ( row.getEtcBt() ) {
            getEtcActivity();
        } else {
            if ( row.getClick() ) {
                if (position == 0) this.step10SaveDTO.setAche_effect1("N");
                else if (position == 1) this.step10SaveDTO.setAche_effect2("N");
                else if (position == 2) this.step10SaveDTO.setAche_effect3("N");
                else if (position == 3) this.step10SaveDTO.setAche_effect4("N");
                else if (position == 4) this.step10SaveDTO.setAche_effect5("N");
                row.setVal("N");
                row.setClick(false);
            } else {

                if (position == 4) this.step10SaveDTO.setAche_effect5("Y");
                if ( position == 0 ) {
                    this.step10SaveDTO.setAche_effect1("Y");
                    this.step10SaveDTO.setAche_effect2("N");
                    this.step10SaveDTO.getArrayList().get(1).setClick(false);
                    this.step10SaveDTO.getArrayList().get(1).setVal("N");
                } else if ( position == 1 ) {
                    this.step10SaveDTO.setAche_effect2("Y");
                    this.step10SaveDTO.setAche_effect1("N");
                    this.step10SaveDTO.getArrayList().get(0).setClick(false);
                    this.step10SaveDTO.getArrayList().get(0).setVal("N");
                } else if ( position == 2 ) {
                    this.step10SaveDTO.setAche_effect3("Y");
                    this.step10SaveDTO.setAche_effect4("N");
                    this.step10SaveDTO.getArrayList().get(3).setClick(false);
                    this.step10SaveDTO.getArrayList().get(3).setVal("N");
                } else if ( position == 3 ) {
                    this.step10SaveDTO.setAche_effect4("Y");
                    this.step10SaveDTO.setAche_effect3("N");
                    this.step10SaveDTO.getArrayList().get(2).setClick(false);
                    this.step10SaveDTO.getArrayList().get(2).setVal("N");
                }
                row.setVal("Y");
                row.setClick(true);
            }
            this.parentActivity.save10(this.step10SaveDTO);
            reloadListView();
        }
    }

    public void saveDate(ArrayList<Step9Dates> arrayList) {
        this.step10SaveDTO.setAche_effect_date_val_Array(arrayList);
        this.parentActivity.save10(this.step10SaveDTO);
        this.parentActivity.positionView(this.nextStepNum);
    }

    public void addListView (String etc) {
        this.step10SaveDTO.getArrayList().remove(this.step10SaveDTO.getArrayList().size()-1);
        this.step10SaveDTO.getArrayList().add( new Step10EtcDTO(R.drawable.step_type_etc_add, R.drawable.step_type_etc_add, etc,true,false, true,0,"Y") );
        this.step10SaveDTO.getArrayList().add(new Step10EtcDTO(R.drawable.step_type_etc,R.drawable.step_type_etc,"기타",false,true,false,0 , "N"));
        reloadListView();
    }

    private void reloadListView() {
        viewReset();
        this.adapter = new Step10GridviewAdapter( this.step10SaveDTO.getArrayList() , this.activity.getLayoutInflater() );
        this.gridView.setAdapter( this.adapter );
        this.adapter.notifyDataSetChanged();
    }

    private void getEtcActivity () {
        Intent intent = new Intent(this.activity , EtcInputActivity.class);
        this.activity.startActivityForResult(intent,ETC10_INPUT);
    }

    private void viewReset() {

        this.step10Title.post(new Runnable() {
            @Override
            public void run() {
                final int titleHeight = step10Title.getHeight();
                step10BottomV.post(new Runnable() {
                    @Override
                    public void run() {
                        final int bottomHeight = step10BottomV.getHeight();
                        gridView.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("gridViewHeight=",((int)Math.ceil((double)step10SaveDTO.getArrayList().size() / 4) )+"_");
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                layoutParams.height = (int) ((bottomHeight/1.5) + (400 * Math.ceil((double)step10SaveDTO.getArrayList().size() / 4)));
                                gridView.setLayoutParams(layoutParams);
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.nextBt:

                if ( step1SaveDTO.geteDate() != 0 && !Global.getTimeToStr(step1SaveDTO.getSdate()).equals(Global.getTimeToStr(step1SaveDTO.geteDate())) ) {
                    Intent intent = new Intent(this.activity, Step9DatePicker.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                    if ( this.step1SaveDTO != null ) {
                        intent.putExtra("startDateLong",this.step1SaveDTO.getSdate());
                        intent.putExtra("endDateLong",this.step1SaveDTO.geteDate());
                    }
                    intent.putExtra("step10",true);
                    this.activity.startActivityForResult(intent, ETC10_ARRAY);
                } else {
                    this.parentActivity.positionView(this.nextStepNum);
                }

                break;

            case R.id.backBt:
                this.parentActivity.positionView(this.backStepNum);
                break;
        }
    }

}
