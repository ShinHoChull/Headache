package com.m2comm.headache;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.m2comm.headache.module.Custom_SharedPreferences;
import com.m2comm.headache.module.Urls;
import com.m2comm.headache.DTO.CalendarDTO;
import com.m2comm.headache.DTO.CalendarListDTO;
import com.m2comm.headache.views.ContentStepActivity;
import com.m2comm.headache.views.Main2Activity;
import com.m2comm.headache.views.MensActivity;
import com.m2comm.headache.views.MensDetailViewActivity;
import com.m2comm.headache.views.SubTimePicker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CalendarFragment extends Fragment {

    private ArrayList<String> dayArr;
    private String dateStr;
    private Urls urls;
    private Custom_SharedPreferences csp;
    private ArrayList<CalendarDTO> calendarDTOS;
    private ArrayList<CalendarListDTO> calendarListDTOS;
    private GridView gridView;
    private CalendarAdapter calendarAdapter;
    private LinearLayout mensV;

    private int gridviewHeight = 0;

    public static CalendarFragment newInstance(String dateStr, ArrayList<String> dayArr) {

        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString("dateStr", dateStr);//format = > yyyy-mm
        args.putStringArrayList("dateArr", dayArr);
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

    private void getData(String date) {
        Log.d("Calendar=", this.urls.mainUrl + this.urls.getUrls.get("getMonthDiary") + "user_sid=" + csp.getValue("user_sid", "") + "&year_month=" + date);
        AndroidNetworking.post(this.urls.mainUrl + this.urls.getUrls.get("getMonthDiary"))
                .addBodyParameter("user_sid", csp.getValue("user_sid", ""))
                .addBodyParameter("year_month", date)
                .setPriority(Priority.MEDIUM)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("getMonthDiary=", response.toString());
                    Gson gson = new Gson();
                    if (!response.isNull("cal")) {
                        JSONArray cal = (JSONArray) response.get("cal");
                        calendarDTOS = gson.fromJson(cal.toString(), new TypeToken<ArrayList<CalendarDTO>>() {
                        }.getType());
                    } else if (!response.isNull("list")) {
                        JSONArray list = (JSONArray) response.get("list");
                        calendarListDTOS = gson.fromJson(list.toString(), new TypeToken<ArrayList<CalendarListDTO>>() {
                        }.getType());
                    }
                    reloadCalendar();

                } catch (Exception e) {
                    calendarDTOS = new ArrayList<>();
                    calendarListDTOS = new ArrayList<>();
                    Log.d("MainCareldarError2", e.toString());
                }
            }

            @Override
            public void onError(ANError anError) {
//                calendarDTOS = new ArrayList<>();
//                calendarListDTOS = new ArrayList<>();
                Log.d("MainCareldarError1", anError.getErrorBody());
            }
        });
    }

    private void reloadCalendar() {
        this.calendarAdapter = new CalendarAdapter(this.calendarDTOS, dateStr, dayArr, getContext(), getLayoutInflater(), gridviewHeight);
        gridView.setAdapter(calendarAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.calendar_fragment, container, false);
        TextView meanBt = view.findViewById(R.id.mensBt);
        this.mensV = view.findViewById(R.id.mensV);

        if (this.csp.getValue("sex", "").equals("M") || this.csp.getValue("mens", "").equals("N")) {
            meanBt.setVisibility(View.GONE);
            mensV.setVisibility(View.GONE);
        }

        this.gridView = view.findViewById(R.id.calendar);
        this.gridView.post(new Runnable() {
            @Override
            public void run() {
                gridviewHeight = gridView.getHeight() / 6;
                calendarAdapter = new CalendarAdapter(calendarDTOS, dateStr, dayArr, getContext(), getLayoutInflater(), gridviewHeight);
                gridView.setAdapter(calendarAdapter);
            }
        });




        view.findViewById(R.id.mensBt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MensActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

//                Intent intent = new Intent(getContext() , SubTimePicker.class);
//                intent.putExtra("startDateLong",0);
//                intent.putExtra("isMean",true);
//                startActivity(intent);
            }
        });

        view.findViewById(R.id.todayBt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Main2Activity) getContext()).todayBt();
            }
        });

        return view;
    }


}
