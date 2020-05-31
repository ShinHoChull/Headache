package com.m2comm.headache;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.m2comm.headache.Adapter.CalendarAdapter;
import com.m2comm.headache.module.Custom_SharedPreferences;
import com.m2comm.headache.module.Urls;
import com.m2comm.headache.sendDTO.CalendarDTO;
import com.m2comm.headache.sendDTO.CalendarListDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

public class CalendarFragment extends Fragment {

    private ArrayList<String> dayArr;
    private String dateStr;
    private Urls urls;
    private Custom_SharedPreferences csp;
    private ArrayList<CalendarDTO> calendarDTOS;
    private ArrayList<CalendarListDTO> calendarListDTOS;
    private GridView gridView;
    private CalendarAdapter calendarAdapter;

    private int gridviewHeight = 0;

    public static CalendarFragment newInstance( String dateStr ,ArrayList<String> dayArr) {

        CalendarFragment fragment = new CalendarFragment();
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
                .addBodyParameter("user_sid","13")
                .addBodyParameter("year_month",date)
                .setPriority(Priority.MEDIUM)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
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

    private void reloadCalendar() {
        this.calendarAdapter = new CalendarAdapter( this.calendarDTOS, dateStr ,dayArr , getContext() , getLayoutInflater(),gridviewHeight);
        gridView.setAdapter(calendarAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.calendar_fragment , container , false);
        this.gridView = view.findViewById(R.id.calendar);
        this.gridView.post(new Runnable() {
            @Override
            public void run() {
                gridviewHeight = gridView.getHeight()/6;
                calendarAdapter = new CalendarAdapter(calendarDTOS, dateStr ,dayArr , getContext() , getLayoutInflater(),gridviewHeight);
                gridView.setAdapter(calendarAdapter);
            }
        });



        //tv.setText(this.dayArr.size());

        return view;
    }




}
