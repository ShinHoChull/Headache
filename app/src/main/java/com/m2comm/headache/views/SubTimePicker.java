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
import com.m2comm.headache.module.CalendarModule;
import com.m2comm.headache.module.CalendarMonthModule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class SubTimePicker extends AppCompatActivity implements View.OnClickListener {

    private final int VIEW_PAGER = 0;
    private final int VIEW_TIME_PICKER = 1;

    ActivitySubTimePickerBinding binding;

    int dateIndex = -1;
    CalendarMonthModule cmm;
    private CalendarModule cm;
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
    private boolean isMean = false;
    private boolean isTime = false; //클릭했을때 클릭시간.
    boolean isTimeStep = false;

    private Activity activity;
    private void regObj() {
        this.binding.dateBt.setOnClickListener(this);
        this.binding.timeBt.setOnClickListener(this);
        this.binding.nextBt.setOnClickListener(this);
        this.binding.backBt.setOnClickListener(this);
        this.binding.successBt.setOnClickListener(this);
        this.binding.cancelBt.setOnClickListener(this);
        this.binding.closeBt.setOnClickListener(this);
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
        this.cm = new CalendarModule(this , this);
        this.dateStrings = new ArrayList<>();

        Intent intent = getIntent();
        //시작날짜 가져오기
        this.startDateLong = intent.getLongExtra("startDateLong",0);
        this.isEnd = intent.getBooleanExtra("isEnd",false);
        this.isMean = intent.getBooleanExtra("isMean",false);
        this.isTime = intent.getBooleanExtra("isTime",false);


        if ( this.startDateLong != 0 ) {
            String reciveStartDate = Global.getTimeToStr(this.startDateLong);
            this.startDate = new Date(Global.getStrToDate(reciveStartDate).getTime());

            if ( !this.isEnd ) {
                this.time = Global.inputTimeToStr(this.startDateLong);
                String[] timeCut = this.time.split(":");
                this.binding.timepicker.setCurrentHour(Integer.parseInt(timeCut[0]));
                this.binding.timepicker.setCurrentMinute(Integer.parseInt(timeCut[1]));
            }
        }

        if ( this.isMean ) {
            this.binding.title.setText("월경 시작일을 선택해 주세요.");
            this.binding.dateTimeParentV.setVisibility(View.GONE);
            this.isTimeStep = true;
        }

        if (this.isEnd) {
            if ( this.isMean ) {
                this.binding.title.setText("월경 종료일을 선택해 주세요.");
            } else {
                this.binding.title.setText("두통 종료일을 선택해 주세요.");
            }

            this.endDateLong = intent.getLongExtra("endDateLong",0);
            if ( this.endDateLong != 0 ) {
                String reciveEndDate = Global.getTimeToStr(this.endDateLong);
                this.endDate = new Date(Global.getStrToDate(reciveEndDate).getTime());

                this.time = Global.inputTimeToStr(this.endDateLong);
                String[] timeCut = this.time.split(":");
                this.binding.timepicker.setCurrentHour(Integer.parseInt(timeCut[0]));
                this.binding.timepicker.setCurrentMinute(Integer.parseInt(timeCut[1]));

            } else {
                String reciveEndDate = Global.getTimeToStr(this.startDateLong);
                this.endDate = new Date(Global.getStrToDate(reciveEndDate).getTime());
                this.endDateLong = endDate.getTime();
            }

        }

        this.binding.nextBt.setColorFilter(Color.parseColor("#000000"));
        this.binding.backBt.setColorFilter(Color.parseColor("#000000"));

        this.binding.timepicker.setIs24HourView(false);

        changeBt(binding.dateBtTxt, binding.dateBtLine, binding.timeBtTxt, binding.timeBtLine, VIEW_PAGER);


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

        if ( !this.isMean && !this.isTimeStep ) {
            this.binding.cancelBt.setVisibility(View.GONE);
            this.binding.successBt.setText("시간 입력");
        } else  {
            this.binding.cancelBt.setVisibility(View.GONE);
            this.binding.successBt.setText("확인");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ( isTime ) {
            this.isTimeStep = true;
            this.binding.calendarView.post(new Runnable() {
                @Override
                public void run() {
                    changeBt(binding.timeBtTxt, binding.timeBtLine, binding.dateBtTxt, binding.dateBtLine, VIEW_TIME_PICKER);
                }
            });

        }

        if ( this.startDateLong != 0 ) {
            String dateYearMonth = Global.getTimeToStrYearMonth(this.startDateLong);
            for( int i = 0 , j = this.dateStrings.size() ; i < j ; i++ ) {
                if ( dateYearMonth != null && dateYearMonth.equals(this.dateStrings.get(i)) ) {
                    dateIndex = i;
                }
            }
            this.binding.pager.setCurrentItem(this.dateIndex);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.closeBt:
                finish();
                break;

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
                this.isTimeStep = false;
                this.changeBt(this.binding.dateBtTxt, this.binding.dateBtLine, this.binding.timeBtTxt, this.binding.timeBtLine, this.VIEW_PAGER);
                break;

            case R.id.timeBt:
                this.isTimeStep = true;
                this.changeBt(this.binding.timeBtTxt, this.binding.timeBtLine, this.binding.dateBtTxt, this.binding.dateBtLine, this.VIEW_TIME_PICKER);
                break;

            case R.id.successBt:
                if ( this.startDate == null ) return;

                //날짜입력 후 시간 입력 강제하는 처리
                if ( !this.isTimeStep ) {
                    this.isTimeStep = true;
                    this.changeBt(this.binding.timeBtTxt, this.binding.timeBtLine, this.binding.dateBtTxt, this.binding.dateBtLine, this.VIEW_TIME_PICKER);
                    return;
                }

                if (this.time.equals("")) this.time = this.cmm.getTime();
                Intent intent = new Intent();
                if ( this.isEnd ) {
                    if ( endDate == null ) {
                        intent.putExtra("endDateTime",0);
                    } else {
                        intent.putExtra("endDateTime",Global.getStrToDateTime( Global.getTimeToStr(this.endDate.getTime())+" "+this.time).getTime());
                    }

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

            this.calendarAdapter = new SubTimeCalendarAdapter(isEnd , this.innerstartDate, this.innerEndDate, dayArr, currentlyDateStr, getApplicationContext(), getLayoutInflater(), (binding.pager.getHeight()-10)/6,activity);
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
                        if ( Global.getStrToDate(currentlyDateStr+"-"+dayArr.get(position)).getTime() < startDate.getTime() ||
                        Global.getStrToDate(currentlyDateStr+"-"+dayArr.get(position)).getTime() > Global.getStrToDate(cmm.getStrRealDate()).getTime()
                        ) {
                            return;
                        }
                        endDate = Global.getStrToDate(currentlyDateStr+"-"+dayArr.get(position));
                    }
                    calendarAdapter = new SubTimeCalendarAdapter(isEnd ,startDate, endDate, dayArr, currentlyDateStr, getApplicationContext(), getLayoutInflater(), (binding.pager.getHeight()-10)/6,activity);
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
