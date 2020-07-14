package com.m2comm.headache.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.m2comm.headache.DTO.MensDTO;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivityMensBinding;
import com.m2comm.headache.databinding.ActivityMensDetailViewBinding;
import com.m2comm.headache.module.Custom_SharedPreferences;
import com.m2comm.headache.module.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MensDetailViewActivity extends AppCompatActivity implements View.OnClickListener {

    private int MENS_DETAIL_START_CODE = 0;
    private int MENS_DETAIL_END_CODE = 1;

    private ActivityMensDetailViewBinding binding;
    private Custom_SharedPreferences csp;
    BottomActivity bottomActivity;
    private Urls urls;

    private int num = 0;
    private long sData = 0;
    private long eData = 0;


    private void regObj() {
        this.binding.backBt.setOnClickListener(this);
        this.binding.delBt.setOnClickListener(this);
        this.binding.startMensBt.setOnClickListener(this);
        this.binding.endMensBt.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mens_detail_view);
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_mens_detail_view);
        this.binding.setMensDetail(this);
        this.init();
        this.regObj();
    }

    private void init() {
        this.csp = new Custom_SharedPreferences(this);
        this.bottomActivity = new BottomActivity(getLayoutInflater(), R.id.bottom, this, this, -1);
        this.urls = new Urls();

        Intent intent = getIntent();
        this.num = intent.getIntExtra("num", 0);
        this.sData = intent.getLongExtra("sDate", 0);
        this.eData = intent.getLongExtra("eDate", 0);

        this.dateSetting();
    }

    private void dateSetting() {

        if ( this.sData == 0 ) {
            this.binding.mensStartDate.setVisibility(View.INVISIBLE);
        } else if (this.eData == 0) {
            this.binding.mensEndDate.setVisibility(View.INVISIBLE);
        }

        this.binding.mensStartDate.setText(Global.inputDateToStr(this.sData));
        this.binding.mensEndDate.setText(Global.inputDateToStr(this.eData));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.start_mens_bt:
                Intent intent1 = new Intent(this, SubTimePicker.class);
                intent1.putExtra("startDateLong", sData);
                intent1.putExtra("isMean", true);
                startActivityForResult(intent1, MENS_DETAIL_START_CODE);
                break;

            case R.id.end_mens_bt:
                Intent intent2 = new Intent(this, SubTimePicker.class);
                intent2.putExtra("startDateLong", sData );
                intent2.putExtra("endDateLong", eData );
                intent2.putExtra("isMean", true);
                intent2.putExtra("isEnd", true);
                startActivityForResult(intent2, MENS_DETAIL_END_CODE);
                break;

            case R.id.backBt:
                finish();
                break;

            case R.id.delBt:

                new AlertDialog.Builder(this).setTitle("안내").setMessage("삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                AndroidNetworking.post(urls.mainUrl+urls.getUrls.get("delMens"))
                                        .addBodyParameter("mens_sid",String.valueOf(num))
                                        .addBodyParameter("device","android")
                                        .addBodyParameter("deviceid",csp.getValue("deviceid",""))
                                        .setPriority(Priority.MEDIUM)
                                        .build().getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            if (csp.getValue("mens_sid","").equals(response.getString("mens_sid"))) {
                                                csp.put("mensSdate",0L);
                                                csp.put("mens_sid","");
                                                csp.put("isMensStart",false);
                                            }
                                            finish();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onError(ANError anError) {
                                        Log.d("anError=",anError.getErrorDetail());
                                    }
                                });

                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();


                break;
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            assert data != null;

            if (requestCode == MENS_DETAIL_START_CODE) {

                sData = data.getLongExtra("startDateTime", 0);
                //서버 저장 후 sDate , sid , 저장단계 저장
                AndroidNetworking.post(this.urls.mainUrl + this.urls.getUrls.get("setMens"))
                        .addBodyParameter("sdate", Global.getTimeToStr(sData))
                        .addBodyParameter("edate", Global.getTimeToStr(eData))
                        .addBodyParameter("mens_sid", num+"")
                        .addBodyParameter("user_sid", this.csp.getValue("user_sid", ""))
                        .addBodyParameter("device", "android")
                        .addBodyParameter("deviceid", csp.getValue("deviceid", ""))
                        .setPriority(Priority.MEDIUM)
                        .build().getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        binding.mensStartDate.setText(Global.inputDateToStr(sData));
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("MensActivity_Error=", anError.getErrorDetail());
                    }
                });

            } else if (requestCode == MENS_DETAIL_END_CODE) {

                eData = data.getLongExtra("endDateTime", 0);

                AndroidNetworking.post(this.urls.mainUrl + this.urls.getUrls.get("setMens"))
                        .addBodyParameter("sdate", Global.getTimeToStr(sData))
                        .addBodyParameter("edate", Global.getTimeToStr(eData))
                        .addBodyParameter("mens_sid", num+"")
                        .addBodyParameter("user_sid", this.csp.getValue("user_sid", ""))
                        .addBodyParameter("device", "android")
                        .addBodyParameter("deviceid", csp.getValue("deviceid", ""))
                        .setPriority(Priority.MEDIUM)
                        .build().getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        binding.mensEndDate.setText(Global.inputDateToStr(eData));
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("MensActivity_Error=", anError.getErrorDetail());
                    }
                });

            }
        }
    }






    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }

}