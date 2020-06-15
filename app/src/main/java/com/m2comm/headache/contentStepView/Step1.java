package com.m2comm.headache.contentStepView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonObject;
import com.m2comm.headache.DTO.Step1SaveDTO;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.module.CalendarModule;
import com.m2comm.headache.module.GpsTracker;
import com.m2comm.headache.views.ContentStepActivity;
import com.m2comm.headache.views.SubTimePicker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Step1 implements View.OnClickListener {

    public final static int BT1 = 111;
    public final static int BT2 = 222;

    private LayoutInflater inflater;
    private int ParentID;
    private LinearLayout parent;
    private Context context;
    private Activity activity;
    private ContentStepActivity parentActivity;
    private View view;

    /*step1*/
    int nextStepNum = 2;
    LinearLayout nextBt;

    //달력보기 버튼 1,2
    TextView calendarBt1 , calendarBt2 ;
    //날짜
    TextView startDateTxt , startTimeTxt , endDateTxt , endTimeTxt ,
    temp , pressureAndHumidity , addressTxt;

    ImageView weatherImg , locationBt;


    //getTime
    long startDateTimeLong = 0;
    long endDateTimeLong = 0;

    public Step1SaveDTO step1SaveDTO;

    private CalendarModule cm;

    //날씨
    private GpsTracker gpsTracker;
    double latitude = 0;
    double longitude = 0;
    String address = "";
    boolean isDetail = false;


    public Step1(LayoutInflater inflater, int parentID, Context context, Activity activity , ContentStepActivity parentActivity , Step1SaveDTO step1SaveDTO , boolean isDetail) {
        this.inflater = inflater;
        ParentID = parentID;
        this.context = context;
        this.activity = activity;
        this.parentActivity = parentActivity;
        this.step1SaveDTO = step1SaveDTO;
        this.isDetail = isDetail;
        this.init();
    }

    private void regObj () {
        this.nextBt.setOnClickListener(this);
        this.calendarBt1.setOnClickListener(this);
        this.calendarBt2.setOnClickListener(this);
        this.locationBt.setOnClickListener(this);
    }

    private void init () {
        this.parent = this.activity.findViewById(this.ParentID);
        this.parent.removeAllViews();

        this.view = inflater.inflate(R.layout.step1,this.parent,true);
        this.nextBt = this.view.findViewById(R.id.nextBt);
        this.calendarBt1 = this.view.findViewById(R.id.calendarBt1);
        this.calendarBt2 = this.view.findViewById(R.id.calendarBt2);
        this.startDateTxt = this.view.findViewById(R.id.startDate);
        this.startTimeTxt = this.view.findViewById(R.id.startTime);
        this.endDateTxt = this.view.findViewById(R.id.endDate);
        this.endTimeTxt = this.view.findViewById(R.id.endTime);
        this.temp = this.view.findViewById(R.id.temp);
        this.pressureAndHumidity = this.view.findViewById(R.id.pressureAndHumidity);
        this.addressTxt = this.view.findViewById(R.id.address);
        this.weatherImg = this.view.findViewById(R.id.weatherImg);
        this.locationBt = this.view.findViewById(R.id.locationBt);

        this.cm = new CalendarModule(this.context , this.activity);
        this.regObj();

        if ( this.step1SaveDTO == null ) {
            //새 일기 작성시에는 오늘날짜는 넣어줍니다.
            this.step1SaveDTO = new Step1SaveDTO(Global.getStrToDateTime(this.cm.getStrRealDateTime()).getTime() , 0L ,"");
            this.startTimeSetting(this.step1SaveDTO.getSdate());
            this.getLocation();
        } else {
            this.startTimeSetting(this.step1SaveDTO.getSdate());
            this.endTimeSetting(this.step1SaveDTO.geteDate());
            this.weatherSetting();
        }

        if ( this.isDetail ) {
            if ( this.step1SaveDTO.geteDate() > 0 ) {
                this.calendarBt1.setVisibility(View.INVISIBLE);
                this.calendarBt2.setVisibility(View.INVISIBLE);
                this.locationBt.setVisibility(View.GONE);
            }
        }

    }

    public void getLocation () {
        gpsTracker = new GpsTracker(this.context);
        this.latitude = gpsTracker.getLatitude();
        this.longitude = gpsTracker.getLongitude();
        this.address = getCurrentAddress(latitude, longitude);
        Log.d("address",address);

        AndroidNetworking.post("https://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&appid=60b48ba536f4029e11f19e4a0b407466")
                .addBodyParameter("lat",String.valueOf(latitude))
                .addBodyParameter("lon",String.valueOf(longitude))
                .setPriority(Priority.MEDIUM)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("respon",response.toString());
                try {

                    JSONObject obj = response.getJSONObject("main");
                    JSONArray iconObj = response.getJSONArray("weather");

                    Log.d("temp",obj.getDouble("temp")+"_");
                    Log.d("pressure",obj.getDouble("pressure")+"_");
                    Log.d("humidity",obj.getDouble("humidity")+"_");

                    step1SaveDTO.setHumidity(obj.getDouble("humidity"));
                    step1SaveDTO.setPressure(obj.getDouble("pressure"));
                    step1SaveDTO.setTemp(obj.getDouble("temp"));


                    JSONObject jsonObj = (JSONObject) iconObj.get(0);
                    step1SaveDTO.setWeather_icon(jsonObj.getString("icon"));
                    weatherSetting();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Error1",e.toString());
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.d("Error2",anError.getErrorBody());
            }
        });
    }

    public void weatherSetting() {
        if ( this.step1SaveDTO.getTemp() < 100 ) {
            this.temp.setText(this.step1SaveDTO.getTemp()+"°");
        }else {
            this.temp.setText(String.format("%.0f",(this.step1SaveDTO.getTemp()-273.15))+"°");
        }

        this.pressureAndHumidity.setText("습도  "+this.step1SaveDTO.getPressure()+"%"+"\n 압력 "+this.step1SaveDTO.getHumidity()+"obar");

        if ( !this.step1SaveDTO.getAddress().equals("") ) {
            this.addressTxt.setText(this.step1SaveDTO.getAddress());
        } else {
            this.addressTxt.setText(address);
        }
        this.step1SaveDTO.setAddress(this.addressTxt.getText().toString());

        Picasso.get().load("http://openweathermap.org/img/wn/"+this.step1SaveDTO.getWeather_icon()+"@2x.png").into(this.weatherImg);
        this.parentActivity.save1(this.step1SaveDTO);
    }

    public void startTimeSetting (long startDateTime) {
        //값 저장후 반드시 Save를 시켜준다.
        this.step1SaveDTO.setSdate(startDateTime);
        this.parentActivity.save1(this.step1SaveDTO);

        //this.startDateTimeLong = startDateTime;
        String date = Global.inputDateTimeToStr(this.step1SaveDTO.getSdate());
        if ( startDateTime == 0 ) {
            date = " - ";
        }
        String[] cut = date.split("-");

        this.startDateTxt.setText(cut[0]);
        this.startTimeTxt.setText(cut[1]);

    }

    public void endTimeSetting (long endDateTime) {

        this.step1SaveDTO.seteDate(endDateTime);
        this.parentActivity.save1(this.step1SaveDTO);

        //this.endDateTimeLong = endDateTime;
        String date = Global.inputDateTimeToStr(this.step1SaveDTO.geteDate());
        if ( endDateTime == 0 ) {
            date = " - ";
        }
        String[] cut = date.split("-");

        this.endDateTxt.setText(cut[0]);
        this.endTimeTxt.setText(cut[1]);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.nextBt:
                this.parentActivity.positionView(this.nextStepNum);
                break;

            case R.id.calendarBt1:
                endTimeSetting(0);
                intent = new Intent(this.activity , SubTimePicker.class);
                intent.putExtra("startDateLong",this.step1SaveDTO.getSdate());
                this.activity.startActivityForResult(intent , BT1);
                break;

            case R.id.calendarBt2:
                if ( this.step1SaveDTO.getSdate() == 0 ) {
                    Toast.makeText(this.context , "두통 시작시간을 선택해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }

                intent = new Intent(this.activity , SubTimePicker.class);
                intent.putExtra("isEnd",true);
                intent.putExtra("startDateLong",this.step1SaveDTO.getSdate());
                intent.putExtra("endDateLong",this.step1SaveDTO.geteDate());
                this.activity.startActivityForResult(intent , BT2);
                break;

            case R.id.locationBt:
                this.getLocation();
                break;
        }
    }


    public String getCurrentAddress( double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this.activity, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this.activity, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this.activity, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this.activity, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        }
        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }



}
