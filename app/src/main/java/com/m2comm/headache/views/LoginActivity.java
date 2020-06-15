package com.m2comm.headache.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivityLoginBinding;
import com.m2comm.headache.module.Custom_SharedPreferences;
import com.m2comm.headache.module.Urls;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityLoginBinding binding;
    private Urls urls;
    private Custom_SharedPreferences csp;

    private void regObj () {
        this.binding.findPw.setOnClickListener(this);
        this.binding.signUp.setOnClickListener(this);
        this.binding.loginBt.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        this.binding.setLogin(this);
        this.init();
        this.regObj();
    }

    private void init () {
        this.urls = new Urls();
        this.csp = new Custom_SharedPreferences(this);
        if ( !this.csp.getValue("user_sid","").equals("") ) {
            this.loginSuccess();
        }
    }

    private void login () {

        if ( this.binding.emailInput.getText().toString().equals("") ) {
            Toast.makeText(this , "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        } else if ( this.binding.pwInput.getText().toString().equals("") ) {
            Toast.makeText(this , "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
            return ;
        }

        AndroidNetworking.post(this.urls.mainUrl+this.urls.getUrls.get("getLogin"))
                .addBodyParameter("user_id",this.binding.emailInput.getText().toString())
                .addBodyParameter("passwd",this.binding.pwInput.getText().toString())
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
                        csp.put("isLogin",true);
                        csp.put("user_sid",response.getString("user_sid"));
                        csp.put("user_id",response.getString("user_id"));
                        csp.put("sex",response.getString("sex"));
                        csp.put("birth_year",response.getString("birth_year"));
                        csp.put("mens",response.getString("mens"));
                        loginSuccess();
                    } else {
                        Toast.makeText(getApplicationContext() , response.getString("msg"), Toast.LENGTH_SHORT).show();
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
    }

    private void loginSuccess() {
        Intent intent = new Intent(this , Main2Activity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch ( v.getId() ) {

            case R.id.findPw:
                    intent = new Intent(this , Setting2.class);
                    startActivity(intent);
                break;

            case R.id.signUp:
                intent = new Intent(this , JoinStep1Activity.class);
                startActivity(intent);
                break;

            case R.id.loginBt:
                this.login();
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
