package com.m2comm.headache.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.m2comm.headache.Adapter.AlarmListAdapter;
import com.m2comm.headache.Adapter.Step10GridviewAdapter;
import com.m2comm.headache.Adapter.Step4GridviewAdapter;
import com.m2comm.headache.Adapter.Step5GridviewAdapter;
import com.m2comm.headache.Adapter.Step7GridviewAdapter;
import com.m2comm.headache.Adapter.Step8GridviewAdapter;
import com.m2comm.headache.DTO.AlarmDTO;
import com.m2comm.headache.DTO.Step10EtcDTO;
import com.m2comm.headache.DTO.Step10MainDTO;
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
import com.m2comm.headache.DTO.Step9MainDTO;
import com.m2comm.headache.DTO.Step9MainEtcDTO;
import com.m2comm.headache.DTO.Step9SaveDTO;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivityDetaiViewBinding;
import com.m2comm.headache.module.Custom_SharedPreferences;
import com.m2comm.headache.module.Urls;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class DetaiViewActivity extends AppCompatActivity implements View.OnClickListener {

    String LOGO_STR = "DetailViewActivity";
    BottomActivity bottomActivity;
    ActivityDetaiViewBinding binding;
    Urls urls;
    Custom_SharedPreferences csp;

    Step1SaveDTO step1SaveDTO;
    Step2SaveDTO step2SaveDTO;
    Step3SaveDTO step3SaveDTO;
    Step4SaveDTO step4SaveDTO;
    Step5SaveDTO step5SaveDTO;
    Step6SaveDTO step6SaveDTO;
    Step7SaveDTO step7SaveDTO;
    Step8SaveDTO step8SaveDTO;
    //Step9SaveDTO step9SaveDTO;
    private ArrayList<Step9MainDTO> step9NewSaveDTO;
    private ArrayList<Step10MainDTO> step10NewSaveDTO;
    //Step10SaveDTO step10SaveDTO;
    Step11SaveDTO step11SaveDTO;
    Step12SaveDTO step12SaveDTO;

    LinearLayout step1, step2, step3, step4, step5, step6, step7, step8, step9, step10, step11, step12;

    private Step4GridviewAdapter adapter4;
    private Step5GridviewAdapter adapter5;
    private Step7GridviewAdapter adapter7;
    private Step8GridviewAdapter adapter8;
    private Step10GridviewAdapter adapter10;

    private boolean isMens = false;

    int gridItemViewHeight = 380;
    int gridItemWidthMaxCount = 4;
    int etcItemViewHeight = 800;
    int textHeight = 300;
    int baseHeight = 70;
    int diary_sid;
    String desc = "", calendar_desc = "", date="";


    boolean[] isCheckImgs = {
            false, false, false, false,
            false, false, false, false,
            false, false, false, false,
            false, false, false, false,
            false, false
    };

    int[] btIds = {
            R.id.bt1,
            R.id.bt2,
            R.id.bt3,
            R.id.bt4,
            R.id.bt5_1,
            R.id.bt6_1,
            R.id.bt5_2,
            R.id.bt6_2,
            R.id.bt7,
            R.id.bt8,
            R.id.bt9,
            R.id.bt10,
            R.id.bt11,
            R.id.bt12,
            R.id.bt13_1,
            R.id.bt13_2,
            R.id.bt14,
            R.id.bt15,
    };

    private String getStep2Desc(int num) {
        if (num <= 3) {
            return "약한 통증";
        } else if (num <= 6) {
            return "보통 통증";
        } else if (num <= 9) {
            return "심한 통증";
        } else {
            return "상상할 수 있는 가장 심한 통증";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detai_view);
        this.init();
    }

    private void regObj() {
        this.binding.backBt.setOnClickListener(this);
        this.binding.step1Next.setOnClickListener(this);
        this.binding.step2Next.setOnClickListener(this);
        this.binding.step3Next.setOnClickListener(this);
        this.binding.step4Next.setOnClickListener(this);
        this.binding.step5Next.setOnClickListener(this);
        this.binding.step6Next.setOnClickListener(this);
        this.binding.step7Next.setOnClickListener(this);
        this.binding.step8Next.setOnClickListener(this);
        this.binding.step9Next.setOnClickListener(this);
        this.binding.step10Next.setOnClickListener(this);
        this.binding.step11Next.setOnClickListener(this);
        this.binding.step12Next.setOnClickListener(this);
        this.binding.delBt.setOnClickListener(this);

        this.binding.step1.setOnClickListener(this);
        this.binding.step2.setOnClickListener(this);
        this.binding.step3.setOnClickListener(this);
        this.binding.step4.setOnClickListener(this);
        this.binding.step5.setOnClickListener(this);
        this.binding.step6.setOnClickListener(this);
        this.binding.step7.setOnClickListener(this);
        this.binding.step8.setOnClickListener(this);
        this.binding.step9.setOnClickListener(this);
        this.binding.step10.setOnClickListener(this);
        this.binding.step11TitleV.setOnClickListener(this);
        this.binding.step12.setOnClickListener(this);
    }

    private void init() {

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_detai_view);
        this.binding.setDetail(this);
        this.bottomActivity = new BottomActivity(getLayoutInflater(), R.id.bottom, this, this, -1);
        this.csp = new Custom_SharedPreferences(this);
        this.urls = new Urls();

        this.regObj();

        Intent intent = getIntent();
        this.diary_sid = intent.getIntExtra("diary_sid", -1);
        this.desc = intent.getStringExtra("desc");
        this.calendar_desc = intent.getStringExtra("calendar_desc");
        this.date = intent.getStringExtra("date");


        this.isMens = this.csp.getValue("mens", "").equals("N");


        this.binding.step11TitleV.setVisibility(View.GONE);
        this.binding.step11ParentV.setVisibility(View.GONE);
        this.binding.step12Next.setText("11. 기록");

        this.getDiary();


    }

    private void detailSetting() {
        this.mainSetting();
        this.step1Setting();
        this.step2Setting();
        this.step3Setting();
        this.step4Setting();
        this.step5Setting();
        this.step6Setting();
        this.step7Setting();
        this.step8Setting();
        this.step9Setting();
        this.step10Setting();
        this.step11Setting();
        this.step12Setting();

        AndroidNetworking.post(this.urls.mainUrl + this.urls.getUrls.get("getMensChk"))
                .addBodyParameter("user_sid", this.csp.getValue("user_sid", ""))
                .addBodyParameter("sdate", this.step1SaveDTO.getSdate() / 1000 + "")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("1")) {
                            binding.mainMens.setText("월경중");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void mainSetting() {
        this.binding.mainIconBack.setVisibility(View.VISIBLE);
        this.binding.mainIcon.setImageResource(Global.icon[Global.getIconNumberReturn(this.step2SaveDTO.getAche_power())]);
        this.binding.mainIconBack.setBackgroundResource(Global.icon_back[Global.getIconNumberReturn(this.step2SaveDTO.getAche_power())]);
        //this.binding.mainDate.setText(Global.getTimeToDateAndWeek(this.step1SaveDTO.getSdate()));
        this.binding.mainDate.setText(this.date);

        this.binding.mainAchePower.setText("(" + this.step2SaveDTO.getAche_power() + "/10)");



        if (this.desc != null && !this.desc.equals("")) {
            String[] desccut = this.desc.split("\n");
            if (desccut.length > 1) {
                String[] medicCut = desccut[1].split("\\(");
                if (medicCut.length > 1) {
                    this.binding.mainMedicineName1.setText(medicCut[0].replace("·", ""));
                    this.binding.mainMedicineName2.setText("(" + medicCut[1]);
                } else {
                    this.binding.mainMedicineName1.setText(medicCut[0].replace("·", ""));
                }
            }
        } else if (!(this.calendar_desc.contains("null")) && !this.calendar_desc.equals("")) {
            String[] cut = this.calendar_desc.split("~");
            if (cut.length > 1 ) {
                this.binding.mainMedicineName1.setText(cut[0]);
                this.binding.mainMedicineName2.setText(cut[1]);
            }
        }
    }

    private void step1Setting() {
        //Start
        String startDate = Global.inputDateTimeToStr(this.step1SaveDTO.getSdate());
        if (this.step1SaveDTO.getSdate() == 0) {
            startDate = " - ";
        }
        String[] cut = startDate.split("-");
        this.binding.step1StartDate.setText(cut[0]);
        this.binding.step1StartTime.setText(cut[1]);
        //End
        String endDate = Global.inputDateTimeToStr(this.step1SaveDTO.geteDate());
        if (this.step1SaveDTO.geteDate() == 0) {
            endDate = " - ";
        }
        String[] cut2 = endDate.split("-");
        this.binding.step1EndDate.setText(cut2[0]);
        this.binding.step1EndTime.setText(cut2[1]);
    }

    private void step2Setting() {
        this.binding.step2AchePower.setText(String.valueOf(this.step2SaveDTO.getAche_power()));
        this.binding.step2Icon.setImageResource(Global.icon[Global.getIconNumberReturn(this.step2SaveDTO.getAche_power())]);
        this.binding.step2IconBack.setBackgroundResource(Global.icon_back[Global.getIconNumberReturn(this.step2SaveDTO.getAche_power())]);
        this.binding.step2Txt.setText(this.getStep2Desc(this.step2SaveDTO.getAche_power()) + " 상태입니다.");
    }

    private void step3Setting() {

        Log.d("check=",!this.step3SaveDTO.getAche_location7().equals("Y")+"_");
        this.isCheckImgs[0] = !this.step3SaveDTO.getAche_location1().equals("Y");
        this.isCheckImgs[1] = !this.step3SaveDTO.getAche_location2().equals("Y");
        this.isCheckImgs[2] = !this.step3SaveDTO.getAche_location3().equals("Y");
        this.isCheckImgs[3] = !this.step3SaveDTO.getAche_location4().equals("Y");
        this.isCheckImgs[4] = !this.step3SaveDTO.getAche_location5().equals("Y");
        this.isCheckImgs[5] = !this.step3SaveDTO.getAche_location6().equals("Y");
        this.isCheckImgs[6] = !this.step3SaveDTO.getAche_location7().equals("Y");
        this.isCheckImgs[7] = !this.step3SaveDTO.getAche_location8().equals("Y");
        this.isCheckImgs[8] = !this.step3SaveDTO.getAche_location9().equals("Y");
        this.isCheckImgs[9] = !this.step3SaveDTO.getAche_location10().equals("Y");
        this.isCheckImgs[10] = !this.step3SaveDTO.getAche_location11().equals("Y");
        this.isCheckImgs[11] = !this.step3SaveDTO.getAche_location12().equals("Y");
        this.isCheckImgs[12] = !this.step3SaveDTO.getAche_location13().equals("Y");
        this.isCheckImgs[13] = !this.step3SaveDTO.getAche_location14().equals("Y");
        this.isCheckImgs[14] = !this.step3SaveDTO.getAche_location15().equals("Y");
        this.isCheckImgs[15] = !this.step3SaveDTO.getAche_location16().equals("Y");
        this.isCheckImgs[16] = !this.step3SaveDTO.getAche_location17().equals("Y");
        this.isCheckImgs[17] = !this.step3SaveDTO.getAche_location18().equals("Y");


        for (int i = 0, j = this.isCheckImgs.length; i < j; i++) {
            FrameLayout f = findViewById(this.btIds[i]);
            f.setTag(i);
            step3checkImg(f);
        }

    }

    private void step4Setting() {
        final ArrayList<Step4EtcDTO> datas = new ArrayList<>();
        final ArrayList<Step4EtcDTO> etcDatas = new ArrayList<>();
        for (int i = 0, j = this.step4SaveDTO.getArrayList().size(); i < j; i++) {
            Step4EtcDTO row = this.step4SaveDTO.getArrayList().get(i);
            if (row.getVal().equals("Y") && !row.getEtc()) {
                row.setClick(false);
                datas.add(row);
            } else if (row.getVal().equals("Y") && row.getEtc()) {
                etcDatas.add(row);
            }
        }
        final int gridViewH = (gridItemViewHeight * ((int) Math.ceil((double) datas.size() / gridItemWidthMaxCount)));
        final int etcViewH = (int) (Global.pxToDp(this, this.etcItemViewHeight) * etcDatas.size());

        this.binding.step4Parent.getLayoutParams().height = gridViewH + etcViewH + this.baseHeight;
        this.binding.step4LinearView.getLayoutParams().height = gridViewH;
        this.binding.step4EtcParent.getLayoutParams().height = etcViewH;
        for (int i = 0, j = etcDatas.size(); i < j; i++) {
            Step4EtcDTO row = etcDatas.get(i);
            this.binding.step4EtcParent.addView(this.createTextView(row.getContent(), 15));
        }

        this.adapter4 = new Step4GridviewAdapter(datas, getLayoutInflater());
        this.binding.detail4GridV.setAdapter(this.adapter4);
    }

    private void step5Setting() {
        if ( this.step5SaveDTO.getAche_realize_hour() > 0 ) {
            this.binding.step5Hour.setText(String.valueOf(this.step5SaveDTO.getAche_realize_hour()));
        }
        if ( this.step5SaveDTO.getAche_realize_minute() > 0 ) {
            this.binding.step5Min.setText(String.valueOf(this.step5SaveDTO.getAche_realize_minute()));
        }

        this.step5IsHeadche(this.step5SaveDTO.getAche_realize_yn().equals("Y"));

        final ArrayList<Step5EtcDTO> datas = new ArrayList<>();
        final ArrayList<Step5EtcDTO> etcDatas = new ArrayList<>();
        for (int i = 0, j = this.step5SaveDTO.getArrayList().size(); i < j; i++) {
            Step5EtcDTO row = this.step5SaveDTO.getArrayList().get(i);
            if (row.getVal().equals("Y") && !row.getEtc()) {
                row.setClick(false);
                datas.add(row);
            } else if (row.getVal().equals("Y") && row.getEtc()) {
                etcDatas.add(row);
            }
        }

        final int gridViewH = (gridItemViewHeight * ((int) Math.ceil((double) datas.size() / gridItemWidthMaxCount)));
        final int etcViewH = (int) (Global.pxToDp(this, this.etcItemViewHeight)) * etcDatas.size();

        this.binding.step5YesnoParent.post(new Runnable() {
            @Override
            public void run() {
                int yesnoBtH = binding.step5YesnoParent.getHeight();
                binding.step5ParentV.getLayoutParams().height = gridViewH + etcViewH + baseHeight + yesnoBtH;
                binding.step5LinearView.getLayoutParams().height = gridViewH;
                binding.step5EtcParent.getLayoutParams().height = etcViewH;

                for (int i = 0, j = etcDatas.size(); i < j; i++) {
                    Step5EtcDTO row = etcDatas.get(i);
                    binding.step5EtcParent.addView(createTextView(row.getContent(), 15));
                }

                adapter5 = new Step5GridviewAdapter(datas, getLayoutInflater());
                binding.detail5GridV.setAdapter(adapter5);
            }
        });
    }

    private void step6Setting() {

        int heightCount = 0;
        this.isHeadche(this.step6SaveDTO.getAche_sign_yn(), this.binding.step6YesBt, this.binding.step6NoBt);

        if (this.step6SaveDTO.getAche_sign1().equals("Y")) {
            heightCount += 1;
            this.binding.step6CheckBox.addView(createStep6CheckBox("이러한 증상은 5분에서 60분간 지속된다"));

        }
        if (this.step6SaveDTO.getAche_sign2().equals("Y")) {

            heightCount += 1;
            this.binding.step6CheckBox.addView(createStep6CheckBox("이러한 증상은 5분이상 동안 서서히 나타나기 시작한다."));

        }
        if (this.step6SaveDTO.getAche_sign3().equals("Y")) {

            heightCount += 1;
            this.binding.step6CheckBox.addView(createStep6CheckBox("시야 중 보이지 않는 부분이 있다."));

        }
        if (this.step6SaveDTO.getAche_sign4().equals("Y")) {

            heightCount += 1;
            this.binding.step6CheckBox.addView(createStep6CheckBox("지그재그선(성곽모양)이 보인다."));

        }
        if (this.step6SaveDTO.getAche_sign5().equals("Y")) {

            heightCount += 1;
            this.binding.step6CheckBox.addView(createStep6CheckBox("이러한 증상이 오른쪽 또는 왼쪽 한쪽에서만 나타난다."));

        }
        if (this.step6SaveDTO.getAche_sign6().equals("Y")) {

            heightCount += 1;
            this.binding.step6CheckBox.addView(createStep6CheckBox("단어가 잘 떠오르지 않거나 말이 어둔해진다."));

        }
        if (this.step6SaveDTO.getAche_sign7().equals("Y")) {

            heightCount += 1;
            this.binding.step6CheckBox.addView(createStep6CheckBox("한쪽 손발이 저리거나 감각이 없어진다."));
        }

        final int height = (int) (Global.pxToDp(this, this.etcItemViewHeight - 310)) * heightCount;
        this.binding.step6YesAndNoV.post(new Runnable() {
            @Override
            public void run() {
                int checkH = binding.step6YesAndNoV.getHeight() + baseHeight + height;
                binding.step6ParentV.getLayoutParams().height = checkH;
                Log.d("checkH", checkH + "_");
            }
        });

        this.binding.step6CheckBox.getLayoutParams().height = this.baseHeight + height;
    }

    private void step7Setting() {
        this.isHeadche("Y", this.binding.step7YesBt, this.binding.step7NoBt);
        final ArrayList<Step7EtcDTO> datas = new ArrayList<>();
        final ArrayList<Step7EtcDTO> etcDatas = new ArrayList<>();
        for (int i = 0, j = this.step7SaveDTO.getStep7EtcDTOS().size(); i < j; i++) {
            Step7EtcDTO row = this.step7SaveDTO.getStep7EtcDTOS().get(i);
            if (row.getVal().equals("Y") && !row.getEtc()) {
                row.setClick(false);
                datas.add(row);
            } else if (row.getVal().equals("Y") && row.getEtc()) {
                etcDatas.add(row);
            }
        }


        final int gridViewH = (gridItemViewHeight * ((int) Math.ceil((double) datas.size() / gridItemWidthMaxCount)));
        final int etcViewH = (int) (Global.pxToDp(this, this.etcItemViewHeight)) * etcDatas.size();

        this.binding.step7YesAndNoV.post(new Runnable() {
            @Override
            public void run() {
                int h = binding.step7YesAndNoV.getHeight();
                binding.step7ParentV.getLayoutParams().height = h + gridViewH + etcViewH + baseHeight;
                binding.detail7GridV.getLayoutParams().height = gridViewH;
                binding.step7EtcParent.getLayoutParams().height = etcViewH;
            }
        });

        this.adapter7 = new Step7GridviewAdapter(datas, getLayoutInflater());
        this.binding.detail7GridV.setAdapter(adapter7);

        for (int i = 0, j = etcDatas.size(); i < j; i++) {
            Step7EtcDTO row = etcDatas.get(i);
            binding.step7EtcParent.addView(createTextView(row.getContent(), 15));
        }

    }

    private void step8Setting() {
        this.isHeadche(this.step8SaveDTO.getAche_factor_yn(), this.binding.step8YesBt, this.binding.step8NoBt);
        final ArrayList<Step8EtcDTO> datas = new ArrayList<>();
        final ArrayList<Step8EtcDTO> etcDatas = new ArrayList<>();
        for (int i = 0, j = this.step8SaveDTO.getArrayList().size(); i < j; i++) {
            Step8EtcDTO row = this.step8SaveDTO.getArrayList().get(i);
            if (row.getVal().equals("Y") && !row.getEtc()) {
                row.setClick(false);
                datas.add(row);
            } else if (row.getVal().equals("Y") && row.getEtc()) {
                etcDatas.add(row);
            }
        }

        final int gridViewH = (gridItemViewHeight * ((int) Math.ceil((double) datas.size() / gridItemWidthMaxCount)));
        final int etcViewH = (int) (Global.pxToDp(this, this.etcItemViewHeight)) * etcDatas.size();

        this.binding.step8YesAndNoV.post(new Runnable() {
            @Override
            public void run() {
                int h = binding.step8YesAndNoV.getHeight();
                binding.step8ParentV.getLayoutParams().height = h + gridViewH + etcViewH + baseHeight;
                binding.detail8GridV.getLayoutParams().height = gridViewH;
                binding.step8EtcParent.getLayoutParams().height = etcViewH;
            }
        });

        this.adapter8 = new Step8GridviewAdapter(datas, getLayoutInflater());
        this.binding.detail8GridV.setAdapter(adapter8);

        for (int i = 0, j = etcDatas.size(); i < j; i++) {
            Step8EtcDTO row = etcDatas.get(i);
            binding.step8EtcParent.addView(createTextView(row.getContent(), 15));
        }
    }

    private boolean isStep9Check(Step9MainDTO row) {
        if (row.getAche_medicine1().equals("Y")) return true;
        else if (row.getAche_medicine2().equals("Y")) return true;
        else if (row.getAche_medicine3().equals("Y")) return true;
        else if (row.getAche_medicine4().equals("Y")) return true;
        else if (row.getAche_medicine5().equals("Y")) return true;
        else if (row.getAche_medicine6().equals("Y")) return true;
        else if (row.getAche_medicine7().equals("Y")) return true;
        else if (row.getAche_medicine8().equals("Y")) return true;
        else if (row.getAche_medicine9().equals("Y")) return true;
        else if (row.getAche_medicine10().equals("Y")) return true;

        for (int i = 0, j = row.getAche_medicine_etc().size(); i < j; i++) {
            Step9MainEtcDTO row_etc = row.getAche_medicine_etc().get(i);
            if (row_etc.getVal().equals("Y")) return true;
        }
        return false;
    }

    private void step9Setting() {
        int count = 0;

        for (int i = 0, j = this.step9NewSaveDTO.size(); i < j; i++) {
            Step9MainDTO row = this.step9NewSaveDTO.get(i);

            if (this.isStep9Check(row)) {

                this.binding.step9ParentV.addView(this.step9CreateTextView("\n" + row.getDate(), 20, true));
                count += 3;
                if (row.getAche_medicine1().equals("Y")) {
                    this.binding.step9ParentV.addView(this.step9CreateTextView("\n· 이미그란", 15, false));
                    count += 1;
                }
                if (row.getAche_medicine2().equals("Y")) {
                    this.binding.step9ParentV.addView(this.step9CreateTextView("\n· 수마트란", 15, false));
                    count += 1;
                }
                if (row.getAche_medicine3().equals("Y")) {
                    this.binding.step9ParentV.addView(this.step9CreateTextView("\n· 슈그란", 15, false));
                    count += 1;
                }
                if (row.getAche_medicine4().equals("Y")) {
                    this.binding.step9ParentV.addView(this.step9CreateTextView("\n· 마이그란", 15, false));
                    count += 1;
                }
                if (row.getAche_medicine5().equals("Y")) {
                    this.binding.step9ParentV.addView(this.step9CreateTextView("\n· 조믹", 15, false));
                    count += 1;
                }
                if (row.getAche_medicine6().equals("Y")) {
                    this.binding.step9ParentV.addView(this.step9CreateTextView("\n· 나라믹", 15, false));
                    count += 1;
                }
                if (row.getAche_medicine7().equals("Y")) {
                    this.binding.step9ParentV.addView(this.step9CreateTextView("\n· 알모그란", 15, false));
                    count += 1;
                }
                if (row.getAche_medicine8().equals("Y")) {
                    this.binding.step9ParentV.addView(this.step9CreateTextView("\n· 미가드", 15, false));
                    count += 1;
                }
                if (row.getAche_medicine9().equals("Y")) {
                    this.binding.step9ParentV.addView(this.step9CreateTextView("\n· 크래밍", 15, false));
                    count += 1;
                }
                if (row.getAche_medicine10().equals("Y")) {
                    this.binding.step9ParentV.addView(this.step9CreateTextView("\n· 모름", 15, false));
                    count += 1;
                }

                for (int k = 0, l = row.getAche_medicine_etc().size(); k < l; k++) {
                    Step9MainEtcDTO row_etc = row.getAche_medicine_etc().get(k);
                    if (row_etc.getVal().equals("Y")) {
                        this.binding.step9ParentV.addView(this.step9CreateTextView("\n· " + row_etc.getContent(), 15, false));
                        count += 1;
                    }
                }

                if (row.getEffect().equals("0")) {
                    this.binding.step9ParentV.addView(this.step9CreateTextView("\n· 효과없음", 15, false));
                    count += 1;
                } else if (row.getEffect().equals("1")) {
                    this.binding.step9ParentV.addView(this.step9CreateTextView("\n· 1시간 이내", 15, false));
                    count += 1;
                } else if (row.getEffect().equals("2")) {
                    this.binding.step9ParentV.addView(this.step9CreateTextView("\n· 2시간 이내", 15, false));
                    count += 1;
                } else if (row.getEffect().equals("3")) {
                    this.binding.step9ParentV.addView(this.step9CreateTextView("\n· 4시간 이내", 15, false));
                    count += 1;
                }
                this.binding.step9ParentV.addView(this.createLine());
            }
        }
        //Log.d("textHeight=", (Global.pxToDp(getApplicationContext(), textHeight)+"_"));
        //this.binding.step9ParentV.getLayoutParams().height = (int)(Global.pxToDp(getApplicationContext(), textHeight)) * (count + 2);
        this.binding.step9ParentV.getLayoutParams().height = 100 * (count + 2);
    }

    private boolean isStep10Check(Step10MainDTO row) {
        if ( row.getAche_effect1().equals("Y") ) return true;
        else if ( row.getAche_effect2().equals("Y") ) return true;
        else if ( row.getAche_effect3().equals("Y") ) return true;
        else if ( row.getAche_effect4().equals("Y") ) return true;
        else if ( row.getAche_effect5().equals("Y") ) return true;

        return false;
    }
    private void step10Setting() {

        int count = 0;
        for ( int i = 0 , j = this.step10NewSaveDTO.size(); i < j ; i ++ ) {
            Step10MainDTO row = this.step10NewSaveDTO.get(i);
            if ( !this.isStep10Check(row) ) continue;
            count += 3;
            this.binding.step10ParentV.addView(this.step9CreateTextView("\n"+row.getDate(),20 , true));
            if ( row.getAche_effect1().equals("Y") ) {
                this.binding.step10ParentV.addView(this.step9CreateTextView("\n· 결근/결석",15 , false));
                count += 1;
            }
            if ( row.getAche_effect2().equals("Y") ) {
                this.binding.step10ParentV.addView(this.step9CreateTextView("\n· 업무/학습 능률저하",15 , false));
                count += 1;
            }
            if ( row.getAche_effect3().equals("Y") ) {
                this.binding.step10ParentV.addView(this.step9CreateTextView("\n· 가사활동 못함",15 , false));
                count += 1;
            }
            if ( row.getAche_effect4().equals("Y") ) {
                this.binding.step10ParentV.addView(this.step9CreateTextView("\n· 가사활동 능률저하",15 , false));
                count += 1;
            }
            if ( row.getAche_effect5().equals("Y") ) {
                this.binding.step10ParentV.addView(this.step9CreateTextView("\n· 여가활동 불참",15 , false));
                count += 1;
            }
            this.binding.step10ParentV.addView(this.createLine());
        }

        this.binding.step10ParentV.getLayoutParams().height = (int) (Global.pxToDp(getApplicationContext(), textHeight) * (count + 2));

    }

    private void step11Setting() {
        String startDate = Global.inputDateTimeToStr(this.step11SaveDTO.getMens_sdate());
        if (this.step11SaveDTO.getMens_sdate() == 0) {
            startDate = " - ";
        }
        String[] cut = startDate.split("-");
        this.binding.step11StartDate.setText(cut[0]);
        this.binding.step11StartTime.setText(cut[1]);
        //End
        String endDate = Global.inputDateTimeToStr(this.step11SaveDTO.getMens_edate());
        if (this.step11SaveDTO.getMens_edate() == 0) {
            endDate = " - ";
        }
        String[] cut2 = endDate.split("-");
        this.binding.step11EndDate.setText(cut2[0]);
        this.binding.step11EndTime.setText(cut2[1]);

        this.weatherSetting();
    }

    public void weatherSetting() {
        this.binding.temp.setText(String.format("%.0f", (this.step1SaveDTO.getTemp() - 273.15)) + "°");
        this.binding.pressureAndHumidity.setText("습도  " + this.step1SaveDTO.getHumidity() + "%" + "\n압력 " + this.step1SaveDTO.getPressure() + "hPa");
        this.binding.address.setText(this.step1SaveDTO.getAddress());
        Picasso.get().load("http://openweathermap.org/img/wn/" + this.step1SaveDTO.getWeather_icon() + "@2x.png").into(binding.weatherImg);
    }

    private void step12Setting() {
        this.binding.step12Desc.setText(this.step12SaveDTO.getDesc());
    }

    private LinearLayout createLine() {
        LinearLayout line = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
        params.topMargin = 30;
        params.leftMargin = 20;
        params.rightMargin = 20;
        params.bottomMargin = 20;
        line.setLayoutParams(params);
        line.setBackgroundColor(Color.parseColor("#dedede"));
        return line;
    }

    ;

    private LinearLayout createStep6CheckBox(String content) {
        LinearLayout checkBoxParent = new LinearLayout(this);
        LinearLayout.LayoutParams checkBoxParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        checkBoxParam.height = (int) (Global.pxToDp(this, this.etcItemViewHeight - 300));
        checkBoxParent.setOrientation(LinearLayout.HORIZONTAL);
        checkBoxParent.setLayoutParams(checkBoxParam);
        //checkBoxParent.getLayoutParams().height = this.etcItemViewHeight;

        ImageView checkImg = new ImageView(this);
        checkBoxParent.addView(checkImg);
        LinearLayout.LayoutParams imgParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        imgParam.width = 130;
        imgParam.height = 130;
        imgParam.gravity = Gravity.CENTER;
        checkImg.setLayoutParams(imgParam);
        checkImg.setImageResource(R.drawable.login_check_on);


        LinearLayout.LayoutParams tvParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        TextView tv = new TextView(this);
        checkBoxParent.addView(tv);
        tv.setLayoutParams(tvParam);
        tv.setText(" " + content);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.setTextColor(Color.parseColor("#222222"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

        return checkBoxParent;
    }

    private void isHeadche(String isHeadache, TextView yesBt, TextView noBt) {
        if (isHeadache.equals("Y")) {
            yesBt.setBackgroundResource(R.drawable.step5_select_board);
            yesBt.setTextColor(Color.parseColor("#1EA2B6"));
            noBt.setTextColor(Color.parseColor("#C2C2C2"));
            noBt.setBackgroundColor(Color.TRANSPARENT);
        } else if (isHeadache.equals("Y")) {
            yesBt.setBackgroundColor(Color.TRANSPARENT);
            yesBt.setTextColor(Color.parseColor("#C2C2C2"));
            noBt.setBackgroundResource(R.drawable.step5_no_select_board);
            noBt.setTextColor(Color.parseColor("#1EA2B6"));
        } else {
            yesBt.setBackgroundColor(Color.TRANSPARENT);
            yesBt.setTextColor(Color.parseColor("#C2C2C2"));

            noBt.setTextColor(Color.parseColor("#C2C2C2"));
            noBt.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void step5IsHeadche(boolean isHeadache) {
        if (isHeadache) {
            this.binding.step5YesBt.setBackgroundResource(R.drawable.step5_select_board);
            this.binding.step5YesBt.setTextColor(Color.parseColor("#1EA2B6"));
            this.binding.step5NoBt.setTextColor(Color.parseColor("#C2C2C2"));
            ;
            this.binding.step5NoBt.setBackgroundColor(Color.TRANSPARENT);
            this.binding.timeSettingV.setVisibility(View.VISIBLE);
        } else {
            this.binding.step5YesBt.setBackgroundColor(Color.TRANSPARENT);
            this.binding.step5YesBt.setTextColor(Color.parseColor("#C2C2C2"));
            this.binding.step5NoBt.setBackgroundResource(R.drawable.step5_no_select_board);
            this.binding.step5NoBt.setTextColor(Color.parseColor("#1EA2B6"));
            this.binding.timeSettingV.setVisibility(View.GONE);
        }
    }

    private TextView step10CreateTextView(String cotent, int size) {
        TextView tv = new TextView(this);
        tv.setText(cotent);
        tv.setTextColor(Color.parseColor("#222222"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
        tv.setPadding(10, 3, 3, 3);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        return tv;
    }

    private TextView step9CreateTextView(String cotent, int size, boolean isBold) {
        TextView tv = new TextView(this);
        tv.setText(cotent);
        if (isBold) tv.setTypeface(null, Typeface.BOLD);
        tv.setTextColor(Color.parseColor("#222222"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
        tv.setPadding(10, 3, 3, 3);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        return tv;
    }

    private TextView createTextView(String cotent, int size) {
        TextView tv = new TextView(this);
        tv.setText("·기타: " + cotent);
        tv.setTextColor(Color.parseColor("#222222"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
        tv.setPadding(3, 3, 3, 3);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        return tv;
    }

    private void step3checkImg(FrameLayout frameLayout) {
        boolean isCheck = this.isCheckImgs[(int) frameLayout.getTag()];
        if (isCheck) {
            this.isCheckImgs[(int) frameLayout.getTag()] = false;
            for (int k = 0, l = frameLayout.getChildCount(); k < l; k++) {
                ImageView index = (ImageView) frameLayout.getChildAt(k);
                index.setVisibility(View.GONE);
            }

        } else {
            this.isCheckImgs[(int) frameLayout.getTag()] = true;
            for (int k = 0, l = frameLayout.getChildCount(); k < l; k++) {
                ImageView index = (ImageView) frameLayout.getChildAt(k);
                index.setVisibility(View.VISIBLE);
            }
        }
    }

    private void getDiary() {
        AndroidNetworking.post(this.urls.mainUrl + this.urls.getUrls.get("getDiary"))
                .addBodyParameter("user_sid", csp.getValue("user_sid", ""))
                .addBodyParameter("diary_sid", String.valueOf(this.diary_sid))
                .setPriority(Priority.MEDIUM)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("stepContent_son", response.toString());
                try {
                    long edate = response.isNull("edate") ? 0L : response.getLong("edate") * 1000;
                    step1SaveDTO = new Step1SaveDTO(response.getLong("sdate") * 1000, edate, response.getString("address"),
                            response.getString("pressure").equals("") ? 0 : response.getDouble("pressure"), response.getString("humidity").equals("") ? 0 : response.getDouble("humidity")
                            , response.getString("temp").equals("") ? 0 : response.getDouble("temp"),
                            response.getString("weather_icon")
                    );

                    step2SaveDTO = new Step2SaveDTO(response.getInt("ache_power"));
                    step3SaveDTO = new Step3SaveDTO(response.getString("ache_location1"), response.getString("ache_location2"), response.getString("ache_location3"), response.getString("ache_location4"),
                            response.getString("ache_location5"), response.getString("ache_location6"), response.getString("ache_location7"), response.getString("ache_location8"),
                            response.getString("ache_location9"), response.getString("ache_location10"), response.getString("ache_location11"), response.getString("ache_location12"),
                            response.getString("ache_location13"), response.getString("ache_location14"), response.getString("ache_location15"),
                            response.getString("ache_location16"), response.getString("ache_location17"), response.getString("ache_location18"));

                    ArrayList<Step4EtcDTO> step4EtcDTOS = new ArrayList<>();

                    step4EtcDTOS.add(new Step4EtcDTO(R.drawable.step4_type_default1, R.drawable.step4_type_click1, "욱신거림", false, false, response.getString("ache_type1").equals("Y"), 0, response.getString("ache_type1")));
                    step4EtcDTOS.add(new Step4EtcDTO(R.drawable.step4_type_default2, R.drawable.step4_type_click2, "조임", false, false, response.getString("ache_type2").equals("Y"), 0, response.getString("ache_type2")));
                    step4EtcDTOS.add(new Step4EtcDTO(R.drawable.step4_type_default3, R.drawable.step4_type_click3, "터질듯함", false, false, response.getString("ache_type3").equals("Y"), 0, response.getString("ache_type3")));
                    step4EtcDTOS.add(new Step4EtcDTO(R.drawable.step4_type_default4, R.drawable.step4_type_click4, "찌름", false, false, response.getString("ache_type4").equals("Y"), 0, response.getString("ache_type4")));
                    //step4EtcDTOS.add(new Step4EtcDTO(R.drawable.step4_type_default5,R.drawable.step4_type_click5,"따끔따끔",false,false, response.getString("ache_type5").equals("Y"),0,response.getString("ache_type5")));
                    step4EtcDTOS.add(new Step4EtcDTO(R.drawable.step_type_etc, R.drawable.step_type_etc, "기타", false, true, false, 0, "N"));

                    if (!response.isNull("ache_type_etc")) {
                        JSONArray list4 = (JSONArray) response.get("ache_type_etc");
                        for (int i = 0, j = list4.length(); i < j; i++) {
                            JSONObject obj = (JSONObject) list4.get(i);
                            step4EtcDTOS.add(new Step4EtcDTO(R.drawable.step4_type_default1, R.drawable.step4_type_click1, obj.isNull("content") ? "" : obj.getString("content"), true, false, obj.getString("val").equals("Y"), obj.isNull("key") ? 0 : obj.getInt("key"), obj.getString("val")));
                        }
                    }

                    step4SaveDTO = new Step4SaveDTO(response.getString("ache_type1"), response.getString("ache_type2"), response.getString("ache_type3"), response.getString("ache_type4"), "N", step4EtcDTOS);


                    ArrayList<Step5EtcDTO> step5EtcDTOS = new ArrayList<>();
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default1, R.drawable.step5_type_click1, "아플 것 같은\n느낌", false, false, response.getString("ache_realize1").equals("Y"), 0, response.getString("ache_realize1")));
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default2, R.drawable.step5_type_click2, "뒷목통증\n뻐근함/당김", false, false, response.getString("ache_realize1").equals("Y"), 0, response.getString("ache_realize2")));
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default3, R.drawable.step5_type_click3, "하품", false, false, response.getString("ache_realize3").equals("Y"), 0, response.getString("ache_realize3")));
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default4, R.drawable.step5_type_click4, "피로", false, false, response.getString("ache_realize4").equals("Y"), 0, response.getString("ache_realize4")));
//                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default5, R.drawable.step5_type_click5, "집중력저하", false, false, response.getString("ache_realize5").equals("Y"), 0, response.getString("ache_realize5")));
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default6, R.drawable.step5_type_click6, "기분변화", false, false, response.getString("ache_realize5").equals("Y"), 0, response.getString("ache_realize6")));
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default7, R.drawable.step5_type_click7, "식욕변화", false, false, response.getString("ache_realize6").equals("Y"), 0, response.getString("ache_realize7")));
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default8, R.drawable.step5_type_click8, "빛/소리/\n냄새에 과민", false, false, response.getString("ache_realize7").equals("Y"), 0, response.getString("ache_realize7")));
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step_type_etc, R.drawable.step_type_etc, "기타", false, true, false, 0, "N"));
                    if (!response.isNull("ache_realize_etc")) {
                        JSONArray list5 = (JSONArray) response.get("ache_realize_etc");
                        for (int i = 0, j = list5.length(); i < j; i++) {
                            JSONObject obj = (JSONObject) list5.get(i);
                            step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step4_type_default1, R.drawable.step4_type_click1, obj.isNull("content") ? "" : obj.getString("content"), true, false, obj.getString("val").equals("Y"), obj.isNull("key") ? 0 : obj.getInt("key"), obj.getString("val")));
                        }
                    }

                    step5SaveDTO = new Step5SaveDTO(response.getString("ache_realize_yn"), response.isNull("ache_realize_hour") ? 0 : response.getInt("ache_realize_hour"), response.isNull("ache_realize_minute") ? 0 : response.getInt("ache_realize_minute"),
                            response.getString("ache_realize1"), response.getString("ache_realize2"), response.getString("ache_realize3"), response.getString("ache_realize4"),
                            response.getString("ache_realize5"), response.getString("ache_realize6"), response.getString("ache_realize7"), "", step5EtcDTOS);


                    step6SaveDTO = new Step6SaveDTO(response.getString("ache_sign_yn"), response.getString("ache_sign1"), response.getString("ache_sign2"),
                            response.getString("ache_sign3"), response.getString("ache_sign4"), response.getString("ache_sign5"),
                            response.getString("ache_sign6"), response.getString("ache_sign7"));


                    ArrayList<Step7EtcDTO> step7EtcDTOS = new ArrayList<>();
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default1, R.drawable.step7_type_click1, "소화가 안됨", false, false, response.getString("ache_with1").equals("Y"), 0, response.getString("ache_with1")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default2, R.drawable.step7_type_click2, "울렁거림", false, false, response.getString("ache_with2").equals("Y"), 0, response.getString("ache_with2")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default3, R.drawable.step7_type_click3, "구토", false, false, response.getString("ache_with3").equals("Y"), 0, response.getString("ache_with3")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default4, R.drawable.step7_type_click4, "어지럼증", false, false, response.getString("ache_with4").equals("Y"), 0, response.getString("ache_with4")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default5, R.drawable.step7_type_click5, "움직임에\n의해 악화", false, false, response.getString("ache_with5").equals("Y"), 0, response.getString("ache_with5")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default6, R.drawable.step7_type_click6, "빛에 예민", false, false, response.getString("ache_with6").equals("Y"), 0, response.getString("ache_with6")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default7, R.drawable.step7_type_click7, "소리에 예민", false, false, response.getString("ache_with7").equals("Y"), 0, response.getString("ache_with7")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default8, R.drawable.step7_type_click8, "냄새에 예민", false, false, response.getString("ache_with8").equals("Y"), 0, response.getString("ache_with8")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default9, R.drawable.step7_type_click9, "뒷목통증/\n뻐근함/당김", false, false, response.getString("ache_with9").equals("Y"), 0, response.getString("ache_with9")));
                   // step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default10, R.drawable.step7_type_click10, "어깨통증", false, false, response.getString("ache_with9").equals("Y"), 0, response.getString("ache_with9")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default11, R.drawable.step7_type_click11, "눈물/눈충혈", false, false, response.getString("ache_with10").equals("Y"), 0, response.getString("ache_with10")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default12, R.drawable.step7_type_click12, "콧물/코막힘", false, false, response.getString("ache_with11").equals("Y"), 0, response.getString("ache_with11")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step_type_etc, R.drawable.step_type_etc, "기타", false, true, false, 0, "N"));

                    if (!response.isNull("ache_with_etc")) {
                        JSONArray list7 = (JSONArray) response.get("ache_with_etc");
                        for (int i = 0, j = list7.length(); i < j; i++) {
                            JSONObject obj = (JSONObject) list7.get(i);
                            step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step_type_etc, R.drawable.step_type_etc, obj.isNull("content") ? "" : obj.getString("content"), true, false, obj.getString("val").equals("Y"), obj.isNull("key") ? 0 : obj.getInt("key"), obj.getString("val")));
                        }
                    }

                    step7SaveDTO = new Step7SaveDTO(response.getString("ache_with1"), response.getString("ache_with2"), response.getString("ache_with3"),
                            response.getString("ache_with4"), response.getString("ache_with5"), response.getString("ache_with6"), response.getString("ache_with7"),
                            response.getString("ache_with8"), response.getString("ache_with9"), response.getString("ache_with10"), response.getString("ache_with11"),
                            step7EtcDTOS);


                    ArrayList<Step8EtcDTO> step8EtcDTOS = new ArrayList<>();
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default1, R.drawable.step8_type_click1, "스트레스", false, false, response.getString("ache_factor1").equals("Y"), 0, response.getString("ache_factor1")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default2, R.drawable.step8_type_click2, "피로", false, false, response.getString("ache_factor2").equals("Y"), 0, response.getString("ache_factor2")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default3, R.drawable.step8_type_click3, "수면부족", false, false, response.getString("ache_factor3").equals("Y"), 0, response.getString("ache_factor3")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default4, R.drawable.step8_type_click4, "낮잠/늦잠", false, false, response.getString("ache_factor4").equals("Y"), 0, response.getString("ache_factor4")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default5, R.drawable.step8_type_click5, "주말", false, false, response.getString("ache_factor5").equals("Y"), 0, response.getString("ache_factor5")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default6, R.drawable.step8_type_click6, "굶음", false, false, response.getString("ache_factor6").equals("Y"), 0, response.getString("ache_factor6")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default8, R.drawable.step8_type_click8, "체함", false, false, response.getString("ache_factor7").equals("Y"), 0, response.getString("ache_factor7")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default7, R.drawable.step8_type_click7, "과식", false, false, response.getString("ache_factor8").equals("Y"), 0, response.getString("ache_factor8")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default9, R.drawable.step8_type_click9, "빛", false, false, response.getString("ache_factor9").equals("Y"), 0, response.getString("ache_factor9")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default10, R.drawable.step8_type_click10, "소리", false, false, response.getString("ache_factor10").equals("Y"), 0, response.getString("ache_factor10")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default11, R.drawable.step8_type_click11, "냄새", false, false, response.getString("ache_factor11").equals("Y"), 0, response.getString("ache_factor11")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default12, R.drawable.step8_type_click12, "감기", false, false, response.getString("ache_factor12").equals("Y"), 0, response.getString("ache_factor12")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default13, R.drawable.step8_type_click13, "운동", false, false, response.getString("ache_factor13").equals("Y"), 0, response.getString("ache_factor13")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default14, R.drawable.step8_type_click14, "술", false, false, response.getString("ache_factor14").equals("Y"), 0, response.getString("ache_factor14")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default15, R.drawable.step8_type_click15, "월경", false, false, response.getString("ache_factor15").equals("Y"), 0, response.getString("ache_factor15")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step_type_etc, R.drawable.step_type_etc, "기타", false, true, false, 0, "N"));

                    if (!response.isNull("ache_factor_etc")) {
                        JSONArray list8 = (JSONArray) response.get("ache_factor_etc");
                        for (int i = 0, j = list8.length(); i < j; i++) {
                            JSONObject obj = (JSONObject) list8.get(i);
                            step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step4_type_default1, R.drawable.step4_type_click1, obj.isNull("content") ? "" : obj.getString("content"), true, false, obj.getString("val").equals("Y"), obj.isNull("key") ? 0 : obj.getInt("key"), obj.getString("val")));
                        }
                    }


                    step8SaveDTO = new Step8SaveDTO(response.getString("ache_factor_yn"), response.getString("ache_factor1"), response.getString("ache_factor2"), response.getString("ache_factor3"),
                            response.getString("ache_factor4"), response.getString("ache_factor5"), response.getString("ache_factor6"), response.getString("ache_factor7"),
                            response.getString("ache_factor8"), response.getString("ache_factor9"), response.getString("ache_factor10"), response.getString("ache_factor11"),
                            response.getString("ache_factor12"), response.getString("ache_factor13"),
                            response.getString("ache_factor14"), response.getString("ache_factor15"), step8EtcDTOS);

                    step9NewSaveDTO = new ArrayList<>();
                    if (!response.isNull("ache_medicine")) {
                        JSONArray list9 = response.getJSONArray("ache_medicine");
                        for (int i = 0, j = list9.length(); i < j; i++) {
                            JSONObject obj = list9.getJSONObject(i);
                            step9NewSaveDTO.add(new Step9MainDTO(obj.getString("date"), obj.getString("effect"), obj.getString("ache_medicine1"),
                                    obj.getString("ache_medicine2"), obj.getString("ache_medicine3"), obj.getString("ache_medicine4"), obj.getString("ache_medicine5"),
                                    obj.getString("ache_medicine6"), obj.getString("ache_medicine7"), obj.getString("ache_medicine8"),
                                    obj.getString("ache_medicine9"), obj.getString("ache_medicine10"), new ArrayList<Step9MainEtcDTO>()));
                            //ache_medicine Etc Data Add
                            if (!obj.isNull("ache_medicine_etc")) {
                                JSONArray list9_etc = obj.getJSONArray("ache_medicine_etc");
                                for (int k = 0, l = list9_etc.length(); k < l; k++) {
                                    JSONObject obj_etc = list9_etc.getJSONObject(k);
                                    step9NewSaveDTO.get(i).getAche_medicine_etc().add(new Step9MainEtcDTO(obj_etc.isNull("key")?"":obj_etc.getString("key"),
                                            obj_etc.isNull("content")?"":obj_etc.getString("content"),obj_etc.isNull("val")?"":obj_etc.getString("val")));
                                }
                            }
                        }
                    }

                    step10NewSaveDTO = new ArrayList<>();
                    if (!response.isNull("ache_effect")) {
                        JSONArray list10 = response.getJSONArray("ache_effect");
                        for (int i = 0, j = list10.length(); i < j; i++) {
                            JSONObject obj = list10.getJSONObject(i);
                            step10NewSaveDTO.add(new Step10MainDTO(obj.getString("date"), obj.getString("ache_effect1"), obj.getString("ache_effect2"),
                                    obj.getString("ache_effect3"), obj.getString("ache_effect4"), obj.getString("ache_effect5")));
                        }
                    }

                    //step11
                    Long stime = response.isNull("mens_sdate") ? 0L : response.getLong("mens_sdate") * 1000;
                    Long etime = response.isNull("mens_edate") ? 0L : response.getLong("mens_edate") * 1000;
                    step11SaveDTO = new Step11SaveDTO(stime, etime);
                    step12SaveDTO = new Step12SaveDTO(response.getString("memo"));

                    detailSetting();

                } catch (JSONException e) {
                    Log.d("Exceptionee", e.toString());
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.d("stepContent Json Error", anError.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.backBt:
                //finish();
                break;
            case R.id.step1:
            case R.id.step1Next:
                intent = new Intent(this, ContentStepActivity.class);
                intent.putExtra("diary_sid", this.diary_sid);
                intent.putExtra("detailGo", true);
                intent.putExtra("step", 0);
                startActivity(intent);
                break;
            case R.id.step2:
            case R.id.step2Next:
                intent = new Intent(this, ContentStepActivity.class);
                intent.putExtra("diary_sid", this.diary_sid);
                intent.putExtra("detailGo", true);
                intent.putExtra("step", 1);
                startActivity(intent);
                break;
            case R.id.step3:
            case R.id.step3Next:
                intent = new Intent(this, ContentStepActivity.class);
                intent.putExtra("diary_sid", this.diary_sid);
                intent.putExtra("detailGo", true);
                intent.putExtra("step", 2);
                startActivity(intent);
                break;
            case R.id.step4:
            case R.id.step4Next:
                intent = new Intent(this, ContentStepActivity.class);
                intent.putExtra("diary_sid", this.diary_sid);
                intent.putExtra("detailGo", true);
                intent.putExtra("step", 3);
                startActivity(intent);
                break;
            case R.id.step5:
            case R.id.step5Next:
                intent = new Intent(this, ContentStepActivity.class);
                intent.putExtra("diary_sid", this.diary_sid);
                intent.putExtra("detailGo", true);
                intent.putExtra("step", 4);
                startActivity(intent);
                break;

            case R.id.step6:
            case R.id.step6Next:
                intent = new Intent(this, ContentStepActivity.class);
                intent.putExtra("diary_sid", this.diary_sid);
                intent.putExtra("detailGo", true);
                intent.putExtra("step", 5);
                startActivity(intent);
                break;

            case R.id.step7:
            case R.id.step7Next:
                intent = new Intent(this, ContentStepActivity.class);
                intent.putExtra("diary_sid", this.diary_sid);
                intent.putExtra("detailGo", true);
                intent.putExtra("step", 6);
                startActivity(intent);
                break;

            case R.id.step8:
            case R.id.step8Next:
                intent = new Intent(this, ContentStepActivity.class);
                intent.putExtra("diary_sid", this.diary_sid);
                intent.putExtra("detailGo", true);
                intent.putExtra("step", 7);
                startActivity(intent);
                break;

            case R.id.step9:
            case R.id.step9Next:
                intent = new Intent(this, ContentStepActivity.class);
                intent.putExtra("diary_sid", this.diary_sid);
                intent.putExtra("detailGo", true);
                intent.putExtra("step", 8);
                startActivity(intent);
                break;

            case R.id.step10:
            case R.id.step10Next:
                intent = new Intent(this, ContentStepActivity.class);
                intent.putExtra("diary_sid", this.diary_sid);
                intent.putExtra("detailGo", true);
                intent.putExtra("step", 9);
                startActivity(intent);
                break;

            case R.id.step11TitleV:
            case R.id.step11Next:
                intent = new Intent(this, ContentStepActivity.class);
                intent.putExtra("diary_sid", this.diary_sid);
                intent.putExtra("detailGo", true);
                intent.putExtra("step", 10);
                startActivity(intent);
                break;

            case R.id.step12:
            case R.id.step12Next:
                intent = new Intent(this, ContentStepActivity.class);
                intent.putExtra("detailGo", true);
                intent.putExtra("diary_sid", this.diary_sid);
                intent.putExtra("step", 11);
                startActivity(intent);
                break;

            case R.id.delBt:
                new AlertDialog.Builder(this).setTitle("안내").setMessage("작성하신 두통일기를\n삭제하시겠습니까?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AndroidNetworking.post(urls.mainUrl + urls.getUrls.get("del")).
                                        addBodyParameter("user_sid", csp.getValue("user_sid", ""))
                                        .addBodyParameter("diary_sid", String.valueOf(diary_sid))
                                        .setPriority(Priority.MEDIUM)
                                        .build().getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            if (csp.getValue("notSaveSid", "").equals(response.getString("diary_sid"))) {
                                                csp.put("notSaveSid", "");
                                                csp.put("saveStartDate", "");
                                                csp.put("notSaveNowDate", "");
                                            }
                                            Intent intent = new Intent(getApplicationContext(), DetailCalendarActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } catch (JSONException e) {
                                            Log.d("DVActivity_Error=", e.toString());
                                        }
                                    }

                                    @Override
                                    public void onError(ANError anError) {
                                        Log.d("DVActivity_NetError=", anError.getErrorDetail());
                                    }
                                });


                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                return;
        }
        finish();
    }

}
