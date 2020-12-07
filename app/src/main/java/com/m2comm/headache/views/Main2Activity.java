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
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.m2comm.headache.CalendarFragment;
import com.m2comm.headache.DTO.Step10EtcDTO;
import com.m2comm.headache.DTO.Step10MainDTO;
import com.m2comm.headache.DTO.Step10SaveDTO;
import com.m2comm.headache.DTO.Step11SaveDTO;
import com.m2comm.headache.DTO.Step12SaveDTO;
import com.m2comm.headache.DTO.Step1SaveDTO;
import com.m2comm.headache.DTO.Step2SaveDTO;
import com.m2comm.headache.DTO.Step3SaveDTO;
import com.m2comm.headache.DTO.Step4EtcDTO;
import com.m2comm.headache.DTO.Step4SaveDTO;
import com.m2comm.headache.DTO.Step5EtcDTO;
import com.m2comm.headache.DTO.Step5SaveDTO;
import com.m2comm.headache.DTO.Step6SaveDTO;
import com.m2comm.headache.DTO.Step7EtcDTO;
import com.m2comm.headache.DTO.Step7SaveDTO;
import com.m2comm.headache.DTO.Step8EtcDTO;
import com.m2comm.headache.DTO.Step8SaveDTO;
import com.m2comm.headache.DTO.Step9DTO;
import com.m2comm.headache.DTO.Step9Dates;
import com.m2comm.headache.DTO.Step9MainDTO;
import com.m2comm.headache.DTO.Step9MainEtcDTO;
import com.m2comm.headache.DTO.Step9SaveDTO;
import com.m2comm.headache.DTO.StepParentDTO;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.contentStepView.Step9;
import com.m2comm.headache.databinding.ActivityMain2Binding;
import com.m2comm.headache.module.CalendarModule;
import com.m2comm.headache.module.Custom_SharedPreferences;
import com.m2comm.headache.module.GpsTracker;
import com.m2comm.headache.module.Urls;
import com.m2comm.headache.sendDTO.Send9PixDTO;
import com.m2comm.headache.sendDTO.Step4SendDTO;
import com.m2comm.headache.sendDTO.Step9SendDTO;
import com.m2comm.headache.views.BottomActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private static final int CONTENT_STEP1 = 1010;
    private static final int CONTENT_STEP9 = 9090;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    BottomActivity bottomActivity;

    ContentViewPagerAdapter contentViewPagerAdapter;

    CalendarModule cm;
    ArrayList<String> dateStrings;
    ArrayList<String> dayStringArr;

    int dateIndex = -1;
    int todayIndex = -1;
    private boolean isSaved = false;
    private boolean isPush = false;

    ActivityMain2Binding binding;
    Urls urls;
    private GpsTracker gpsTracker;
    private Custom_SharedPreferences csp;
    int diary_sid = -1;
    Date saveStartDate;

    StepParentDTO stepParentDTO;

    Step1SaveDTO step1SaveDTO;
    Step2SaveDTO step2SaveDTO;
    Step3SaveDTO step3SaveDTO;
    Step4SaveDTO step4SaveDTO;
    Step5SaveDTO step5SaveDTO;
    Step6SaveDTO step6SaveDTO;
    Step7SaveDTO step7SaveDTO;
    Step8SaveDTO step8SaveDTO;
    //Step9SaveDTO step9SaveDTO;
    public ArrayList<Step9MainDTO> step9NewSaveDTO;
    public ArrayList<Step10MainDTO> step10NewSaveDTO;

    Step10SaveDTO step10SaveDTO;
    Step11SaveDTO step11SaveDTO;
    Step12SaveDTO step12SaveDTO;


    private void regObj() {
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
        this.csp = new Custom_SharedPreferences(this);
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main2);
        this.binding.setMain(this);
        this.init();
        this.regObj();
    }

    private void init() {
        this.urls = new Urls();
        this.dateStrings = new ArrayList<>();
        this.cm = new CalendarModule(this, this);

        this.binding.pager.setClipToPadding(false);
        this.binding.pager.setPadding((int) (this.getMargin() / 1.5), 10, (int) (this.getMargin() / 1.5), 10);
        this.binding.pager.setPageMargin((int) (this.getMargin() / 3.5));

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

        this.createNotificationChannel();

        AndroidNetworking.get(this.urls.mainUrl + this.urls.getUrls.get("getNotiCount"))
                .addQueryParameter("code", "notice")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ( response.getInt("rows") > 0 ) {
                                binding.notiCount.setText(String.valueOf(response.getInt("rows")));
                            } else {
                                binding.notiCount.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("error", anError.getErrorDetail());
                    }
                });

        if (csp.getValue("isOff", false) && !this.csp.getValue("notSaveSid", "").equals("")) {

            csp.put("isOff", false);

            //종료하지 않은 일기가 있을때 7일 지나면 자동으로 End날짜를 입력해 Save 해줍니다.
            saveStartDate = Global.getStrToDateTime(csp.getValue("notSaveNowDate", ""));
            Log.d("sDate==", csp.getValue("notSaveNowDate", ""));

            Calendar cal = Calendar.getInstance();
            cal.setTime(this.cm.getDate());
            cal.add(Calendar.DATE, -7);
            Date nowDate = new Date(cal.getTimeInMillis());



            //현재 날짜에서 7일을 빼서 저장된 날짜보다 크면 입력된 시작날짜보다 12시간 크게 저장한다..
            if ( saveStartDate.getTime() < nowDate.getTime() ) {
                this.diary_sid = Integer.parseInt(this.csp.getValue("notSaveSid","-1"));
                this.isPush = true;
                this.getDiary();
            }
            /*
            * 2020.11.06 수정사항
            * 기존에 종료날짜를 입력하지않으면 두통이있는지 Alert을 띄었는데 없앴음.
            * */
//            else {
//                new android.app.AlertDialog.Builder(this).setTitle("안내").setMessage("작성이 완료되지 않은 두통일기가 있습니다.\n현재도 두통이 지속되고 있나요?")
//                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        }).setNegativeButton("아니요", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String sid = csp.getValue("notSaveSid", "-1");
//                        diary_sid = Integer.parseInt(sid);
//                        getDiary();
//                    }
//                }).setCancelable(false).show();
//            }
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = new NotificationChannel(getResources().getString(R.string.app_name), getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(getResources().getString(R.string.app_name));
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 100, 200});
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        } else {
            checkRunTimePermission();
        }
        this.bottomActivity = new BottomActivity(getLayoutInflater(), R.id.bottom, this, this, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case GPS_ENABLE_REQUEST_CODE:

                    //사용자가 GPS 활성 시켰는지 검사
                    if (checkLocationServicesStatus()) {
                        if (checkLocationServicesStatus()) {

                            Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                            checkRunTimePermission();
                            return;
                        }
                    }
                    break;

                case CONTENT_STEP1:
                    this.step1SaveDTO.seteDate(data.getLongExtra("endDateTime", 0));
                    Intent intent = new Intent(this, Step9DatePicker.class);
                    intent.putExtra("startDateLong", this.step1SaveDTO.getSdate());
                    intent.putExtra("endDateLong", this.step1SaveDTO.geteDate());
                    startActivityForResult(intent, CONTENT_STEP9);
                    break;

                case CONTENT_STEP9:
                    ArrayList<Step9Dates> dateArry = (ArrayList<Step9Dates>) data.getSerializableExtra("dates");
                    Log.d("dateArraySize", dateArry.size() + "__");
                    sendData();
                    break;
            }
        }
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
                this.todayIndex = i;
            }

            this.dateStrings.add(curYearFormat.format(cal.getTime()) + "-" + curMonthFormat.format(cal.getTime()));
            Log.d("dateList", curYearFormat.format(cal.getTime()) + "-" + curMonthFormat.format(cal.getTime()));
        }
    }

    public void todayBt() {
        this.binding.pager.setCurrentItem(this.todayIndex);
        this.changeDate(this.todayIndex);
    }

    private void changeAdapter() {
        this.contentViewPagerAdapter = new ContentViewPagerAdapter(getSupportFragmentManager(), this, this.dateStrings);
        this.binding.pager.setAdapter(this.contentViewPagerAdapter);
        this.contentViewPagerAdapter.notifyDataSetChanged();
    }

    private int getMargin() {
        int dpvalue = 54;
        float d = getResources().getDisplayMetrics().density;
        return (int) (dpvalue * d);
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
            Log.d("pager_position", dateStrings.get(position));
            dayStringArr = cm.getCalendar(dateStrings.get(position));
            return CalendarFragment.newInstance(dateStrings.get(position), dayStringArr);
        }

        @Override
        public int getCount() {
            return this.arrayList.size();
        }
    }

    public void changeDate(int index) {
        this.dateIndex = index;
        String[] cutDateFormat = this.dateStrings.get(index).split("-");
        this.binding.thisMonth.setText(cutDateFormat[0] + "년 " + cutDateFormat[1] + "월");
    }

    @Override
    public void onClick(View v) {
        Intent intent;
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

            case R.id.bell:
                intent = new Intent(this, NewsActivity.class);
                intent.putExtra("page", this.urls.getUrls.get("notice"));
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                break;

            case R.id.setting:
                intent = new Intent(this, SettingMainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                break;

            case R.id.diaryRecord:
                intent = new Intent(this, ContentStepActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            boolean check_result = true;

            // 모든 퍼미션을 허용했는지 체크합니다.
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if (check_result) {
                //위치 값을 가져올 수 있음

            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(Main2Activity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();

                } else {
                    Toast.makeText(Main2Activity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission() {

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


    public String getCurrentAddress(double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        }
        Address address = addresses.get(0);
        return address.getAddressLine(0).toString() + "\n";

    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
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

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void getDiary() {

        Log.d("diary_sid123", this.diary_sid + "_");

        AndroidNetworking.post(this.urls.mainUrl + this.urls.getUrls.get("getDiary"))
                .addBodyParameter("user_sid", csp.getValue("user_sid", ""))
                .addBodyParameter("diary_sid", String.valueOf(this.diary_sid))
                .setPriority(Priority.MEDIUM)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("stepContent_son", response.toString());
                try {

                    long edate = response.isNull("edate") ? 0L : response.getLong("edate") * 1000;
                    step1SaveDTO = new Step1SaveDTO(response.getLong("sdate") * 1000, edate, response.getString("address"),
                            response.getString("pressure").equals("") ? 0 : response.getDouble("pressure"), response.getString("humidity").equals("") ? 0 : response.getDouble("humidity")
                            , response.getString("temp").equals("") ? 0 : response.getDouble("temp"),
                            response.getString("weather_icon")
                    );

                    step2SaveDTO = new Step2SaveDTO(response.getInt("ache_power"));
                    step3SaveDTO = new Step3SaveDTO(response.getString("ache_location1"), response.getString("ache_location2"), response.getString("ache_location3"), response.getString("ache_location4"),
                            response.getString("ache_location5"), response.getString("ache_location6"), response.getString("ache_location7"), response.getString("ache_location8"),
                            response.getString("ache_location9"), response.getString("ache_location10"), response.getString("ache_location11"), response.getString("ache_location12"),
                            response.getString("ache_location13"), response.getString("ache_location14"), response.getString("ache_location15"), response.getString("ache_location16"), response.getString("ache_location17"), response.getString("ache_location18"));

                    ArrayList<Step4EtcDTO> step4EtcDTOS = new ArrayList<>();

                    step4EtcDTOS.add(new Step4EtcDTO(R.drawable.step4_type_default1, R.drawable.step4_type_click1, "욱신거림", false, false, response.getString("ache_type1").equals("Y"), 0, response.getString("ache_type1")));
                    step4EtcDTOS.add(new Step4EtcDTO(R.drawable.step4_type_default2, R.drawable.step4_type_click2, "조임", false, false, response.getString("ache_type2").equals("Y"), 0, response.getString("ache_type2")));
                    step4EtcDTOS.add(new Step4EtcDTO(R.drawable.step4_type_default3, R.drawable.step4_type_click3, "터질듯함", false, false, response.getString("ache_type3").equals("Y"), 0, response.getString("ache_type3")));
                    step4EtcDTOS.add(new Step4EtcDTO(R.drawable.step4_type_default4, R.drawable.step4_type_click4, "찌름", false, false, response.getString("ache_type4").equals("Y"), 0, response.getString("ache_type4")));
                    //step4EtcDTOS.add(new Step4EtcDTO(R.drawable.step4_type_default5,R.drawable.step4_type_click5,"따끔따끔",false,false, response.getString("ache_type5").equals("Y"),0,response.getString("ache_type5")));
                    if (!response.isNull("ache_type_etc")) {
                        JSONArray list4 = (JSONArray) response.get("ache_type_etc");
                        for (int i = 0, j = list4.length(); i < j; i++) {
                            JSONObject obj = (JSONObject) list4.get(i);
                            step4EtcDTOS.add(new Step4EtcDTO(R.drawable.step_type_etc_add, R.drawable.step_type_etc_add, obj.getString("content"), true, false, obj.getString("val").equals("Y"), obj.getInt("key"), obj.getString("val")));
                        }
                    }
                    step4EtcDTOS.add(new Step4EtcDTO(R.drawable.step_type_etc, R.drawable.step_type_etc, "기타", false, true, false, 0, "N"));

                    step4SaveDTO = new Step4SaveDTO(response.getString("ache_type1"), response.getString("ache_type2"), response.getString("ache_type3"), response.getString("ache_type4"), "N", step4EtcDTOS);


                    ArrayList<Step5EtcDTO> step5EtcDTOS = new ArrayList<>();
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default1, R.drawable.step5_type_click1, "아플 것 같은\n느낌", false, false, response.getString("ache_realize1").equals("Y"), 0, response.getString("ache_realize1")));
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default2, R.drawable.step5_type_click2, "뒷목통증\n뻐근함/당김", false, false, response.getString("ache_realize1").equals("Y"), 0, response.getString("ache_realize2")));
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default3, R.drawable.step5_type_click3, "하품", false, false, response.getString("ache_realize3").equals("Y"), 0, response.getString("ache_realize3")));
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default4, R.drawable.step5_type_click4, "피로", false, false, response.getString("ache_realize4").equals("Y"), 0, response.getString("ache_realize4")));
                    //step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default5, R.drawable.step5_type_click5, "집중력저하", false, false, response.getString("ache_realize5").equals("Y"), 0, response.getString("ache_realize5")));
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default6, R.drawable.step5_type_click6, "기분변화", false, false, response.getString("ache_realize5").equals("Y"), 0, response.getString("ache_realize5")));
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default7, R.drawable.step5_type_click7, "식욕변화", false, false, response.getString("ache_realize6").equals("Y"), 0, response.getString("ache_realize6")));
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step5_type_default8, R.drawable.step5_type_click8, "빛/소리/\n냄새에 과민", false, false, response.getString("ache_realize7").equals("Y"), 0, response.getString("ache_realize7")));

                    if (!response.isNull("ache_realize_etc")) {
                        JSONArray list5 = (JSONArray) response.get("ache_realize_etc");
                        for (int i = 0, j = list5.length(); i < j; i++) {
                            JSONObject obj = (JSONObject) list5.get(i);
                            step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step_type_etc_add, R.drawable.step_type_etc_add, obj.getString("content"), true, false, obj.getString("val").equals("Y"), obj.getInt("key"), obj.getString("val")));
                        }
                    }
                    step5EtcDTOS.add(new Step5EtcDTO(R.drawable.step_type_etc, R.drawable.step_type_etc, "기타", false, true, false, 0, "N"));

                    step5SaveDTO = new Step5SaveDTO(response.getString("ache_realize_yn"), response.isNull("ache_realize_hour") ? 0 : response.getInt("ache_realize_hour"), response.isNull("ache_realize_minute") ? 0 : response.getInt("ache_realize_minute"),
                            response.getString("ache_realize1"), response.getString("ache_realize2"), response.getString("ache_realize3"), response.getString("ache_realize4"),
                            response.getString("ache_realize5"), response.getString("ache_realize6"), response.getString("ache_realize7"), "", step5EtcDTOS);


                    step6SaveDTO = new Step6SaveDTO(response.getString("ache_sign_yn"), response.getString("ache_sign1"), response.getString("ache_sign2"),
                            response.getString("ache_sign3"), response.getString("ache_sign4"), response.getString("ache_sign5"),
                            response.getString("ache_sign6"), response.getString("ache_sign7"));


                    ArrayList<Step7EtcDTO> step7EtcDTOS = new ArrayList<>();
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default1, R.drawable.step7_type_click1, "소화가 안됨", false, false, response.getString("ache_with1").equals("Y"), 0, response.getString("ache_with1")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default2, R.drawable.step7_type_click2, "울렁거림", false, false, response.getString("ache_with2").equals("Y"), 0, response.getString("ache_with2")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default3, R.drawable.step7_type_click3, "구토", false, false, response.getString("ache_with3").equals("Y"), 0, response.getString("ache_with3")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default4, R.drawable.step7_type_click4, "어지럼증", false, false, response.getString("ache_with4").equals("Y"), 0, response.getString("ache_with4")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default5, R.drawable.step7_type_click5, "움직임에\n의해 악화", false, false, response.getString("ache_with5").equals("Y"), 0, response.getString("ache_with5")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default6, R.drawable.step7_type_click6, "빛에 예민", false, false, response.getString("ache_with6").equals("Y"), 0, response.getString("ache_with6")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default7, R.drawable.step7_type_click7, "소리에 예민", false, false, response.getString("ache_with7").equals("Y"), 0, response.getString("ache_with7")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default8, R.drawable.step7_type_click8, "냄새에 예민", false, false, response.getString("ache_with8").equals("Y"), 0, response.getString("ache_with8")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default9, R.drawable.step7_type_click9, "뒷목통증/\n뻐근함/당김", false, false, response.getString("ache_with9").equals("Y"), 0, response.getString("ache_with9")));
                    //step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default10, R.drawable.step7_type_click10, "어깨통증", false, false, response.getString("ache_with9").equals("Y"), 0, response.getString("ache_with9")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default11, R.drawable.step7_type_click11, "눈물/눈충혈", false, false, response.getString("ache_with10").equals("Y"), 0, response.getString("ache_with10")));
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step7_type_default12, R.drawable.step7_type_click12, "콧물/코막힘", false, false, response.getString("ache_with11").equals("Y"), 0, response.getString("ache_with11")));

                    if (!response.isNull("ache_with_etc")) {
                        JSONArray list7 = (JSONArray) response.get("ache_with_etc");
                        for (int i = 0, j = list7.length(); i < j; i++) {
                            JSONObject obj = (JSONObject) list7.get(i);
                            step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step_type_etc_add, R.drawable.step_type_etc_add, obj.getString("content"), true, false, obj.getString("val").equals("Y"), obj.getInt("key"), obj.getString("val")));
                        }
                    }
                    step7EtcDTOS.add(new Step7EtcDTO(R.drawable.step_type_etc, R.drawable.step_type_etc, "기타", false, true, false, 0, "N"));

                    step7SaveDTO = new Step7SaveDTO(response.getString("ache_with1"), response.getString("ache_with2"), response.getString("ache_with3"),
                            response.getString("ache_with4"), response.getString("ache_with5"), response.getString("ache_with6"), response.getString("ache_with7"),
                            response.getString("ache_with8"), response.getString("ache_with9"), response.getString("ache_with10"), response.getString("ache_with11"),
                            step7EtcDTOS);


                    ArrayList<Step8EtcDTO> step8EtcDTOS = new ArrayList<>();
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default1, R.drawable.step8_type_click1, "스트레스", false, false, response.getString("ache_factor1").equals("Y"), 0, response.getString("ache_factor1")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default2, R.drawable.step8_type_click2, "피로", false, false, response.getString("ache_factor2").equals("Y"), 0, response.getString("ache_factor2")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default3, R.drawable.step8_type_click3, "수면부족", false, false, response.getString("ache_factor3").equals("Y"), 0, response.getString("ache_factor3")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default4, R.drawable.step8_type_click4, "낮잠/늦잠", false, false, response.getString("ache_factor4").equals("Y"), 0, response.getString("ache_factor4")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default5, R.drawable.step8_type_click5, "주말", false, false, response.getString("ache_factor5").equals("Y"), 0, response.getString("ache_factor5")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default6, R.drawable.step8_type_click6, "굶음", false, false, response.getString("ache_factor6").equals("Y"), 0, response.getString("ache_factor6")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default8, R.drawable.step8_type_click8, "체함", false, false, response.getString("ache_factor7").equals("Y"), 0, response.getString("ache_factor7")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default7, R.drawable.step8_type_click7, "과식", false, false, response.getString("ache_factor8").equals("Y"), 0, response.getString("ache_factor8")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default9, R.drawable.step8_type_click9, "빛", false, false, response.getString("ache_factor9").equals("Y"), 0, response.getString("ache_factor9")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default10, R.drawable.step8_type_click10, "소리", false, false, response.getString("ache_factor10").equals("Y"), 0, response.getString("ache_factor10")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default11, R.drawable.step8_type_click11, "냄새", false, false, response.getString("ache_factor11").equals("Y"), 0, response.getString("ache_factor11")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default12, R.drawable.step8_type_click12, "감기", false, false, response.getString("ache_factor12").equals("Y"), 0, response.getString("ache_factor12")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default13, R.drawable.step8_type_click13, "운동", false, false, response.getString("ache_factor13").equals("Y"), 0, response.getString("ache_factor13")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default14, R.drawable.step8_type_click14, "술", false, false, response.getString("ache_factor14").equals("Y"), 0, response.getString("ache_factor14")));
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step8_type_default15, R.drawable.step8_type_click15, "월경", false, false, response.getString("ache_factor15").equals("Y"), 0, response.getString("ache_factor15")));

                    if (!response.isNull("ache_factor_etc")) {
                        JSONArray list8 = (JSONArray) response.get("ache_factor_etc");
                        for (int i = 0, j = list8.length(); i < j; i++) {
                            JSONObject obj = (JSONObject) list8.get(i);
                            step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step_type_etc_add, R.drawable.step_type_etc_add, obj.getString("content"), true, false, obj.getString("val").equals("Y"), obj.getInt("key"), obj.getString("val")));
                        }
                    }
                    step8EtcDTOS.add(new Step8EtcDTO(R.drawable.step_type_etc, R.drawable.step_type_etc, "기타", false, true, false, 0, "N"));


                    step8SaveDTO = new Step8SaveDTO(response.getString("ache_factor_yn"), response.getString("ache_factor1"), response.getString("ache_factor2"), response.getString("ache_factor3"),
                            response.getString("ache_factor4"), response.getString("ache_factor5"), response.getString("ache_factor6"), response.getString("ache_factor7"),
                            response.getString("ache_factor8"), response.getString("ache_factor9"), response.getString("ache_factor10"), response.getString("ache_factor11"),
                            response.getString("ache_factor12"), response.getString("ache_factor13"),
                            response.getString("ache_factor14"), response.getString("ache_factor15"), step8EtcDTOS);


                    //step10
                    ArrayList<Step10EtcDTO> step10EtcDTOS = new ArrayList<Step10EtcDTO>();
                    step10EtcDTOS.add(new Step10EtcDTO(R.drawable.step10_type_default1, R.drawable.step10_type_click1, "결근/결석", false, false, response.getString("ache_effect1").equals("Y"), 0, response.getString("ache_effect1")));
                    step10EtcDTOS.add(new Step10EtcDTO(R.drawable.step10_type_default2, R.drawable.step10_type_click2, "업무/학습\n능률저하", false, false, response.getString("ache_effect2").equals("Y"), 0, response.getString("ache_effect2")));
                    step10EtcDTOS.add(new Step10EtcDTO(R.drawable.step10_type_default3, R.drawable.step10_type_click3, "가사활동\n못함", false, false, response.getString("ache_effect3").equals("Y"), 0, response.getString("ache_effect3")));
                    step10EtcDTOS.add(new Step10EtcDTO(R.drawable.step10_type_default4, R.drawable.step10_type_click4, "가사활동\n능률 저하", false, false, response.getString("ache_effect4").equals("Y"), 0, response.getString("ache_effect4")));
                    step10EtcDTOS.add(new Step10EtcDTO(R.drawable.step10_type_default5, R.drawable.step10_type_click5, "여가활동\n불참", false, false, response.getString("ache_effect5").equals("Y"), 0, response.getString("ache_effect5")));
                    step10EtcDTOS.add(new Step10EtcDTO(R.drawable.step_type_etc, R.drawable.step_type_etc, "기타", false, true, false, 0, "N"));

                    if (!response.isNull("ache_effect_etc")) {
                        JSONArray list10 = (JSONArray) response.get("ache_effect_etc");
                        for (int i = 0, j = list10.length(); i < j; i++) {
                            JSONObject obj = (JSONObject) list10.get(i);
                            step10EtcDTOS.add(new Step10EtcDTO(R.drawable.step4_type_default1, R.drawable.step4_type_click1, obj.getString("content"), true, false, obj.getString("val").equals("Y"), obj.getInt("key"), obj.getString("val")));
                        }
                    }

                    ArrayList<Step9Dates> step10Dates = new ArrayList<Step9Dates>();
                    if (!response.isNull("ache_effect_date_val")) {
                        JSONArray list10date = (JSONArray) response.get("ache_effect_date_val");
                        for (int i = 0, j = list10date.length(); i < j; i++) {
                            JSONObject obj = (JSONObject) list10date.get(i);
                            step10Dates.add(new Step9Dates(obj.getString("date"), obj.getString("val")));
                        }
                    }

                    step10SaveDTO = new Step10SaveDTO(response.getString("ache_effect1"), response.getString("ache_effect2"), response.getString("ache_effect3"),
                            response.getString("ache_effect4"), response.getString("ache_effect5"), step10Dates, step10EtcDTOS);

                    //step11
                    Long stime = response.isNull("mens_sdate") ? 0L : response.getLong("mens_sdate") * 1000;
                    Long etime = response.isNull("mens_edate") ? 0L : response.getLong("mens_edate") * 1000;

                    step11SaveDTO = new Step11SaveDTO(stime, etime);
                    step12SaveDTO = new Step12SaveDTO(response.getString("memo"));

                    //7일 지난데이터는 따로 작업을 합니다.
                    if (isPush) {
                        Log.d("saveStartDate=", saveStartDate.getTime() + "_");
                        Date startDate = new Date(saveStartDate.getTime());

                        Calendar cal = Calendar.getInstance();
                        cal.setTime(startDate);
                        cal.add(Calendar.HOUR_OF_DAY, 12);

                        Date endDate = new Date(cal.getTimeInMillis());
                        step1SaveDTO.seteDate(endDate.getTime());
                        sendData();

                    } else {
                        Intent intent = new Intent(getApplicationContext(), SubTimePicker.class);
                        intent.putExtra("isEnd", true);
                        intent.putExtra("startDateLong", step1SaveDTO.getSdate());
                        intent.putExtra("endDateLong", step1SaveDTO.geteDate());
                        startActivityForResult(intent, CONTENT_STEP1);
                    }

                } catch (JSONException e) {
                    Log.d("Exceptionee", e.toString());
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.d("stepContent Json Error", anError.toString());
            }
        });
    }


    public void sendData() {

        if (this.isSaved) return;
        this.isSaved = true;

        if (this.step1SaveDTO == null) {
            Log.d("step1Numm=", "nullOK");
            this.step1SaveDTO = new Step1SaveDTO(0L, 0L,
                    "", 0L, 0L, 0L, "");
        }
        if (step2SaveDTO == null) {
            step2SaveDTO = new Step2SaveDTO(1);
        }
        if (step3SaveDTO == null) {
            step3SaveDTO = new Step3SaveDTO(
                    "N", "N", "N", "N",
                    "N", "N", "N", "N",
                    "N", "N", "N", "N",
                    "N", "N", "N", "N",
                    "N", "N");
        }
        if (this.step4SaveDTO == null) {
            this.step4SaveDTO = new Step4SaveDTO("N", "N", "N", "N", "N", new ArrayList<Step4EtcDTO>());
            this.step4SaveDTO.getArrayList().add(new Step4EtcDTO(R.drawable.step4_type_default1, R.drawable.step4_type_click1, "욱신거림", false, false, false, 0, "N"));
            this.step4SaveDTO.getArrayList().add(new Step4EtcDTO(R.drawable.step4_type_default2, R.drawable.step4_type_click2, "조임", false, false, false, 0, "N"));
            this.step4SaveDTO.getArrayList().add(new Step4EtcDTO(R.drawable.step4_type_default3, R.drawable.step4_type_click3, "터질듯함", false, false, false, 0, "N"));
            this.step4SaveDTO.getArrayList().add(new Step4EtcDTO(R.drawable.step4_type_default4, R.drawable.step4_type_click4, "찌름", false, false, false, 0, "N"));
            this.step4SaveDTO.getArrayList().add(new Step4EtcDTO(R.drawable.step4_type_default5, R.drawable.step4_type_click5, "따끔따끔", false, false, false, 0, "N"));
            this.step4SaveDTO.getArrayList().add(new Step4EtcDTO(R.drawable.step_type_etc, R.drawable.step_type_etc, "기타", false, true, false, 0, "N"));
        }
        if (this.step5SaveDTO == null) {
            this.step5SaveDTO = new Step5SaveDTO("", 0, 0,
                    "N", "N", "N", "N",
                    "N", "N", "N", "N", new ArrayList<Step5EtcDTO>());
            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default1, R.drawable.step5_type_click1, "아플 것 같은\n느낌", false, false, false, 0, "N"));
            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default2, R.drawable.step5_type_click2, "뒷목통증\n뻐근함/당김", false, false, false, 0, "N"));
            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default3, R.drawable.step5_type_click3, "하품", false, false, false, 0, "N"));
            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default4, R.drawable.step5_type_click4, "피로", false, false, false, 0, "N"));
            //this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default5, R.drawable.step5_type_click5, "집중력저하", false, false, false, 0, "N"));
            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default6, R.drawable.step5_type_click6, "기분변화", false, false, false, 0, "N"));
            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default7, R.drawable.step5_type_click7, "식욕변화", false, false, false, 0, "N"));
            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step5_type_default8, R.drawable.step5_type_click8, "빛/소리/\n냄새에 과민", false, false, false, 0, "N"));
            this.step5SaveDTO.getArrayList().add(new Step5EtcDTO(R.drawable.step_type_etc, R.drawable.step_type_etc, "기타", false, true, false, 0, "N"));
        }

        if (this.step6SaveDTO == null) {
            this.step6SaveDTO = new Step6SaveDTO("", "N", "N",
                    "N", "N", "N", "N", "N");
        }

        if (this.step7SaveDTO == null) {

            this.step7SaveDTO = new Step7SaveDTO("N", "N", "N", "N",
                    "N", "N", "N", "N",
                    "N", "N", "N", new ArrayList<Step7EtcDTO>());
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default1, R.drawable.step7_type_click1, "소화가 안됨", false, false, false, 0, "N"));
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default2, R.drawable.step7_type_click2, "울렁거림", false, false, false, 0, "N"));
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default3, R.drawable.step7_type_click3, "구토", false, false, false, 0, "N"));
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default4, R.drawable.step7_type_click4, "어지럼증", false, false, false, 0, "N"));
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default5, R.drawable.step7_type_click5, "움직임에\n의해 악화", false, false, false, 0, "N"));
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default6, R.drawable.step7_type_click6, "빛에 예민", false, false, false, 0, "N"));
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default7, R.drawable.step7_type_click7, "소리에 예민", false, false, false, 0, "N"));
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default8, R.drawable.step7_type_click8, "냄새에 예민", false, false, false, 0, "N"));
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default9, R.drawable.step7_type_click9, "뒷목통증/\n뻐근함/당김", false, false, false, 0, "N"));
            //this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default10, R.drawable.step7_type_click10, "어깨통증", false, false, false, 0, "N"));
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default11, R.drawable.step7_type_click11, "눈물/눈충혈", false, false, false, 0, "N"));
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step7_type_default12, R.drawable.step7_type_click12, "콧물/코막힘", false, false, false, 0, "N"));
            this.step7SaveDTO.getStep7EtcDTOS().add(new Step7EtcDTO(R.drawable.step_type_etc, R.drawable.step_type_etc, "기타", false, true, false, 0, "N"));
        }

        if (this.step8SaveDTO == null) {
            this.step8SaveDTO = new Step8SaveDTO("", "N", "N", "N", "N",
                    "N", "N", "N", "N",
                    "N", "N", "N", "N",
                    "N", "N", "N", new ArrayList<Step8EtcDTO>());

            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default1, R.drawable.step8_type_click1, "스트레스", false, false, false, 0, "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default2, R.drawable.step8_type_click2, "피로", false, false, false, 0, "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default3, R.drawable.step8_type_click3, "수면부족", false, false, false, 0, "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default4, R.drawable.step8_type_click4, "낮잠/늦잠", false, false, false, 0, "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default5, R.drawable.step8_type_click5, "주말", false, false, false, 0, "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default6, R.drawable.step8_type_click6, "굶음", false, false, false, 0, "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default7, R.drawable.step8_type_click7, "과식", false, false, false, 0, "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default8, R.drawable.step8_type_click8, "체함", false, false, false, 0, "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default9, R.drawable.step8_type_click9, "빛", false, false, false, 0, "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default10, R.drawable.step8_type_click10, "소리", false, false, false, 0, "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default11, R.drawable.step8_type_click11, "냄새", false, false, false, 0, "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default12, R.drawable.step8_type_click12, "감기", false, false, false, 0, "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default13, R.drawable.step8_type_click13, "운동", false, false, false, 0, "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default14, R.drawable.step8_type_click14, "술", false, false, false, 0, "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step8_type_default15, R.drawable.step8_type_click15, "월경", false, false, false, 0, "N"));
            this.step8SaveDTO.getArrayList().add(new Step8EtcDTO(R.drawable.step_type_etc, R.drawable.step_type_etc, "기타", false, true, false, 0, "N"));

        }

        if (this.step9NewSaveDTO == null) {
            this.step9NewSaveDTO = new ArrayList<>();
            ArrayList<String> formatDates = Global.getFormatDates(this.step1SaveDTO.getSdate() , this.step1SaveDTO.geteDate());
            for ( String date : formatDates ) {
                this.step9NewSaveDTO.add(new Step9MainDTO(date,"0","N","N","N","N",
                        "N","N","N","N","N","N",new ArrayList<Step9MainEtcDTO>()));
            }
        }

        if ( this.step10NewSaveDTO == null  ) {
            this.step10NewSaveDTO = new ArrayList<>();
            ArrayList<String> formatDates = Global.getFormatDates(this.step1SaveDTO.getSdate() , this.step1SaveDTO.geteDate());
            for ( String date : formatDates ) {
                this.step10NewSaveDTO.add(new Step10MainDTO(date, "N" , "N" ,
                        "N", "N" , "N"));
            }
        }

        if (this.step11SaveDTO == null) {
            this.step11SaveDTO = new Step11SaveDTO(0L, 0L);
        }

        if (this.step12SaveDTO == null) {
            this.step12SaveDTO = new Step12SaveDTO("");
        }

        ArrayList<Step4SendDTO> step4Arraylist = new ArrayList<Step4SendDTO>();
        for (int i = 0, j = this.step4SaveDTO.getArrayList().size(); i < j; i++) {
            Step4EtcDTO row = this.step4SaveDTO.getArrayList().get(i);
            if (row.getEtc()) {
                Log.d("zzzzz", row.getContent());
                step4Arraylist.add(new Step4SendDTO(row.getKey(), row.getContent(), row.getVal()));
            }
        }

        ArrayList<Step4SendDTO> step5Arraylist = new ArrayList<Step4SendDTO>();
        for (int i = 0, j = this.step5SaveDTO.getArrayList().size(); i < j; i++) {
            Step5EtcDTO row = this.step5SaveDTO.getArrayList().get(i);
            if (row.getEtc())
                step5Arraylist.add(new Step4SendDTO(row.getKey(), row.getContent(), row.getVal()));
        }

        ArrayList<Step4SendDTO> step7Arraylist = new ArrayList<Step4SendDTO>();
        for (int i = 0, j = this.step7SaveDTO.getStep7EtcDTOS().size(); i < j; i++) {
            Step7EtcDTO row = this.step7SaveDTO.getStep7EtcDTOS().get(i);
            if (row.getEtc())
                step7Arraylist.add(new Step4SendDTO(row.getKey(), row.getContent(), row.getVal()));
        }

        ArrayList<Step4SendDTO> step8Arraylist = new ArrayList<Step4SendDTO>();
        for (int i = 0, j = this.step8SaveDTO.getArrayList().size(); i < j; i++) {
            Step8EtcDTO row = this.step8SaveDTO.getArrayList().get(i);
            if (row.getEtc())
                step8Arraylist.add(new Step4SendDTO(row.getKey(), row.getContent(), row.getVal()));
        }


        //step11월경
        //String meanStartDate = this.step11SaveDTO.getMens_sdate() == 0L ? null :  Global.getTimeToStr(this.step11SaveDTO.getMens_sdate());
        //String meanEndDate = this.step11SaveDTO.getMens_edate() == 0L ? null :  Global.getTimeToStr(this.step11SaveDTO.getMens_edate());


        Log.d("mean_sdate", this.step11SaveDTO.getMens_sdate() + "_");
        Log.d("mean_edate", this.step11SaveDTO.getMens_edate() + "_");

        String effect = null;
