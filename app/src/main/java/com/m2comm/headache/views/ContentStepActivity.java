package com.m2comm.headache.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.m2comm.headache.DTO.Step10Dates;
import com.m2comm.headache.DTO.Step10EtcDTO;
import com.m2comm.headache.DTO.Step10SaveDTO;
import com.m2comm.headache.DTO.Step11SaveDTO;
import com.m2comm.headache.DTO.Step12SaveDTO;
import com.m2comm.headache.DTO.Step1SaveDTO;
import com.m2comm.headache.DTO.Step2SaveDTO;
import com.m2comm.headache.DTO.Step3SaveDTO;
import com.m2comm.headache.DTO.Step4EtcDTO;
import com.m2comm.headache.DTO.Step4SaveDTO;
import com.m2comm.headache.DTO.Step5EtcDTO;
import com.m2comm.headache.DTO.Step5SaveDTO;
import com.m2comm.headache.DTO.Step6SaveDTO;
import com.m2comm.headache.DTO.Step7EtcDTO;
import com.m2comm.headache.DTO.Step7SaveDTO;
import com.m2comm.headache.DTO.Step8EtcDTO;
import com.m2comm.headache.DTO.Step8SaveDTO;
import com.m2comm.headache.DTO.Step9DTO;
import com.m2comm.headache.DTO.Step9Dates;
import com.m2comm.headache.DTO.Step9SaveDTO;
import com.m2comm.headache.DTO.StepParentDTO;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.contentStepView.Step1;
import com.m2comm.headache.contentStepView.Step10;
import com.m2comm.headache.contentStepView.Step11;
import com.m2comm.headache.contentStepView.Step12;
import com.m2comm.headache.contentStepView.Step2;
import com.m2comm.headache.contentStepView.Step3;
import com.m2comm.headache.contentStepView.Step4;
import com.m2comm.headache.contentStepView.Step5;
import com.m2comm.headache.contentStepView.Step6;
import com.m2comm.headache.contentStepView.Step7;
import com.m2comm.headache.contentStepView.Step8;
import com.m2comm.headache.contentStepView.Step9;
import com.m2comm.headache.databinding.ActivityContentStepBinding;
import com.m2comm.headache.module.Custom_SharedPreferences;
import com.m2comm.headache.module.Urls;
import com.m2comm.headache.sendDTO.CalendarDTO;
import com.m2comm.headache.sendDTO.Send9PixDTO;
import com.m2comm.headache.sendDTO.Step4SendDTO;
import com.m2comm.headache.sendDTO.Step9SendDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ContentStepActivity extends AppCompatActivity  implements View.OnClickListener {

    ActivityContentStepBinding binding;
    private Custom_SharedPreferences csp;
    private Urls urls;

    int defaultStep = 0;
    int BUTTON_COUNT = 12;

    Step1 step1;
    Step2 step2;
    Step3 step3;
    Step4 step4;
    Step5 step5;
    Step6 step6;
    Step7 step7;
    Step8 step8;
    Step9 step9;
    Step10 step10;
    Step11 step11;

    StepParentDTO stepParentDTO;

    Step1SaveDTO step1SaveDTO;
    Step2SaveDTO step2SaveDTO;
    Step3SaveDTO step3SaveDTO;
    Step4SaveDTO step4SaveDTO;
    Step5SaveDTO step5SaveDTO;
    Step6SaveDTO step6SaveDTO;
    Step7SaveDTO step7SaveDTO;
    Step8SaveDTO step8SaveDTO;
    Step9SaveDTO step9SaveDTO;
    Step10SaveDTO step10SaveDTO;
    Step11SaveDTO step11SaveDTO;
    Step12SaveDTO step12SaveDTO;

    int[] stepIds = {
            R.id.step1Bt,
            R.id.step2Bt,
            R.id.step3Bt,
            R.id.step4Bt,
            R.id.step5Bt,
            R.id.step6Bt,
            R.id.step7Bt,
            R.id.step8Bt,
            R.id.step9Bt,
            R.id.step10Bt,
            R.id.step11Bt,
            R.id.step12Bt,
    };

    int[] stepDotIds = {
            R.id.step1_dot,
            R.id.step2_dot,
            R.id.step3_dot,
            R.id.step4_dot,
            R.id.step5_dot,
            R.id.step6_dot,
            R.id.step7_dot,
            R.id.step8_dot,
            R.id.step9_dot,
            R.id.step10_dot,
            R.id.step11_dot,
            R.id.step12_dot,
    };

    String[] titles = {
            "기간",
            "강도",
            "위치",
            "유형",
            "전구",
            "조짐",
            "증상",
            "요인",
            "약물",
            "장애",
            "월경",
            "기록"
    };

    int diary_sid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_step);

        this.binding = DataBindingUtil.setContentView(this,R.layout.activity_content_step);
        this.binding.setStep(this);
        this.init();
        this.regObj();
    }

    private void regObj () {
        this.binding.closeBt.setOnClickListener(this);
    }

    public void positionView (int num) {
        LinearLayout l = findViewById(stepIds[num-1]);
        this.stayStep(l);
    }

    private void init () {
        this.csp = new Custom_SharedPreferences(this);
        this.urls = new Urls();

        for ( int i = 0, j = stepIds.length ; i < j ; i ++ ) {
            LinearLayout l = findViewById(stepIds[i]);
            l.setTag(i);
            l.setOnClickListener(this);
        }

        Intent intent = getIntent();
        this.diary_sid = intent.getIntExtra("diary_sid",-1);

        if ( this.diary_sid != -1 ) {
            getDiary();
        } else {
            LinearLayout l = findViewById(stepIds[defaultStep]);
            this.stayStep(l);
        }

    }

    private void getDiary() {

        AndroidNetworking.post(this.urls.mainUrl+this.urls.getUrls.get("getDiary"))
                .addBodyParameter("user_sid","13")
                .addBodyParameter("diary_sid",String.valueOf(this.diary_sid))
                .setPriority(Priority.MEDIUM)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("stepContent _son",response.toString());
                try {
                    Log.d("sdate",response.getLong("sdate")+"");
                    step1SaveDTO = new Step1SaveDTO(response.getLong("sdate")*1000 , response.getLong("edate")*1000 , response.getString("address"));
                    step2SaveDTO = new Step2SaveDTO(response.getInt("ache_power"));
                    step3SaveDTO = new Step3SaveDTO(response.getString("ache_location1"),response.getString("ache_location2"),response.getString("ache_location3"),response.getString("ache_location4"),
                            response.getString("ache_location5"),response.getString("ache_location6"),response.getString("ache_location7"),response.getString("ache_location8"),
                            response.getString("ache_location9"),response.getString("ache_location10"),response.getString("ache_location11"),response.getString("ache_location12"),
                            response.getString("ache_location13"),response.getString("ache_location14"),response.getString("ache_location15"));

                    ArrayList<Step4EtcDTO> step4EtcDTOS = new ArrayList<>();

                    step4EtcDTOS.add(new Step4EtcDTO(R.drawable.step4_type_default1,R.drawable.step4_type_click1,"욱신거림",false,false , response.getString("ache_type1").equals("Y") ,0 , response.getString("ache_type1")));
                    step4EtcDTOS.add(new Step4EtcDTO(R.drawable.step4_type_default2,R.drawable.step4_type_click2,"조임",false,false, response.getString("ache_type2").equals("Y"),0,response.getString("ache_type2")));
                    step4EtcDTOS.add(new Step4EtcDTO(R.drawable.step4_type_default3,R.drawable.step4_type_click3,"터질듯함",false,false, response.getString("ache_type3").equals("Y"),0,response.getString("ache_type3")));
                    step4EtcDTOS.add(new Step4EtcDTO(R.drawable.step4_type_default4,R.drawable.step4_type_click4,"찌름",false,false, response.getString("ache_type4").equals("Y"),0,response.getString("ache_type4")));
                    //step4EtcDTOS.add(new Step4EtcDTO(R.drawable.step4_type_default5,R.drawable.step4_type_click5,"따끔따끔",false,false, response.getString("ache_type5").equals("Y"),0,response.getString("ache_type5")));
                    step4EtcDTOS.add(new Step4EtcDTO(R.drawable.step_type_etc,R.drawable.step_type_etc,"기타",false,true, false,0,"N"));
                    JSONArray list4 = (JSONArray) response.get("ache_type_etc");
                    for (int i = 0, j = list4.length(); i < j; i++) {
                        JSONObject obj = (JSONObject) list4.get(i);
                        step4EtcDTOS.add(new Step4EtcDTO(R.drawable.step4_type_default1,R.drawable.step4_type_click1,obj.getString("content"),true,false , obj.getString("val").equals("Y") ,obj.getInt("key") , obj.getString("val")));
                    }
                    step4SaveDTO = new Step4SaveDTO(response.getString("ache_type1"),response.getString("ache_type2"),response.getString("ache_type3"),response.getString("ache_type4"),"N",step4EtcDTOS);


                    ArrayList<Step5EtcDTO> step5EtcDTOS = new ArrayList<>();
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default1,R.drawable.step5_type_click1,"아플 것 같은\n느낌",false,false,response.getString("ache_realize1").equals("Y"),0 , response.getString("ache_realize1")));
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default2,R.drawable.step5_type_click2,"뒷목통증\n뻐근함/당김",false,false,response.getString("ache_realize1").equals("Y"),0 , response.getString("ache_realize2")));
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default3,R.drawable.step5_type_click3,"하품",false,false,response.getString("ache_realize3").equals("Y"),0 , response.getString("ache_realize3")));
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default4,R.drawable.step5_type_click4,"피로",false,false,response.getString("ache_realize4").equals("Y"),0 , response.getString("ache_realize4")));
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default5,R.drawable.step5_type_click5,"집중력저하",false,false,response.getString("ache_realize5").equals("Y"),0 , response.getString("ache_realize5")));
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default6,R.drawable.step5_type_click6,"기분변화",false,false,response.getString("ache_realize6").equals("Y"),0 , response.getString("ache_realize6")));
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default7,R.drawable.step5_type_click7,"식욕변화",false,false,response.getString("ache_realize7").equals("Y"),0 , response.getString("ache_realize7")));
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default8,R.drawable.step5_type_click8,"빛/소리/\n냄새에 과민",false,false,response.getString("ache_realize8").equals("Y"),0 , response.getString("ache_realize8")));
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step_type_etc,R.drawable.step_type_etc,"기타",false,true, false,0 , "N"));
                    JSONArray list5 = (JSONArray) response.get("ache_realize_etc");
                    for (int i = 0, j = list5.length(); i < j; i++) {
                        JSONObject obj = (JSONObject) list5.get(i);
                        step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step4_type_default1,R.drawable.step4_type_click1,obj.getString("content"),true,false , obj.getString("val").equals("Y") ,obj.getInt("key") , obj.getString("val")));
                    }
                    step5SaveDTO = new Step5SaveDTO(response.getString("ache_realize_yn"),response.getInt("ache_realize_hour"),response.getInt("ache_realize_minute"),
                            response.getString("ache_realize1"),response.getString("ache_realize2"),response.getString("ache_realize3"),response.getString("ache_realize4"),
                            response.getString("ache_realize5"),response.getString("ache_realize6"),response.getString("ache_realize7"),response.getString("ache_realize8"),step5EtcDTOS);


                    step6SaveDTO = new Step6SaveDTO(response.getString("ache_sign_yn"),response.getString("ache_sign1"),response.getString("ache_sign2"),
                            response.getString("ache_sign3"),response.getString("ache_sign4"),response.getString("ache_sign5"),
                            response.getString("ache_sign6"),response.getString("ache_sign7"));


                    ArrayList<Step7EtcDTO> step7EtcDTOS = new ArrayList<>();
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default1,R.drawable.step7_type_click1,"소화가 안됨",false,false,response.getString("ache_with1").equals("Y"),0 , response.getString("ache_with1")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default2,R.drawable.step7_type_click2,"울렁거림",false,false,response.getString("ache_with2").equals("Y"),0 , response.getString("ache_with2")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default3,R.drawable.step7_type_click3,"구토",false,false,response.getString("ache_with3").equals("Y"),0 , response.getString("ache_with3")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default4,R.drawable.step7_type_click4,"어지럼증",false,false,response.getString("ache_with4").equals("Y"),0 , response.getString("ache_with4")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default5,R.drawable.step7_type_click5,"움직임에\n의해 악화",false,false,response.getString("ache_with5").equals("Y"),0 , response.getString("ache_with5")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default6,R.drawable.step7_type_click6,"빛에 예민",false,false,response.getString("ache_with6").equals("Y"),0 , response.getString("ache_with6")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default7,R.drawable.step7_type_click7,"소리에 예민",false,false,response.getString("ache_with7").equals("Y"),0 , response.getString("ache_with7")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default8,R.drawable.step7_type_click8,"냄새에 예민",false,false,response.getString("ache_with8").equals("Y"),0 , response.getString("ache_with8")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default9,R.drawable.step7_type_click9,"뒷목통증/\n뻐근함/당김",false,false,response.getString("ache_with8").equals("Y"),0 , response.getString("ache_with9")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default10,R.drawable.step7_type_click10,"어깨통증",false,false,response.getString("ache_with9").equals("Y"),0 , response.getString("ache_with9")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default11,R.drawable.step7_type_click11,"눈물/눈충혈",false,false,response.getString("ache_with10").equals("Y"),0 , response.getString("ache_with10")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default12,R.drawable.step7_type_click12,"콧물/코막힘",false,false,response.getString("ache_with11").equals("Y"),0 , response.getString("ache_with11")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step_type_etc,R.drawable.step_type_etc,"기타",false,true,false,0 , "N"));

                    JSONArray list7 = (JSONArray) response.get("ache_with_etc");
                    for (int i = 0, j = list7.length(); i < j; i++) {
                        JSONObject obj = (JSONObject) list7.get(i);
                        step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step4_type_default1,R.drawable.step4_type_click1,obj.getString("content"),true,false , obj.getString("val").equals("Y") ,obj.getInt("key") , obj.getString("val")));
                    }
                    step7SaveDTO = new Step7SaveDTO(response.getString("ache_with_yn"),response.getString("ache_with1"),response.getString("ache_with2"),response.getString("ache_with3"),
                            response.getString("ache_with4"),response.getString("ache_with5"),response.getString("ache_with6"),response.getString("ache_with7"),
                            response.getString("ache_with8"),response.getString("ache_with9"),response.getString("ache_with10"),response.getString("ache_with11"),
                            response.getString("ache_with12"),step7EtcDTOS);



                    ArrayList<Step8EtcDTO> step8EtcDTOS = new ArrayList<>();
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default1,R.drawable.step8_type_click1,"스트레스",false,false,response.getString("ache_factor1").equals("Y"),0 , response.getString("ache_factor1")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default2,R.drawable.step8_type_click2,"피로",false,false,response.getString("ache_factor2").equals("Y"),0 , response.getString("ache_factor2")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default3,R.drawable.step8_type_click3,"수면부족",false,false,response.getString("ache_factor3").equals("Y"),0 , response.getString("ache_factor3")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default4,R.drawable.step8_type_click4,"낮잠 또는\n늦잠",false,false,response.getString("ache_factor4").equals("Y"),0 , response.getString("ache_factor4")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default5,R.drawable.step8_type_click5,"주말",false,false,response.getString("ache_factor5").equals("Y"),0 , response.getString("ache_factor5")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default6,R.drawable.step8_type_click6,"굶음",false,false,response.getString("ache_factor6").equals("Y"),0 , response.getString("ache_factor6")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default8,R.drawable.step8_type_click8,"체함",false,false,response.getString("ache_factor7").equals("Y"),0 , response.getString("ache_factor7")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default7,R.drawable.step8_type_click7,"과식",false,false,response.getString("ache_factor8").equals("Y"),0 , response.getString("ache_factor8")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default9,R.drawable.step8_type_click9,"빛",false,false,response.getString("ache_factor9").equals("Y"),0 , response.getString("ache_factor9")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default10,R.drawable.step8_type_click10,"소리",false,false,response.getString("ache_factor10").equals("Y"),0 , response.getString("ache_factor10")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default11,R.drawable.step8_type_click11,"냄새",false,false,response.getString("ache_factor11").equals("Y"),0 , response.getString("ache_factor11")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default12,R.drawable.step8_type_click12,"감기",false,false,response.getString("ache_factor12").equals("Y"),0 , response.getString("ache_factor12")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default13,R.drawable.step8_type_click13,"운동",false,false,response.getString("ache_factor13").equals("Y"),0 , response.getString("ache_factor13")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default14,R.drawable.step8_type_click14,"술",false,false,response.getString("ache_factor14").equals("Y"),0 , response.getString("ache_factor14")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default15,R.drawable.step8_type_click15,"월경",false,false,response.getString("ache_factor15").equals("Y"),0 , response.getString("ache_factor15")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step_type_etc,R.drawable.step_type_etc,"기타",false,true,false,0 , "N"));
                    JSONArray list8 = (JSONArray) response.get("ache_with_etc");
                    for (int i = 0, j = list8.length(); i < j; i++) {
                        JSONObject obj = (JSONObject) list8.get(i);
                        step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step4_type_default1,R.drawable.step4_type_click1,obj.getString("content"),true,false , obj.getString("val").equals("Y") ,obj.getInt("key") , obj.getString("val")));
                    }

                    step8SaveDTO = new Step8SaveDTO(response.getString("ache_factor_yn"),response.getString("ache_factor1"),response.getString("ache_factor2"),response.getString("ache_factor3"),
                            response.getString("ache_factor4"),response.getString("ache_factor5"),response.getString("ache_factor6"),response.getString("ache_factor7"),
                            response.getString("ache_factor8"),response.getString("ache_factor9"),response.getString("ache_factor10"),response.getString("ache_factor11"),
                            response.getString("ache_factor12"),response.getString("ache_factor13"),
                            response.getString("ache_factor14"),response.getString("ache_factor15"),step8EtcDTOS);



                    //step9는 나중에...


                    //step10
                    ArrayList<Step10EtcDTO> step10EtcDTOS = new ArrayList<Step10EtcDTO>();
                    step10EtcDTOS.add(new Step10EtcDTO(R.drawable.step10_type_default1,R.drawable.step10_type_click1,"결근/결석",false,false,response.getString("ache_effect1").equals("Y"),0 , response.getString("ache_effect1")));
                    step10EtcDTOS.add(new Step10EtcDTO(R.drawable.step10_type_default2,R.drawable.step10_type_click2,"업무/학습\n능률저하",false,false,response.getString("ache_effect2").equals("Y"),0 , response.getString("ache_effect2")));
                    step10EtcDTOS.add(new Step10EtcDTO(R.drawable.step10_type_default3,R.drawable.step10_type_click3,"가사활동\n못함",false,false,response.getString("ache_effect3").equals("Y"),0 , response.getString("ache_effect3")));
                    step10EtcDTOS.add(new Step10EtcDTO(R.drawable.step10_type_default4,R.drawable.step10_type_click4,"가사활동\n능률 저하",false,false,response.getString("ache_effect4").equals("Y"),0 , response.getString("ache_effect4")));
                    step10EtcDTOS.add(new Step10EtcDTO(R.drawable.step10_type_default5,R.drawable.step10_type_click5,"여가활동\n불참",false,false,response.getString("ache_effect5").equals("Y"),0 , response.getString("ache_effect5")));
                    step10EtcDTOS.add(new Step10EtcDTO(R.drawable.step_type_etc,R.drawable.step_type_etc,"기타",false,true,false,0 , "N"));
                    JSONArray list10 = (JSONArray) response.get("ache_effect_etc");
                    for (int i = 0, j = list10.length(); i < j; i++) {
                        JSONObject obj = (JSONObject) list10.get(i);
                        step10EtcDTOS.add(new Step10EtcDTO(R.drawable.step4_type_default1,R.drawable.step4_type_click1,obj.getString("content"),true,false , obj.getString("val").equals("Y") ,obj.getInt("key") , obj.getString("val")));
                    }

                    ArrayList<Step9Dates> step10Dates = new ArrayList<Step9Dates>();

                    step10SaveDTO = new Step10SaveDTO(response.getString("ache_effect1"),response.getString("ache_effect2"),response.getString("ache_effect3"),
                            response.getString("ache_effect4"),response.getString("ache_effect5"),step10Dates,step10EtcDTOS);

                    //step11
                    Long stime = response.isNull("mens_sdate") ? 0L: Global.getStrToDate(response.getString("mens_sdate")).getTime();
                    Long etime = response.isNull("mens_edate") ? 0L: Global.getStrToDate(response.getString("mens_sdate")).getTime();
                    step11SaveDTO = new Step11SaveDTO(stime , etime);
                    step12SaveDTO = new Step12SaveDTO(response.getString("memo"));

                    LinearLayout l = findViewById(stepIds[0]);
                    stayStep(l);

                } catch (JSONException e) {
                    Log.d("Exceptionee",e.toString());
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.d("stepContent Json Error",anError.toString());
            }
        });
    }


    private void getStep(int num) {
        //이 함수를 호출하기전에 step 의 모든 데이터를 넣어준다.
        LinearLayout l = findViewById(stepIds[num]);
        this.stayStep(l);
    }


    private void stayStep ( LinearLayout parentV ) {

        //1. 현재 버튼 색상 변경
        for ( int i = 0, j = parentV.getChildCount(); i < j; i++ ) {
            LinearLayout child1 = ( LinearLayout ) parentV.getChildAt(i);
            child1.setBackgroundResource( R.drawable.step_content_bt_work );
            for ( int k = 0 , l = child1.getChildCount(); k < l ; k ++ ) {
                TextView child2 = (TextView) child1.getChildAt(k);
                child2.setBackgroundColor(Color.TRANSPARENT);
                child2.setTextColor(Color.WHITE);
            }
        }

        //2.Dot 현재 진행 Dot 작업.
        for ( int i = (int)parentV.getTag(); i >= 0; i-- ) {
            if ( i != 0 && i != this.BUTTON_COUNT-1) {
                ImageView img = findViewById(this.stepDotIds[i-1]);
                img.setImageResource(R.drawable.step_success_dot);
            }
        }

        ImageView dot = findViewById(this.stepDotIds[(int)parentV.getTag()]);
        dot.setImageResource(R.drawable.step_not_work_dot);

        //3.  이전 버튼 색상 변경
        for ( int i = (int)parentV.getTag(); i >= 0; i-- ) {
            if ( i == (int)parentV.getTag()) continue;

            LinearLayout previousV = (LinearLayout) findViewById(this.stepIds[i]);
            LinearLayout child1 = ( LinearLayout ) previousV.getChildAt(0);
            child1.setBackgroundResource( R.drawable.step_content_bt_success );

            for ( int k = 0 , l = child1.getChildCount(); k < l ; k ++ ) {
                TextView child2 = (TextView) child1.getChildAt(k);
                child2.setBackgroundResource( R.drawable.step_content_bt_sub_success );
                child2.setTextColor(Color.parseColor("#9C9C9C"));
            }
        }

        //4. 이후 버튼 색상변경
        for ( int i = (int)parentV.getTag(); i < this.BUTTON_COUNT; i++ ) {
            if ( i == (int)parentV.getTag()) continue;

            LinearLayout previousV = (LinearLayout) findViewById(this.stepIds[i]);
            LinearLayout child1 = ( LinearLayout ) previousV.getChildAt(0);
            child1.setBackgroundResource( R.drawable.step_content_bt_not_work );
            for ( int k = 0 , l = child1.getChildCount(); k < l ; k ++ ) {
                TextView child2 = (TextView) child1.getChildAt(k);
                child2.setBackgroundColor(Color.TRANSPARENT);
                child2.setTextColor(Color.parseColor("#D9D9D7"));
            }
            ImageView img = findViewById(this.stepDotIds[i]);
            img.setImageResource(R.drawable.step_not_work_dot);
        }

        this.binding.scrollview.smoothScrollTo((int)parentV.getX()-parentV.getWidth(),0);
        this.getView((int)parentV.getTag());
    }

    private void getView( int num) {
        this.binding.title.setText(this.titles[num]);
        switch (num) {
            case 0:
                this.step1 = new Step1( getLayoutInflater(),R.id.contentV , this , this , this , this.step1SaveDTO);
                break;
            case 1:
                this.step2 = new Step2(getLayoutInflater(),R.id.contentV , this , this , this , this.step2SaveDTO);
                break;
            case 2:
                this.step3 = new Step3(getLayoutInflater(),R.id.contentV , this , this , this , this.step3SaveDTO);
                break;
            case 3:
                this.step4 = new Step4(getLayoutInflater(),R.id.contentV , this , this, this , this.step4SaveDTO);
                break;
            case 4:
                this.step5 = new Step5(getLayoutInflater(),R.id.contentV , this , this, this , this.step5SaveDTO);
                break;
            case 5:
                this.step6 = new Step6(getLayoutInflater(),R.id.contentV , this , this,this , this.step6SaveDTO);
                break;
            case 6:
                this.step7 = new Step7(getLayoutInflater(),R.id.contentV , this , this ,this , this.step7SaveDTO);
                break;
            case 7:
                this.step8 = new Step8(getLayoutInflater(),R.id.contentV , this , this,this , this.step8SaveDTO);
                break;
            case 8:
                this.step9 = new Step9(getLayoutInflater(),R.id.contentV , this , this,this , this.step9SaveDTO , this.step1SaveDTO);
                break;
            case 9:
                this.step10 = new Step10(getLayoutInflater(),R.id.contentV , this , this,this , this.step10SaveDTO, this.step1SaveDTO);
                break;
            case 10:
                this.step11 = new Step11(getLayoutInflater(),R.id.contentV , this , this,this , this.step11SaveDTO);
                break;
            case 11:
                new Step12(getLayoutInflater(),R.id.contentV , this , this,this , this.step12SaveDTO);
                break;
            case 12:

                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.step1Bt:
            case R.id.step2Bt:
            case R.id.step3Bt:
            case R.id.step4Bt:
            case R.id.step5Bt:
            case R.id.step6Bt:
            case R.id.step7Bt:
            case R.id.step8Bt:
            case R.id.step9Bt:
            case R.id.step10Bt:
            case R.id.step11Bt:
            case R.id.step12Bt:
                this.stayStep( (LinearLayout) v );
                break;

            case R.id.closeBt:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult", requestCode + "_");
        if (resultCode == RESULT_OK) {
            assert data != null;

            if ( requestCode == Step1.BT1 ) {
                this.step1.startTimeSetting(data.getLongExtra("startDateTime",0));
            } else if ( requestCode == Step1.BT2 ) {
                this.step1.endTimeSetting(data.getLongExtra("endDateTime",0));
            } else if ( requestCode == Step4.ETC4_INPUT ) {
                this.step4.addListView(data.getStringExtra("input1"));
            } else if ( requestCode == Step5.ETC5_INPUT ) {
                this.step5.addListView(data.getStringExtra("input1"));
            }  else if ( requestCode == Step7.ETC7_INPUT ) {
                this.step7.addListView(data.getStringExtra("input1"));
            }  else if ( requestCode == Step8.ETC8_INPUT ) {
                this.step8.addListView(data.getStringExtra("input1"));
            } else if ( requestCode == Step9.ETC9_INPUT ) {
                ArrayList<Step9Dates> dateArry = (ArrayList<Step9Dates>) data.getSerializableExtra("dates");
                this.step9.addListView(data.getStringExtra("input1"),dateArry);
            } else if ( requestCode == Step10.ETC10_INPUT ) {
                ArrayList<Step9Dates> dateArry = (ArrayList<Step9Dates>) data.getSerializableExtra("dates");
                Log.d("dateSizee",dateArry.size()+"_");
                //this.step10.addListView(data.getStringExtra("input1"));
                this.step10.saveDate(dateArry);
            } else if ( requestCode == Step11.BT1 ) {
                this.step11.startTimeSetting(data.getLongExtra("startDateTime",0));
            } else if ( requestCode == Step11.BT2 ) {
                this.step11.endTimeSetting(data.getLongExtra("endDateTime",0));
            } else if ( requestCode == Step9.ETC9_INPUT2 ) {
                ArrayList<Step9Dates> dateArry = (ArrayList<Step9Dates>) data.getSerializableExtra("dates");
                this.step9.countSet(dateArry);

            }
        }
    }

    public void save1(Step1SaveDTO step1SaveDTO) {
        this.step1SaveDTO = step1SaveDTO;
    }
    public void save2(Step2SaveDTO step2SaveDTO) {
        this.step2SaveDTO = step2SaveDTO;
    }
    public void save3(Step3SaveDTO step3SaveDTO) {
        this.step3SaveDTO = step3SaveDTO;
    }
    public void save4(Step4SaveDTO step4SaveDTO) {
        this.step4SaveDTO = step4SaveDTO;
    }
    public void save5(Step5SaveDTO step5SaveDTO) {
        this.step5SaveDTO = step5SaveDTO;
    }
    public void save6(Step6SaveDTO step6SaveDTO) {
        this.step6SaveDTO = step6SaveDTO;
    }
    public void save7(Step7SaveDTO step7SaveDTO) {
        this.step7SaveDTO = step7SaveDTO;
    }
    public void save8(Step8SaveDTO step8SaveDTO) {
        this.step8SaveDTO = step8SaveDTO;
    }
    public void save9(Step9SaveDTO step9SaveDTO) {
        this.step9SaveDTO = step9SaveDTO;
    }
    public void save10(Step10SaveDTO step10SaveDTO) {
        this.step10SaveDTO = step10SaveDTO;
    }
    public void save11(Step11SaveDTO step11SaveDTO) {
        this.step11SaveDTO = step11SaveDTO;
    }
    public void save12(Step12SaveDTO step12SaveDTO) {
        this.step12SaveDTO = step12SaveDTO;
    }

    public void sendData() {

        if (this.step1SaveDTO == null) {
            this.step1SaveDTO = new Step1SaveDTO(0L , 0L ,"");
        }
        if ( step2SaveDTO == null ) {
            step2SaveDTO = new Step2SaveDTO(1);
        }
        if ( step3SaveDTO == null ) {
            step3SaveDTO = new Step3SaveDTO(
                    "N","N","N","N",
                    "N","N","N","N",
                    "N","N","N","N",
                    "N","N","N");
        }
        if ( this.step4SaveDTO == null ) {
            this.step4SaveDTO = new Step4SaveDTO("N","N","N","N","N",new ArrayList<Step4EtcDTO>());
            this.step4SaveDTO.getArrayList().add(new Step4EtcDTO(R.drawable.step4_type_default1,R.drawable.step4_type_click1,"욱신거림",false,false , false ,0 , "N"));
            this.step4SaveDTO.getArrayList().add(new Step4EtcDTO(R.drawable.step4_type_default2,R.drawable.step4_type_click2,"조임",false,false, false,0,"N"));
            this.step4SaveDTO.getArrayList().add(new Step4EtcDTO(R.drawable.step4_type_default3,R.drawable.step4_type_click3,"터질듯함",false,false, false,0,"N"));
            this.step4SaveDTO.getArrayList().add(new Step4EtcDTO(R.drawable.step4_type_default4,R.drawable.step4_type_click4,"찌름",false,false, false,0,"N"));
            this.step4SaveDTO.getArrayList().add(new Step4EtcDTO(R.drawable.step4_type_default5,R.drawable.step4_type_click5,"따끔따끔",false,false, false,0,"N"));
            this.step4SaveDTO.getArrayList().add(new Step4EtcDTO(R.drawable.step_type_etc,R.drawable.step_type_etc,"기타",false,true, false,0,"N"));
        }
        if ( this.step5SaveDTO == null ) {
            this.step5SaveDTO = new Step5SaveDTO("N",0,0,
                    "N","N","N","N",
                    "N","N","N","N",new ArrayList<Step5EtcDTO>());
            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default1,R.drawable.step5_type_click1,"아플 것 같은\n느낌",false,false,false,0 , "N"));
            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default2,R.drawable.step5_type_click2,"뒷목통증\n뻐근함/당김",false,false,false,0 , "N"));
            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default3,R.drawable.step5_type_click3,"하품",false,false,false,0 , "N"));
            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default4,R.drawable.step5_type_click4,"피로",false,false,false,0 , "N"));
            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default5,R.drawable.step5_type_click5,"집중력저하",false,false,false,0 , "N"));
            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default6,R.drawable.step5_type_click6,"기분변화",false,false,false,0 , "N"));
            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default7,R.drawable.step5_type_click7,"식욕변화",false,false,false,0 , "N"));
            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default8,R.drawable.step5_type_click8,"빛/소리/\n냄새에 과민",false,false,false,0 , "N"));
            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step_type_etc,R.drawable.step_type_etc,"기타",false,true, false,0 , "N"));
        }

        if ( this.step6SaveDTO == null ) {
            this.step6SaveDTO = new Step6SaveDTO("N","N","N",
                    "N","N","N","N","N");
        }

        if ( this.step7SaveDTO == null ) {

            this.step7SaveDTO = new Step7SaveDTO("N","N","N","N","N",
                    "N","N","N","N",
                    "N","N","N","N",new ArrayList<Step7EtcDTO>());
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default1,R.drawable.step7_type_click1,"소화가 안됨",false,false,false,0 , "N"));
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default2,R.drawable.step7_type_click2,"울렁거림",false,false,false,0 , "N"));
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default3,R.drawable.step7_type_click3,"구토",false,false,false,0 , "N"));
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default4,R.drawable.step7_type_click4,"어지럼증",false,false,false,0 , "N"));
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default5,R.drawable.step7_type_click5,"움직임에\n의해 악화",false,false,false,0 , "N"));
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default6,R.drawable.step7_type_click6,"빛에 예민",false,false,false,0 , "N"));
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default7,R.drawable.step7_type_click7,"소리에 예민",false,false,false,0 , "N"));
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default8,R.drawable.step7_type_click8,"냄새에 예민",false,false,false,0 , "N"));
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default9,R.drawable.step7_type_click9,"뒷목통증/\n뻐근함/당김",false,false,false,0 , "N"));
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default10,R.drawable.step7_type_click10,"어깨통증",false,false,false,0 , "N"));
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default11,R.drawable.step7_type_click11,"눈물/눈충혈",false,false,false,0 , "N"));
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default12,R.drawable.step7_type_click12,"콧물/코막힘",false,false,false,0 , "N"));
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step_type_etc,R.drawable.step_type_etc,"기타",false,true,false,0 , "N"));
        }

        if ( this.step8SaveDTO == null ) {
            this.step8SaveDTO = new Step8SaveDTO("N","N","N","N","N",
                    "N","N","N","N",
                    "N","N","N","N",
                    "N","N","N",new ArrayList<Step8EtcDTO>());

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

        }

        if (this.step9SaveDTO == null) {

            this.step9SaveDTO = new Step9SaveDTO("N","N","N","N"
                    ,"N", "N","N","N",
                    "N","N","N",new ArrayList<Step9DTO>(),-1);

            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default1, R.drawable.step9_type_click1, "모름", false, false, false,null));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "나라믹", false, false, false,null));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "마이그란", false, false, false,null));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "미가드", false, false, false,null));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "맥살트", false, false, false,null));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "수마트란25", false, false, false,null));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "수마트란50", false, false, false,null));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "슈그란", false, false, false,null));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "알모그란", false, false, false,null));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "이미그란", false, false, false,null));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step9_type_default2, R.drawable.step9_type_click2, "조믹", false, false, false,null));
            this.step9SaveDTO.getStep9DTOS().add(new Step9DTO(R.drawable.step_type_etc, R.drawable.step_type_etc, "기타", false, true, false,null));

        }

        if ( this.step10SaveDTO == null ) {
            this.step10SaveDTO = new Step10SaveDTO("N","N","N","N"
                    ,"N",new ArrayList<Step9Dates>() , new ArrayList<Step10EtcDTO>());

            this.step10SaveDTO.getArrayList().add(new Step10EtcDTO(R.drawable.step10_type_default1,R.drawable.step10_type_click1,"결근/결석",false,false,false,0 , "N"));
            this.step10SaveDTO.getArrayList().add(new Step10EtcDTO(R.drawable.step10_type_default2,R.drawable.step10_type_click2,"업무/학습\n능률저하",false,false,false,0 , "N"));
            this.step10SaveDTO.getArrayList().add(new Step10EtcDTO(R.drawable.step10_type_default3,R.drawable.step10_type_click3,"가사활동\n못함",false,false,false,0 , "N"));
            this.step10SaveDTO.getArrayList().add(new Step10EtcDTO(R.drawable.step10_type_default4,R.drawable.step10_type_click4,"가사활동\n능률 저하",false,false,false,0 , "N"));
            this.step10SaveDTO.getArrayList().add(new Step10EtcDTO(R.drawable.step10_type_default5,R.drawable.step10_type_click5,"여가활동\n불참",false,false,false,0 , "N"));
            this.step10SaveDTO.getArrayList().add(new Step10EtcDTO(R.drawable.step_type_etc,R.drawable.step_type_etc,"기타",false,true,false,0 , "N"));

        }

        if ( this.step11SaveDTO == null ) {
            this.step11SaveDTO = new Step11SaveDTO(0L , 0L );
        }

        if (this.step12SaveDTO == null) {
            this.step12SaveDTO = new Step12SaveDTO("");
        }

        ArrayList<Step4SendDTO> step4Arraylist= new ArrayList<Step4SendDTO>();
        for ( int i = 0 , j = this.step4SaveDTO.getArrayList().size(); i < j ; i++ ) {
            Step4EtcDTO row = this.step4SaveDTO.getArrayList().get(i);
            if (row.getEtc()) {
                Log.d("zzzzz",row.getContent());
                step4Arraylist.add(new Step4SendDTO(0,row.getContent(),row.getVal()));
            }
        }

        Log.d("sizezzz",step4Arraylist.size()+"__");

        ArrayList<Step4SendDTO> step5Arraylist= new ArrayList<Step4SendDTO>();
        for ( int i = 0 , j = this.step5SaveDTO.getArrayList().size(); i < j ; i++ ) {
            Step5EtcDTO row = this.step5SaveDTO.getArrayList().get(i);
            if (row.getEtc()) step5Arraylist.add(new Step4SendDTO(0,row.getContent(),row.getVal()));
        }

        ArrayList<Step4SendDTO> step7Arraylist= new ArrayList<Step4SendDTO>();
        for ( int i = 0 , j = this.step7SaveDTO.getStep7EtcDTOS().size(); i < j ; i++ ) {
            Step7EtcDTO row = this.step7SaveDTO.getStep7EtcDTOS().get(i);
            if (row.getEtc()) step7Arraylist.add(new Step4SendDTO(0,row.getContent(),row.getVal()));
        }

        ArrayList<Step4SendDTO> step8Arraylist= new ArrayList<Step4SendDTO>();
        for ( int i = 0 , j = this.step8SaveDTO.getArrayList().size(); i < j ; i++ ) {
            Step8EtcDTO row = this.step8SaveDTO.getArrayList().get(i);
            if (row.getEtc()) step7Arraylist.add(new Step4SendDTO(0,row.getContent(),row.getVal()));
        }

        Send9PixDTO send9PixDTO1 = new Send9PixDTO(0 , this.step9SaveDTO.getStep9DTOS().get(0).getDrugArray());
        Send9PixDTO send9PixDTO2 = new Send9PixDTO(0 , this.step9SaveDTO.getStep9DTOS().get(1).getDrugArray());
        Send9PixDTO send9PixDTO3 = new Send9PixDTO(0 , this.step9SaveDTO.getStep9DTOS().get(2).getDrugArray());
        Send9PixDTO send9PixDTO4 = new Send9PixDTO(0 , this.step9SaveDTO.getStep9DTOS().get(3).getDrugArray());
        Send9PixDTO send9PixDTO5 = new Send9PixDTO(0 , this.step9SaveDTO.getStep9DTOS().get(4).getDrugArray());
        Send9PixDTO send9PixDTO6 = new Send9PixDTO(0 , this.step9SaveDTO.getStep9DTOS().get(5).getDrugArray());
        Send9PixDTO send9PixDTO7 = new Send9PixDTO(0 , this.step9SaveDTO.getStep9DTOS().get(6).getDrugArray());
        Send9PixDTO send9PixDTO8 = new Send9PixDTO(0 , this.step9SaveDTO.getStep9DTOS().get(7).getDrugArray());
        Send9PixDTO send9PixDTO9 = new Send9PixDTO(0 , this.step9SaveDTO.getStep9DTOS().get(8).getDrugArray());
        Send9PixDTO send9PixDTO10 = new Send9PixDTO(0 , this.step9SaveDTO.getStep9DTOS().get(9).getDrugArray());
        Send9PixDTO send9PixDTO11 = new Send9PixDTO(0 , this.step9SaveDTO.getStep9DTOS().get(10).getDrugArray());

        ArrayList<Step9SendDTO> step9 = new ArrayList<>();



        this.stepParentDTO = new StepParentDTO(
                "13",
                this.step1SaveDTO.getSdate()/1000,this.step1SaveDTO.getSdate()/1000,this.step1SaveDTO.getAddress(),
                this.step2SaveDTO.getAche_power(),
                this.step3SaveDTO.getAche_location1(),this.step3SaveDTO.getAche_location2(),this.step3SaveDTO.getAche_location3(),this.step3SaveDTO.getAche_location4(),this.step3SaveDTO.getAche_location5(),this.step3SaveDTO.getAche_location6(),this.step3SaveDTO.getAche_location7(),this.step3SaveDTO.getAche_location8(),this.step3SaveDTO.getAche_location9(),this.step3SaveDTO.getAche_location10(),this.step3SaveDTO.getAche_location11(),this.step3SaveDTO.getAche_location12(),this.step3SaveDTO.getAche_location13(),this.step3SaveDTO.getAche_location14(),
                this.step4SaveDTO.getAche_type1(),this.step4SaveDTO.getAche_type2(),this.step4SaveDTO.getAche_type3(),this.step4SaveDTO.getAche_type4(),this.step4SaveDTO.getAche_type5(),step4Arraylist,
                this.step5SaveDTO.getAche_realize_yn(),this.step5SaveDTO.getAche_realize_hour(),this.step5SaveDTO.getAche_realize_minute(),this.step5SaveDTO.getAche_realize1(),this.step5SaveDTO.getAche_realize2(),this.step5SaveDTO.getAche_realize3(),this.step5SaveDTO.getAche_realize4(),this.step5SaveDTO.getAche_realize5(),this.step5SaveDTO.getAche_realize6(),this.step5SaveDTO.getAche_realize7(),this.step5SaveDTO.getAche_realize8(),step5Arraylist,
                this.step6SaveDTO.getAche_sign_yn(),this.step6SaveDTO.getAche_sign1(),this.step6SaveDTO.getAche_sign2(),this.step6SaveDTO.getAche_sign3(),this.step6SaveDTO.getAche_sign4(),this.step6SaveDTO.getAche_sign5(),this.step6SaveDTO.getAche_sign6(),this.step6SaveDTO.getAche_sign7(),
                this.step7SaveDTO.getAche_with_yn(),this.step7SaveDTO.getAche_with1(),this.step7SaveDTO.getAche_with2(),this.step7SaveDTO.getAche_with3(),this.step7SaveDTO.getAche_with4(),this.step7SaveDTO.getAche_with5(),this.step7SaveDTO.getAche_with6(),this.step7SaveDTO.getAche_with7(),this.step7SaveDTO.getAche_with8(),this.step7SaveDTO.getAche_with9(),this.step7SaveDTO.getAche_with10(),this.step7SaveDTO.getAche_with11(),this.step7SaveDTO.getAche_with12(),step7Arraylist,
                this.step8SaveDTO.getAche_factor_yn(),this.step8SaveDTO.getAche_factor1(),this.step8SaveDTO.getAche_factor2(),this.step8SaveDTO.getAche_factor3(),this.step8SaveDTO.getAche_factor4(),this.step8SaveDTO.getAche_factor5(),this.step8SaveDTO.getAche_factor6(),this.step8SaveDTO.getAche_factor7(),this.step8SaveDTO.getAche_factor8(),this.step8SaveDTO.getAche_factor9(),this.step8SaveDTO.getAche_factor10(),this.step8SaveDTO.getAche_factor11(),this.step8SaveDTO.getAche_factor12(),this.step8SaveDTO.getAche_factor13(),this.step8SaveDTO.getAche_factor14(),this.step8SaveDTO.getAche_factor15(),step8Arraylist,
                send9PixDTO1,send9PixDTO2,send9PixDTO3,send9PixDTO4,send9PixDTO5,send9PixDTO6,send9PixDTO7,send9PixDTO8,send9PixDTO9,send9PixDTO10,send9PixDTO11,new ArrayList<Step9SendDTO>(Arrays.asList(new Step9SendDTO(2,"타이레놀",1,new ArrayList<Step9Dates>(Arrays.asList(new Step9Dates("2020-05-14","Y"),new Step9Dates("2020-05-14","Y")))))),this.step9SaveDTO.getAche_medicine_effect(),
                this.step10SaveDTO.getAche_effect1(),this.step10SaveDTO.getAche_effect2(),this.step10SaveDTO.getAche_effect3(),this.step10SaveDTO.getAche_effect4(),this.step10SaveDTO.getAche_effect5(),new ArrayList<Step9Dates>(Arrays.asList(new Step9Dates("2020-05-14","Y"))),new ArrayList<Step4SendDTO>(Arrays.asList(new Step4SendDTO(0,"스테엡 10","Y"))),
                Global.getTimeToStr(this.step11SaveDTO.getMens_sdate()),Global.getTimeToStr(this.step11SaveDTO.getMens_edate()),
                this.step12SaveDTO.getDesc()
        );

        Gson gson = new Gson();
        String data = gson.toJson(this.stepParentDTO);
        Log.d("sendData",data);

        if (data.equals(""))return;
        AndroidNetworking.post(this.urls.mainUrl+this.urls.getUrls.get("setDiary"))
                .addBodyParameter("data",data)
                .addBodyParameter("user_sid","13")
                .setPriority(Priority.MEDIUM)
                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                Log.d("sendDate",response+"");
            }

            @Override
            public void onError(ANError anError) {
                Log.d("sendDate123",anError.getErrorBody());
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v == null) return super.dispatchTouchEvent(event);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(event);
    }

}
