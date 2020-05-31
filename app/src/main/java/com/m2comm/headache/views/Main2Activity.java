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
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m2comm.headache.CalendarFragment;
import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivityMain2Binding;
import com.m2comm.headache.module.CalendarModule;
import com.m2comm.headache.views.BottomActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity implements View.OnTouchListener , View.OnClickListener {

    BottomActivity bottomActivity;

    ContentViewPagerAdapter contentViewPagerAdapter;

    CalendarModule cm;
    ArrayList<String> dateStrings;
    ArrayList<String> dayStringArr;

    int dateIndex = -1;

    ActivityMain2Binding binding;

    private void regObj () {
        this.binding.nextBt.setOnClickListener(this);
        this.binding.backBt.setOnClickListener(this);
        this.binding.bell.setOnClickListener(this);
        this.binding.setting.setOnClickListener(this);
        this.binding.diaryRecord.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        this.binding = DataBindingUtil.setContentView(this,R.layout.activity_main2);
        this.binding.setMain(this);
        this.init();
        this.regObj();
    }

    private void init () {

        this.bottomActivity = new BottomActivity(getLayoutInflater() , R.id.bottom , this , this);

        this.dateStrings = new ArrayList<>();
        this.cm = new CalendarModule(this,this);

        this.binding.pager.setClipToPadding(false);
        this.binding.pager.setPadding((int) (this.getMargin()/1.5), 10 , (int)(this.getMargin()/1.5) , 10);
        this.binding.pager.setPageMargin((int)(this.getMargin()/3.5));

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
    }

    private void getMonth() {

        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);

        String[] cutDateFormat = this.cm.getStrRealDate().split("-");
        this.binding.thisMonth.setText(cutDateFormat[0]+"년 "+cutDateFormat[1]+"월");
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.cm.getDate());

        //3년전 기준
        cal.add(cal.YEAR , -3);
        //앞뒤 3년 전후 = 6 * 12개월 = 72개월
        for ( int i = 0 , j = 72; i < j ; i ++ ) {
            cal.add(cal.MONTH , 1);

            if ( curYearFormat.format(cal.getTime()).equals(cutDateFormat[0]) && curMonthFormat.format(cal.getTime()).equals(cutDateFormat[1]) ) {
                this.dateIndex = i;
            }

            this.dateStrings.add(curYearFormat.format(cal.getTime()) + "-" + curMonthFormat.format(cal.getTime()));
            Log.d("dateList",curYearFormat.format(cal.getTime()) + "-" + curMonthFormat.format(cal.getTime()));
        }
    }

    private void changeAdapter () {
        this.contentViewPagerAdapter = new ContentViewPagerAdapter(getSupportFragmentManager() , this , this.dateStrings );
        this.binding.pager.setAdapter(this.contentViewPagerAdapter);
        this.contentViewPagerAdapter.notifyDataSetChanged();
    }

    private int getMargin() {
        int dpvalue = 54;
        float d = getResources().getDisplayMetrics().density;
        return (int) ( dpvalue * d );
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
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
            Log.d("pager_position",dateStrings.get(position));
            dayStringArr = cm.getCalendar(dateStrings.get(position));
            return CalendarFragment.newInstance(dateStrings.get(position) , dayStringArr);
        }

        @Override
        public int getCount() {
            return this.arrayList.size();
        }
    }
    
    public void changeDate (int index) {
        this.dateIndex = index;
        String[] cutDateFormat = this.dateStrings.get(index).split("-");
        this.binding.thisMonth.setText(cutDateFormat[0]+"년 "+cutDateFormat[1]+"월");
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.nextBt:
                this.dateIndex = this.dateIndex + 1;
                Log.d("dateIndex=",this.dateIndex+"");

                //0보다 작을때는 밀어넣는다?
                if ( this.dateIndex >= this.dateStrings.size() ) {
                    this.dateIndex = this.dateIndex - 1;
                    return;
                }
                this.changeDate(this.dateIndex);
                this.binding.pager.setCurrentItem(this.dateIndex);
                break;

            case R.id.backBt:

                this.dateIndex = this.dateIndex - 1;
                Log.d("dateIndex=",this.dateIndex+"");

                //0보다 작을때는 밀어넣는다?
                if ( this.dateIndex < 0 ) {
                    this.dateIndex = this.dateIndex + 1;
                    return;
                }
                this.changeDate(this.dateIndex);
                this.binding.pager.setCurrentItem(this.dateIndex);
                break;

            case R.id.bell:
                break;

            case R.id.setting:
                intent = new Intent(this , SettingMainActivity.class);
                startActivity(intent);
                break;

            case R.id.diaryRecord:
                intent = new Intent(this , ContentStepActivity.class);
                startActivity(intent);
                break;
        }

    }
}
