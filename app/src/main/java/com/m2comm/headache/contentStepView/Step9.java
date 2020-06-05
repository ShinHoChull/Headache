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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m2comm.headache.Adapter.Step4GridviewAdapter;
import com.m2comm.headache.Adapter.Step8GridviewAdapter;
import com.m2comm.headache.Adapter.Step9GridviewAdapter;
import com.m2comm.headache.DTO.Step1SaveDTO;
import com.m2comm.headache.DTO.Step4DTO;
import com.m2comm.headache.DTO.Step9DTO;
import com.m2comm.headache.DTO.Step9Dates;
import com.m2comm.headache.DTO.Step9NewSaveDTO;
import com.m2comm.headache.DTO.Step9SaveDTO;
import com.m2comm.headache.R;
import com.m2comm.headache.sendDTO.Send9PixDTO;
import com.m2comm.headache.sendDTO.Step9SendDTO;
import com.m2comm.headache.views.ContentStepActivity;
import com.m2comm.headache.views.DrugInput;
import com.m2comm.headache.views.EtcInputActivity;
import com.m2comm.headache.views.Step9DatePicker;

import java.util.ArrayList;

public class Step9 implements View.OnClickListener, AdapterView.OnItemClickListener {

    public final static int ETC9_INPUT = 999;
    public final static int ETC9_INPUT2 = 9999;

    private LayoutInflater inflater;
    private int ParentID;
    private LinearLayout parent, step9LinearView, step9Title, radio1, radio2, radio3, radio4, radio5;
    private Context context;
    private Activity activity;

    private GridView gridView;
    private Step9GridviewAdapter adapter;
    View view;
    ContentStepActivity parentActivity;

    //step8
    TextView nextBt, backBt;

    int nextStepNum = 10;
    int backStepNum = 8;


    int itemClickRow = 0;


    int[] radioBts = {
            R.id.radio1_img,
            R.id.radio2_img,
            R.id.radio3_img,
            R.id.radio4_img,
            R.id.radio5_img
    };

    int radioClickNum = 999;

    private Step9SaveDTO step9SaveDTO;
    private Step1SaveDTO step1SaveDTO; //고정 날짜를 알기위해서 넘겨줬다.

    private Step9NewSaveDTO step9NewSaveDTO;
    private Step9SendDTO step9SendDTO;

    public Step9(LayoutInflater inflater, int parentID, Context context, Activity activity, ContentStepActivity parentActivity , Step9SaveDTO step9SaveDTO , Step1SaveDTO step1SaveDTO) {
        this.inflater = inflater;
        ParentID = parentID;
        this.context = context;
        this.activity = activity;
        this.step9SaveDTO = step9SaveDTO;
        this.parentActivity = parentActivity;
        this.step1SaveDTO = step1SaveDTO;
        this.init();
        this.regObj();
        this.viewReset();
    }

    private void regObj() {
        this.nextBt.setOnClickListener(this);
        this.backBt.setOnClickListener(this);


        this.radio1.setOnClickListener(this);
        this.radio2.setOnClickListener(this);
        this.radio3.setOnClickListener(this);
        this.radio4.setOnClickListener(this);
        this.radio5.setOnClickListener(this);
        
    }