//        if (step9SaveDTO.getAche_medicine_effect() > 10) effect = null;
//        else effect = String.valueOf(step9SaveDTO.getAche_medicine_effect());

        String id = this.diary_sid == -1 ? null : String.valueOf(this.diary_sid);

        Log.d("idd=", this.diary_sid + "__");
        Log.d("eDate==", step1SaveDTO.geteDate() + "_");

        this.stepParentDTO = new StepParentDTO(
                csp.getValue("user_sid", ""), this.step1SaveDTO.getSdate() / 1000,
                this.step1SaveDTO.geteDate() / 1000, this.step1SaveDTO.getAddress(), this.step1SaveDTO.getPressure(), this.step1SaveDTO.getHumidity(),
                this.step1SaveDTO.getTemp(), this.step1SaveDTO.getWeather_icon(),
                this.step2SaveDTO.getAche_power(),
                this.step3SaveDTO.getAche_location1(), this.step3SaveDTO.getAche_location2(), this.step3SaveDTO.getAche_location3(), this.step3SaveDTO.getAche_location4(), this.step3SaveDTO.getAche_location5(), this.step3SaveDTO.getAche_location6(), this.step3SaveDTO.getAche_location7(), this.step3SaveDTO.getAche_location8(), this.step3SaveDTO.getAche_location9(), this.step3SaveDTO.getAche_location10(), this.step3SaveDTO.getAche_location11(), this.step3SaveDTO.getAche_location12(), this.step3SaveDTO.getAche_location13(), this.step3SaveDTO.getAche_location14(), this.step3SaveDTO.getAche_location15(), this.step3SaveDTO.getAche_location16(), this.step3SaveDTO.getAche_location17(), this.step3SaveDTO.getAche_location18(),
                this.step4SaveDTO.getAche_type1(), this.step4SaveDTO.getAche_type2(), this.step4SaveDTO.getAche_type3(), this.step4SaveDTO.getAche_type4(), this.step4SaveDTO.getAche_type5(), step4Arraylist,
                this.step5SaveDTO.getAche_realize_yn(), this.step5SaveDTO.getAche_realize_hour(), this.step5SaveDTO.getAche_realize_minute(), this.step5SaveDTO.getAche_realize1(), this.step5SaveDTO.getAche_realize2(), this.step5SaveDTO.getAche_realize3(), this.step5SaveDTO.getAche_realize4(), this.step5SaveDTO.getAche_realize5(), this.step5SaveDTO.getAche_realize6(), this.step5SaveDTO.getAche_realize7(), this.step5SaveDTO.getAche_realize8(), step5Arraylist,
                this.step6SaveDTO.getAche_sign_yn(), this.step6SaveDTO.getAche_sign1(), this.step6SaveDTO.getAche_sign2(), this.step6SaveDTO.getAche_sign3(), this.step6SaveDTO.getAche_sign4(), this.step6SaveDTO.getAche_sign5(), this.step6SaveDTO.getAche_sign6(), this.step6SaveDTO.getAche_sign7(),
                this.step7SaveDTO.getAche_with1(), this.step7SaveDTO.getAche_with2(), this.step7SaveDTO.getAche_with3(), this.step7SaveDTO.getAche_with4(), this.step7SaveDTO.getAche_with5(), this.step7SaveDTO.getAche_with6(), this.step7SaveDTO.getAche_with7(), this.step7SaveDTO.getAche_with8(), this.step7SaveDTO.getAche_with9(), this.step7SaveDTO.getAche_with10(), this.step7SaveDTO.getAche_with11(), step7Arraylist,
                this.step8SaveDTO.getAche_factor_yn(), this.step8SaveDTO.getAche_factor1(), this.step8SaveDTO.getAche_factor2(), this.step8SaveDTO.getAche_factor3(), this.step8SaveDTO.getAche_factor4(), this.step8SaveDTO.getAche_factor5(), this.step8SaveDTO.getAche_factor6(), this.step8SaveDTO.getAche_factor7(), this.step8SaveDTO.getAche_factor8(), this.step8SaveDTO.getAche_factor9(), this.step8SaveDTO.getAche_factor10(), this.step8SaveDTO.getAche_factor11(), this.step8SaveDTO.getAche_factor12(), this.step8SaveDTO.getAche_factor13(), this.step8SaveDTO.getAche_factor14(), this.step8SaveDTO.getAche_factor15(), step8Arraylist,
                this.step9NewSaveDTO,
                this.step10NewSaveDTO,
                this.step11SaveDTO.getMens_sdate() / 1000, this.step11SaveDTO.getMens_edate() / 1000,
                this.step12SaveDTO.getDesc(),
                id
        );

        Gson gson = new Gson();
        String data = gson.toJson(this.stepParentDTO);
        Log.d("sendData", data);

        if (data.equals("")) return;

        if (step1SaveDTO.getSdate() < Global.getStrToDate(this.cm.getStrRealDate()).getTime() && this.step1SaveDTO.geteDate() == 0) {
            new android.app.AlertDialog.Builder(this).setTitle("안내").setMessage("과거 두통일기 입력시에는 종료일을 입력해 주세요.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setCancelable(false).show();
            this.isSaved = false;
            return;
        }

        AndroidNetworking.post(this.urls.mainUrl + this.urls.getUrls.get("setDiary"))
                .addBodyParameter("data", data)
                .addBodyParameter("user_sid", csp.getValue("user_sid", ""))
                .setPriority(Priority.MEDIUM)
                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                Log.d("responseString", response);
                try {
                    JSONObject obj = new JSONObject(response);
                    if (step1SaveDTO.geteDate() == 0) {
                        try {
                            Log.d("eeee", "nonoData=" + obj.getString("diary_sid"));
                            csp.put("notSaveSid", obj.getString("diary_sid"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        csp.put("notSaveSid", "");
                        csp.put("saveStartDate", "");
                        csp.put("notSaveNowDate", "");
                    }

                    if (!isPush) {

                        new android.app.AlertDialog.Builder(Main2Activity.this).setTitle("안내")
                                .setMessage("입력해 주셔서 감사합니다.\n오늘의 두통일기를 계속 입력해 주세요.")
                                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), ContentStepActivity.class);
                                        startActivity(intent);
                                    }
                                }).setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
//                            Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
                            }
                        }).setCancelable(false).show();
                        isSaved = false;

                    } else {
                        csp.put("notSaveSid", "");
                        csp.put("saveStartDate", "");
                        csp.put("notSaveNowDate", "");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    isSaved = false;
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.d("anError", anError.getErrorBody());
                isSaved = false;
            }
        });


    }


}
