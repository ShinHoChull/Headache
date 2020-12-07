package com.m2comm.headache.views;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivitySetting2Binding;
import com.m2comm.headache.module.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Setting2 extends AppCompatActivity implements View.OnClickListener {

    BottomActivity bottomActivity;
    ActivitySetting2Binding binding;
    private boolean isView;
    private Urls urls;
    private String codeNumber = "";

    String[] email = {
            "선택하세요",
            "naver.com",
            "hanmail.net",
            "gmail.com",
            "nate.com",
            "hotmail.com",
            "freechal.com",
            "hanmir.com",
            "korea.com",
            "paran.com",
            "daum.net",
            "직접입력"
    };

    private boolean isPw = false , isNewPw = false;

    private void regObj () {
        this.binding.backBt.setOnClickListener(this);
        this.binding.certificationBt.setOnClickListener(this);
        this.binding.pwChangeBt.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting2);
        this.binding = DataBindingUtil.setContentView(this,R.layout.activity_setting2);
        this.binding.setSetting2(this);
        this.init();
        this.regObj();
    }

    private void init () {
        //this.bottomActivity = new BottomActivity(getLayoutInflater() , R.id.bottom , this , this,-1);
        this.spinnerSetEmail();
        this.binding.spinnerEmailDropBt.setOnClickListener(this);
        this.urls = new Urls();


        this.binding.newPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().length() < 4) {
                    isPw = false;
                    binding.pwHint1.setVisibility(View.VISIBLE);
                } else {
                    isPw = true;
                    binding.pwHint1.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        this.binding.newPwSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() < 4) {
                    isNewPw = false;
                    binding.pwHint2.setVisibility(View.VISIBLE);
                } else {
                    isNewPw = true;
                    binding.pwHint2.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void spinnerSetEmail() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, this.email);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.binding.spinnerEmail.setAdapter(adapter);
        this.binding.spinnerEmail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (email[position].equals("직접입력")) {
                    binding.spinnerEmail.setVisibility(View.GONE);
                    binding.emailInput.setEnabled(true);
                    binding.emailInput.setText("");
                    binding.emailInput.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                } else {
                    binding.emailInput.setText(String.valueOf(parent.getItemAtPosition(position)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.spinner_email_dropBt:
                this.binding.emailInput.setText("선택하세요");
                this.binding.spinnerEmail.setVisibility(View.VISIBLE);
                break;

            case R.id.backBt:
                finish();
                break;

            case R.id.pwChangeBt:

                if ( this.binding.numberCode.getText().toString().equals("") ) {
                    Toast.makeText(this , "인증코드를 입력해 주세요.",Toast.LENGTH_SHORT).show();
                    return;
                } else if ( !codeNumber.equals(binding.numberCode.getText().toString()) ) {
                    Toast.makeText(this , "인증코드를 확인해 주세요.",Toast.LENGTH_SHORT).show();
                    return;
                } else if ( !this.isPw ) {
                    Toast.makeText(this , "비밀번호는 4자리 이상 입력하셔야합니다.",Toast.LENGTH_SHORT).show();
                    return;
                } else if ( !this.isNewPw ) {
                    Toast.makeText(this , "비밀번호는 4자리 이상 입력하셔야합니다.",Toast.LENGTH_SHORT).show();
                    return;
                } else if ( !this.binding.newPw.getText().toString().equals(this.binding.newPwSearch.getText().toString()) ) {
                    Toast.makeText(this , "비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }

                AndroidNetworking.post(this.urls.mainUrl + this.urls.getUrls.get("changePw"))
                        .addBodyParameter("user_id", this.binding.email.getText().toString()+"@"+this.binding.emailInput.getText().toString() )
                        .addBodyParameter("passwd", binding.newPw.getText().toString() )
                        .addBodyParameter("codeNumber", binding.numberCode.getText().toString() )
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("reponse=",response.toString());
                                try {
                                    if( response.getString("success").equals("Y") ) {
                                        String msg = response.isNull("msg")?"비밀번호를 변경 하였습니다.":response.getString("msg");
                                        Toast.makeText(getApplicationContext(),msg , Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        String msg = response.isNull("msg")?"실패하였습니다.":response.getString("msg");
                                        Toast.makeText(getApplicationContext(),msg , Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.d("anError=",anError.getMessage());
                            }
                        });
                break;


            case R.id.certificationBt:
                if ( this.isView ) {
                    this.isView = false;
                    this.binding.certificationParentV.setVisibility(View.GONE);
                } else {

                    if ( this.binding.email.getText().toString().equals("") || this.binding.emailInput.getText().toString().equals("") ) {
                        Toast.makeText(this , "이메일을 확인해주세요.",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    AndroidNetworking.post(this.urls.mainUrl + this.urls.getUrls.get("findPw"))
                            .addBodyParameter("user_id", this.binding.email.getText().toString()+"@"+this.binding.emailInput.getText().toString() )
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("reponse=",response.toString());

                                    try {
                                        if ( response.getString("rows").equals("Y") ) {
                                            binding.certificationParentV.setVisibility(View.VISIBLE);
                                            codeNumber = response.getString("c_code");
                                            Toast.makeText(getApplicationContext(),response.getString("msg"),Toast.LENGTH_SHORT).show();
                                        }  else {
                                            Toast.makeText(getApplicationContext(),response.getString("msg"),Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onError(ANError anError) {
                                    Log.d("anError=",anError.getMessage());
                                }
                            });
                }

                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}
