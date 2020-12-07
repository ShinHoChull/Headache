package com.m2comm.headache.views;
/**
 * V 1.5    code 5  2020-11-24  yjh     두통일기 작성 중 종료시 알람의 시간, 3일 지나고 나서 12시로 수정
 * V 1.6    code 6  2020-11-25  yjh     달력 날짜 클릭시 보여지는 약물데이터 null로 보여지는 버그 수정.
 * V 1.7    code 7  2020-11-30  yjh     조짐 - 선택지간격 좀 더 좁히기
 *                                      두통 분석 - 선택지 4개로 수정, y축 라인에 맞춤.
 *
 **/



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
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
        csp.put("isOff",true);
        csp.put("joinId","");


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Main", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();
                        // Log and toast
                        csp.put("token",token);
                    }
                });

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
