package com.m2comm.headache.contentStepView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.m2comm.headache.DTO.Step10EtcDTO;
import com.m2comm.headache.DTO.Step1SaveDTO;
import com.m2comm.headache.DTO.Step9DTO;
import com.m2comm.headache.DTO.Step9Dates;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.module.CalendarModule;
import com.m2comm.headache.module.GpsTracker;
import com.m2comm.headache.views.ContentStepActivity;
import com.m2comm.headache.views.NaverMapSelectActivity;
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
    public final static int BT3 = 333;

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
        this.startDateTxt.setOnClickListener(this);
        this.startTimeTxt.setOnClickListener(this);
        this.endDateTxt.setOnClickListener(this);
        this.endTimeTxt.setOnClickListener(this);
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
            this.step1SaveDTO = new Step1SaveDTO(Global.getStrToDateTime(this.cm.getStrRealDateTime()).getTime() , 0L ,
                    "",0L,0L,0L,"");
            this.startTimeSetting(this.step1SaveDTO.getSdate());
        } else {
            this.startTimeSetting(this.step1SaveDTO.getSdate());
            this.endTimeSetting(this.step1SaveDTO.geteDate());
        }

        //날씨가 저장되어있으면 저장되어있는것을 가져오고 , 아니면 새로 불러온다.
        if ( step1SaveDTO.getTemp() <= 0 ) {
            this.getLocation(0,0);
        } else {
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

    public void getLocation (double lat , double lon) {

        if ( lat == 0 || lon == 0 ) {
            gpsTracker = new GpsTracker(this.context);
            this.latitude = gpsTracker.getLatitude();
            this.longitude = gpsTracker.getLongitude();

        } else {
            this.latitude = lat;
            this.longitude = lon;
        }

        this.address = getCurrentAddress(latitude, longitude);
        Log.d("address",address);
        if(!address.equals(""))this.step1SaveDTO.setAddress(address);

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

        this.pressureAndHumidity.setText("습도  "+this.step1SaveDTO.getHumidity()+"%"+"\n압력 "+this.step1SaveDTO.getPressure()+"hPa");
        if ( this.step1SaveDTO.getAddress().equals("") ) {
            this.step1SaveDTO.setAddress(this.address);
        }

        this.addressTxt.setText(this.step1SaveDTO.getAddress());

//        if (this.address.equals("")) {
//            this.addressTxt.setText(this.step1SaveDTO.getAddress());
//        } else {
//            this.step1SaveDTO.setAddress(this.address);
//        }

        Picasso.get().load("http://openweathermap.org/img/wn/"+this.step1SaveDTO.getWeather_icon()+"@2x.png").into(this.weatherImg);
        this.parentActivity.save1(this.step1SaveDTO);
    }

    public void startTimeSetting (long startDateTime) {
        //값 저장후 반드시 Save를 시켜준다.
        this.step1SaveDTO.setSdate(startDateTime);
        this.parentActivity.save1(this.step1SaveDTO);

        String date = Global.inputDateTimeToStr(this.step1SaveDTO.getSdate());
        if ( startDateTime == 0 ) {
            date = " - ";
        }
        String[] cut = date.split("-");

        this.startDateTxt.setText(cut[0]);
        this.startTimeTxt.setText(cut[1]);

        //종료날짜를 지정하고 시작날짜를 다시 바꿀때 종료날짜보다 미래를 눌렀으면 종료날짜 초기
        if ( this.step1SaveDTO.getSdate() > this.step1SaveDTO.geteDate() ) {
            endTimeSetting(0);
        }
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

    public void setLocation(double lat , double lon) {
        this.getLocation( lat , lon );
    }

    private boolean countCheck () {
        if ( this.parentActivity.step9SaveDTO != null ) {
            for ( int i = 0 , j = this.parentActivity.step9SaveDTO.getStep9DTOS().size(); i < j; i++ ) {
                Step9DTO row = this.parentActivity.step9SaveDTO.getStep9DTOS().get(i);
                if ( row.getDrugArray() != null ) {
                    for( int k = 0 , l = row.getDrugArray().size(); k < l ; k++ ) {
                        Step9Dates dateRow = row.getDrugArray().get(k);
                        if ( dateRow.getVal().equals("Y")) return true;
                    }
                }
            }
        }

        if (this.parentActivity.step10SaveDTO != null) {
            for ( int i = 0 , j = this.parentActivity.step10SaveDTO.getArrayList().size(); i < j; i++ ) {
                Step10EtcDTO row = this.parentActivity.step10SaveDTO.getArrayList().get(i);
                if ( row.getVal().equals("Y") )return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.nextBt:
                this.parentActivity.positionView(this.nextStepNum);
                break;

            case R.id.startDate:
            case R.id.calendarBt1:
                if (this.countCheck()) {
                    new AlertDialog.Builder(activity).setTitle("안내").setMessage("두통기간 수정 시 기존에 입력하셨던\n" +
                            "약물/장애 정보가 초기화됩니다.\n정보를 수정하시겠습니까?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    parentActivity.getETC();

                                    Intent intent = new Intent(activity , SubTimePicker.class);
                                    intent.putExtra("startDateLong",step1SaveDTO.getSdate());
                                    activity.startActivityForResult(intent , BT1);
                                }
                            }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                } else {
                    parentActivity.getETC();

                    Intent intent2 = new Intent(activity , SubTimePicker.class);
                    intent2.putExtra("startDateLong",step1SaveDTO.getSdate());
                    activity.startActivityForResult(intent2 , BT1);
                }

                break;

            case R.id.endDate:
            case R.id.calendarBt2:
                if ( this.step1SaveDTO.getSdate() == 0 ) {
                    Toast.makeText(this.context , "두통 시작시간을 선택해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (this.countCheck()) {
                    new AlertDialog.Builder(activity).setTitle("안내").setMessage("두통기간 수정 시 기존에 입력하셨던\n" +
                            "약물/장애 정보가 초기화됩니다.\n정보를 수정하시겠습니까?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    parentActivity.getETC();

                                    Intent intent = new Intent(activity , SubTimePicker.class);
                                    intent.putExtra("isEnd",true);
                                    intent.putExtra("startDateLong",step1SaveDTO.getSdate());
                                    intent.putExtra("endDateLong",step1SaveDTO.geteDate());
                                    activity.startActivityForResult(intent , BT2);
                                }
                            }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                } else {
                    parentActivity.getETC();

                    Intent intent2 = new Intent(activity , SubTimePicker.class);
                    intent2.putExtra("isEnd",true);
                    intent2.putExtra("startDateLong",step1SaveDTO.getSdate());
                    intent2.putExtra("endDateLong",step1SaveDTO.geteDate());
                    activity.startActivityForResult(intent2 , BT2);
                }


//                intent = new Intent(this.activity , SubTimePicker.class);
//                intent.putExtra("isEnd",true);
//                intent.putExtra("startDateLong",this.step1SaveDTO.getSdate());
//                intent.putExtra("endDateLong",this.step1SaveDTO.geteDate());
//                this.activity.startActivityForResult(intent , BT2);
                break;

            case R.id.locationBt:

                intent = new Intent(this.context , NaverMapSelectActivity.class);
                this.activity.startActivityForResult(intent,BT3);
                this.activity.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);

                //this.getLocation();
                break;

            case R.id.startTime:
                //endTimeSetting(0);
                if (this.countCheck()) {
                    new AlertDialog.Builder(activity).setTitle("안내").setMessage("두통기간 수정 시 기존에 입력하셨던\n" +
                            "약물/장애 정보가 초기화됩니다.\n정보를 수정하시겠습니까?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    parentActivity.getETC();

                                    Intent intent = new Intent(activity , SubTimePicker.class);
                                    intent.putExtra("startDateLong",step1SaveDTO.getSdate());
                                    intent.putExtra("isTime",true);
                                    activity.startActivityForResult(intent , BT1);
                                }
                            }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                } else {
                    parentActivity.getETC();

                    Intent intent2 = new Intent(activity , SubTimePicker.class);
                    intent2.putExtra("startDateLong",step1SaveDTO.getSdate());
                    intent2.putExtra("isTime",true);
                    activity.startActivityForResult(intent2 , BT1);
                }



//                intent = new Intent(this.activity , SubTimePicker.class);
//                intent.putExtra("startDateLong",this.step1SaveDTO.getSdate());
//                intent.putExtra("isTime",true);
//                this.activity.startActivityForResult(intent , BT1);
                break;

            case R.id.endTime:
                if ( this.step1SaveDTO.getSdate() == 0 ) {
                    Toast.makeText(this.context , "두통 시작시간을 선택해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (this.countCheck()) {
                    new AlertDialog.Builder(activity).setTitle("안내").setMessage("두통기간 수정 시 기존에 입력하셨던\n" +
                            "약물/장애 정보가 초기화됩니다.\n정보를 수정하시겠습니까?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    parentActivity.getETC();

                                    Intent intent = new Intent(activity , SubTimePicker.class);
                                    intent.putExtra("isEnd",true);
                                    intent.putExtra("startDateLong",step1SaveDTO.getSdate());
                                    intent.putExtra("endDateLong",step1SaveDTO.geteDate());
                                    intent.putExtra("isTime",true);
                                    activity.startActivityForResult(intent , BT2);
                                }
                            }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                } else {
                    parentActivity.getETC();

                    Intent intent2 = new Intent(activity , SubTimePicker.class);
                    intent2.putExtra("isEnd",true);
                    intent2.putExtra("startDateLong",step1SaveDTO.getSdate());
                    intent2.putExtra("endDateLong",step1SaveDTO.geteDate());
                    intent2.putExtra("isTime",true);
                    activity.startActivityForResult(intent2 , BT2);
                }


//                intent = new Intent(this.activity , SubTimePicker.class);
//                intent.putExtra("isEnd",true);
//                intent.putExtra("startDateLong",this.step1SaveDTO.getSdate());
//                intent.putExtra("endDateLong",this.step1SaveDTO.geteDate());
//                intent.putExtra("isTime",true);
//                this.activity.startActivityForResult(intent , BT2);
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
