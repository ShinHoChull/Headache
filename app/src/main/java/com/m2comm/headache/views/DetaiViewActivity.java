package com.m2comm.headache.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.m2comm.headache.Adapter.AlarmListAdapter;
import com.m2comm.headache.DTO.AlarmDTO;
import com.m2comm.headache.R;

import java.util.ArrayList;
import java.util.Date;

public class DetaiViewActivity extends AppCompatActivity {

    BottomActivity bottomActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detai_view);

        this.init();

    }

    private void init () {
        this.bottomActivity = new BottomActivity(getLayoutInflater() , R.id.bottom , this , this);
    }




}
