package com.m2comm.headache.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonObject;
import com.m2comm.headache.Adapter.AnalysisGridviewAdapter;
import com.m2comm.headache.DTO.AnalyGraphDTO;
import com.m2comm.headache.DTO.AnalyMedicineTimeValDTO;
import com.m2comm.headache.DTO.AnalyRankDTO;
import com.m2comm.headache.DTO.AnalyReallizeDTO;
import com.m2comm.headache.DTO.AnalyTotDTO;
import com.m2comm.headache.DTO.Step5EtcDTO;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivityAnalysisViewBinding;
import com.m2comm.headache.module.Custom_SharedPreferences;
import com.m2comm.headache.module.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AnalysisViewActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAnalysisViewBinding binding;
    private BottomActivity bottomActivity;
    private Urls urls;
    private AnalyTotDTO analyTotDTO;
    private ArrayList<AnalyGraphDTO> analyGraphDTOS;
    private ArrayList<AnalyRankDTO> analyRankDTOArrayList;
    private ArrayList<AnalyReallizeDTO> analyReallizeDTOArrayList;
    private ArrayList<AnalyReallizeDTO> factorDTOArrayList;

    private Custom_SharedPreferences csp;
    AnalysisGridviewAdapter adapter;
    AnalysisGridviewAdapter adapter2;

    private int graphMaxDay = 30;
    private int rankMaxHour = 10;

    private int[] monthBt = {
            R.id.monthBt1,
            R.id.monthBt2,
            R.id.monthBt3
    };

    private int defaultNum = 1;

    private void regObj () {
        this.binding.backBt.setOnClickListener(this);
        this.binding.monthBt1.setOnClickListener(this);
        this.binding.monthBt2.setOnClickListener(this);
        this.binding.monthBt3.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_view);
        this.binding = DataBindingUtil.setContentView(this,R.layout.activity_analysis_view);
        this.binding.setAnaysis(this);
        this.init();
        this.regObj();
    }

    private void init () {

        this.csp = new Custom_SharedPreferences(this);
        this.urls = new Urls();
        this.analyGraphDTOS = new ArrayList<>();
        this.analyRankDTOArrayList = new ArrayList<>();

        this.changeButton(this.binding.monthBt1,this.defaultNum);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.bottomActivity = new BottomActivity(getLayoutInflater() , R.id.bottom , this , this,3);
    }

    private void btReset () {
        for ( int i = 0 , j = monthBt.length; i < j ; i ++ ) {
            TextView tv = findViewById(this.monthBt[i]);
            tv.setBackgroundColor(Color.TRANSPARENT);
            tv.setTextColor(Color.parseColor("#FFFFFF"));
        }
    }

    private void changeButton(TextView v ,int num) {
        this.btReset();
        v.setBackgroundResource(R.drawable.analysis_top_check_radius);
        v.setTextColor(Color.parseColor("#187596"));
        this.sendDate(num);
    }

    private void sendDate (final int num) {

        AndroidNetworking.post(this.urls.mainUrl+this.urls.getUrls.get("getStatus"))
                .addBodyParameter("user_sid",csp.getValue("user_sid",""))
                .addBodyParameter("month",String.valueOf(num))
                .setPriority(Priority.MEDIUM)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("responseData",response.toString()) ;
                try {
                    if ( !response.isNull("alert_txt")) Toast.makeText(getApplicationContext() , response.getString("alert_txt"),Toast.LENGTH_SHORT).show();

                    JSONObject obj =  response.getJSONObject("tot");

                    analyTotDTO = new AnalyTotDTO(obj.getString("ache_day"),obj.getString("medicine_day"),
                            obj.getString("effect_day"),obj.getString("ache_time"),
                            obj.isNull("ache_power")?"":obj.getString("ache_power"),
                            obj.isNull("ache_sign")?"":obj.getString("ache_sign")
                            );

                    if ( !response.isNull("graph") ) {
                        JSONArray graphs = response.getJSONArray("graph");
                        analyGraphDTOS = new ArrayList<>();
                        for ( int i = 0 , j = graphs.length(); i < j ; i++ ) {
                            JSONObject row1 = (JSONObject) graphs.get(i);
                            analyGraphDTOS.add(new AnalyGraphDTO(row1.getInt("month"),
                                    Integer.parseInt(row1.getString("ache_day")),
                                    row1.getInt("medicine_day"),
                                    row1.getInt("effect_day"),
                                    row1.isNull("ache_power") ? 0:row1.getInt("ache_power")));
                        }
                    }


                    if ( !response.isNull("rank") ) {
                        JSONArray ranks = response.getJSONArray("rank");
                        for ( int i = 0, j = ranks.length(); i < j; i++ ) {
                            JSONObject row2 = (JSONObject) ranks.get(i);

                            JSONArray array =  row2.getJSONArray("time_val");
                            ArrayList<AnalyMedicineTimeValDTO> arrayList = new ArrayList<>();
                            for (int k =0 , l = array.length() ; k < l; k++) {
                                JSONObject timeRow = (JSONObject) array.get(k);
                                arrayList.add(new AnalyMedicineTimeValDTO(timeRow.getString("time_txt"),timeRow.getInt("val")));
                            }
                            analyRankDTOArrayList.add(new AnalyRankDTO(row2.getString("medicine_txt"),arrayList));
                        }
                    }


                    analyReallizeDTOArrayList = new ArrayList<>();
                    if ( !response.isNull("ache_realize") ) {
                        JSONArray ache_realizeArr = response.getJSONArray("ache_realize");
                        for ( int i =0 , j = ache_realizeArr.length(); i < j; i++ ) {
                            JSONObject row = (JSONObject) ache_realizeArr.get(i);
                            analyReallizeDTOArrayList.add(new AnalyReallizeDTO(step5IconReturn(row.getString("key_txt")),row.getString("key"),row.getString("key_txt"),row.getString("val")));
                        }
                    }


                    factorDTOArrayList = new ArrayList<>();
                    if ( !response.isNull("ache_factor") ) {
                        JSONArray ache_factorArr = response.getJSONArray("ache_factor");
                        for ( int i =0 , j = ache_factorArr.length(); i < j; i++ ) {
                            JSONObject row = (JSONObject) ache_factorArr.get(i);
                            factorDTOArrayList.add(new AnalyReallizeDTO(step8IconReturn(row.getString("key_txt")),row.getString("key"),row.getString("key_txt"),row.getString("val")));
                        }
                    }


                    dataSetting(num);

                } catch (JSONException e) {
                    Log.d("errrr",e.toString());
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.d("ERror",anError.getErrorDetail());
            }
        });
    }

    private int step5IconReturn(String txt) {
        int icon = R.drawable.step_type_etc;
        if ( txt.equals("아플 것 같은느낌") ) {
            icon = R.drawable.step5_type_click1;
        } else if ( txt.equals("뒷목통증 뻐근함/당김") ){
            icon = R.drawable.step5_type_click2;
        } else if ( txt.equals("하품") ){
            icon = R.drawable.step5_type_click3;
        } else if ( txt.equals("피로") ){
            icon = R.drawable.step5_type_click4;
        } else if ( txt.equals("집중력저하") ){
            icon = R.drawable.step5_type_click5;
        } else if ( txt.equals("기분변화") ){
            icon = R.drawable.step5_type_click6;
        } else if ( txt.equals("식욕변화") ){
            icon = R.drawable.step5_type_click7;
        } else if ( txt.equals("빛/소리/냄새에 과민") ){
            icon = R.drawable.step5_type_click8;
        } else {
            icon = R.drawable.step_type_etc_add_click;
        }
        return icon;
    }

    private int step8IconReturn(String txt) {
        int icon = R.drawable.step_type_etc;
        if ( txt.equals("스트레스") ) {
            icon = R.drawable.step8_type_click1;
        } else if ( txt.equals("피로") ){
            icon = R.drawable.step8_type_click2;
        } else if ( txt.equals("수면부족") ){
            icon = R.drawable.step8_type_click3;
        } else if ( txt.equals("낮잠/늦잠") ){
            icon = R.drawable.step8_type_click4;
        } else if ( txt.equals("주말") ){
            icon = R.drawable.step8_type_click5;
        } else if ( txt.equals("굶음") ){
            icon = R.drawable.step8_type_click6;
        } else if ( txt.equals("과식") ){
            icon = R.drawable.step8_type_click7;
        } else if ( txt.equals("체함") ){
            icon = R.drawable.step8_type_click8;
        } else if ( txt.equals("빛") ){
            icon = R.drawable.step8_type_click9;
        } else if ( txt.equals("소리") ){
            icon = R.drawable.step8_type_click10;
        } else if ( txt.equals("냄새") ){
            icon = R.drawable.step8_type_click11;
        } else if ( txt.equals("감기") ){
            icon = R.drawable.step8_type_click12;
        } else if ( txt.equals("운동") ){
            icon = R.drawable.step8_type_click13;
        } else if ( txt.equals("술") ){
            icon = R.drawable.step8_type_click14;
        } else if ( txt.equals("월경") ){
            icon = R.drawable.step8_type_click15;
        } else {
            icon = R.drawable.step_type_etc_add_click;
        }
        return icon;
    }

    private void dataSetting(int num) {
        this.totSetting();
        this.graph(num);
        this.rank();
        this.step5Setting();
        this.step6Setting();
    }

    private void step5Setting() {
//        if ( this.analyRankDTOArrayList.size() < 1 ) {
//            this.binding.reallizeParent.setVisibility(View.GONE);
//            return;
//        }
        this.adapter = new AnalysisGridviewAdapter(this.analyReallizeDTOArrayList,getLayoutInflater());
        this.binding.step5GridV.setAdapter(this.adapter);
    }

    private void step6Setting() {
//        if ( this.factorDTOArrayList.size() < 1 ) {
//            this.binding.factorParent.setVisibility(View.GONE);
//            return;
//        }
        this.adapter2 = new AnalysisGridviewAdapter(this.factorDTOArrayList,getLayoutInflater());
        this.binding.step6GridV.setAdapter(this.adapter2);
    }

    private void rank() {

        if ( this.analyRankDTOArrayList.size() <= 0 ) {
            this.binding.rankAllTopParent.setVisibility(View.GONE);
            return;
        } else if (this.analyRankDTOArrayList.size() < 2) {
            this.binding.rank2TopParent.setVisibility(View.GONE);
            this.binding.rankAllTopParent.post(new Runnable() {
                @Override
                public void run() {
                    binding.rankAllTopParent.getLayoutParams().height = binding.rankAllTopParent.getLayoutParams().height / 2;
                }
            });
        }

        this.binding.rank1Title.setText("·"+analyRankDTOArrayList.get(0).getMedicine_txt());
        this.binding.rankParent1.post(new Runnable() {
            @Override
            public void run() {
                final int h = binding.rankParent1.getHeight();

              //  int rank11H = 0;
                int rank12H = 0;
                int rank13H = 0;
                int rank14H = 0;
                int rank15H = 0;

                for ( int i = 0 , j = analyRankDTOArrayList.get(0).getAnalyMedicineTimeValDTOS().size() ; i < j ; i++ ) {

                    if ( analyRankDTOArrayList.get(0).getAnalyMedicineTimeValDTOS().get(i).getTime_txt().equals("효과 없음") ) {
                        rank15H =  (h / rankMaxHour) * analyRankDTOArrayList.get(0).getAnalyMedicineTimeValDTOS().get(i).getVal();
                    } else if ( analyRankDTOArrayList.get(0).getAnalyMedicineTimeValDTOS().get(i).getTime_txt().equals("1시간 이내") ){
                        rank14H =  (h / rankMaxHour) * analyRankDTOArrayList.get(0).getAnalyMedicineTimeValDTOS().get(i).getVal();
                    } else if ( analyRankDTOArrayList.get(0).getAnalyMedicineTimeValDTOS().get(i).getTime_txt().equals("2시간 이내") ) {
                        rank13H =  (h / rankMaxHour) * analyRankDTOArrayList.get(0).getAnalyMedicineTimeValDTOS().get(i).getVal();
                    } else if ( analyRankDTOArrayList.get(0).getAnalyMedicineTimeValDTOS().get(i).getTime_txt().equals("4시간 이내") ) {
                        rank12H =  (h / rankMaxHour) * analyRankDTOArrayList.get(0).getAnalyMedicineTimeValDTOS().get(i).getVal();
                    }
                }

                LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1.5f);
                layoutParams5.height = rank15H;
                layoutParams5.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
                binding.rank15.setLayoutParams(layoutParams5);

                LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1.5f);
                layoutParams4.height = rank14H;
                layoutParams4.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
                binding.rank14.setLayoutParams(layoutParams4);

                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1.5f);
                layoutParams3.height = rank13H;
                layoutParams3.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
                binding.rank13.setLayoutParams(layoutParams3);

                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1.5f);
                layoutParams2.height = rank12H;
                layoutParams2.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
                binding.rank12.setLayoutParams(layoutParams2);

    /*            Log.d("rank11H",rank11H+"_");
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1.5f);
                layoutParams1.height = rank11H;
                layoutParams1.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
                binding.rank11.setLayoutParams(layoutParams1);*/
            }
        });

        if ( this.analyRankDTOArrayList.size() < 2 ) {
            return;
        }

        this.binding.rank2Title.setText("·"+analyRankDTOArrayList.get(1).getMedicine_txt());
        this.binding.rankParent2.post(new Runnable() {
            @Override
            public void run() {
                final int h = binding.rankParent2.getHeight();

                int rank11H = 0;
                int rank12H = 0;
                int rank13H = 0;
                int rank14H = 0;
                int rank15H = 0;

                for ( int i = 0 , j = analyRankDTOArrayList.get(1).getAnalyMedicineTimeValDTOS().size() ; i < j ; i++ ) {

                    if ( analyRankDTOArrayList.get(1).getAnalyMedicineTimeValDTOS().get(i).getTime_txt().equals("효과 없음") ) {
                        rank15H =  (h / rankMaxHour) * analyRankDTOArrayList.get(1).getAnalyMedicineTimeValDTOS().get(i).getVal();
                    } else if ( analyRankDTOArrayList.get(1).getAnalyMedicineTimeValDTOS().get(i).getTime_txt().equals("1시간 이내") ){
                        rank14H =  (h / rankMaxHour) * analyRankDTOArrayList.get(1).getAnalyMedicineTimeValDTOS().get(i).getVal();
                    } else if ( analyRankDTOArrayList.get(1).getAnalyMedicineTimeValDTOS().get(i).getTime_txt().equals("2시간 이내") ) {
                        rank13H =  (h / rankMaxHour) * analyRankDTOArrayList.get(1).getAnalyMedicineTimeValDTOS().get(i).getVal();
                    } else if ( analyRankDTOArrayList.get(1).getAnalyMedicineTimeValDTOS().get(i).getTime_txt().equals("4시간 이내") ) {
                        rank12H =  (h / rankMaxHour) * analyRankDTOArrayList.get(1).getAnalyMedicineTimeValDTOS().get(i).getVal();
                    }
                }

                LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1.5f);
                layoutParams5.height = rank15H;
                layoutParams5.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
                binding.rank25.setLayoutParams(layoutParams5);

                LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1.5f);
                layoutParams4.height = rank14H;
                layoutParams4.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
                binding.rank24.setLayoutParams(layoutParams4);

                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1.5f);
                layoutParams3.height = rank13H;
                layoutParams3.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
                binding.rank23.setLayoutParams(layoutParams3);

                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1.5f);
                layoutParams2.height = rank12H;
                layoutParams2.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
                binding.rank22.setLayoutParams(layoutParams2);

              /*  LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1.5f);
                layoutParams1.height = rank11H;
                layoutParams1.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
                binding.rank21.setLayoutParams(layoutParams1);*/
            }
        });


    }


    private void graphReset() {
        this.binding.graph1.setVisibility(View.GONE);
        this.binding.graph2.setVisibility(View.GONE);
        this.binding.graph3.setVisibility(View.GONE);
        this.binding.bottom1.setVisibility(View.GONE);
        this.binding.bottom2.setVisibility(View.GONE);
        this.binding.bottom3.setVisibility(View.GONE);
        this.binding.graph1Bottom.setVisibility(View.GONE);
        this.binding.graph2Bottom.setVisibility(View.GONE);
        this.binding.graph3Bottom.setVisibility(View.GONE);
    }

    private void graph(int num) {
        this.graphReset();
        if ( num == 1 ) {
            this.binding.graph1.setVisibility(View.VISIBLE);
            this.binding.bottom1.setVisibility(View.VISIBLE);
            this.binding.graph1Bottom.setVisibility(View.VISIBLE);
            this.graph1Setting();
        } else if ( num == 3 ) {
            this.binding.graph2.setVisibility(View.VISIBLE);
            this.binding.bottom2.setVisibility(View.VISIBLE);
            this.binding.graph2Bottom.setVisibility(View.VISIBLE);
            this.graph2Setting();
        } else  {
            this.binding.graph3.setVisibility(View.VISIBLE);
            this.binding.bottom3.setVisibility(View.VISIBLE);
            this.binding.graph3Bottom.setVisibility(View.VISIBLE);
            this.graph3Setting();
        }
    }

    private int graphColorSetting (int num) {
        if ( num <= 3 ) {
            return R.drawable.graph_bar1;
        } else if ( num <= 6 ) {
            return R.drawable.graph_bar2;
        } else if ( num <= 9 ) {
            return R.drawable.graph_bar3;
        } else {
            return R.drawable.graph_bar4;
        }
    }

    private void graph1Setting () {

        this.binding.medicine1.setText(String.valueOf(this.analyGraphDTOS.get(0).getMedicine_day()));
        this.binding.effect1.setText(String.valueOf(this.analyGraphDTOS.get(0).getEffect_day()));

        this.binding.graphParent.post(new Runnable() {
            @Override
            public void run() {
                final int h = binding.graphParent.getHeight();
                int graph11H = (h / 30) * analyGraphDTOS.get(0).getAche_day();
                Log.d("graph11h",h+"_");
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,8.5f);
                layoutParams.height = graph11H;
                layoutParams.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
                binding.graph11.setLayoutParams(layoutParams);
                binding.graph11In.setBackgroundResource(graphColorSetting(analyGraphDTOS.get(0).getAche_power()));
            }
        });
    }

    private void graph2Setting () {

        this.binding.medicine21.setText(String.valueOf(this.analyGraphDTOS.get(0).getMedicine_day()));
        this.binding.medicine22.setText(String.valueOf(this.analyGraphDTOS.get(1).getMedicine_day()));
        this.binding.medicine23.setText(String.valueOf(this.analyGraphDTOS.get(2).getMedicine_day()));

        this.binding.effect21.setText(String.valueOf(this.analyGraphDTOS.get(0).getEffect_day()));
        this.binding.effect22.setText(String.valueOf(this.analyGraphDTOS.get(1).getEffect_day()));
        this.binding.effect23.setText(String.valueOf(this.analyGraphDTOS.get(2).getEffect_day()));


        this.binding.graphParent.post(new Runnable() {
            @Override
            public void run() {
                final int h = binding.graphParent.getHeight();
                int graph21H = (h / graphMaxDay) * analyGraphDTOS.get(0).getAche_day();
                int graph22H = (h / graphMaxDay) * analyGraphDTOS.get(1).getAche_day();
                int graph23H = (h / graphMaxDay) * analyGraphDTOS.get(2).getAche_day();

                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,2.7f);
                layoutParams3.height = graph23H;
                layoutParams3.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
                binding.graph23.setLayoutParams(layoutParams3);
                binding.graph23In.setBackgroundResource(graphColorSetting(analyGraphDTOS.get(2).getAche_power()));

                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,2.7f);
                layoutParams2.height = graph22H;
                layoutParams2.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
                binding.graph22.setLayoutParams(layoutParams2);
                binding.graph22In.setBackgroundResource(graphColorSetting(analyGraphDTOS.get(1).getAche_power()));

                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,2.7f);
                layoutParams1.height = graph21H;
                layoutParams1.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
                binding.graph21.setLayoutParams(layoutParams1);
                binding.graph21In.setBackgroundResource(graphColorSetting(analyGraphDTOS.get(0).getAche_power()));

            }
        });
    }

    private void graph3Setting () {

        this.binding.medicine31.setText(String.valueOf(this.analyGraphDTOS.get(0).getMedicine_day()));
        this.binding.medicine32.setText(String.valueOf(this.analyGraphDTOS.get(1).getMedicine_day()));
        this.binding.medicine33.setText(String.valueOf(this.analyGraphDTOS.get(2).getMedicine_day()));
        this.binding.medicine34.setText(String.valueOf(this.analyGraphDTOS.get(3).getMedicine_day()));
        this.binding.medicine35.setText(String.valueOf(this.analyGraphDTOS.get(4).getMedicine_day()));
        this.binding.medicine36.setText(String.valueOf(this.analyGraphDTOS.get(5).getMedicine_day()));

        this.binding.effect31.setText(String.valueOf(this.analyGraphDTOS.get(0).getEffect_day()));
        this.binding.effect32.setText(String.valueOf(this.analyGraphDTOS.get(1).getEffect_day()));
        this.binding.effect33.setText(String.valueOf(this.analyGraphDTOS.get(2).getEffect_day()));
        this.binding.effect34.setText(String.valueOf(this.analyGraphDTOS.get(3).getEffect_day()));
        this.binding.effect35.setText(String.valueOf(this.analyGraphDTOS.get(4).getEffect_day()));
        this.binding.effect36.setText(String.valueOf(this.analyGraphDTOS.get(5).getEffect_day()));

        this.binding.graphParent.post(new Runnable() {
            @Override
            public void run() {
                final int h = binding.graphParent.getHeight();
                int graph31H = (h / graphMaxDay) * analyGraphDTOS.get(0).getAche_day();
                int graph32H = (h / graphMaxDay) * analyGraphDTOS.get(1).getAche_day();
                int graph33H = (h / graphMaxDay) * analyGraphDTOS.get(2).getAche_day();
                int graph34H = (h / graphMaxDay) * analyGraphDTOS.get(3).getAche_day();
                int graph35H = (h / graphMaxDay) * analyGraphDTOS.get(4).getAche_day();
                int graph36H = (h / graphMaxDay) * analyGraphDTOS.get(5).getAche_day();


                Log.d("graph36H",analyGraphDTOS.get(5).getAche_day()+"__");
                Log.d("graph35H",analyGraphDTOS.get(4).getAche_day()+"__");
                Log.d("graph34H",analyGraphDTOS.get(3).getAche_day()+"__");
                Log.d("graph33H",analyGraphDTOS.get(2).getAche_day()+"__");
                Log.d("graph32H",analyGraphDTOS.get(1).getAche_day()+"__");
                Log.d("graph31H",analyGraphDTOS.get(0).getAche_day()+"__");

                LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1f);
                layoutParams6.height = graph36H;
                layoutParams6.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
                layoutParams6.leftMargin = (int)Global.pxToDp(getApplicationContext(),100);
                layoutParams6.rightMargin = (int)Global.pxToDp(getApplicationContext(),100);
                binding.graph36.setLayoutParams(layoutParams6);
                binding.graph36.setBackgroundResource(graphColorSetting(analyGraphDTOS.get(5).getAche_power()));

                LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1f);
                layoutParams5.height = graph35H;
                layoutParams5.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
                layoutParams5.leftMargin = (int)Global.pxToDp(getApplicationContext(),100);
                layoutParams5.rightMargin = (int)Global.pxToDp(getApplicationContext(),100);
                binding.graph35.setLayoutParams(layoutParams5);
                binding.graph35.setBackgroundResource(graphColorSetting(analyGraphDTOS.get(4).getAche_power()));

                LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1f);
                layoutParams4.height = graph34H;
                layoutParams4.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
                layoutParams4.leftMargin = (int)Global.pxToDp(getApplicationContext(),100);
                layoutParams4.rightMargin = (int)Global.pxToDp(getApplicationContext(),100);
                binding.graph34.setLayoutParams(layoutParams4);
                binding.graph34.setBackgroundResource(graphColorSetting(analyGraphDTOS.get(3).getAche_power()));

                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1f);
                layoutParams3.height = graph33H;
                layoutParams3.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
                layoutParams3.leftMargin = (int)Global.pxToDp(getApplicationContext(),100);
                layoutParams3.rightMargin = (int)Global.pxToDp(getApplicationContext(),100);
                binding.graph33.setLayoutParams(layoutParams3);
                binding.graph33.setBackgroundResource(graphColorSetting(analyGraphDTOS.get(2).getAche_power()));

                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1f);
                layoutParams2.height = graph32H;
                layoutParams2.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
                layoutParams2.leftMargin = (int)Global.pxToDp(getApplicationContext(),100);
                layoutParams2.rightMargin = (int)Global.pxToDp(getApplicationContext(),100);
                binding.graph32.setLayoutParams(layoutParams2);
                binding.graph32.setBackgroundResource(graphColorSetting(analyGraphDTOS.get(1).getAche_power()));

                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1f);
                layoutParams1.height = graph31H;
                layoutParams1.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
                layoutParams1.leftMargin = (int)Global.pxToDp(getApplicationContext(),100);
                layoutParams1.rightMargin = (int)Global.pxToDp(getApplicationContext(),100);
                binding.graph31.setLayoutParams(layoutParams1);
                binding.graph31.setBackgroundResource(graphColorSetting(analyGraphDTOS.get(0).getAche_power()));

            }
        });
    }

    private void totSetting () {
        this.binding.analysisAcheDay.setText(this.analyTotDTO.getAche_day().equals("null")?"":this.analyTotDTO.getAche_day());
        this.binding.analysisMedicineDay.setText(this.analyTotDTO.getMedicine_day().equals("null")?"":this.analyTotDTO.getMedicine_day());
        this.binding.analysisEffectDay.setText(this.analyTotDTO.getEffect_day().equals("null")?"":this.analyTotDTO.getEffect_day());
        this.binding.analysisAcheTime.setText(this.analyTotDTO.getAche_time().equals("null")?"":this.analyTotDTO.getAche_time());
        this.binding.analysisAchePower.setText(this.analyTotDTO.getAche_power().equals("null")?"":this.analyTotDTO.getAche_power());
        this.binding.acheSign.setText(this.analyTotDTO.getAche_sign().equals("null")?"0":this.analyTotDTO.getAche_sign());
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.backBt:
                finish();
                break;

            case R.id.monthBt1:
                changeButton((TextView)v,1);
                break;
            case R.id.monthBt2:
                changeButton((TextView)v,3);
                break;
            case R.id.monthBt3:
                changeButton((TextView)v,6);
                break;
        }

    }
}
