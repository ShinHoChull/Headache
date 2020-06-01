package com.m2comm.headache.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.m2comm.headache.Adapter.AlarmListAdapter;
import com.m2comm.headache.DTO.AlarmDTO;
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
import com.m2comm.headache.DTO.Step9Dates;
import com.m2comm.headache.DTO.Step9SaveDTO;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivityDetaiViewBinding;
import com.m2comm.headache.module.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class DetaiViewActivity extends AppCompatActivity implements View.OnClickListener {

    BottomActivity bottomActivity;
    ActivityDetaiViewBinding binding;
    Urls urls;

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



    int diary_sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detai_view);
        this.init();
    }

    private void regObj () {
        this.binding.backBt.setOnClickListener(this);
    }


    private void init () {
        this.bottomActivity = new BottomActivity(getLayoutInflater() , R.id.bottom , this , this);
        this.binding = DataBindingUtil.setContentView(this,R.layout.activity_detai_view);
        this.binding.setDetail(this);
        this.bottomActivity = new BottomActivity(getLayoutInflater() , R.id.bottom , this , this);
        this.urls = new Urls();
        this.regObj();

        Intent intent = getIntent();
        this.diary_sid = intent.getIntExtra("diary_sid",-1);

        this.getDiary();
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBt:
                finish();
                break;
        }
    }

    private void detailSetting () {
        this.mainSetting();
    }

    private void mainSetting () {
        this.binding.mainIconBack.setVisibility(View.VISIBLE);
        this.binding.mainIcon.setImageResource(Global.icon[Global.getIconNumberReturn(this.step2SaveDTO.getAche_power())]);
        this.binding.mainIconBack.setBackgroundResource(Global.icon_back[Global.getIconNumberReturn(this.step2SaveDTO.getAche_power())]);
    }


    private void step1Setting() {

    }

    private void step2Setting() {

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


                    detailSetting();
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
}
