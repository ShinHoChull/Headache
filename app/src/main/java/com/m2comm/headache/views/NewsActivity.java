package com.m2comm.headache.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.m2comm.headache.R;
import com.m2comm.headache.databinding.ActivityNewsBinding;
import com.m2comm.headache.module.Urls;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class NewsActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityNewsBinding binding;
    BottomActivity bottomActivity;
    Urls urls;
    String page = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        this.init();
    }

    private void regObj() {
        this.binding.backBt.setOnClickListener(this);
    }

    private void init() {
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_news);
        this.urls = new Urls();
        this.binding.setNews(this);

        this.regObj();
        Intent intent = getIntent();
        this.page = intent.getStringExtra("page");


        this.binding.webview.setWebViewClient(new WebviewCustomClient());
        this.binding.webview.getSettings().setUseWideViewPort(true);
        this.binding.webview.getSettings().setJavaScriptEnabled(true);
        this.binding.webview.getSettings().setLoadWithOverviewMode(true);
        this.binding.webview.getSettings().setDefaultTextEncodingName("utf-8");
        this.binding.webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.binding.webview.getSettings().setSupportMultipleWindows(false);
        this.binding.webview.getSettings().setDomStorageEnabled(true);
        this.binding.webview.getSettings().setBuiltInZoomControls(true);
        this.binding.webview.getSettings().setDisplayZoomControls(false);
        this.binding.webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.binding.webview.loadUrl(this.urls.mainUrl+page);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.bottomActivity = new BottomActivity(getLayoutInflater(), R.id.bottom, this, this,4);
    }

    private class WebviewCustomClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String[] urlCut = url.split("/");
            Log.d("NowUrl", url);
//            if (url.startsWith(g.mainUrl) == false && url.startsWith(g.contentMainUrl) == false) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(intent);
//                return true;
//            } else

            if (urlCut[urlCut.length - 1].equals("back.php")) {
                if (binding.webview.canGoBack()) {
                    binding.webview.goBack();
                } else {
                    finish();
                    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                }
                return true;
            }
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d("onPageStarted", url);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            Log.d("onLoadResource", url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d("onPageFinished", url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            Toast.makeText(getApplicationContext(), "서버와 연결이 끊어졌습니다", Toast.LENGTH_SHORT).show();
            view.loadUrl("about:blank");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBt:
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
