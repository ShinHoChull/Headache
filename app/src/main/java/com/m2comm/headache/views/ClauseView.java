package com.m2comm.headache.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;

import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivityClauseViewBinding;

public class ClauseView extends AppCompatActivity implements View.OnClickListener {

    ActivityClauseViewBinding binding;

    private void regObj () {
        this.binding.successBt.setOnClickListener(this);
    }

    private void init() {
        Intent intent = getIntent();

//        this.binding.webview.getSettings().setUseWideViewPort(true);
//        this.binding.webview.getSettings().setJavaScriptEnabled(true);
//        this.binding.webview.getSettings().setLoadWithOverviewMode(true);
//        this.binding.webview.getSettings().setDefaultTextEncodingName("utf-8");
//        this.binding.webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        this.binding.webview.getSettings().setSupportMultipleWindows(false);
//        this.binding.webview.getSettings().setDomStorageEnabled(true);
//        this.binding.webview.getSettings().setBuiltInZoomControls(true);
//        this.binding.webview.getSettings().setDisplayZoomControls(false);


        //this.binding.webview.getSettings().setTextZoom(90);

        if ( intent.getIntExtra("code",-1) == 0) {
            this.binding.webview.loadUrl("https://ezv.kr:4447/headache/agree/agree1.php");
        } else if ( intent.getIntExtra("code",-1) == 1) {
            this.binding.webview.loadUrl("https://ezv.kr:4447/headache/agree/agree2.php");
        }  else if ( intent.getIntExtra("code",-1) == 2) {
            this.binding.webview.loadUrl("https://ezv.kr:4447/headache/agree/agree3.php");
        }  else if ( intent.getIntExtra("code",-1) == 3) {
            this.binding.webview.loadUrl("https://ezv.kr:4447/headache/agree/agree4.php");
        }

        this.binding.webview.getSettings().setLoadWithOverviewMode(true);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clause_view);
        this.binding = DataBindingUtil.setContentView(this,R.layout.activity_clause_view);
        this.binding.setClause(this);
        this.init();
        this.regObj();
    }

    @Override
    public void onClick(View v) {

        switch ( v.getId() ) {
            case R.id.successBt:
                Intent intent = getIntent();
                intent.putExtra("success",true);
                setResult(JoinStep1Activity.CLAUSE_CODE,intent);
                finish();
                break;
        }
    }
}
