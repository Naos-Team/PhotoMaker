package com.kessi.photovideomaker.activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.kessi.photovideomaker.KessiApplication;
import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.activities.kessiimagepicker.activity.ImagePickerActivity;
import com.kessi.photovideomaker.activities.myalbum.MyAlbumActivity;
import com.kessi.photovideomaker.activities.swap.SwapperActivity;
import com.kessi.photovideomaker.util.AdManager;
import com.kessi.photovideomaker.util.Animatee;
import com.kessi.photovideomaker.util.KSUtil;
import com.kessi.photovideomaker.util.Render;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String[] permissionsList = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    ImageView mainBg, icon, btnStart, btnAllVideo, rateIV, shareIV, privacyIV, moreIV;
    LinearLayout btnLay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainBg = findViewById(R.id.mainBg);
        Glide.with(this)
                .load(R.drawable.f_bg)
                .into(mainBg);
        icon = findViewById(R.id.icon);
        Glide.with(this)
                .load(R.drawable.sp_logo)
                .into(icon);
        btnLay = findViewById(R.id.btnLay);

            // Set Animation
        Render render = new Render(MainActivity.this);
        render.setAnimation(KSUtil.Bubble(icon));
        render.start();


        init();



    }

    void init() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        KessiApplication.VIDEO_HEIGHT = displaymetrics.widthPixels;
        KessiApplication.VIDEO_WIDTH = displaymetrics.widthPixels;


        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);

        btnAllVideo = findViewById(R.id.btnAllVideo);
        btnAllVideo.setOnClickListener(this);

        rateIV = findViewById(R.id.rateIV);
        rateIV.setOnClickListener(this);

        shareIV = findViewById(R.id.shareIV);
        shareIV.setOnClickListener(this);

        privacyIV = findViewById(R.id.privacyIV);
        privacyIV.setOnClickListener(this);

        moreIV = findViewById(R.id.moreIV);
        moreIV.setOnClickListener(this);

        FrameLayout nativeContainer = findViewById(R.id.nativeContainer);
        FrameLayout nativeContainerMAX = findViewById(R.id.nativeContainerMAX);
        if (!AdManager.isloadFbMAXAd) {
            //admob
            AdManager.initAd(MainActivity.this);
            AdManager.loadInterAd(MainActivity.this);
            AdManager.loadNativeAd(MainActivity.this, nativeContainer);
        } else {
            //MAX + Fb banner Ads
            AdManager.initMAX(MainActivity.this);
            AdManager.maxInterstital(MainActivity.this);
            AdManager.loadNativeMAX(MainActivity.this, nativeContainerMAX);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                KSUtil.Bounce(this, btnStart);
                new Handler().postDelayed(() -> {
                    if (!checkPermissions(MainActivity.this, permissionsList)) {
                        ActivityCompat.requestPermissions(MainActivity.this, permissionsList, 21);
                    } else {
                        KSUtil.fromAlbum = false;
                        Intent mIntent = new Intent(MainActivity.this, ImagePickerActivity.class);
                        mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MAX_IMAGE, 30);
                        mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MIN_IMAGE, 4);
                        startActivity(mIntent);
                        Animatee.animateSlideUp(MainActivity.this);
                    }
                },200);

                break;

            case R.id.btnAllVideo:
                KSUtil.Bounce(this, btnAllVideo);
                if (!checkPermissions(this, permissionsList)) {
                    ActivityCompat.requestPermissions(this, permissionsList, 22);
                } else {
                    KSUtil.fromAlbum = true;
                    AdManager.adCounter = AdManager.adDisplayCounter;
                    if (!AdManager.isloadFbMAXAd) {
                        AdManager.showInterAd(MainActivity.this,new Intent(MainActivity.this, MyAlbumActivity.class), 0);
                    } else {
                        AdManager.showMaxInterstitial(MainActivity.this,new Intent(MainActivity.this, MyAlbumActivity.class), 0);
                    }
                    Animatee.animateSlideUp(MainActivity.this);
                }
                break;

            case R.id.rateIV:
                KSUtil.Bounce(this, rateIV);
                rateUs();
                break;

            case R.id.shareIV:
                KSUtil.Bounce(this, shareIV);
                shareApp();
                break;

            case R.id.privacyIV:
                KSUtil.Bounce(this, privacyIV);
                startActivityes(new Intent(MainActivity.this, PrivacyActivity.class), 0);
                Animatee.animateSlideUp(MainActivity.this);
                break;

            case R.id.moreIV:
                KSUtil.Bounce(this, moreIV);
                moreApp();
                break;

            default:
                break;
        }
    }


    void startActivityes(Intent intent, int reqCode) {
        if (!AdManager.isloadFbMAXAd) {
            AdManager.adCounter++;
            AdManager.showInterAd(MainActivity.this, intent, reqCode);
        } else {
            AdManager.adCounter++;
            AdManager.showMaxInterstitial(MainActivity.this, intent, reqCode);
        }
    }

    public void moreApp() {
        startActivity(new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/dev?id=7081479513420377164&hl=en")));
    }

    public void shareApp() {
        String shareBody = "https://play.google.com/store/apps/details?id="
                + getApplicationContext().getPackageName();

        Intent sharingIntent = new Intent(
                Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent
                .putExtra(Intent.EXTRA_SUBJECT,
                        "(This app is for making beautiful video from photos. Open it in Google Play Store to Download the Application)");

        sharingIntent.putExtra(Intent.EXTRA_TEXT,
                shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public void rateUs() {
        Uri uri = Uri.parse("market://details?id="
                + getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
                | Intent.FLAG_ACTIVITY_NEW_DOCUMENT
                | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id="
                            + getApplicationContext().getPackageName())));
        }
    }


    public static boolean checkPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 21){
            if (checkPermissions(this, permissionsList)){
                KSUtil.fromAlbum = false;
                Intent mIntent = new Intent(MainActivity.this, ImagePickerActivity.class);
                mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MAX_IMAGE, 30);
                mIntent.putExtra(ImagePickerActivity.KEY_LIMIT_MIN_IMAGE, 4);
                Animatee.animateSlideUp(MainActivity.this);
                startActivity(mIntent);
            }
        }
        if(requestCode == 22){
            KSUtil.fromAlbum = true;
            startActivityes(new Intent(MainActivity.this, MyAlbumActivity.class), 0);
            Animatee.animateSlideUp(MainActivity.this);
        }
    }

}