    private void init() {
        this.parent = this.activity.findViewById(this.ParentID);
        this.parent.removeAllViews();
        this.view = inflater.inflate(R.layout.step9, this.parent, true);
        
        this.nextBt = this.view.findViewById(R.id.nextBt);
        this.backBt = this.view.findViewById(R.id.backBt);
        this.step9LinearView = this.view.findViewById(R.id.step9View1);
        this.step9Title = this.view.findViewById(R.id.step9Title);
        this.radio1 = this.view.findViewById(R.id.radio1);
        this.radio2 = this.view.findViewById(R.id.radio2);
        this.radio3 = this.view.findViewById(R.id.radio3);
        this.radio4 = this.view.findViewById(R.id.radio4);
        this.radio5 = this.view.findViewById(R.id.radio5);

        if (this.step9SaveDTO == null) {

            this.step9SaveDTO = new Step9SaveDTO("N","N","N","N"
                    ,"N", "N","N","N",
                    "N","N","N",new ArrayList<Step9DTO>(),-1);

            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default1, R.drawable.step9_type_click1, "모름", false, false, false, new ArrayList<Step9Dates>(),0));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "이미그란", false, false, false,new ArrayList<Step9Dates>(),0));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "수마트란", false, false, false,new ArrayList<Step9Dates>(),0));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "슈그란", false, false, false,new ArrayList<Step9Dates>(),0));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "마이그란", false, false, false,new ArrayList<Step9Dates>(),0));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "조믹", false, false, false,new ArrayList<Step9Dates>(),0));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "나라믹", false, false, false,new ArrayList<Step9Dates>(),0));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "알모그란", false, false, false,new ArrayList<Step9Dates>(),0));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "미가드", false, false, false,new ArrayList<Step9Dates>(),0));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "크래밍", false, false, false,new ArrayList<Step9Dates>(),0));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step_type_etc, R.drawable.step_type_etc, "기타", false, true, false,new ArrayList<Step9Dates>(),0));
        } else {
            this.checkRadio(this.step9SaveDTO.getAche_medicine_effect());
        }

        this.gridView = view.findViewById(R.id.step9_gridV);
        this.adapter = new Step9GridviewAdapter(this.step9SaveDTO.getStep9DTOS(), this.activity.getLayoutInflater());
        this.gridView.setAdapter(this.adapter);
        this.gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Step9DTO row = this.step9SaveDTO.getStep9DTOS().get(position);
        if (row.getEtcBt()) {
            getEtcActivity();
        } else {

            if ( position == 0 ) {
                if ( row.getClick() ) {
                    this.step9SaveDTO.setAche_medicine1("N");
                    row.setClick(false);
                } else {
                    this.step9SaveDTO.setAche_medicine1("Y");
                    row.setClick(true);
                }
                this.parentActivity.save9(step9SaveDTO);
                reloadListView();
                return;
            }

            if ( row.getClick() ) {
                if (position == 1) this.step9SaveDTO.setAche_medicine2("N");
                else if (position == 2) this.step9SaveDTO.setAche_medicine3("N");
                else if (position == 3) this.step9SaveDTO.setAche_medicine4("N");
                else if (position == 4) this.step9SaveDTO.setAche_medicine5("N");
                else if (position == 5) this.step9SaveDTO.setAche_medicine6("N");
                else if (position == 7) this.step9SaveDTO.setAche_medicine7("N");
                else if (position == 8) this.step9SaveDTO.setAche_medicine8("N");
                else if (position == 9) this.step9SaveDTO.setAche_medicine9("N");
                else if (position == 10) this.step9SaveDTO.setAche_medicine10("N");

                row.setClick(false);
                row.setDrugArray(new ArrayList<Step9Dates>());
                this.parentActivity.save9(step9SaveDTO);
                reloadListView();
            } else {
                this.itemClickRow = position;
                Intent intent = new Intent(this.activity, Step9DatePicker.class);
                if ( this.step1SaveDTO != null ) {
                    intent.putExtra("startDateLong",this.step1SaveDTO.getSdate());
                    intent.putExtra("endDateLong",this.step1SaveDTO.geteDate());
                }
                this.activity.startActivityForResult(intent, ETC9_INPUT2);
            }
        }
    }

    public void countSet(ArrayList<Step9Dates> arrayList) {
        Log.d("ArrayListcount", arrayList.size() + "");
        this.step9SaveDTO.getStep9DTOS().get(this.itemClickRow).setDrugArray(arrayList);
        this.step9SaveDTO.getStep9DTOS().get(this.itemClickRow).setClick(true);

        if (this.itemClickRow == 1) this.step9SaveDTO.setAche_medicine2("Y");
        else if (this.itemClickRow == 2) this.step9SaveDTO.setAche_medicine3("Y");
        else if (this.itemClickRow == 3) this.step9SaveDTO.setAche_medicine4("Y");
        else if (this.itemClickRow == 4) this.step9SaveDTO.setAche_medicine5("Y");
        else if (this.itemClickRow == 5) this.step9SaveDTO.setAche_medicine6("Y");
        else if (this.itemClickRow == 6) this.step9SaveDTO.setAche_medicine7("Y");
        else if (this.itemClickRow == 7) this.step9SaveDTO.setAche_medicine8("Y");
        else if (this.itemClickRow == 8) this.step9SaveDTO.setAche_medicine9("Y");
        else if (this.itemClickRow == 9) this.step9SaveDTO.setAche_medicine10("Y");
        else if (this.itemClickRow == 10) this.step9SaveDTO.setAche_medicine11("Y");


       // Step9DTO row = this.step9SaveDTO.getStep9DTOS().get(this.itemClickRow);
        //row.setDrugArray(arrayList);
      //  row.setClick(true);
        this.parentActivity.save9(step9SaveDTO);
        reloadListView();
    }

    public void addListView(String etc , ArrayList<Step9Dates> arrayList) {
        this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step_type_etc, R.drawable.step_type_etc, etc, true, false, true,arrayList,0));
        reloadListView();
    }

    private void reloadListView() {
        viewReset();
        this.adapter = new Step9GridviewAdapter(this.step9SaveDTO.getStep9DTOS(), this.activity.getLayoutInflater());
        this.gridView.setAdapter(this.adapter);
        this.adapter.notifyDataSetChanged();
    }

    private void getEtcActivity() {
        Intent intent = new Intent(this.activity, DrugInput.class);
        if ( this.step1SaveDTO != null ) {
            intent.putExtra("startDateLong",this.step1SaveDTO.getSdate());
            intent.putExtra("endDateLong",this.step1SaveDTO.geteDate());
        }
        this.activity.startActivityForResult(intent, ETC9_INPUT);
    }

    private void viewReset() {

        this.step9Title.post(new Runnable() {
            @Override
            public void run() {
                final int titleHeight = step9Title.getHeight();
                step9LinearView.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("gridViewHeight=", ((int) Math.ceil((double) step9SaveDTO.getStep9DTOS().size() / 4)) + "_");
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.height = (int) (titleHeight + (400 * Math.ceil((double) step9SaveDTO.getStep9DTOS().size() / 4)));
                        step9LinearView.setLayoutParams(layoutParams);
                    }
                });
            }
        });
    }

    private void checkRadio(int num) {
        this.radioClickNum = num;
        for (int i = 0, j = radioBts.length; i < j; i++) {
            ImageView img = this.view.findViewById(radioBts[i]);
            if (num == i) {
                img.setImageResource(R.drawable.step9_radio_check);
            } else {
                img.setImageResource(R.drawable.step9_radio_not_check);
            }
        }
        this.step9SaveDTO.setAche_medicine_effect(num);
        this.parentActivity.save9(this.step9SaveDTO);
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

            case R.id.radio1:
                this.checkRadio(0);
                break;
            case R.id.radio2:
                this.checkRadio(1);
                break;
            case R.id.radio3:
                this.checkRadio(2);
                break;
            case R.id.radio4:
                this.checkRadio(3);
                break;
            case R.id.radio5:
                this.checkRadio(4);
                break;

        }
    }


}
