package com.m2comm.headache.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.m2comm.headache.CalendarFragment;
import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivityMain2Binding;
import com.m2comm.headache.module.CalendarModule;
import com.m2comm.headache.module.Urls;
import com.m2comm.headache.views.BottomActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity implements View.OnTouchListener , View.OnClickListener {

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    BottomActivity bottomActivity;
    ContentViewPagerAdapter contentViewPagerAdapter;

    CalendarModule cm;
    ArrayList<String> dateStrings;
    ArrayList<String> dayStringArr;

    int dateIndex = -1;

    ActivityMain2Binding binding;
    Urls urls;
    GpsTracker gpsTracker;

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

        this.bottomActivity = new BottomActivity(getLayoutInflater() , R.id.bottom , this , this,0);
        this.urls = new Urls();
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

    @Override
    protected void onResume() {
        super.onResume();
        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        }else {
            checkRunTimePermission();
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
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
                intent = new Intent(this , NewsActivity.class);
                intent.putExtra("page",this.urls.getUrls.get("notice"));
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                break;

            case R.id.setting:
                intent = new Intent(this , SettingMainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                break;

            case R.id.diaryRecord:
                intent = new Intent(this , ContentStepActivity.class);
                startActivity(intent);
                break;
        }

    }

    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            boolean check_result = true;

            // 모든 퍼미션을 허용했는지 체크합니다.
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {

                //위치 값을 가져올 수 있음
                ;
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(Main2Activity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                }else {

                    Toast.makeText(Main2Activity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(Main2Activity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(Main2Activity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음



        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(Main2Activity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(Main2Activity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(Main2Activity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(Main2Activity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }


}
