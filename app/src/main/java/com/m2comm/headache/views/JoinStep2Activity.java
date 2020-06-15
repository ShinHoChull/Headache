package com.m2comm.headache.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivityJoinStep2Binding;
import com.m2comm.headache.module.CalendarModule;
import com.m2comm.headache.module.Custom_SharedPreferences;
import com.m2comm.headache.module.Urls;

import org.json.JSONException;
import org.json.JSONObject;

public class JoinStep2Activity extends AppCompatActivity implements View.OnClickListener {

    ActivityJoinStep2Binding binding;
    private CalendarModule cm;
    private Urls urls;
    private Custom_SharedPreferences csp;
    String[] years;
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
    String gender = "M";//성별
    String mens = "";

    int endMinusYear = 100;

    boolean isPw = false, isRePw = false;


    private void regObj () {
        this.binding.backBt.setOnClickListener(this);
        this.binding.joinSuccessBt.setOnClickListener(this);
        this.binding.boyBt.setOnClickListener(this);
        this.binding.girlBt.setOnClickListener(this);
        this.binding.girlOnly.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_step2);
        this.binding = DataBindingUtil.setContentView(this,R.layout.activity_join_step2);
        this.binding.setJoinStep2(this);
        this.init();
        this.regObj();
    }

    private void init () {
        this.cm = new CalendarModule(this , this);
        this.csp = new Custom_SharedPreferences(this);
        this.urls = new Urls();
        this.spinnerSetEmail();
        this.spinnerSetDate();

        this.binding.pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().length() < 4) {
                    isPw = false;
                    binding.pwHint.setVisibility(View.VISIBLE);
                } else {
                    isPw = true;
                    binding.pwHint.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        this.binding.repw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() < 4) {
                    isRePw = false;
                    binding.pwReHint.setVisibility(View.VISIBLE);
                } else {
                    isRePw = true;
                    binding.pwReHint.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        this.binding.spinnerEmailDropBt.setOnClickListener(this);

    }

    private void spinnerSetEmail () {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_item , this.email );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.binding.spinnerEmail.setAdapter(adapter);
        this.binding.spinnerEmail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if ( email[position].equals("직접입력") ) {
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


    private void spinnerSetDate () {
        //현재 년도부터 100년 밑으로
        this.years = new String[this.endMinusYear];
        String[] cutDateFormat = this.cm.getStrRealDate().split("-");
        Log.d("cutDate",cutDateFormat[0]);
        int nowYear = Integer.parseInt(cutDateFormat[0]);
        for ( int i = 0, j = this.endMinusYear; i < j; i++ ) {
            this.years[i] = String.valueOf(nowYear - i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_item , this.years );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.binding.spinnerYear.setAdapter(adapter);
        this.binding.spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    binding.yearInput.setText(String.valueOf(parent.getItemAtPosition(position)));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void setBoyGirlCheck(int num) {
        if ( num == 0 ) {
            this.binding.girlBt.setBackgroundResource(R.drawable.step5_no_select_board);
            this.binding.girlBt.setTextColor(Color.parseColor("#1EA2B6"));
            this.binding.boyBt.setTextColor(Color.parseColor("#777777"));
            this.binding.boyBt.setBackgroundColor(Color.TRANSPARENT);
            this.binding.girlOnly.setVisibility(View.VISIBLE);
            this.menuSetting("Y","W");
        } else {
            this.binding.boyBt.setBackgroundResource(R.drawable.step5_select_board);
            this.binding.boyBt.setTextColor(Color.parseColor("#1EA2B6"));
            this.binding.girlBt.setTextColor(Color.parseColor("#777777"));
            this.binding.girlBt.setBackgroundColor(Color.TRANSPARENT);
            this.binding.girlOnly.setVisibility(View.INVISIBLE);
            this.menuSetting("","M");

        }
    }

    private void menuSetting (String mens , String gender) {
        this.mens = mens;
        this.gender = gender;
    }

    private void checkBox() {
        if ( !this.gender.equals("M") ) {
            if ( mens.equals("Y") ) {
                this.binding.girlOnlyImg.setImageResource(R.drawable.login_check_on);
                this.menuSetting("N","W");
            } else {
                this.binding.girlOnlyImg.setImageResource(R.drawable.login_check_off);
                this.menuSetting("Y","W");
            }
        }
    }

    private boolean login() {
        if ( this.binding.email.getText().toString().equals("") ) {
            Toast.makeText(this , "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if ( !this.isPw ) {
            Toast.makeText(this , "비밀번호는 4자리 이상 입력하셔야합니다.", Toast.LENGTH_SHORT).show();
            return false;
        } else if ( !this.isRePw ) {
            Toast.makeText(this , "비밀번호는 4자리 이상 입력하셔야합니다.", Toast.LENGTH_SHORT).show();
            return false;
        } else if ( !this.binding.pw.getText().toString().equals(this.binding.repw.getText().toString()) ) {
            Toast.makeText(this , "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (this.binding.emailInput.getText().toString().equals("선택하세요")) {
            Toast.makeText(this , "이메일을 선택해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        AndroidNetworking.post(this.urls.mainUrl+this.urls.getUrls.get("setLogin"))
                .addBodyParameter("type","insert") //고정
                .addBodyParameter("user_id",this.binding.email.getText().toString() +"@"+ this.binding.emailInput.getText().toString())
                .addBodyParameter("passwd",this.binding.pw.getText().toString())
                .addBodyParameter("birth_year",this.binding.yearInput.getText().toString())
                .addBodyParameter("birth_year",this.binding.yearInput.getText().toString())
                .addBodyParameter("sex",this.gender)
                .addBodyParameter("mens",this.mens)
                .addBodyParameter("token","CreateToken Fail")
                .addBodyParameter("device","android")
                .addBodyParameter("deviceid",csp.getValue("deviceid",""))
                .setPriority(Priority.MEDIUM)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("respone",response.toString());
                try {
                    if (response.getString("rows").equals("Y")) {
                        //Toast.makeText(getApplicationContext() , response.getString("msg"), Toast.LENGTH_SHORT).show();
                        joinSuccess();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext() , "잠시후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.d("joinStep2",anError.getErrorDetail());
                Toast.makeText(getApplicationContext() , "회원가입 실패입니다.", Toast.LENGTH_SHORT).show();
            }
        });
        return true;
    }

    private void joinSuccess () {
        Intent intent = new Intent(this , JoinStepSuccess.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        finish();
    }


    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {

            case R.id.spinner_email_dropBt:
                this.binding.emailInput.setText("선택하세요");
                this.binding.spinnerEmail.setVisibility(View.VISIBLE);
                break;

            case R.id.backBt:
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                break;

            case R.id.joinSuccessBt:

                if (!login()) {
                    return;
                }


                break;

            case R.id.girlBt:
                this.setBoyGirlCheck(0);
                break;

            case R.id.boyBt:
                this.setBoyGirlCheck(1);
                break;

            case R.id.girl_only:
                checkBox();
                break;

        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v == null) return super.dispatchTouchEvent(event);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(event);
    }
}
