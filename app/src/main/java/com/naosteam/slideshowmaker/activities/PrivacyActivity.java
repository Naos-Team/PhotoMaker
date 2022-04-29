package com.naosteam.slideshowmaker.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.naosteam.slideshowmaker.R;
import com.naosteam.slideshowmaker.util.AdManager;
import com.xinlan.imageeditlibrary.editimage.view.imagezoom.easing.Linear;

public class PrivacyActivity extends Activity {

    LinearLayout ll_adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        LoadAds();

        WebView mWebView ;
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/privacy_policy.html");
    }

    private void LoadAds(){
        AdManager.initAd(PrivacyActivity.this);
        ll_adView = findViewById(R.id.ll_adView2);
        AdManager.loadAdmobBanner(this, ll_adView);
    }

    @Override
    public void onBackPressed() {
        AdManager.showAdmobInterAd(PrivacyActivity.this, new AdManager.InterAdsListener() {
            @Override
            public void onClick() {
                PrivacyActivity.super.onBackPressed();
            }
        });
    }
}
