package com.m2comm.headache.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.PointF;
import android.location.Location;
import android.os.Bundle;
import android.os.TokenWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonObject;
import com.m2comm.headache.Global;
import com.m2comm.headache.R;
import com.m2comm.headache.contentStepView.Step1;
import com.m2comm.headache.module.Custom_SharedPreferences;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NaverMapSelectActivity extends AppCompatActivity implements OnMapReadyCallback, Overlay.OnClickListener, View.OnClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    int MAP_SELECT = 999;

    private Custom_SharedPreferences csp;
    private MapFragment mapFragment;
    private NaverMap naverMap;
    private FusedLocationSource locationSource;
    private Marker marker;
    private TextView successBt , cancelBt;
    private ImageView searchImg , backBt;
    private EditText address;
    private double lat,lon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naver_map_select);

        this.init();
        this.regObj();
    }


    private void regObj () {
        this.searchImg.setOnClickListener(this);
        this.successBt.setOnClickListener(this);
        this.backBt.setOnClickListener(this);
        this.cancelBt.setOnClickListener(this);
    }

    private void init () {

        this.csp = new Custom_SharedPreferences(this);
        this.marker  = new Marker();
        this.searchImg = findViewById(R.id.searchBt);
        this.address = findViewById(R.id.add);
        this.successBt = findViewById(R.id.nextBt);
        this.backBt = findViewById(R.id.backBt);
        this.cancelBt = findViewById(R.id.cancelBt);

        NaverMapSdk.getInstance(this).setClient(
                new NaverMapSdk.NaverCloudPlatformClient("9hcpsz5vyy"));

        this.locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

//        lat = this.locationSource.getLastLocation().getLatitude();
//        lon = this.locationSource.getLastLocation().getLongitude();

        FragmentManager fm = getSupportFragmentManager();
        this.mapFragment = (MapFragment) fm.findFragmentById(R.id.map_fragment);

        if ( mapFragment == null ) {

            //초깃값 지정.
            //틸트 제스처를 비활성화 및 현위치 버튼 활성화
            NaverMapOptions options = new NaverMapOptions()
                    .locationButtonEnabled(true)
                    .tiltGesturesEnabled(false);

            this.mapFragment = MapFragment.newInstance(options);
            fm.beginTransaction().add(R.id.map_fragment, this.mapFragment).commit();
        }
        //지도 생성.
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull final NaverMap naverMap) {
        Log.d("onMapReady=","click");
        this.naverMap = naverMap;
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(false);

        naverMap.setLocationSource(this.locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Face);

        //사용자 위치 객체 생성
        LocationOverlay locationOverlay = naverMap.getLocationOverlay();
        locationOverlay.setVisible(true);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);


        //map Click Event
        naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {

                setMarker(latLng.latitude , latLng.longitude);
                //Toast.makeText(getApplicationContext(), "lat="+latLng.latitude +"/ lon="+latLng.longitude, Toast.LENGTH_SHORT).show();
            }
        });

        this.setMarker( naverMap.getCameraPosition().target.latitude , naverMap.getCameraPosition().target.longitude);
    }

    private void setMarker(double lat , double lon) {

        LatLng latLng = new LatLng(lat,lon);
        marker.setMap(null);
        marker.setPosition(latLng);
        marker.setMap(naverMap);
        this.lat = lat;
        this.lon = lon;

        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(latLng);
        naverMap.moveCamera(cameraUpdate);
    }

    @Override
    public boolean onClick(@NonNull Overlay overlay) {
        Log.d("NaverMapSelect=","click");
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == MAP_SELECT) {
                Log.d("address=",data.getStringExtra("address"));
                setMarker(data.getDoubleExtra("lat",0) ,data.getDoubleExtra("lon",0));
            }
        }
    }

    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {

            case R.id.cancelBt:
            case R.id.backBt:
                finish();
                break;

            case R.id.nextBt:
                intent = new Intent();
                intent.putExtra("lat",this.lat);
                intent.putExtra("lon",this.lon);
                setResult(RESULT_OK,intent);
                finish();
                break;

            case R.id.searchBt:
                intent = new Intent(this , MapListActivity.class);
                startActivityForResult(intent,MAP_SELECT);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                break;
        }
    }
}