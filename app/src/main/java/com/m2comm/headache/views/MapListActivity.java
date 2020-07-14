package com.m2comm.headache.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.m2comm.headache.Adapter.MapListAdapter;
import com.m2comm.headache.DTO.AddressDTO;
import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivityMapListBinding;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapListActivity extends AppCompatActivity implements View.OnClickListener , AdapterView.OnItemClickListener {

    private ActivityMapListBinding binding;
    private ArrayList<AddressDTO> arrayList;
    private MapListAdapter mapListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_list);
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_map_list);
        this.binding.setMapList(this);
        this.init();
    }

    private void regObj() {
        this.binding.searchBt.setOnClickListener(this);
        this.binding.listview.setOnItemClickListener(this);
    }

    private void init() {
        this.regObj();
        this.arrayList = new ArrayList<>();
        this.reloadAdapter();
    }

    private void reloadAdapter () {
        this.mapListAdapter = new MapListAdapter(this , this , this.arrayList , getLayoutInflater());
        this.binding.listview.setAdapter(this.mapListAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AddressDTO row = arrayList.get(position);
        Intent intent = getIntent();
        intent.putExtra("lat",row.getLat());
        intent.putExtra("lon",row.getLon());
        intent.putExtra("address",row.getAddress());
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchBt:

                this.arrayList.clear();

                AndroidNetworking.get("https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode")
                        .addQueryParameter("query", this.binding.mapEditText.getText().toString())
                        .addQueryParameter("X-NCP-APIGW-API-KEY-ID", "9hcpsz5vyy")
                        .addQueryParameter("X-NCP-APIGW-API-KEY", "adJBgqzwN4H69l3M6Y6isgIUZ81Wcl2jltD9U9Pm")
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("respons=", response.toString());
                                try {
                                    if (response.getString("status").equals("OK")) {
                                        JSONObject metaObj = response.getJSONObject("meta");
                                        if (metaObj.getInt("totalCount") > 0) {
                                            JSONArray address = response.getJSONArray("addresses");
                                            JSONObject addressObj = address.getJSONObject(0);

                                            Log.d("addressX=", addressObj.getDouble("x") + "_");
                                            Log.d("addressY=", addressObj.getDouble("y") + "_");

                                            arrayList.add(new AddressDTO(addressObj.getString("roadAddress") ,
                                                    addressObj.getDouble("y"),addressObj.getDouble("x") ));

//                                            CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(addressObj.getDouble("y"), addressObj.getDouble("x")));
//                                            naverMap.moveCamera(cameraUpdate);
//
//                                            setMarker(addressObj.getDouble("y"),addressObj.getDouble("x"));
                                            reloadAdapter();

                                        } else {
                                            Toast.makeText(getApplicationContext(), "유효하지 않은 주소입니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.d("anError1=", anError.getErrorDetail());
                            }
                        });
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }

}