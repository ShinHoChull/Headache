package com.m2comm.headache.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;

import com.m2comm.headache.Adapter.AlarmListAdapter;
import com.m2comm.headache.DTO.AlarmDTO;
import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivityAlarmListBinding;

import java.util.ArrayList;
import java.util.Date;

public class AlarmListActivity extends AppCompatActivity implements View.OnClickListener {

    BottomActivity bottomActivity;
    private ActivityAlarmListBinding binding;
    private AlarmListAdapter adapter;
    private ArrayList<AlarmDTO> arrayList;

    private void regObj () {
        this.binding.backBt.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
        this.binding = DataBindingUtil.setContentView(this,R.layout.activity_alarm_list);
        this.binding.setAlarmList(this);

        this.init();
        this.regObj();
    }

    private void init () {
        this.bottomActivity = new BottomActivity(getLayoutInflater() , R.id.bottom , this , this,-1);
        this.arrayList = new ArrayList<>();
        byte[] week = {};
        this.arrayList.add(new AlarmDTO(new Date() , week , true , 1));
        this.arrayList.add(new AlarmDTO(new Date() , week , true , 1));
        this.adapter = new AlarmListAdapter( this , this ,this.arrayList, getLayoutInflater() );
        this.binding.listview.setAdapter(this.adapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.backBt:
                finish();
                break;


        }

    }
}
