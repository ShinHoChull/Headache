package com.m2comm.headache.contentStepView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.m2comm.headache.Adapter.Step10NewListAdapter;
import com.m2comm.headache.DTO.Step10MainDTO;
import com.m2comm.headache.DTO.Step10SaveDTO;
import com.m2comm.headache.DTO.Step1SaveDTO;
import com.m2comm.headache.DTO.Step9Dates;
import com.m2comm.headache.DTO.Step9MainDTO;
import com.m2comm.headache.DTO.Step9MainEtcDTO;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.views.ContentStepActivity;

import java.util.ArrayList;

public class Step10_New implements View.OnClickListener {

    private LayoutInflater inflater;
    private int ParentID;
    private Context context;
    private Activity activity;

    int nextStepNum = 12;
    int backStepNum = 9;

    private LinearLayout parent;
    private View view;
    ContentStepActivity parentActivity;
    private Step1SaveDTO step1SaveDTO;

    ArrayList<Step10MainDTO> step10MainDTOArrayList;

    private ListView listView;
    private Step10NewListAdapter adapter;
    private TextView nextBt , backBt;

    public Step10_New(LayoutInflater inflater, int parentID, Context context, Activity activity,
                      ContentStepActivity parentActivity, ArrayList<Step10MainDTO> step10MainDTOArrayList, Step1SaveDTO step1SaveDTO) {
        this.inflater = inflater;
        ParentID = parentID;
        this.context = context;
        this.activity = activity;
        this.parentActivity = parentActivity;
        this.step10MainDTOArrayList = step10MainDTOArrayList;
        this.step1SaveDTO = step1SaveDTO;
        this.init();
    }

    private void regObj() {

        this.parent = this.activity.findViewById(this.ParentID);
        this.parent.removeAllViews();

        this.view = inflater.inflate(R.layout.step10_new, this.parent, true);
        this.listView = this.view.findViewById(R.id.step10ListView);

        this.nextBt = this.view.findViewById(R.id.nextBt);
        this.nextBt.setOnClickListener(this);
        this.backBt = this.view.findViewById(R.id.backBt);
        this.backBt.setOnClickListener(this);
    }

    private void init() {

        this.regObj();

        if (this.step10MainDTOArrayList == null || this.step10MainDTOArrayList.size() <= 0) {
            this.step10MainDTOArrayList = new ArrayList<>();
            ArrayList<String> formatDates = Global.getFormatDates(this.step1SaveDTO.getSdate(), this.step1SaveDTO.geteDate());
            for (String date : formatDates) {
                this.step10MainDTOArrayList.add(new Step10MainDTO(date, "N", "N",
                        "N", "N", "N"));
            }
        }

        this.adapter = new Step10NewListAdapter(this.context, this, this.step1SaveDTO, this.step10MainDTOArrayList, this.inflater);
        this.listView.setAdapter(this.adapter);
    }

    public void changeRow(Step10MainDTO row, int position) {
        this.step10MainDTOArrayList.set(position, row);
        this.parentActivity.save10(this.step10MainDTOArrayList);
    }

    public void nextStepClick(boolean isNextBt) {

    }

    public void addListView(String etc) {

    }

    public void saveDate(ArrayList<Step9Dates> arrayList) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextBt:
                this.parentActivity.positionView(this.nextStepNum);
                break;

            case R.id.backBt:
                this.parentActivity.positionView(this.backStepNum);
                break;
        }
    }

}
