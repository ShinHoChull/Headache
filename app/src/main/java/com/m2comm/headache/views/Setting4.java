package com.m2comm.headache.views;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivitySetting4Binding;
import com.m2comm.headache.module.AlarmReceiver;
import com.m2comm.headache.module.Custom_SharedPreferences;
import com.m2comm.headache.module.Urls;

import org.json.JSONObject;

public class Setting4 extends AppCompatActivity implements View.OnClickListener {

    BottomActivity bottomActivity;
    ActivitySetting4Binding binding;
    private boolean isCheck = true;
    private String check2 = "Y";
    private Urls urls;
    private Custom_SharedPreferences csp;

    private void regObj() {
        this.binding.backBt.setOnClickListener(this);
        this.binding.option2.setOnClickListener(this);
        this.binding.setting3CheckBt.setOnClickListener(this);
        this.binding.setting3CheckBt2.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting4);

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_setting4);
        this.binding.setSetting4(this);
        this.urls = new Urls();
        this.csp = new Custom_SharedPreferences(this);

        this.init();
        this.regObj();
        isCheck();
    }

    private void isCheck() {
        if (this.isCheck) {
            this.binding.setting3CheckBt.setImageResource(R.drawable.setting_cehck_off);
            this.isCheck = false;
            csp.put("alarm_push", false);
            AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlarmReceiver.class);
            PendingIntent pIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
            alarmManager.cancel(pIntent);


        } else {
            this.binding.setting3CheckBt.setImageResource(R.drawable.setting_cehck_on);
            this.isCheck = true;
            csp.put("alarm_push", true);

        }
    }

    private void isCheck2() {
        if (this.check2.equals("N")) {
            this.binding.setting3CheckBt2.setImageResource(R.drawable.setting_cehck_off);
        } else {
            this.binding.setting3CheckBt2.setImageResource(R.drawable.setting_cehck_on);
        }
    }

    private void setCheck2() {
        AndroidNetworking.get(this.urls.mainUrl+"/member/set_push.php")
                .addQueryParameter("user_id", csp.getValue("user_id",""))
                .addQueryParameter("push", this.check2)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {

                isCheck2();
            }

            @Override
            public void onError(ANError anError) {

            }
        });
    }

    private void init() {

        isCheck = csp.getValue("alarm_push", true);
        isCheck();
        this.bottomActivity = new BottomActivity(getLayoutInflater(), R.id.bottom, this, this, -1);
Log.d("getUserSId",csp.getValue("user_sid",""));
        //두통뉴스 check2
        AndroidNetworking.get(this.urls.mainUrl+"/member/get_push.php")
                .addQueryParameter("user_id", csp.getValue("user_id",""))
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response=",response.toString());
                try {
                    check2 = response.isNull("push")? check2 : response.getString("push");
                } catch (Exception e) {

                }
                isCheck2();
            }

            @Override
            public void onError(ANError anError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.backBt:
                finish();
                break;

            case R.id.option2:
                intent = new Intent(this, AlarmListActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                break;

            case R.id.setting3CheckBt:
                this.isCheck();
                break;

            case R.id.setting3CheckBt2:
                if ( this.check2.equals("Y") ) this.check2 = "N";
                else this.check2 = "Y";

                this.setCheck2();
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}

