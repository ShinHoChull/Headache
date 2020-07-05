package com.m2comm.headache;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.PointF;
import android.location.Location;
import android.os.Bundle;
import android.os.TokenWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonObject;
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

    private Custom_SharedPreferences csp;
    private MapFragment mapFragment;
    private NaverMap naverMap;
    private FusedLocationSource locationSource;
    private Marker marker;
    private ImageView searchImg;
    private EditText address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naver_map_select);

        this.init();
        this.regObj();
    }


    private void regObj () {
        this.searchImg.setOnClickListener(this);
    }

    private void init () {

        this.csp = new Custom_SharedPreferences(this);
        this.marker  = new Marker();
        this.searchImg = findViewById(R.id.searchBt);
        this.address = findViewById(R.id.add);

        NaverMapSdk.getInstance(this).setClient(
                new NaverMapSdk.NaverCloudPlatformClient("9hcpsz5vyy"));

        this.locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

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

                Toast.makeText(getApplicationContext(), "lon="+latLng.latitude +"/ lan="+latLng.longitude, Toast.LENGTH_SHORT).show();
                marker.setMap(null);
                marker.setPosition(latLng);
                marker.setMap(naverMap);
            }
        });

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchBt:
                if ( this.address.getText().equals("") ) {

                    return;
                }

                AndroidNetworking.get("https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode")
                        .addQueryParameter("query",this.address.getText().toString())
                        .addQueryParameter("X-NCP-APIGW-API-KEY-ID","9hcpsz5vyy")
                        .addQueryParameter("X-NCP-APIGW-API-KEY","adJBgqzwN4H69l3M6Y6isgIUZ81Wcl2jltD9U9Pm")
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("respons=",response.toString());
                                try {
                                    if ( response.getString("status").equals("OK") ) {
                                        JSONObject metaObj = response.getJSONObject("meta");
                                        if ( metaObj.getInt("totalCount") > 0 ) {
                                            JSONArray address = response.getJSONArray("addresses");
                                            JSONObject addressObj = address.getJSONObject(0);

                                            Log.d("addressX=",addressObj.getDouble("x")+"_");
                                            Log.d("addressY=",addressObj.getDouble("y")+"_");

                                            CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(addressObj.getDouble("y"), addressObj.getDouble("x")));
                                            naverMap.moveCamera(cameraUpdate);

                                            LatLng latLng = new LatLng(addressObj.getDouble("y"),addressObj.getDouble("x"));
                                            marker.setMap(null);
                                            marker.setPosition(latLng);
                                            marker.setMap(naverMap);

                                        }
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.d("anError1=",anError.getErrorDetail());
                            }
                        });


                break;
        }
    }
}