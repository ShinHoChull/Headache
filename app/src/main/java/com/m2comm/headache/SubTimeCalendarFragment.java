package com.m2comm.headache;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.m2comm.headache.Adapter.CalendarAdapter;
import com.m2comm.headache.Adapter.SubTimeCalendarAdapter;
import com.m2comm.headache.module.Custom_SharedPreferences;
import com.m2comm.headache.views.SubTimePicker;

import java.util.ArrayList;
import java.util.Date;

public class SubTimeCalendarFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ArrayList<String> dayArr;
    private String currentlyDateStr;
    private Date startDate;
    private boolean isStart = false , isEnd = false;
    private GridView gridView;
    private SubTimeCalendarAdapter calendarAdapter;
    private Date endDate;

    public static SubTimeCalendarFragment newInstance(String currentlyDateStr, ArrayList<String> dayArr) {

        SubTimeCalendarFragment fragment = new SubTimeCalendarFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("dateArr",dayArr);
        args.putString("dateStr",currentlyDateStr);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        this.dayArr = getArguments().getStringArrayList("dateArr");
        this.currentlyDateStr = getArguments().getString("dateStr");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.calendar_month_fragment , container , false);

        this.gridView = view.findViewById(R.id.calendar);
        this.gridView.post(new Runnable() {
            @Override
            public void run() {
                reloadGridView();
            }
        });

        this.gridView.setOnItemClickListener(this);
        //tv.setText(this.dayArr.size());
        return view;
    }

    private void reloadGridView () {
        //this.calendarAdapter = new SubTimeCalendarAdapter(this.startDate , this.dayArr ,this.currentlyDateStr, getContext() , getLayoutInflater(),this.gridView.getHeight()/6);
        this.gridView.setAdapter(this.calendarAdapter);
        this.calendarAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       // Toast.makeText(getActivity() , ""+this.dayArr.get(position) , Toast.LENGTH_SHORT).show();
        this.startDate = Global.getStrToDate(this.currentlyDateStr+"-"+this.dayArr.get(position));
        this.reloadGridView();
    }


}
