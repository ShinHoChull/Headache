package com.m2comm.headache.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.m2comm.headache.Adapter.Step9CalendarAdapter;
import com.m2comm.headache.Adapter.SubTimeCalendarAdapter;
import com.m2comm.headache.DTO.Step9Dates;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivityStep9DatePickerBinding;
import com.m2comm.headache.module.CalendarMonthModule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Step9DatePicker extends AppCompatActivity implements View.OnClickListener {

    ActivityStep9DatePickerBinding binding;
    CalendarMonthModule cmm;
    NewPagerAdapter newPagerAdapter;

    ArrayList<String> dateStrings;
    private ArrayList<Step9Dates> pagerDateList;
    Date date;

    Long startDateLong , endDateLong;
    boolean isStep10 = false;
    int dateIndex = -1;

    private void regObj() {
        this.binding.nextBt.setOnClickListener(this);
        this.binding.backBt.setOnClickListener(this);
        this.binding.successBt.setOnClickListener(this);
        this.binding.cancelBt.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step9_date_picker);

        this.init();
        this.regObj();
    }

    private void init () {
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_step9_date_picker);
        this.binding.setSubTime(this);

        this.cmm = new CalendarMonthModule(this, this);
        this.dateStrings = new ArrayList<>();

        this.binding.nextBt.setColorFilter(Color.parseColor("#000000"));
        this.binding.backBt.setColorFilter(Color.parseColor("#000000"));

        this.getMonth();
        this.binding.pager.post(new Runnable() {
            @Override
            public void run() {
                changeAdapter();
            }
        });

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

        Intent intent = getIntent();
        this.startDateLong = intent.getLongExtra("startDateLong",0L);
        this.endDateLong = intent.getLongExtra("endDateLong",0L);
        this.isStep10 = intent.getBooleanExtra("step10",false);
        Log.d("nowDate",Global.getTimeToStr(this.startDateLong));

        if (this.isStep10) {
            this.binding.txt.setText("영향을 받은 날짜를 선택해 주세요");
        }

    }

    private void changeAdapter() {

        this.newPagerAdapter = new NewPagerAdapter( date ,this, this.dateStrings);
        this.binding.pager.setAdapter(this.newPagerAdapter);
        this.newPagerAdapter.notifyDataSetChanged();

        //오늘날짜 인덱스로 이동하게 하기.
        binding.pager.setCurrentItem(dateIndex);
    }

    public void changeDate(int index) {
        this.dateIndex = index;
        String[] cutDateFormat = this.dateStrings.get(index).split("-");
        this.binding.thisMonth.setText(cutDateFormat[0] + "년 " + cutDateFormat[1] + "월");
    }

    private void getMonth() {

        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);

        String[] cutDateFormat = this.cmm.getStrRealDate().split("-");
        this.binding.thisMonth.setText(cutDateFormat[0] + "년 " + cutDateFormat[1] + "월");
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.cmm.getDate());

        //현재에서 빼는 월.
        int minusDate = 12;
        cal.add(cal.MONTH, -minusDate);

        for (int i = 0, j = minusDate; i < j; i++) {
            //1월 씩 더한다. (현재날짜가 위치하게.)
            cal.add(cal.MONTH, 1);
            if (curYearFormat.format(cal.getTime()).equals(cutDateFormat[0]) && curMonthFormat.format(cal.getTime()).equals(cutDateFormat[1])) {
                this.dateIndex = i;
            }
            this.dateStrings.add(curYearFormat.format(cal.getTime()) + "-" + curMonthFormat.format(cal.getTime()));
            Log.d("dateList", curYearFormat.format(cal.getTime()) + "-" + curMonthFormat.format(cal.getTime()));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.cancelBt:
                setResult(RESULT_CANCELED);
                finish();
                break;

            case R.id.successBt:
                Intent intent = new Intent();
                intent.putExtra("dates",this.pagerDateList);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

    public class NewPagerAdapter extends PagerAdapter {

        private Context context;
        private ArrayList<String> arrayList;
        private Step9CalendarAdapter calendarAdapter;
        private Date innerDate;

        public NewPagerAdapter(Date date, Context context, ArrayList<String> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
            this.innerDate = date;
            pagerDateList = new ArrayList<>();
        }

        @Override
        public int getCount() {
            return this.arrayList.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            View view = null;

            // LayoutInflater를 통해 "/res/layout/page.xml"을 뷰로 생성.
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.calendar_month_fragment, container, false);
            final GridView gridView = view.findViewById(R.id.calendar);

            final String currentlyDateStr = arrayList.get(position);
            final ArrayList<String> dayArr = cmm.getCalendar(arrayList.get(position));

            this.calendarAdapter = new Step9CalendarAdapter(startDateLong , endDateLong , pagerDateList, dayArr, currentlyDateStr, getApplicationContext(), getLayoutInflater(), (binding.pager.getHeight()-10)/6);
            gridView.setAdapter(this.calendarAdapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    innerDate = Global.getStrToDate(currentlyDateStr+"-"+dayArr.get(position));
                    String step1StartDate = Global.getTimeToStr(startDateLong);
                    if ( innerDate.getTime() < Global.getStrToDate(step1StartDate).getTime() ||  innerDate.getTime() > endDateLong) {
                        Log.d("outDateTime",Global.getTimeToStr(innerDate.getTime()) +"_");
                        Log.d("startDateLong",Global.getTimeToStr(startDateLong)+"_");
                        return;
                    }

                    Log.d("currentlyDate",innerDate.getTime()+"__");
                    pagerDateList.add( new Step9Dates(Global.getTimeToStr(innerDate.getTime()),"Y") );
                    calendarAdapter = new Step9CalendarAdapter(startDateLong , endDateLong,pagerDateList, dayArr, currentlyDateStr, getApplicationContext(), getLayoutInflater(), (binding.pager.getHeight()-10)/6);
                    Log.d("clickDate",currentlyDateStr+"-"+dayArr.get(position));
                    reloadGridView(gridView , calendarAdapter);
                }
            });

            container.addView(view);
            return view;
        }

        private void reloadGridView(GridView gridView , Step9CalendarAdapter calendarAdapter) {
            gridView.setAdapter(calendarAdapter);
            calendarAdapter.notifyDataSetChanged();
//            changeAdapter();
        }


        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return (view == (View) object);
        }
    }
}
