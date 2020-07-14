package com.m2comm.headache.contentStepView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.m2comm.headache.Adapter.Step4GridviewAdapter;
import com.m2comm.headache.Adapter.Step8GridviewAdapter;
import com.m2comm.headache.Adapter.Step9GridviewAdapter;
import com.m2comm.headache.DTO.Step1SaveDTO;
import com.m2comm.headache.DTO.Step4DTO;
import com.m2comm.headache.DTO.Step8EtcDTO;
import com.m2comm.headache.DTO.Step9DTO;
import com.m2comm.headache.DTO.Step9Dates;
import com.m2comm.headache.DTO.Step9NewSaveDTO;
import com.m2comm.headache.DTO.Step9SaveDTO;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.module.Urls;
import com.m2comm.headache.sendDTO.Send9PixDTO;
import com.m2comm.headache.sendDTO.Step9SendDTO;
import com.m2comm.headache.views.ContentStepActivity;
import com.m2comm.headache.views.DrugInput;
import com.m2comm.headache.views.EtcInputActivity;
import com.m2comm.headache.views.Step9DatePicker;

import java.util.ArrayList;
import java.util.Arrays;

public class Step9 implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    public final static int ETC9_INPUT = 999;
    public final static int ETC9_INPUT2 = 9999;
    public final static int ETC9_INPUT3 = 8989;

    private LayoutInflater inflater;
    private int ParentID;
    private LinearLayout parent, step9LinearView, step9Title, radio1, radio2, radio3, radio4, radio5;
    private LinearLayout step9bottomParent , stpe9bottomView ;
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

    int radioClickNum = 0;
    int textHeight = 500;
    int successHeight = 700;

    private Urls urls;

    private Step9SaveDTO step9SaveDTO;
    private Step1SaveDTO step1SaveDTO; //고정 날짜를 알기위해서 넘겨줬다.

    private Step9NewSaveDTO step9NewSaveDTO;
    private Step9SendDTO step9SendDTO;

    public Step9(LayoutInflater inflater, int parentID, Context context, Activity activity, ContentStepActivity parentActivity, Step9SaveDTO step9SaveDTO, Step1SaveDTO step1SaveDTO) {
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

//        this.radio1.setOnClickListener(this);
//        this.radio2.setOnClickListener(this);
//        this.radio3.setOnClickListener(this);
//        this.radio4.setOnClickListener(this);
//        this.radio5.setOnClickListener(this);
    }

    private void init() {
        this.parent = this.activity.findViewById(this.ParentID);
        this.parent.removeAllViews();
        this.view = inflater.inflate(R.layout.step9, this.parent, true);

        this.nextBt = this.view.findViewById(R.id.nextBt);
        this.backBt = this.view.findViewById(R.id.backBt);
        this.step9LinearView = this.view.findViewById(R.id.step9View1);
        this.step9Title = this.view.findViewById(R.id.step9Title);
        this.step9bottomParent = this.view.findViewById(R.id.step9bottomParent);
        this.stpe9bottomView = this.view.findViewById(R.id.stpe9bottomView);
        this.urls = new Urls();

        if (this.step9SaveDTO == null) {

            this.step9SaveDTO = new Step9SaveDTO("N", "N", "N", "N"
                    , "N", "N", "N", "N",
                    "N", "N", "N", new ArrayList<Step9DTO>(), 0);

            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default1, R.drawable.step9_type_click1, "모름", false, false, false, new ArrayList<Step9Dates>(), 0, 0));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "이미그란", false, false, false, new ArrayList<Step9Dates>(), 0, 0));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "수마트란", false, false, false, new ArrayList<Step9Dates>(), 0, 0));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "슈그란", false, false, false, new ArrayList<Step9Dates>(), 0, 0));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "마이그란", false, false, false, new ArrayList<Step9Dates>(), 0, 0));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "조믹", false, false, false, new ArrayList<Step9Dates>(), 0, 0));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "나라믹", false, false, false, new ArrayList<Step9Dates>(), 0, 0));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "알모그란", false, false, false, new ArrayList<Step9Dates>(), 0, 0));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "미가드", false, false, false, new ArrayList<Step9Dates>(), 0, 0));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "크래밍", false, false, false, new ArrayList<Step9Dates>(), 0, 0));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step_type_etc, R.drawable.step_type_etc, "기타", false, true, false, new ArrayList<Step9Dates>(), 0, 0));
        } else {
           // this.checkRadio(this.step9SaveDTO.getAche_medicine_effect());
        }

        this.gridView = view.findViewById(R.id.step9_gridV);
        this.adapter = new Step9GridviewAdapter(this.step9SaveDTO.getStep9DTOS(), this.activity.getLayoutInflater());
        this.gridView.setAdapter(this.adapter);
        this.gridView.setOnItemClickListener(this);
        this.gridView.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        Step9DTO row = this.step9SaveDTO.getStep9DTOS().get(position);
        if (row.getEtcBt() || !row.getEtc()) return true;

        new AlertDialog.Builder(activity).setTitle("안내").setMessage("해당 항목을 삭제 하시겠습니까?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        parentActivity.removeETC(step9SaveDTO.getStep9DTOS().get(position).getKey());
                        step9SaveDTO.getStep9DTOS().remove(position);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Step9DTO row = this.step9SaveDTO.getStep9DTOS().get(position);
        if (row.getEtcBt()) {
            //종료일이 선택이 안되면 클릭.
            getEtcActivity();
        } else {
            if (position == 0) {
                if (row.getClick()) {
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

            if (row.getClick()) {
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

                if (step1SaveDTO.geteDate() != 0 && !Global.getTimeToStr(step1SaveDTO.getSdate()).equals(Global.getTimeToStr(step1SaveDTO.geteDate()))) {
                    Log.d("step9=","1");
                    Intent intent = new Intent(this.activity, Step9DatePicker.class);
                    if (this.step1SaveDTO != null) {
                        intent.putExtra("startDateLong", this.step1SaveDTO.getSdate());
                        intent.putExtra("endDateLong", this.step1SaveDTO.geteDate());
                    }
                    this.activity.startActivityForResult(intent, ETC9_INPUT2);
                } else {
                    Log.d("step9=","2");
                    Intent intent = new Intent(this.activity, Step9DatePicker.class);
                    intent.putExtra("isTime", true);
                    this.activity.startActivityForResult(intent, ETC9_INPUT3);
                }
            }
        }
    }

    public void oneDayClick(int effect) {
        this.step9SaveDTO.getStep9DTOS().get(this.itemClickRow).setClick(true);
        this.step9SaveDTO.getStep9DTOS().get(this.itemClickRow).setDrugArray(new ArrayList<Step9Dates>(Arrays.asList(new Step9Dates(Global.getTimeToStr(step1SaveDTO.getSdate()), "Y"))));
        this.step9SaveDTO.getStep9DTOS().get(this.itemClickRow).setEffect(effect);
        this.parentActivity.save9(step9SaveDTO);
        reloadListView();
    }

    public void countSet(ArrayList<Step9Dates> arrayList, int effect) {
        Log.d("ArrayListcount", arrayList.size() + "");
        this.step9SaveDTO.getStep9DTOS().get(this.itemClickRow).setDrugArray(arrayList);
        this.step9SaveDTO.getStep9DTOS().get(this.itemClickRow).setClick(true);
        this.step9SaveDTO.getStep9DTOS().get(this.itemClickRow).setEffect(effect);

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

    public void addListView(String etc, ArrayList<Step9Dates> arrayList, int effect) {
        this.step9SaveDTO.getStep9DTOS().remove(this.step9SaveDTO.getStep9DTOS().size() - 1);
        this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step_type_etc_add, R.drawable.step_type_etc_add, etc, true, false, true, arrayList, 0, effect));
        this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step_type_etc, R.drawable.step_type_etc, "기타", false, true, false, new ArrayList<Step9Dates>(), 0, 0));
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
        if (this.step1SaveDTO != null) {
            intent.putExtra("startDateLong", this.step1SaveDTO.getSdate());
            intent.putExtra("endDateLong", this.step1SaveDTO.geteDate());
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
                        layoutParams.height = (int) (titleHeight + (500 * Math.ceil((double) step9SaveDTO.getStep9DTOS().size() / 4)));
                        step9LinearView.setLayoutParams(layoutParams);
                    }
                });
            }
        });

        this.bottomResetView();
    }

    private void bottomResetView () {
        int count = 0;
        String data = "";
        stpe9bottomView.removeAllViews();
        for ( int i = 0, j = this.step9SaveDTO.getStep9DTOS().size(); i < j ; i ++ ) {
            if (this.step9SaveDTO.getStep9DTOS().get(i).getClick()) {
                Step9DTO row = this.step9SaveDTO.getStep9DTOS().get(i);

                for ( int k = 0 , l = this.step9SaveDTO.getStep9DTOS().get(i).getDrugArray().size(); k < l; k++ ) {
                    Step9Dates dateRows = this.step9SaveDTO.getStep9DTOS().get(i).getDrugArray().get(k);
                    if ( dateRows.getVal().equals("Y") ) {
                        count += 1;
                        data += "·복용일:"+dateRows.getDate()+"\n";
                    }
                }
                data+="\n·복용약:"+row.getName()+"\n";
                data+="·효과 본 시간:"+checkTime(row.getEffect())+"\n";
                count += 2;
                stpe9bottomView.addView(this.createView());
                stpe9bottomView.addView(this.creatTextView(data , 16));
                stpe9bottomView.addView(this.createLine());
                data = "";
            }
        }

        int parentH = (int) (count * Global.pxToDp(this.context, this.textHeight));
        this.stpe9bottomView.getLayoutParams().height = parentH;
        parentH = (int) ((int)parentH + Global.pxToDp(this.context, this.successHeight));
        Log.d("stpe9=",parentH+"_");

        this.step9bottomParent.getLayoutParams().height = parentH;

    }

    private String checkTime (int num) {
        String returnData = "";
        switch (num) {
            case 0:
                returnData = "효과없음";
                break;
            case 1:
                returnData = "30분 이내";
                break;
            case 2:
                returnData = "1시간 이내";
                break;
            case 3:
                returnData = "2시간 이내";
                break;
            case 4:
                returnData = "2시간 이상";
                break;
        }
        return returnData;
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

    private TextView creatTextView (String cotent , int size) {
        TextView tv = new TextView(this.context);
//        Typeface face = Typeface.createFromFile("/system/font/nanum.ttf");
//        tv.setTypeface(face);
        tv.setText(cotent);
        tv.setTextColor(Color.parseColor("#222222"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,size);
        tv.setPadding(10,3,3,3);
        tv.setGravity(Gravity.CENTER_VERTICAL);

        return tv;
    }

    private LinearLayout createView () {
        LinearLayout line = new LinearLayout(this.context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , 50);
        params.topMargin = 30;
        params.leftMargin = 20;
        params.rightMargin = 20;
        params.bottomMargin = 20;
        line.setLayoutParams(params);
        return  line;
    };

    private LinearLayout createLine () {
        LinearLayout line = new LinearLayout(this.context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , 2);
        params.topMargin = 30;
        params.leftMargin = 20;
        params.rightMargin = 20;
        params.bottomMargin = 20;
        line.setLayoutParams(params);
        line.setBackgroundColor(Color.parseColor("#dedede"));
        return  line;
    };


}
