package com.m2comm.headache.contentStepView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.m2comm.headache.Adapter.Step9NewListAdapter;
import com.m2comm.headache.DTO.Step1SaveDTO;
import com.m2comm.headache.DTO.Step9DTO;
import com.m2comm.headache.DTO.Step9Dates;
import com.m2comm.headache.DTO.Step9MainDTO;
import com.m2comm.headache.DTO.Step9MainEtcDTO;
import com.m2comm.headache.DTO.Step9NewSaveDTO;
import com.m2comm.headache.DTO.Step9SaveDTO;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.module.Custom_SharedPreferences;
import com.m2comm.headache.views.ContentStepActivity;
import com.m2comm.headache.views.Step9NewPopup1;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Step9_New implements AdapterView.OnItemClickListener, View.OnClickListener {

    public static int NEW_STEP9_CALL = 19;
    int nextStepNum = 10;
    int backStepNum = 8;
    private LayoutInflater inflater;
    private Context context;
    private Activity activity;

    private View view;
    private int parentID;
    private ContentStepActivity parentActivity;
    Custom_SharedPreferences csp;
    //private Step9SaveDTO step9SaveDTO;

    private Step1SaveDTO step1SaveDTO;
    private ArrayList<Step9MainDTO> step9DayArray;
    public ArrayList<Step9MainEtcDTO> step9MainEtcDTOS;

    LinearLayout parent;
    private ListView listView;
    private Step9NewListAdapter adapter;
    private TextView nextBt, backBt;

    public Step9_New(LayoutInflater inflater, int parentID, Context context, Activity activity, ContentStepActivity parentActivity,
                     ArrayList<Step9MainDTO> step9DayArray, Step1SaveDTO step1SaveDTO, ArrayList<Step9MainEtcDTO> step9MainEtcDTOS) {
        this.inflater = inflater;
        this.parentID = parentID;
        this.context = context;
        this.activity = activity;
        this.step9DayArray = step9DayArray;
        this.parentActivity = parentActivity;
        this.step1SaveDTO = step1SaveDTO;
        this.step9MainEtcDTOS = step9MainEtcDTOS;
        this.init();
    }

    private void regObj() {
        //1.Parent View 에 있는 childView를 모두 지운다.
        this.parent = this.activity.findViewById(this.parentID);
        this.parent.removeAllViews();

        //2.Parent View 에 childView를 추가합니다.
        this.view = inflater.inflate(R.layout.step9_new, this.parent, true);

        this.listView = this.view.findViewById(R.id.step9ListView);

        this.nextBt = this.view.findViewById(R.id.nextBt);
        this.nextBt.setOnClickListener(this);
        this.backBt = this.view.findViewById(R.id.backBt);
        this.backBt.setOnClickListener(this);
        this.csp = new Custom_SharedPreferences(this.context);
    }

    private void init() {

        this.regObj();

        if (step9DayArray == null || step9DayArray.size() <= 0) {
            //시작 날짜와 끝날짜를 비교하여서 리스트 날짜범위 만큼 리스트 Item 을 출력해준다.
            this.step9DayArray = new ArrayList();
            ArrayList<String> formatDates = Global.getFormatDates(this.step1SaveDTO.getSdate(), this.step1SaveDTO.geteDate());
            for (String date : formatDates) {
                if (this.step9MainEtcDTOS == null) this.step9MainEtcDTOS = new ArrayList<>();
                this.step9DayArray.add(new Step9MainDTO(date, "0", "N", "N", "N", "N",
                        "N", "N", "N", "N", "N", "N", this.step9MainEtcDTOS));
            }
        }
        this.resetAdapter();
    }

    private void resetAdapter() {
        this.adapter = new Step9NewListAdapter(this.context, this.activity, this.step1SaveDTO, this.step9DayArray, this.inflater);
        this.listView.setAdapter(this.adapter);
        this.listView.setOnItemClickListener(this);
    }

    //ListView Click  PopUp 생성.
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Step9MainDTO row = this.step9DayArray.get(position);
        Intent intent = new Intent(this.activity, Step9NewPopup1.class);
        intent.putExtra("row", row);
        intent.putExtra("position", position);
        intent.putExtra("arr",this.step9DayArray);
        this.activity.startActivityForResult(intent, Step9_New.NEW_STEP9_CALL);
    }

    //Day 에서 데이터 입력후 처리.
    public void save(Step9MainDTO row, int position , ArrayList<Step9MainDTO> step9MainDTOArrayList) {
        this.step9DayArray.set(position, row);
        Log.d("step9NewSize=",step9MainDTOArrayList.size()+"_");
        this.step9DayArray = step9MainDTOArrayList;

        this.resetAdapter();
        this.parentActivity.save9(this.step9DayArray);
    }

    public void cancel_save(ArrayList<Step9MainDTO> step9MainDTOArrayList) {
        this.step9DayArray = step9MainDTOArrayList;
        this.resetAdapter();
        this.parentActivity.save9(this.step9DayArray);
    }

    public void addListView(String etc, ArrayList<Step9Dates> arrayList, int effect) {

    }

    public void countSet(ArrayList<Step9Dates> arrayList, int effect) {

    }

    public void oneDayClick(int effect) {

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
