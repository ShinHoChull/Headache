package com.m2comm.headache.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import com.m2comm.headache.R;
import com.m2comm.headache.module.Custom_SharedPreferences;

public class IntroActivity extends AppCompatActivity {

    private Handler handler;
    private int time = 1500;
    private Custom_SharedPreferences csp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        this.handler = new Handler();
        this.csp = new Custom_SharedPreferences(this);

        final String deviceid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        csp.put("deviceid", deviceid);

        this.moveMain();
    }

    public void moveMain() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, time);
    }

}
