package com.m2comm.headache;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.m2comm.headache.Adapter.CalendarAdapter;
import com.m2comm.headache.Adapter.DetailCalendarAdapter;
import com.m2comm.headache.DTO.CalendarDTO;
import com.m2comm.headache.DTO.CalendarListDTO;
import com.m2comm.headache.module.Custom_SharedPreferences;
import com.m2comm.headache.module.Urls;
import com.m2comm.headache.views.DetailCalendarActivity;
import com.m2comm.headache.views.SubTimePicker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailCalendarFragment extends Fragment {

    private ArrayList<String> dayArr;
    private String dateStr;
    private Urls urls;
    private Custom_SharedPreferences csp;
    private ArrayList<CalendarDTO> calendarDTOS;
    private ArrayList<CalendarListDTO> calendarListDTOS;
    private GridView gridView;
    private DetailCalendarAdapter calendarAdapter;

    private int gridviewHeight = 0;

    private LinearLayout mensV;

    public static DetailCalendarFragment newInstance(String dateStr , ArrayList<String> dayArr) {

        DetailCalendarFragment fragment = new DetailCalendarFragment();
        Bundle args = new Bundle();
        args.putString("dateStr",dateStr);//format = > yyyy-mm
        args.putStringArrayList("dateArr",dayArr);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        this.init();
        this.dayArr = getArguments().getStringArrayList("dateArr");
        this.dateStr = getArguments().getString("dateStr");
        this.getData(this.dateStr);
    }

    private void init() {
        this.urls = new Urls();
        this.csp = new Custom_SharedPreferences(getContext());
        calendarDTOS = new ArrayList<>();
        calendarListDTOS = new ArrayList<>();
    }

    private void getData (String date) {
        AndroidNetworking.post(this.urls.mainUrl+this.urls.getUrls.get("getMonthDiary"))
                .addBodyParameter("user_sid",csp.getValue("user_sid",""))
                .addBodyParameter("year_month",date)
                .setPriority(Priority.MEDIUM)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("getMonthDiary",response.toString());
                    JSONArray cal = (JSONArray) response.get("cal");
                    JSONArray list = (JSONArray) response.get("list");
                    Gson gson = new Gson();
                    calendarDTOS = gson.fromJson(cal.toString(), new TypeToken<ArrayList<CalendarDTO>>(){}.getType());
                    calendarListDTOS = gson.fromJson(list.toString(), new TypeToken<ArrayList<CalendarListDTO>>(){}.getType());
                    reloadCalendar();

                } catch (Exception e) {
                    calendarDTOS = new ArrayList<>();
                    calendarListDTOS = new ArrayList<>();
                }
            }

            @Override
            public void onError(ANError anError) {
                calendarDTOS = new ArrayList<>();
                calendarListDTOS = new ArrayList<>();
            }
        });
    }

    public void reloadCalendar() {
        this.calendarAdapter = new DetailCalendarAdapter( this.calendarDTOS, dateStr ,dayArr , getContext() , getLayoutInflater(),gridviewHeight);
        gridView.setAdapter(calendarAdapter);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detail_calendar_fragment , container , false);
        this.gridView = view.findViewById(R.id.calendar);
        this.mensV = view.findViewById(R.id.mensV);

        this.gridView.post(new Runnable() {
            @Override
            public void run() {
                gridviewHeight = (gridView.getHeight()/6);
                calendarAdapter = new DetailCalendarAdapter(calendarDTOS, dateStr ,dayArr , getContext() , getLayoutInflater(),gridviewHeight);
                gridView.setAdapter(calendarAdapter);


            }
        });

        if (this.csp.getValue("sex", "").equals("M") || this.csp.getValue("mens", "").equals("N")) {
            mensV.setVisibility(View.GONE);
        }

//        view.findViewById(R.id.meanBt).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext() , SubTimePicker.class);
//                intent.putExtra("startDateLong",0);
//                intent.putExtra("isMean",true);
//                startActivity(intent);
//            }
//        });



        //tv.setText(this.dayArr.size());

        getContext().sendBroadcast(new Intent("M2COMM_COMPLETED"));

        return view;
    }




}
