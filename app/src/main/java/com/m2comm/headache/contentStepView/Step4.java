package com.m2comm.headache.contentStepView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m2comm.headache.Adapter.Step4GridviewAdapter;
import com.m2comm.headache.DTO.Step4DTO;
import com.m2comm.headache.DTO.Step4EtcDTO;
import com.m2comm.headache.DTO.Step4SaveDTO;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.views.ContentStepActivity;
import com.m2comm.headache.views.EtcInputActivity;

import java.util.ArrayList;

public class Step4 implements View.OnClickListener , AdapterView.OnItemClickListener {

    public final static int ETC4_INPUT = 444;

    private LayoutInflater inflater;
    private int ParentID;
    private LinearLayout parent , step4LinearView , step4Title;
    private Context context;
    private Activity activity;

    private GridView gridView;
    private Step4GridviewAdapter adapter;
    //private ArrayList<Step4EtcDTO> list;
    private View view;
    ContentStepActivity parentActivity;

    //step4
    TextView nextBt , backBt;

    int nextStepNum = 5;
    int backStepNum = 3;

    Step4SaveDTO step4SaveDTO;

    public Step4(LayoutInflater inflater, int parentID, Context context, Activity activity , ContentStepActivity parentActivity , Step4SaveDTO step4SaveDTO ) {
        this.inflater = inflater;
        ParentID = parentID;
        this.context = context;
        this.activity = activity;
        this.parentActivity = parentActivity;
        this.step4SaveDTO = step4SaveDTO;
        this.init();
        this.regObj();
        this.viewReset();
    }

    private void regObj () {
        this.nextBt = this.view.findViewById(R.id.nextBt);
        this.nextBt.setOnClickListener(this);
        this.backBt = this.view.findViewById(R.id.backBt);
        this.backBt.setOnClickListener(this);
        this.step4Title = this.view.findViewById(R.id.step4Title);
        this.step4LinearView = this.view.findViewById(R.id.step4View1);
    }

    private void init () {
        this.parent = this.activity.findViewById(this.ParentID);
        this.parent.removeAllViews();
        this.view = inflater.inflate(R.layout.step4,this.parent,true);
        this.gridView = view.findViewById(R.id.step4_gridV);

        //this.list = new ArrayList<>();

        if ( this.step4SaveDTO == null ) {
            this.step4SaveDTO = new Step4SaveDTO("N","N","N","N","N",new ArrayList<Step4EtcDTO>());
            this.step4SaveDTO.getArrayList().add(new Step4EtcDTO(R.drawable.step4_type_default1,R.drawable.step4_type_click1,"욱신거림",false,false , false ,0 , "N"));
            this.step4SaveDTO.getArrayList().add(new Step4EtcDTO(R.drawable.step4_type_default2,R.drawable.step4_type_click2,"조임",false,false, false,0,"N"));
            this.step4SaveDTO.getArrayList().add(new Step4EtcDTO(R.drawable.step4_type_default3,R.drawable.step4_type_click3,"터질듯함",false,false, false,0,"N"));
            this.step4SaveDTO.getArrayList().add(new Step4EtcDTO(R.drawable.step4_type_default4,R.drawable.step4_type_click4,"찌름",false,false, false,0,"N"));
            this.step4SaveDTO.getArrayList().add(new Step4EtcDTO(R.drawable.step4_type_default5,R.drawable.step4_type_click5,"따끔따끔",false,false, false,0,"N"));
            this.step4SaveDTO.getArrayList().add(new Step4EtcDTO(R.drawable.step_type_etc,R.drawable.step_type_etc,"기타",false,true, false,0,"N"));
        } else {

            this.step4SaveDTO.getArrayList().get(0).setClick(this.step4SaveDTO.getAche_type1().equals("Y"));
            this.step4SaveDTO.getArrayList().get(1).setClick(this.step4SaveDTO.getAche_type2().equals("Y"));
            this.step4SaveDTO.getArrayList().get(2).setClick(this.step4SaveDTO.getAche_type3().equals("Y"));
            this.step4SaveDTO.getArrayList().get(3).setClick(this.step4SaveDTO.getAche_type4().equals("Y"));
            this.step4SaveDTO.getArrayList().get(4).setClick(this.step4SaveDTO.getAche_type5().equals("Y"));

//            for(int i = 0 , j = this.step4SaveDTO.getArrayList().size() ; i < j ; i++) {
//                Step4EtcDTO row = this.step4SaveDTO.getArrayList().get(i);
//                if ( row.getEtc() ) {
//                    this.step4SaveDTO.getArrayList().add( new Step4EtcDTO(row.getDefault_icon(),row.getClick_icon(),
//                            row.getContent(),row.getEtc(),row.getEtcBt(),row.getClick(),row.getKey(),row.getVal()) );
//                }
//            }
        }

        this.adapter = new Step4GridviewAdapter( this.step4SaveDTO.getArrayList() , this.activity.getLayoutInflater());
        this.gridView.setAdapter(this.adapter);
        this.gridView.setOnItemClickListener(this);

    }

    private void viewReset() {

        this.step4Title.post(new Runnable() {
            @Override
            public void run() {
                final int titleHeight = step4Title.getHeight();
                step4LinearView.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("gridViewHeight=",((int)Math.ceil((double)step4SaveDTO.getArrayList().size() / 4) )+"_");
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.height = (int) (titleHeight + (400 * Math.ceil((double)step4SaveDTO.getArrayList().size() / 4)));
                        step4LinearView.setLayoutParams(layoutParams);
                    }
                });
            }
        });
    }

    public void addListView (String etc) {
        Step4EtcDTO row = new  Step4EtcDTO(R.drawable.step4_type_default1,R.drawable.step4_type_click1,etc,true,false , true ,0 , "Y");
        this.step4SaveDTO.getArrayList().add( row );
        this.parentActivity.save4(this.step4SaveDTO);
        reloadListView();
    }

    private void reloadListView() {
        viewReset();
        this.adapter = new Step4GridviewAdapter( this.step4SaveDTO.getArrayList() , this.activity.getLayoutInflater() );
        this.gridView.setAdapter( this.adapter );
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
        Step4EtcDTO row = this.step4SaveDTO.getArrayList().get(position);
        if ( row.getEtcBt() ) {
            getEtcActivity();
        } else {
            if ( row.getClick() ) {

                if (position == 0) this.step4SaveDTO.setAche_type1("N");
                else if (position == 1) this.step4SaveDTO.setAche_type2("N");
                else if (position == 2) this.step4SaveDTO.setAche_type3("N");
                else if (position == 3) this.step4SaveDTO.setAche_type4("N");
                else if (position == 4) this.step4SaveDTO.setAche_type5("N");
                row.setVal("N");
                row.setClick(false);
            } else {
                if (position == 0) this.step4SaveDTO.setAche_type1("Y");
                else if (position == 1) this.step4SaveDTO.setAche_type2("Y");
                else if (position == 2) this.step4SaveDTO.setAche_type3("Y");
                else if (position == 3) this.step4SaveDTO.setAche_type4("Y");
                else if (position == 4) this.step4SaveDTO.setAche_type5("Y");
                row.setVal("Y");
                row.setClick(true);
            }
            this.parentActivity.save4(this.step4SaveDTO);
            reloadListView();
        }
    }

    private void getEtcActivity () {
        Intent intent = new Intent(this.activity , EtcInputActivity.class);
        this.activity.startActivityForResult(intent,ETC4_INPUT);
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
        }
    }


}
