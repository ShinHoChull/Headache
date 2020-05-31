package com.m2comm.headache.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.m2comm.headache.Adapter.CalendarAdapter;
import com.m2comm.headache.Adapter.SubTimeCalendarAdapter;
import com.m2comm.headache.Global;
import com.m2comm.headache.SubTimeCalendarFragment;
import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivitySubTimePickerBinding;
import com.m2comm.headache.module.CalendarMonthModule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SubTimePicker extends AppCompatActivity implements View.OnClickListener {

    private final int VIEW_PAGER = 0;
    private final int VIEW_TIME_PICKER = 1;

    ActivitySubTimePickerBinding binding;

    int dateIndex = -1;
    CalendarMonthModule cmm;
    ArrayList<String> dateStrings;
    ArrayList<String> dayStringArr;
    NewPagerAdapter newPagerAdapter;

    //timepicker
    String time = "";

    //startDateLong
    Long startDateLong = 0L;
    Long endDateLong = 0L;

    //자식 그리드 뷰에서 선택된 날짜
    private Date startDate , endDate;

    //endDate 설정하게 하기.
    private boolean isEnd = false;

    private Activity activity;
    private void regObj() {
        this.binding.dateBt.setOnClickListener(this);
        this.binding.timeBt.setOnClickListener(this);
        this.binding.nextBt.setOnClickListener(this);
        this.binding.backBt.setOnClickListener(this);
        this.binding.successBt.setOnClickListener(this);
        this.binding.cancelBt.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_time_picker);
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_sub_time_picker);
        this.binding.setSubTime(this);
        this.init();
        this.regObj();
    }

    private void init() {

        this.cmm = new CalendarMonthModule(this, this);
        this.dateStrings = new ArrayList<>();

        Intent intent = getIntent();
        //시작날짜 가져오기
        this.startDateLong = intent.getLongExtra("startDateLong",0);
        this.isEnd = intent.getBooleanExtra("isEnd",false);
        Log.d("dataaa",this.startDateLong+"____");
        if ( this.startDateLong != 0 ) {
            String reciveStartDate = Global.getTimeToStr(this.startDateLong);
            this.startDate = new Date(Global.getStrToDate(reciveStartDate).getTime());
        }

        if (this.isEnd) {
            this.endDateLong = intent.getLongExtra("endDateLong",0);
            Log.d("endDateLong",this.endDateLong+"____");
            String reciveEndDate = Global.getTimeToStr(this.endDateLong);
            this.endDate = new Date(Global.getStrToDate(reciveEndDate).getTime());
        }

        this.binding.nextBt.setColorFilter(Color.parseColor("#000000"));
        this.binding.backBt.setColorFilter(Color.parseColor("#000000"));

        this.binding.timepicker.setIs24HourView(false);

        this.changeBt(this.binding.dateBtTxt, this.binding.dateBtLine, this.binding.timeBtTxt, this.binding.timeBtLine, this.VIEW_PAGER);
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

        this.binding.timepicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                time =hourOfDay + ":" + minute;
            }
        });
    }

    public void changeDate(int index) {
        this.dateIndex = index;
        String[] cutDateFormat = this.dateStrings.get(index).split("-");
        this.binding.thisMonth.setText(cutDateFormat[0] + "년 " + cutDateFormat[1] + "월");
    }

    private void changeAdapter() {
        this.newPagerAdapter = new NewPagerAdapter(this.startDate, this.endDate ,this, this.dateStrings);
        this.binding.pager.setAdapter(this.newPagerAdapter);
        this.newPagerAdapter.notifyDataSetChanged();

        //인덱스로 이동하게 하기.
        binding.pager.setCurrentItem(dateIndex);
    }

    private void changeBt(TextView onTxt, LinearLayout onLine,
                          TextView offTxt, LinearLayout offLine, int visibleView) {

        onTxt.setTextColor(Color.parseColor("#222222"));
        onLine.setBackgroundColor(Color.parseColor("#222222"));
        offTxt.setTextColor(Color.parseColor("#888888"));
        offLine.setBackgroundColor(Color.parseColor("#EFF2F7"));

        if (visibleView == this.VIEW_PAGER) {
            this.binding.calendarView.setVisibility(View.VISIBLE);
            this.binding.timepicker.setVisibility(View.GONE);
        } else {
            this.binding.calendarView.setVisibility(View.GONE);
            this.binding.timepicker.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.nextBt:
                this.changeAdapter();
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
                this.changeAdapter();
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

            case R.id.dateBt:
                this.changeBt(this.binding.dateBtTxt, this.binding.dateBtLine, this.binding.timeBtTxt, this.binding.timeBtLine, this.VIEW_PAGER);
                break;

            case R.id.timeBt:
                this.changeBt(this.binding.timeBtTxt, this.binding.timeBtLine, this.binding.dateBtTxt, this.binding.dateBtLine, this.VIEW_TIME_PICKER);
                break;

            case R.id.successBt:
                if ( this.startDate == null ) return;
                if (this.time.equals("")) this.time = this.cmm.getTime();
                Intent intent = new Intent();
                if ( this.isEnd ) {
                    intent.putExtra("endDateTime",Global.getStrToDateTime( Global.getTimeToStr(this.endDate.getTime())+" "+this.time).getTime());
                } else {
                    intent.putExtra("startDateTime",Global.getStrToDateTime( Global.getTimeToStr(this.startDate.getTime())+" "+this.time).getTime());
                }

                setResult(RESULT_OK,intent);
                finish();
                break;

            case R.id.cancelBt:
                finish();
                break;
        }
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


    public class NewPagerAdapter extends PagerAdapter {

        private Context context;
        private ArrayList<String> arrayList;
        private SubTimeCalendarAdapter calendarAdapter;
        private Date innerstartDate , innerEndDate;

        public NewPagerAdapter(Date startDate , Date endDate , Context context, ArrayList<String> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
            this.innerEndDate = endDate;
            this.innerstartDate = startDate;
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

            this.calendarAdapter = new SubTimeCalendarAdapter(this.innerstartDate, this.innerEndDate, dayArr, currentlyDateStr, getApplicationContext(), getLayoutInflater(), (binding.pager.getHeight()-10)/6,activity);
            gridView.setAdapter(this.calendarAdapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if ( !isEnd ) {
                        //미래 날짜를 선택하면 Return
                        if ( Global.getStrToDate(currentlyDateStr+"-"+dayArr.get(position)).getTime() > Global.getStrToDate(cmm.getStrRealDate()).getTime()) {
                            return;
                        }
                        startDate = Global.getStrToDate(currentlyDateStr+"-"+dayArr.get(position));
                    } else {
                        //끝날짜가 시작날짜보다 작으면 Return;
                        //끝날짜가래 미래날짜를 선택하면 Return;
                        if ( Global.getStrToDate(currentlyDateStr+"-"+dayArr.get(position)).getTime() <= startDate.getTime() ||
                        Global.getStrToDate(currentlyDateStr+"-"+dayArr.get(position)).getTime() > Global.getStrToDate(cmm.getStrRealDate()).getTime()
                        ) {
                            return;
                        }
                        endDate = Global.getStrToDate(currentlyDateStr+"-"+dayArr.get(position));
                    }
                    calendarAdapter = new SubTimeCalendarAdapter(startDate, endDate, dayArr, currentlyDateStr, getApplicationContext(), getLayoutInflater(), (binding.pager.getHeight()-10)/6,activity);
                    reloadGridView(gridView , calendarAdapter);
                }
            });

            container.addView(view);
            return view;
        }

        private void reloadGridView(GridView gridView , SubTimeCalendarAdapter calendarAdapter) {
            gridView.setAdapter(calendarAdapter);
            calendarAdapter.notifyDataSetChanged();
            //changeAdapter();
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