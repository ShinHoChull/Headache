package com.m2comm.headache.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.m2comm.headache.Adapter.JoinAddressAdapter;
import com.m2comm.headache.DTO.HospitalDTO;
import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivitySetting1Binding;
import com.m2comm.headache.module.Custom_SharedPreferences;
import com.m2comm.headache.module.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Setting1 extends AppCompatActivity implements View.OnClickListener {

    BottomActivity bottomActivity;
    ActivitySetting1Binding binding;
    private Custom_SharedPreferences csp;
    private boolean isListView = false;

    private ArrayList<HospitalDTO> hospitalDTOArrayList;
    private ArrayList<HospitalDTO> copyArrayList;
    private JoinAddressAdapter addressAdapter;

    private Urls urls;


    private void regObj () {
        this.binding.backBt.setOnClickListener(this);
        this.binding.logoutBt.setOnClickListener(this);
        this.binding.modifyBt.setOnClickListener(this);
        this.binding.saveBt.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting1);

        this.binding = DataBindingUtil.setContentView(this,R.layout.activity_setting1);
        this.binding.setSetting1(this);

        this.init();
        this.regObj();
    }

    private void init () {
        this.bottomActivity = new BottomActivity(getLayoutInflater() , R.id.bottom , this , this,-1);
        this.csp = new Custom_SharedPreferences(this);
        this.binding.id.setText(this.csp.getValue("user_id",""));
        this.binding.year.setText(this.csp.getValue("birth_year",""));
        this.binding.jender.setText(this.csp.getValue("sex","").equals("M") ? "남자":"여자");
        this.binding.hospitalName.setText(this.csp.getValue("hospital",""));
        this.hospitalDTOArrayList = new ArrayList<>();
        this.copyArrayList = new ArrayList<>();
        this.urls = new Urls();


        if ( this.csp.getValue("sex","").equals("M") ) {
            this.binding.meanV.setVisibility(View.GONE);
            this.binding.meanVLine.setVisibility(View.GONE);
        } else {
            if ( this.csp.getValue("mens","").equals("N") ) {
                this.binding.mean.setText("폐경");
            } else {
                this.binding.mean.setText("폐경안함");
            }
        }

        AndroidNetworking.get("http://ezv.kr/headache/func/get_hospital.php")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0, j = response.length(); i < j; i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                hospitalDTOArrayList.add(new HospitalDTO(obj.getInt("sid"),
                                        obj.isNull("name")?"":obj.getString("name"),
                                        obj.isNull("addr")?"":obj.getString("addr")));

//                                copyArrayList.add(new HospitalDTO(obj.getInt("sid"),
//                                        obj.isNull("name")?"":obj.getString("name"),
//                                        obj.isNull("addr")?"":obj.getString("addr")));
                                changeList();
                            } catch (Exception e) {
                                Log.d("error=", e.toString());
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

        this.binding.hospitalEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                copyArrayList.clear();
                if ( count == 0 ) {
                    copyArrayList.addAll(hospitalDTOArrayList);
                } else {
                    for ( int i = 0, j = hospitalDTOArrayList.size(); i < j ; i++) {
                        HospitalDTO row = hospitalDTOArrayList.get(i);
                        if (row.getName().contains(binding.hospitalEditText.getText().toString())) {
                            copyArrayList.add(new HospitalDTO(row.getSid() , row.getName() , row.getAddr()));
                        }
                    }
                }
                addressAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        this.binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HospitalDTO row = copyArrayList.get(position);
                binding.hospitalEditText.setText(row.getName());
                copyArrayList.clear();

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.hospitalEditText.getWindowToken(), 0);
            }
        });

    }

    private void changeList() {
        addressAdapter = new JoinAddressAdapter(this, this, getLayoutInflater(), this.copyArrayList);
        this.binding.listview.setAdapter(addressAdapter);
        addressAdapter.notifyDataSetChanged();
    }


    private void listViewSetting() {
        if ( this.isListView ) {
            this.isListView = false;
            this.binding.hospitalParentV.setVisibility(View.GONE);
            this.binding.listview.setVisibility(View.GONE);
        } else {
            this.isListView = true;
            this.binding.hospitalParentV.setVisibility(View.VISIBLE);
            this.binding.listview.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.modifyBt:
                this.listViewSetting();
                break;

            case R.id.backBt:
                finish();
                break;

            case R.id.saveBt:
                if ( binding.hospitalEditText.getText().toString().equals("") ) {
                    Toast.makeText(this , "내원병원명을 작성해 주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                AndroidNetworking.post(this.urls.mainUrl + this.urls.getUrls.get("setLogin"))
                        .addBodyParameter("type","update")
                        .addBodyParameter("hospital",this.binding.hospitalEditText.getText().toString())
                        .addBodyParameter("user_id",csp.getValue("user_id",""))
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("update=",response.toString());
                                try {
                                    if ( response.getString("rows").equals("Y") ) {
                                        csp.put("hospital",binding.hospitalEditText.getText().toString());
                                        binding.hospitalName.setText(binding.hospitalEditText.getText().toString());
                                        listViewSetting();
                                        Toast.makeText(getApplicationContext() , response.getString("msg"),Toast.LENGTH_SHORT).show();
                                    }  else {
                                        Toast.makeText(getApplicationContext() , response.getString("msg"),Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }



                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(getApplicationContext() , "내원병원명을 작성해 주세요.",Toast.LENGTH_SHORT).show();
                            }
                        });
                break;

            case R.id.logoutBt:
                csp.put("isLogin",false);
                csp.put("user_sid","");
                csp.put("user_id","");
                csp.put("sex","");
                csp.put("birth_year","");
                csp.put("mens","");
                Intent intent = new Intent(this , LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}
