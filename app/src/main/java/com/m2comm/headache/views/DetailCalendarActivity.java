package com.m2comm.headache.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.m2comm.headache.Adapter.DetailListViewAdapter;
import com.m2comm.headache.CalendarFragment;
import com.m2comm.headache.DTO.CalendarListValDTO;
import com.m2comm.headache.DTO.DetailCalendarDTO;
import com.m2comm.headache.DetailCalendarFragment;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivityDetailCalendarBinding;
import com.m2comm.headache.module.CalendarModule;
import com.m2comm.headache.module.Custom_SharedPreferences;
import com.m2comm.headache.module.Urls;
import com.m2comm.headache.DTO.CalendarDTO;
import com.m2comm.headache.DTO.CalendarListDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DetailCalendarActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityDetailCalendarBinding binding;
    private DetailListViewAdapter detailListViewAdapter;
    private ArrayList<DetailCalendarDTO> detailDTOArr;
    private BottomActivity bottomActivity;
    private Custom_SharedPreferences csp;

    ArrayList<String> dayStringArr;
    ArrayList<String> dateStrings;
    private CalendarModule cm;
    ContentViewPagerAdapter contentViewPagerAdapter;
    int dateIndex = -1;

    boolean isList = false;

    private ArrayList<CalendarDTO> calendarDTOS;
    private ArrayList<CalendarListDTO> calendarListDTOS;
    private Urls urls;

    private void regObj() {
        this.binding.getListBt.setOnClickListener(this);
        this.binding.nextBt.setOnClickListener(this);
        this.binding.backBt.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_calendar);
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_calendar);
        this.binding.setDetailCalendar(this);
        this.regObj();
        this.init();
    }

    private void init() {

        this.urls = new Urls();
        this.csp = new Custom_SharedPreferences(this);
        this.detailDTOArr = new ArrayList<>();



        this.cm = new CalendarModule(this, this);
        this.dateStrings = new ArrayList<>();

        this.getMonth();
        this.changeAdapter();
        this.binding.pager.setCurrentItem(this.dateIndex);
        this.binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeDate(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        this.binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailCalendarDTO row = detailDTOArr.get(position);
                //Toast.makeText(getApplicationContext(),""+row.getDiary_key(),Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getApplicationContext() , ContentStepActivity.class);
//                intent.putExtra("diary_sid",row.getDiary_key());
                if (row.getDiary_key() == 0) return;
                String desc_sub = "";

                Intent intent = new Intent(getApplicationContext(), DetaiViewActivity.class);
                intent.putExtra("diary_sid", row.getDiary_key());
                intent.putExtra("desc", row.getDes());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("year_month", this.dateStrings.get(this.dateIndex));
        this.getData(this.dateStrings.get(this.dateIndex));
        this.bottomActivity = new BottomActivity(getLayoutInflater(), R.id.bottom, this, this, 2);
    }

    private void listViewChange() {
        Log.d("detailListArraySize", this.detailDTOArr.size() + "_");
        this.detailListViewAdapter = new DetailListViewAdapter(this.detailDTOArr, this, this, getLayoutInflater());
        this.binding.listview.setAdapter(this.detailListViewAdapter);
    }

    private void getData(final String date) {
        Log.d("dateee12", date);

        Log.d("year_month", this.dateStrings.get(this.dateIndex));
        AndroidNetworking.post(this.urls.mainUrl + this.urls.getUrls.get("getMonthDiary"))
                .addBodyParameter("user_sid", csp.getValue("user_sid", ""))
                .addBodyParameter("year_month", date.trim())
                .setPriority(Priority.MEDIUM)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("calendar_data", response.toString());
                    JSONArray cal = (JSONArray) response.get("cal");

                    JSONArray list = (JSONArray) response.get("list");
                    Gson gson = new Gson();
                    calendarDTOS = gson.fromJson(cal.toString(), new TypeToken<ArrayList<CalendarDTO>>() {
                    }.getType());
                    calendarListDTOS = gson.fromJson(list.toString(), new TypeToken<ArrayList<CalendarListDTO>>() {
                    }.getType());

                    Log.d("dateee", date);
                    Log.d("cmcm", cm.getStrRealDate());
                    detailDTOArr = new ArrayList<>();//리스트뷰 초기화
                    for (int i = 0, j = calendarListDTOS.size(); i < j; i++) {
                        CalendarListDTO row = calendarListDTOS.get(i);
                        detailDTOArr.add(new DetailCalendarDTO(0, true, row.getDate_txt(), row.getDate_txt(), 0, ""));
                        for (int k = 0, l = row.getDate_val().size(); k < l; k++) {
                            CalendarListValDTO valRow = row.getDate_val().get(k);
                            String desc_sub = "";
                            if (!valRow.getMedicine_txt().equals("")) {
                                desc_sub = "·" + valRow.getMedicine_txt() + valRow.getMedicine_effect_txt();
                            }

                            detailDTOArr.add(new DetailCalendarDTO(valRow.getDiary_sid(), false, "", "",
                                    valRow.getAche_power(), "·" + valRow.getAche_power_txt() + "\n" + desc_sub));
                            listViewChange();
                        }
                    }


                } catch (Exception e) {
                    Log.d("ERRROR!", e.toString());
                    detailDTOArr = new ArrayList<>();
                    calendarDTOS = new ArrayList<>();
                    calendarListDTOS = new ArrayList<>();
                    listViewChange();
                }
            }

            @Override
            public void onError(ANError anError) {
                detailDTOArr = new ArrayList<>();
                calendarDTOS = new ArrayList<>();
                calendarListDTOS = new ArrayList<>();
            }
        });
    }

    public void changeDate(int index) {
        this.dateIndex = index;
        String[] cutDateFormat = this.dateStrings.get(index).split("-");
        this.binding.thisMonth.setText(cutDateFormat[0] + "년 " + cutDateFormat[1] + "월");
        Log.d("dataaaaaa", "index=" + index);
        getData(this.dateStrings.get(index));
    }

    private void getMonth() {

        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);

        String[] cutDateFormat = this.cm.getStrRealDate().split("-");
        this.binding.thisMonth.setText(cutDateFormat[0] + "년 " + cutDateFormat[1] + "월");
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.cm.getDate());

        //3년전 기준
        cal.add(cal.YEAR, -3);
        //앞뒤 3년 전후 = 6 * 12개월 = 72개월
        for (int i = 0, j = 72; i < j; i++) {
            cal.add(cal.MONTH, 1);

            if (curYearFormat.format(cal.getTime()).equals(cutDateFormat[0]) && curMonthFormat.format(cal.getTime()).equals(cutDateFormat[1])) {
                this.dateIndex = i;
            }

            this.dateStrings.add(curYearFormat.format(cal.getTime()) + "-" + curMonthFormat.format(cal.getTime()));
            Log.d("dateList", curYearFormat.format(cal.getTime()) + "-" + curMonthFormat.format(cal.getTime()));
        }
    }

    private void changeAdapter() {
        this.contentViewPagerAdapter = new ContentViewPagerAdapter(getSupportFragmentManager(), this, this.dateStrings);
        this.binding.pager.setAdapter(this.contentViewPagerAdapter);
        this.contentViewPagerAdapter.notifyDataSetChanged();
    }

    public class ContentViewPagerAdapter extends FragmentStatePagerAdapter {

        private Context context;
        private ArrayList<String> arrayList;

        public ContentViewPagerAdapter(FragmentManager fragmentManager, Context context, ArrayList<String> arrayList) {
            super(fragmentManager);
            this.context = context;
            this.arrayList = arrayList;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("pager_position", dateStrings.get(position));
            dayStringArr = cm.getCalendar(dateStrings.get(position));
            return DetailCalendarFragment.newInstance(dateStrings.get(position), dayStringArr);
        }

        @Override
        public int getCount() {
            return this.arrayList.size();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.nextBt:
                this.dateIndex = this.dateIndex + 1;
                Log.d("dateIndex=", this.dateIndex + "");

                //0보다 작을때는 밀어넣는다?
                if (this.dateIndex >= this.dateStrings.size()) {
                    this.dateIndex = this.dateIndex - 1;
                    return;
                }
                this.changeDate(this.dateIndex);
                this.binding.pager.setCurrentItem(this.dateIndex);
                break;

            case R.id.backBt:

                this.dateIndex = this.dateIndex - 1;
                Log.d("dateIndex=", this.dateIndex + "");

                //0보다 작을때는 밀어넣는다?
                if (this.dateIndex < 0) {
                    this.dateIndex = this.dateIndex + 1;
                    return;
                }
                this.changeDate(this.dateIndex);
                this.binding.pager.setCurrentItem(this.dateIndex);
                break;

            case R.id.getListBt:

                if (this.isList) {
                    this.isList = false;
                    this.binding.listTxt.setText("목록");
                    LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) this.binding.pagerParent.getLayoutParams();
                    params1.weight = 5.9f;
                    this.binding.pagerParent.setLayoutParams(params1);

                    LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) this.binding.listviewParent.getLayoutParams();
                    params2.weight = 2.3f;
                    this.binding.listviewParent.setLayoutParams(params2);

                } else {
                    this.isList = true;
                    this.binding.listTxt.setText("달력");
                    LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) this.binding.pagerParent.getLayoutParams();
                    params1.weight = 0;
                    this.binding.pagerParent.setLayoutParams(params1);

                    LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) this.binding.listviewParent.getLayoutParams();
                    params2.weight = 8.2f;
                    this.binding.listviewParent.setLayoutParams(params2);
                }
                break;
        }

    }
}
