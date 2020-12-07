package com.m2comm.headache.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.m2comm.headache.Adapter.MensAdapter;
import com.m2comm.headache.DTO.MensDTO;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.contentStepView.Step1;
import com.m2comm.headache.databinding.ActivityMensBinding;
import com.m2comm.headache.module.CalendarModule;
import com.m2comm.headache.module.Custom_SharedPreferences;
import com.m2comm.headache.module.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MensActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    int MENS_START_CODE = 119;
    int MENS_END_CODE = 129;

    private ActivityMensBinding binding;
    private Custom_SharedPreferences csp;
    private ArrayList<MensDTO> arrayList;
    private MensAdapter mensAdapter;
    private CalendarModule cm;
    private Urls urls;

    BottomActivity bottomActivity;

    boolean isEnd = false;
    private boolean isGoing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mens);
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_mens);
        this.binding.setMens(this);

        this.init();
        this.regObj();
        Log.d("MensActivity=","onResume");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MensActivity=","onResume");
        if ( !this.isGoing) {
            this.getData();
        }
    }

    private void getData() {

        this.arrayList = new ArrayList<>();

        AndroidNetworking.post(this.urls.mainUrl + this.urls.getUrls.get("getMens"))
                .addBodyParameter("user_sid", this.csp.getValue("user_sid", ""))
                .addBodyParameter("device", "android")
                .addBodyParameter("deviceid", csp.getValue("deviceid", ""))
                .setPriority(Priority.MEDIUM)
                .build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("response=", response.toString());

                for (int i = 0, j = response.length(); i < j; i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        arrayList.add(new MensDTO(obj.getInt("mens_sid"), obj.isNull("sdate") ? 0L : obj.getLong("sdate") * 1000,
                                obj.isNull("edate") ? 0L : obj.getLong("edate") * 1000));
                    } catch (JSONException e) {
                        Log.d("jsonError", e.toString());
                    }
                }
                topBtChange();
            }

            @Override
            public void onError(ANError anError) {
                Log.d("MensjsonArray=", anError.getErrorDetail());
            }
        });
    }

    private void regObj() {
        this.binding.backBt.setOnClickListener(this);
        this.binding.startBt.setOnClickListener(this);
        this.binding.endBt.setOnClickListener(this);
    }


    private void adapterReg() {
        this.mensAdapter = new MensAdapter(this, this, this.arrayList, getLayoutInflater());
        this.binding.listview.setAdapter(this.mensAdapter);
        this.mensAdapter.notifyDataSetChanged();
    }

    private void init() {
        this.csp = new Custom_SharedPreferences(this);
        this.arrayList = new ArrayList<>();
        this.cm = new CalendarModule(this, this);
        this.urls = new Urls();


        this.bottomActivity = new BottomActivity(getLayoutInflater(), R.id.bottom, this, this, -1);
        this.binding.listview.setOnItemClickListener(this);
    }

    private void topBtChange() {

        this.isEnd = this.csp.getValue("isMensStart", false);

        if (this.isEnd) {
            this.binding.startBt.setVisibility(View.GONE);
            this.binding.endBt.setVisibility(View.VISIBLE);
        } else {
            this.binding.startBt.setVisibility(View.VISIBLE);
            this.binding.endBt.setVisibility(View.GONE);
        }
        this.adapterReg();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        isGoing = false;
        MensDTO r = this.arrayList.get(position);
        Intent intent = new Intent(this, MensDetailViewActivity.class);
        intent.putExtra("num", r.getNum());
        intent.putExtra("sDate", r.getsDate());
        intent.putExtra("eDate", r.geteDate());
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            assert data != null;
            if (requestCode == MENS_START_CODE) {
                Log.d("callState=","Startcall");
                final long sDate = data.getLongExtra("startDateTime", 0);
                //서버 저장 후 sDate , sid , 저장단계 저장
                AndroidNetworking.post(this.urls.mainUrl + this.urls.getUrls.get("setMens"))
                        .addBodyParameter("sdate", Global.getTimeToStr(sDate))
                        .addBodyParameter("user_sid", this.csp.getValue("user_sid", ""))
                        .addBodyParameter("device", "android")
                        .addBodyParameter("deviceid", csp.getValue("deviceid", ""))
                        .setPriority(Priority.MEDIUM)
                        .build().getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("repson=", response.toString());
                        try {

                            csp.put("mensSdate", sDate);
                            csp.put("mens_sid", response.getString("mens_sid"));
                            csp.put("isMensStart", true);

                            getData();

                        } catch (JSONException e) {
                            Log.d("MensActivity_Error=", e.toString());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("MensActivity_Error=", anError.getErrorDetail());
                    }
                });

            } else if (requestCode == MENS_END_CODE) {
                Log.d("callState=","Endcall");
                final long eDate = data.getLongExtra("endDateTime", 0);

                AndroidNetworking.post(this.urls.mainUrl + this.urls.getUrls.get("setMens"))
                        .addBodyParameter("sdate", Global.getTimeToStr(csp.getValue("mensSdate", 0L)))
                        .addBodyParameter("edate", Global.getTimeToStr(eDate))
                        .addBodyParameter("mens_sid", csp.getValue("mens_sid", ""))
                        .addBodyParameter("user_sid", this.csp.getValue("user_sid", ""))
                        .addBodyParameter("device", "android")
                        .addBodyParameter("deviceid", csp.getValue("deviceid", ""))
                        .setPriority(Priority.MEDIUM)
                        .build().getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        csp.put("mensSdate", 0L);
                        csp.put("mens_sid", 0);
                        csp.put("isMensStart", false);

                        getData();
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        Intent mainIntent = new Intent(this , Main2Activity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
        overridePendingTransition(0,R.anim.anim_slide_out_bottom_login);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.backBt:
                this.isGoing = false;
                Intent mainIntent = new Intent(this , Main2Activity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainIntent);
                finish();
                break;

            case R.id.startBt:
                this.isGoing = true;
                Intent intent1 = new Intent(this, SubTimePicker.class);
                intent1.putExtra("startDateLong", 0);
                intent1.putExtra("isMean", true);
                startActivityForResult(intent1, MENS_START_CODE);
                break;

            case R.id.endBt:
                this.isGoing = true;
                Intent intent2 = new Intent(this, SubTimePicker.class);
                intent2.putExtra("startDateLong", csp.getValue("mensSdate", 0L));
                intent2.putExtra("endDateLong", csp.getValue("mensEdate", 0L));
                intent2.putExtra("isMean", true);
                intent2.putExtra("isEnd", true);
                startActivityForResult(intent2, MENS_END_CODE);
                break;
        }
    }
}